package com.akash.bigsale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.akash.bigsale.ProductList.ListItem;
import com.bumptech.glide.Glide;

import java.util.List;

public class GridAdapter extends BaseAdapter {

    Context context;
    List<ListItem> itemList;
    String nameOfProduct;
    public List<String> imagePath;

    GridAdapter(Context context, List<ListItem> itemList){
        this.context = context;
        this.itemList = itemList;
    }
    @Override
    public int getCount() {

        if(itemList.size() < 6)
            return itemList.size();
        else
            return 6;
    }

    @Override
    public Object getItem(int i) {
        return itemList;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

            if (view == null) {
                //Only creates new view when recycling isn't possible
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.main_page_grid, null);
            }

            ListItem listItem = itemList.get(i);
            imagePath = listItem.getImagePath();

            ImageView imageView = view.findViewById(R.id.ivGrid);
            TextView textView = view.findViewById(R.id.tvGrid);

            Glide.with(context).load(imagePath.get(0)).into(imageView);
            textView.setText(listItem.getName());

            return view;
    }

    public String getNameOfProduct() {
        return nameOfProduct;
    }
}
