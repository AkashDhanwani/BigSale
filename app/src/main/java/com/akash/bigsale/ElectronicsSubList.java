package com.akash.bigsale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.akash.bigsale.ProductList.ProductList;

import java.util.ArrayList;

public class ElectronicsSubList extends AppCompatActivity {

    private ListView listView;

    private ArrayList<String> subfolElectronics = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronics_sub_list);

        listView = findViewById(R.id.lvElectronics);

        //Adding to subcategory
        subfolElectronics.add("Cellphones"); subfolElectronics.add("Other Appliances");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, subfolElectronics);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ElectronicsSubList.this, ProductList.class);
                intent.putExtra("mainItem", "Electronics");
                intent.putExtra("subItem", listView.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });
    }
}
