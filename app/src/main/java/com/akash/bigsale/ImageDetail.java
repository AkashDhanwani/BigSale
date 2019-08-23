package com.akash.bigsale;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImageDetail extends AppCompatActivity {

    ArrayList<String> path = new ArrayList<>();
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        Bundle bundle = getIntent().getExtras();
        path = bundle.getStringArrayList("path");
        i = getIntent().getIntExtra("start", 0);

        ViewPager viewPager = findViewById(R.id.viewPager);
        ImagePageAdapter imagePageAdapter = new ImagePageAdapter();
        viewPager.setAdapter(imagePageAdapter);
    }

    private class ImagePageAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return path.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            Context context = ImageDetail.this;
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            //imageView.setImageURI(Uri.parse(path.get(i)));
            Glide.with(context).load(path.get(position)).into(imageView);
            container.addView(imageView, 0);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((ImageView)object);
        }
    }
}
