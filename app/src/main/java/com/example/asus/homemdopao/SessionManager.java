package com.example.asus.homemdopao;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.asus.homemdopao.Model.LoginModel;

/**
 * Created by Asus on 10/11/2016.
 */

public class SessionManager {
    SharedPreferences sharedPreferences;
    static Context mcontex;

    public SessionManager(Context context){
        mcontex = context;

        sharedPreferences = mcontex.getSharedPreferences("Userdetails",0);
    }

public void flagstartorstop(String date,String flag){
    SharedPreferences.Editor editor=sharedPreferences.edit();
    editor.putString("date",date);
    editor.putString("flag",flag);
    editor.commit();
}

    public  void save(LoginModel lm){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("id",lm.getId());
        editor.putString("UserZoneID",lm.getUserZoneID());
        editor.putString("UserPostalCodeID",lm.getUserPostalCodeID());
        editor.putString("UserTitle",lm.getUserTitle());
        editor.putString("UserFullName",lm.getUserFullName());
        editor.putString("UserGender",lm.getUserGender());
        editor.putString("UserDOB",lm.getUserDOB());
        editor.putString("UserProfileImage",lm.getUserProfileImage());
        editor.putString("UserAddress",lm.getUserAddress());
        editor.putString("UserMobileNo",lm.getUserMobileNo());
        editor.putString("UserDeliveryNote",lm.getUserDeliveryNote());
        editor.putString("username",lm.getUsername());
        editor.putString("password",lm.getPassword());
        editor.putString("email",lm.getEmail());
        editor.putString("activated",lm.getActivated());
        editor.putString("banned",lm.getBanned());
        editor.putString("ban_reason",lm.getBan_reason());
        editor.putString("new_password_key",lm.getNew_password_key());
        editor.putString("new_password_requested",lm.getNew_password_requested());
        editor.putString("new_email",lm.getNew_email());
        editor.putString("new_email_key",lm.getNew_email_key());
        editor.putString("last_ip",lm.getLast_ip());
        editor.putString("last_login",lm.getLast_login());
        editor.putString("usertype",lm.getUsertype());
        editor.putString("SubscriptionID",lm.getSubscriptionID());
        editor.putString("created",lm.getCreated());
        editor.putString("modified",lm.getModified());
        editor.putString("OrderDeliveryID",lm.getOrderDeliveryID());
        editor.commit();
    }

    public  String  getid(){
        return sharedPreferences.getString("id","");
    }

    public  String getOrderDeliveryID(){
        return sharedPreferences.getString("OrderDeliveryID","");
    }

    public  String userzoneid(){
        return  sharedPreferences.getString("UserZoneID","");
    }

    public String UserPostalCodeID(){
        return  sharedPreferences.getString("UserPostalCodeID","");
    }
    public  String  UserTitle(){
        return  sharedPreferences.getString("UserTitle","");
    }

    public  String UserFullName(){
        return  sharedPreferences.getString("UserFullName","");
    }

    public  String  UserGender(){
        return  sharedPreferences.getString("UserGender","");
    }

    public  String UserDOB(){
        return  sharedPreferences.getString("UserDOB","");
    }

    public  String UserProfileImage(){
        return  sharedPreferences.getString("UserProfileImage","");
    }

    public  String  UserAddress(){
        return  sharedPreferences.getString("UserAddress","");
    }

    public  String UserMobileNo(){
        return sharedPreferences.getString("UserMobileNo","");
    }

    public String UserDeliveryNote(){
        return  sharedPreferences.getString("UserDeliveryNote","");
    }

    public String  email(){
        return  sharedPreferences.getString("email","");
    }

    public  String password(){
        return  sharedPreferences.getString("password","");
    }


    public  String usename(){
        return  sharedPreferences.getString("username","");
    }

    public  String activated(){
        return  sharedPreferences.getString("activated","");
    }

    public String banned(){
        return  sharedPreferences.getString("banned","");
    }

    public  String  ban_reason(){
        return  sharedPreferences.getString("ban_reason","");
    }
    public  String new_password_key(){
        return  sharedPreferences.getString("new_password_key","");
    }

    public  String new_password_requested(){
        return  sharedPreferences.getString("new_password_requested","");
    }

    public  String new_email(){
        return sharedPreferences.getString("new_email","");
    }

    public  String new_email_key(){
        return  sharedPreferences.getString("new_email_key","");
    }

    public String last_ip(){
        return  sharedPreferences.getString("last_ip","");
    }

    public  String  last_login(){
        return  sharedPreferences.getString("last_login","");
    }

    public  String usertype(){
        return  sharedPreferences.getString("usertype","");
    }

    public  String SubscriptionID(){
        return  sharedPreferences.getString("SubscriptionID","");
    }

    public  String created(){
        return  sharedPreferences.getString("created","");
    }

    public  String modified(){
        return  sharedPreferences.getString("modified","");
    }
    public  String getDate(){
        return  sharedPreferences.getString("date","");
    }

    public  String getFlag(){
        return  sharedPreferences.getString("flag","");
    }

//    public  void logout(){
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear();
//        editor.commit();
//    }

    public void logout(){
        SharedPreferences pref = mcontex.getSharedPreferences("Userdetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }
}
