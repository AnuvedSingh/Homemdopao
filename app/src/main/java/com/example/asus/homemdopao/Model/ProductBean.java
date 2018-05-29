package com.example.asus.homemdopao.Model;

import java.util.ArrayList;

/**
 * Created by Asus on 03/11/2016.
 */

public class ProductBean {
    String ProductID,ProductName,ItemQty,ProductImage,OrderId;
//         int ProductImage ;


 /*   public ArrayList<TodaysdeliveryBean> getTodaysdeliveryBeanArrayList() {
        return todaysdeliveryBeanArrayList;
    }

    public void setDonatelayout(ArrayList<TodaysdeliveryBean> todaysdeliveryBeanArrayList) {
        todaysdeliveryBeanArrayList = todaysdeliveryBeanArrayList;
    }

    ArrayList<TodaysdeliveryBean> todaysdeliveryBeanArrayList;
*/

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    //    public int getProductImage() {
//        return ProductImage;
//    }
//
//    public void setProductImage(int productImage) {
//        ProductImage = productImage;
//    }
    String product;


    public String getItemQty() {
        return ItemQty;
    }

    public void setItemQty(String itemQty) {
        ItemQty = itemQty;
    }

    public ProductBean(String productID, String productName, String itemQty, String productImage,String orderId) {
        ProductID = productID;
        ProductName = productName;
        ItemQty = itemQty;
        ProductImage = productImage;
        OrderId=orderId;
    }
}
