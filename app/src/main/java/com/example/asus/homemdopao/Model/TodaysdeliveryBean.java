package com.example.asus.homemdopao.Model;

import java.util.ArrayList;

/**
 * Created by Asus on 03/11/2016.
 */

public class TodaysdeliveryBean {
    String OrderID,OrderUserID,DliveryBoyID,OrderDeliveryDate,OrderDeliveryID,users_id,UserFullName,email,UserMobileNo,
           OrderDeliveryStatus,UserAddress,UserAddressPostal;
//         int ProductImage ;


    public String getUserAddress() {
        return UserAddress;
    }

    public void setUserAddress(String userAddress) {
        UserAddress = userAddress;
    }

    public String getUserAddressPostal() {
        return UserAddressPostal;
    }
    public String getFixedpalnproduct() {
        return Fixedpalnproduct;
    }

    public void setFixedpalnproduct(String fixedpalnproduct) {
        Fixedpalnproduct = fixedpalnproduct;
    }

    String Fixedpalnproduct;
    public void setUserAddressPostal(String userAddressPostal) {
        UserAddressPostal = userAddressPostal;
    }

    public ArrayList<ProductBean> getProductlist() {
        return productlist;
    }

    public void setProductlist(ArrayList<ProductBean> productlist) {
        this.productlist = productlist;
    }

    ArrayList<ProductBean> productlist;

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

    public String getOrderDeliveryID() {
        return OrderDeliveryID;
    }

    public void setOrderDeliveryID(String orderDeliveryID) {
        OrderDeliveryID = orderDeliveryID;
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

    public String getOrderDeliveryStatus() {
        return OrderDeliveryStatus;
    }

    public void setOrderDeliveryStatus(String orderDeliveryStatus) {
        OrderDeliveryStatus = orderDeliveryStatus;
    }

            public  TodaysdeliveryBean(String OrderID,String OrderUserID, String DliveryBoyID,String OrderDeliveryDate,String OrderDeliveryID,String users_id
            ,String UserFullName,String email,String UserMobileNo,String OrderDeliveryStatus,
                                       String UserAddress,String UserAddressPostal,ArrayList<ProductBean> productBeen)
            {
                this.OrderID=OrderID;
                this.OrderUserID=OrderUserID;
                this.DliveryBoyID=DliveryBoyID;
                this.OrderDeliveryDate=OrderDeliveryDate;
                this.OrderDeliveryID = OrderDeliveryID;
                this.users_id=users_id;
                this.UserFullName=UserFullName;
                this.email=email;
                this.UserMobileNo=UserMobileNo;

                this.OrderDeliveryStatus=OrderDeliveryStatus;
                this.UserAddress=UserAddress;
                this.UserAddressPostal=UserAddressPostal;
                this.productlist=productBeen;

            }


}
