package com.akash.bigsale;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;

public class ProductInfo extends AppCompatActivity {

    private FlipperLayout flipperLayout;
    private TextView tvProductName, tvPriceInfo, tvDescInfo, tvSizeInfo, tvTypeName;
    private Button btnPlaceOrder;
    private EditText userQuantity;

    private ArrayList<String> path;
    private String Name, Desc, Price, Size, TypeName;

    private int num_of_pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        int orientaton = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(orientaton);

        if(!isConnectedToInternet(ProductInfo.this)) {
            showSnackBar("No Internet Connection", (LinearLayout) findViewById(R.id.llproductInfo));
        }
        else mainMethod();
    }

    public void mainMethod(){

        flipperLayout = findViewById(R.id.flipper_layout);
        tvProductName = findViewById(R.id.tvProductName);
        tvDescInfo = findViewById(R.id.tvDescInfo);
        tvPriceInfo = findViewById(R.id.tvPriceInfo);
        tvSizeInfo = findViewById(R.id.tvSizeInfo);
        tvTypeName = findViewById(R.id.tvTypeName);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        userQuantity = findViewById(R.id.userQuantity);

        final Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            Name = bundle.getString("namepro");
            Price = "\u20B9 " + bundle.getString("price");
            Desc = bundle.getString("desc");
            Size = bundle.getString("size");
            TypeName = bundle.getString("typeName");
            path = bundle.getStringArrayList("imagePath");
        }
        if (path != null) {
            num_of_pages = path.size();
        }

        Objects.requireNonNull(getSupportActionBar()).setTitle(Name);

        tvProductName.setText(Name);
        tvPriceInfo.setText(Price);
        tvDescInfo.setText(Desc);
        tvSizeInfo.setText(Size);
        tvTypeName.setText(TypeName);

        final Bundle bundle1 = new Bundle();
        bundle1.putStringArrayList("path", path);

        if (path != null)
            for (int i = 0; i < num_of_pages; i++) {
                final int j = i;
                FlipperView view = new FlipperView(getBaseContext());
                view.setImageUrl(path.get(i))
                        .setImageScaleType(ImageView.ScaleType.CENTER_INSIDE) //You can use any ScaleType
                        .setDescription(getString(R.string.app_name))
                        .setOnFlipperClickListener(new FlipperView.OnFlipperClickListener() {
                            @Override
                            public void onFlipperClick(FlipperView flipperView) {
                                Intent intent = new Intent(ProductInfo.this, ImageDetail.class);
                                intent.putExtras(bundle1);
                                intent.putExtra("start", j);
                                startActivity(intent);
                            }
                        });
                // flipperLayout.setScrollTimeInSec(3); //setting up scroll time, by default it's 3 seconds
                //flipperLayout.getScrollTimeInSec(); //returns the scroll time in sec
                //flipperLayout.getCurrentPagePosition(); //returns the current position of pager
                flipperLayout.addFlipperView(view);
            }

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String quantity;

                if (userQuantity.getText().toString().length() == 0){
                    userQuantity.setError("Mention quantity");
                    userQuantity.requestFocus();
                    return;
                }
                else
                    quantity = userQuantity.getText().toString();

                String productInfo = "Product Name: "+Name+"\n"+
                        "Priced at: "+Price+"\n"+
                        TypeName+": "+Size+"\n"+
                        "Quantity: "+quantity;

               // Toast.makeText(ProductInfo.this, ""+productInfo, Toast.LENGTH_SHORT).show();
                Intent buyIntent = new Intent(ProductInfo.this, PlaceOrder.class);
                buyIntent.putExtra("review", productInfo);
                buyIntent.putExtra("price", bundle.getString("price"));
                buyIntent.putExtra("quantity", quantity);
                startActivity(buyIntent);
            }
        });
    }

    public void showSnackBar(String string, LinearLayout linearLayout)
    {
        Snackbar.make(linearLayout, string, Snackbar.LENGTH_INDEFINITE).
                setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!isConnectedToInternet(ProductInfo.this)){
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.call_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle your other action bar items...

        if(item.getItemId() == R.id.call){
            String number = "918605982854";
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" +number));
            startActivity(Intent.createChooser(intent, "Call Ganesh through..."));
        }
        return super.onOptionsItemSelected(item);
    }
}
