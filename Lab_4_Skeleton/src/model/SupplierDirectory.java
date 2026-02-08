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
public class SupplierDirectory {
    
    private ArrayList<Supplier> supplierList;
    
    public SupplierDirectory() {
        supplierList = new ArrayList<Supplier>();
    }

    public ArrayList<Supplier> getSupplierList() {
        return supplierList;
    }
    
    public Supplier addSupplier() {
        Supplier s = new Supplier();
        supplierList.add(s);
        return s;
    }
    
    public void removeSupplier(Supplier s) {
        supplierList.remove(s);
    }
    
    public Supplier searchSupplier(String keyWord) {
        if (keyWord == null) return null;
        String k = keyWord.trim();
        for (Supplier s : supplierList) {
            if (s.getSupplyName() != null && s.getSupplyName().trim().equalsIgnoreCase(k)) {
                return s;
            }
        }
        return null;
    }
    
}
