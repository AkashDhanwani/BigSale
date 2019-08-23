package com.akash.bigsale;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.akash.bigsale.ProductList.ProductList;

public abstract class AppBaseActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {
    private FrameLayout view_stub; //This is the framelayout to keep your content view
    private NavigationView navigation_view; // The new navigation view from Android Design Library. Can inflate menu resources. Easy
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Menu drawerMenu;
    private View headerLayout;
    Button btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.app_base_layout);// The base layout that contains your navigation drawer.
        view_stub =  findViewById(R.id.view_stub);
        navigation_view =  findViewById(R.id.navigation_view);
        headerLayout = navigation_view.getHeaderView(0); // 0-index header

        navigation_view.setItemIconTintList(null);

        btnHome = headerLayout.findViewById(R.id.homebtn);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        mDrawerLayout =  findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 0, 0);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerMenu = navigation_view.getMenu();
        for(int i = 0; i < drawerMenu.size(); i++) {
            drawerMenu.getItem(i).setOnMenuItemClickListener(this);
        }
        // and so on...
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /* Override all setContentView methods to put the content view to the FrameLayout view_stub
     * so that, we can make other activity implementations looks like normal activity subclasses.
     */
    @Override
    public void setContentView(int layoutResID) {
        if (view_stub != null) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            View stubView = inflater.inflate(layoutResID, view_stub, false);
            view_stub.addView(stubView, lp);
        }
    }

    @Override
    public void setContentView(View view) {
        if (view_stub != null) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            view_stub.addView(view, lp);
        }
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        if (view_stub != null) {
            view_stub.addView(view, params);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.call_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        if(item.getItemId() == R.id.call){
            String number = "918605982854";
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" +number));
            startActivity(Intent.createChooser(intent, "Call Ganesh through..."));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        Intent intent;

        if (id == R.id.garments){
            intent = new Intent(getApplicationContext(), ProductList.class);
            intent.putExtra("mainItem", "Garments");
            intent.putExtra("subItem", "None");
            startActivity(intent);
        }
        if (id == R.id.electronics) {
            intent = new Intent(getApplicationContext(), ElectronicsSubList.class);
            startActivity(intent);
        }
        if (id == R.id.furniture) {
            intent = new Intent(getApplicationContext(), ProductList.class);
            intent.putExtra("mainItem", "Furniture");
            intent.putExtra("subItem", "None");
            startActivity(intent);
        }
        if (id == R.id.accessories) {
            intent = new Intent(getApplicationContext(), ProductList.class);
            intent.putExtra("mainItem", "Accessories");
            intent.putExtra("subItem", "None");
            startActivity(intent);
        }

        if (id == R.id.cosmetics) {
            intent = new Intent(getApplicationContext(), ProductList.class);
            intent.putExtra("mainItem", "Cosmetics");
            intent.putExtra("subItem", "None");
            startActivity(intent);
        }
        if(id == R.id.about){
            intent = new Intent(getApplicationContext(), About.class);
            startActivity(intent);
        }
        if(id == R.id.share){
            String subject = "Hey there have a look at this great App";
            String body = "This App will show you details of all my products with offers you can't refuse" +
                    "\nhttps://play.google.com/store/apps/details?id=com.akash.bigsale";
            intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(intent,"Share App via"));
        }
        return true;
    }
}
