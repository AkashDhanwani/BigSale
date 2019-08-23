package com.akash.bigsale;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;

public class PlaceOrder extends AppCompatActivity {

    TextView tvOrder, tvCost;
    Button btnWhatsApp, btnGmail, btnMessage, btnContinue, btnBack;

    String message = "Hey Ganesh, I want to buy the following product\n ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        getSupportActionBar().setTitle("Place Order");

        tvOrder = findViewById(R.id.tvOrder);
        tvCost = findViewById(R.id.tvCost);
        btnWhatsApp = findViewById(R.id.btnWhatsAppPlaceOrder);
        btnGmail = findViewById(R.id.btnGmailPlaceOrder);
        btnMessage = findViewById(R.id.btnMessagePlaceOrder);
        btnBack = findViewById(R.id.btnBack);
        btnContinue = findViewById(R.id.btnContinue);

        Intent intent = getIntent();

        double price = Double.parseDouble(intent.getStringExtra("price"));
        double quantity = Double.parseDouble(intent.getStringExtra("quantity"));
        double cost = price*quantity;
        String costDisplay = getString(R.string.totalCost)+" "+cost;
        tvCost.setText(costDisplay);

        final String order = intent.getStringExtra("review");
        tvOrder.setText(order);

        btnWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    PackageManager packageManager = getApplicationContext().getPackageManager();
                    Intent i = new Intent(Intent.ACTION_VIEW);

                    String url = "https://api.whatsapp.com/send?phone="+ "918605982854" +"&text=" + URLEncoder.encode(message+order, "UTF-8");
                    i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));
                    if (i.resolveActivity(packageManager) != null) {
                        getApplicationContext().startActivity(i);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(PlaceOrder.this, "WhatsApp is not Installed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Toast.makeText(PlaceOrder.this, "Select the corresponding Mail app", Toast.LENGTH_SHORT).show();
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
//                emailIntent.setData(Uri.parse("nitinkamra88.nk@gmail.com"));
//                emailIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "bigsale.ganesh@gmail.com" });
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Mail for purchasing product");
                emailIntent.putExtra(Intent.EXTRA_TEXT, message+order);

                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        });

        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"+"8605982854"));
                intent.putExtra("sms_body", message+order);
                startActivity(intent);
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
