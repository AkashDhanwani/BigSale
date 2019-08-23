package com.akash.bigsale.ProductList;

import java.util.List;

/**
 * Created by AKASH on 3/12/2018.
 */

public class ListItem {

    public String name;
    public String description;
    public String price;
    public String size;
    public String typeName;
    public List<String> imagePath;

    public ListItem() {

    }

    public ListItem(String name, String description, String price, String size, String typeName,
                    List<String> imagePath)  {
        this.name = name;
        this.description = description;
        this.price = price;
        this.size = size;
        this.typeName = typeName;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getSize() {
        return size;
    }

    public String getTypeName() {
        return typeName;
    }

    public List<String> getImagePath() {
        return imagePath;
    }
}
