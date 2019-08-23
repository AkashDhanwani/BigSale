package com.akash.bigsale;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akash.bigsale.ProductList.ListItem;
import com.akash.bigsale.ProductList.ProductList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;

public class MainActivity extends AppBaseActivity {

    private GridView gvMain;
    private Button btnView, btnRate;
    private FrameLayout frameLayout;
    private FlipperLayout flipperLayout;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private ProgressDialog progressDialog;
    private List<ListItem> itemList = new ArrayList<>();
    private ArrayList<String> path = new ArrayList<>();
    private ArrayList<String> mainfol = new ArrayList<>();
    private ArrayList<String> subfolElectronics = new ArrayList<>();

    private int num_of_pages = 3;
    private String mainItem, subItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int orientaton = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(orientaton);

        gvMain = findViewById(R.id.gvMain);
        frameLayout = findViewById(R.id.flForGrid);

        //Adding to mainFol
        mainfol.add("Garments"); mainfol.add("Electronics"); mainfol.add("Furniture"); mainfol.add("Cosmetics");
        mainfol.add("Accessories");

        //Adding to subcategory
        subfolElectronics.add("Cellphones"); subfolElectronics.add("Other Appliances");

        if(!isConnectedToInternet(MainActivity.this)){
            showSnackBar("No Internet Connection", (LinearLayout) findViewById(R.id.llmainActivity));
        }

        else {
            mainMethod();
        }
    }

    public void mainMethod(){

        flipperLayout = findViewById(R.id.flipper_layout);

        int[] posters = {R.drawable.poster_1, R.drawable.poster_2, R.drawable.poster_3};

        for(int i=0 ; i<posters.length ; i++){
            FlipperView flipperView = new FlipperView(getBaseContext());
                flipperView.setImageDrawable(posters[i])
                        .setImageScaleType(ImageView.ScaleType.CENTER_INSIDE); //You can use any ScaleType
            flipperLayout.addFlipperView(flipperView);
        }

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        // progressDialog.setCancelable(false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        int selectMain = (int) (Math.random()*mainfol.size());
        int selectSubElec = (int) (Math.random()*subfolElectronics.size());

        mainItem = mainfol.get(selectMain);

        if(mainItem.equals(mainfol.get(1))){
            subItem = subfolElectronics.get(selectSubElec);
            databaseReference = firebaseDatabase.getReference(mainItem).child(subItem);
        }else {
            subItem = "None";
            databaseReference = firebaseDatabase.getReference(mainItem);
        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        //dismissing the progress dialog
                        progressDialog.dismiss();

                        ListItem listItem = postSnapshot.getValue(ListItem.class);
                        itemList.add(listItem);
                        //adapter.notifyDataSetChanged();
                    }
                    gvMain.setAdapter(new GridAdapter(MainActivity.this, itemList));
                }else{
                    //dismissing the progress dialog
                    progressDialog.dismiss();
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT
                    , FrameLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(40, 100, 40, 0);

                    TextView textView = new TextView(MainActivity.this);
                    textView.setTextColor(getResources().getColor(R.color.white));
                    textView.setTextSize(15);
                    textView.setLayoutParams(params);
                    textView.setText(getString(R.string.noFeatureProduct));

                    frameLayout.removeView(gvMain);
                    frameLayout.addView(textView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.toException().printStackTrace();
            }
        });

        gvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ProductList.class);
                intent.putExtra("mainItem", mainItem);
                intent.putExtra("subItem", subItem);
                startActivity(intent);
            }
        });
    }

    public void showSnackBar(String string, LinearLayout linearLayout)
    {
        Snackbar.make(linearLayout, string, Snackbar.LENGTH_INDEFINITE).
                setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!isConnectedToInternet(MainActivity.this)){
                            showSnackBar("No Internet Connection",(LinearLayout) findViewById(R.id.llmainActivity));
                        }
                        else mainMethod();
                    }
                }).show();
    }

    private boolean isConnectedToInternet(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setNeutralButton("Rate Us", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.akash.bigsale");
                        Intent intent= new Intent(Intent.ACTION_VIEW,uri);
                        startActivity(intent);
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
