/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;

/**
 *
 * @author Rushabh
 */
public class Product {
    
    private String name;
    private int price;
    private int id;
    
    private ArrayList<Feature> featureList;

    private static int count = 0;
    
    public Product() {
        count++;
        id = count;
        featureList = new ArrayList<>();
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
    
    
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public ArrayList<Feature> getFeatureList() { return featureList; }

    public Feature addFeature(String name, String value) {
        Feature f = new Feature(name, value);
        featureList.add(f);
        return f;
    }

    public void removeFeature(Feature f) {
        featureList.remove(f);
    }

    
    @Override
    public String toString() {
        return name;
    }
    
}
