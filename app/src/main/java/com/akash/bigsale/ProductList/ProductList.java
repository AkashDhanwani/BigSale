package com.akash.bigsale.ProductList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akash.bigsale.AppBaseActivity;
import com.akash.bigsale.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductList extends AppBaseActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private ProgressDialog progressDialog;
    private List<ListItem> itemList;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        int orientaton = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(orientaton);

        recyclerView = findViewById(R.id.rvProductList);

        if(!isConnectedToInternet(ProductList.this)){
            showSnackBar("No Internet Connection", (LinearLayout) findViewById(R.id.llproductList));
        }

        else {
            mainMethod();
        }
    }//end of OnCreate

    protected void mainMethod(){
        progressDialog = new ProgressDialog(ProductList.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        String mainItem = intent.getStringExtra("mainItem");
        String subItem = intent.getStringExtra("subItem");

        if (subItem.equals("None")){
            getSupportActionBar().setTitle(mainItem);
            databaseReference = firebaseDatabase.getReference(mainItem);
        }
        else {
            getSupportActionBar().setTitle(subItem);
            databaseReference = firebaseDatabase.getReference(mainItem).child(subItem);
        }
        // databaseReference = firebaseDatabase.getReference("Electronics").child("Fridges");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        //dismissing the progress dialog
                        progressDialog.dismiss();

                        ListItem listItem = postSnapshot.getValue(ListItem.class);
                        itemList.add(listItem);
                        adapter.notifyDataSetChanged();
                    }
                }else{
                    //dismissing the progress dialog
                    progressDialog.dismiss();

                    LinearLayout linearLayout = new LinearLayout(ProductList.this);
                    setContentView(linearLayout);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    // linearLayout.setGravity(LinearLayout.);

                    TextView textView = new TextView(ProductList.this);
                    textView.setText(getString(R.string.noProduct));
                    //textView.setGravity(TextView.TEXT_ALIGNMENT_CENTER);
                    linearLayout.addView(textView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.toException().printStackTrace();
            }
        });
        adapter = new Adapter(ProductList.this, itemList);

        recyclerView.setAdapter(adapter);
    }
    public void showSnackBar(String string, LinearLayout linearLayout)
    {
        Snackbar.make(linearLayout, string, Snackbar.LENGTH_INDEFINITE).
                setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!isConnectedToInternet(ProductList.this)){
                            showSnackBar("No Internet Connection",(LinearLayout) findViewById(R.id.llproductList));
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
}
