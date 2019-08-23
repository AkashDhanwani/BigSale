package com.akash.bigsale;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;

public class About extends AppCompatActivity {

    private Button btnWasp, btnGmail, btnMessage;
    private TextView tvWasp, tvGmail, tvMessage, tvAkash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        int orientaton = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(orientaton);

        btnWasp = findViewById(R.id.btnWhatsAppAbout);
        btnGmail = findViewById(R.id.btnGmailAbout);
        btnMessage = findViewById(R.id.btnMessageAbout);

        tvAkash = findViewById(R.id.tvAkash);
        tvGmail = findViewById(R.id.tvMailAbout);
        tvMessage = findViewById(R.id.tvMessageAbout);
        tvWasp = findViewById(R.id.tvWhatsAppAbout);

        btnWasp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wasp();
            }
        });
        tvWasp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wasp();
            }
        });

        btnGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gmail();
            }
        });
        tvGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gmail();
            }
        });

        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message();
            }
        });
        tvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message();
            }
        });

        tvAkash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Uri uri = Uri.parse("https://www.linkedin.com/in/akash-dhanwani-235218145/");
//                Intent intent= new Intent(Intent.ACTION_VIEW,uri);
//                startActivity(intent);
                CustomDialog customDialog = new CustomDialog(About.this);
                customDialog.show();
            }
        });
    }
    public void wasp(){
        try {
            PackageManager packageManager = getApplicationContext().getPackageManager();
            Intent i = new Intent(Intent.ACTION_VIEW);

            String url = "https://api.whatsapp.com/send?phone="+ "918605982854" +"&text=" + URLEncoder.encode("", "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                getApplicationContext().startActivity(i);
            }
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(About.this, "WhatsApp is not Installed", Toast.LENGTH_SHORT).show();
        }
    }

    public void gmail(){
        Toast.makeText(About.this, "Select the corresponding Mail app", Toast.LENGTH_SHORT).show();
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
//                emailIntent.setData(Uri.parse("nitinkamra88.nk@gmail.com"));
//                emailIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "bigsale.ganesh@gmail.com" });
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "A Query");

        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    public void message(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"+"8605982854"));
        startActivity(intent);
    }
}
