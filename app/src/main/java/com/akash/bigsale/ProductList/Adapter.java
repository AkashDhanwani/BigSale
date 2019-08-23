package com.akash.bigsale.ProductList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.akash.bigsale.ProductInfo;
import com.akash.bigsale.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context context;
    private List<ListItem> itemList;

    public Adapter(Context context, List<ListItem> itemList){
        this.context = context;
        this.itemList = itemList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        ListItem listItem = itemList.get(i);

        setFadeAnimation(holder.itemView);

        holder.imagePath = listItem.getImagePath();

        if(holder.imagePath != null)
            Glide.with(context).load(holder.imagePath.get(0)).into(holder.imageView);
        holder.textView.setText(listItem.getName());
        holder.tvPrice.setText("\u20B9 "+listItem.getPrice());

        holder.size = listItem.getSize();
        holder.nameproduct = listItem.getName();
        holder.price = listItem.getPrice();
        holder.desc = listItem.getDescription();
        holder.typeName = listItem.getTypeName();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setFadeAnimation(View view){
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(500);
        view.startAnimation(animation);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public String nameproduct, price, desc, size, typeName;
        public List<String> imagePath;

        public ImageView imageView;
        public TextView textView, tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.ivcard);
            textView = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("imagePath", (ArrayList<String>) imagePath);
                    bundle.putString("size", size);
                    bundle.putString("namepro", nameproduct);
                    bundle.putString("price", price);
                    bundle.putString("desc", desc);
                    bundle.putString("typeName", typeName);
                    Intent intent = new Intent(context, ProductInfo.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }
}


