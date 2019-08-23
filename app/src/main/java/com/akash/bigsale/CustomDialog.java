package com.akash.bigsale;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class CustomDialog extends Dialog implements View.OnClickListener {

    public Activity activity;
    public Dialog dialog;
    public Button btnGmail, btnLinkedIn;

    CustomDialog(Activity a){
        super(a);
        this.activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_aboutme);
        btnGmail = findViewById(R.id.btnGmailAboutMe);
        btnLinkedIn = findViewById(R.id.btnLinkedIn);

        btnGmail.setOnClickListener(this);
        btnLinkedIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGmailAboutMe:
                Toast.makeText(activity, "Select the corresponding Mail app", Toast.LENGTH_SHORT).show();
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
//                emailIntent.setData(Uri.parse("nitinkamra88.nk@gmail.com"));
//                emailIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "akash.developer11@gmail.com" });
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "A Query");

                activity.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                break;
            case R.id.btnLinkedIn:
                Uri uri = Uri.parse("https://www.linkedin.com/in/akash-dhanwani-235218145/");
                Intent intent= new Intent(Intent.ACTION_VIEW,uri);
                activity.startActivity(intent);
                break;
                default:
                    break;
        }
    }
}
