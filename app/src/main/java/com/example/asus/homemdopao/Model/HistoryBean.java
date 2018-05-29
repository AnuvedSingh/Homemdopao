package com.example.asus.homemdopao.Model;

import java.util.ArrayList;

/**
 * Created by Asus on 16/11/2016.
 */

public class HistoryBean {
    String OrderID,OrderUserID,DliveryBoyID,OrderDeliveryDate,users_id,UserFullName,email,UserMobileNo,
            ProductID,ProductName,ItemQty,OrderDeliveryStatus,ProductImage,comment;
String items;

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getOrderUserID() {
        return OrderUserID;
    }

    public void setOrderUserID(String orderUserID) {
        OrderUserID = orderUserID;
    }

    public String getDliveryBoyID() {
        return DliveryBoyID;
    }

    public void setDliveryBoyID(String dliveryBoyID) {
        DliveryBoyID = dliveryBoyID;
    }

    public String getOrderDeliveryDate() {
        return OrderDeliveryDate;
    }

    public void setOrderDeliveryDate(String orderDeliveryDate) {
        OrderDeliveryDate = orderDeliveryDate;
    }

    public String getUsers_id() {
        return users_id;
    }

    public void setUsers_id(String users_id) {
        this.users_id = users_id;
    }

    public String getUserFullName() {
        return UserFullName;
    }

    public void setUserFullName(String userFullName) {
        UserFullName = userFullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserMobileNo() {
        return UserMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        UserMobileNo = userMobileNo;
    }

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

    public String getItemQty() {
        return ItemQty;
    }

    public void setItemQty(String itemQty) {
        ItemQty = itemQty;
    }

    public String getOrderDeliveryStatus() {
        return OrderDeliveryStatus;
    }

    public void setOrderDeliveryStatus(String orderDeliveryStatus) {
        OrderDeliveryStatus = orderDeliveryStatus;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }
    public ArrayList<ProductBean> getProductlist() {
        return productlist;
    }

    public void setProductlist(ArrayList<ProductBean> productlist) {
        this.productlist = productlist;
    }

    ArrayList<ProductBean> productlist;
    public  HistoryBean(String OrderID,String OrderUserID, String DliveryBoyID,String OrderDeliveryDate,String users_id
            ,String UserFullName,String email,String UserMobileNo,String OrderDeliveryStatus,String comment,ArrayList<ProductBean>productlist,String items)
    {
        this.OrderID=OrderID;
        this.OrderUserID=OrderUserID;
        this.DliveryBoyID=DliveryBoyID;
        this.OrderDeliveryDate=OrderDeliveryDate;
        this.users_id=users_id;
        this.UserFullName=UserFullName;
        this.email=email;
        this.UserMobileNo=UserMobileNo;
        this.OrderDeliveryStatus=OrderDeliveryStatus;
        this.comment=comment;
        this.productlist=productlist;
        this.items=items;


    }
}
