package com.example.asus.homemdopao.Server;

public class Webservice {
    public static String Url = "http://www.homemdopao.pt/webservice/";
    public static String login = Url + "deliveryboylogin";
    public static String changepassword = Url + "deliveryboychangepassword";
    public static String Today_delivery = Url + "get_deliveryboyorder";
    public static String History = Url + "get_deliveryboycompletedorder";
    public static String Location = Url + "deliveryboylocation";
    public static String Status = Url + "orderstatuschange";
    public static String StatusProcess = Url + "getstatusupdate";
   // http://l22.co.in/homemdopao/webservice/getstatusupdate?OrderDliveryBoyID=159&&OrderDeliveryStatus=1&&OrderDeliveryDate=2017-01-25
}
