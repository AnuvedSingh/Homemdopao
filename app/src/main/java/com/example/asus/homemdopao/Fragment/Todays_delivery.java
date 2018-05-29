package com.example.asus.homemdopao.Fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.asus.homemdopao.Activity.DashBoardActivity;
import com.example.asus.homemdopao.Activity.Details;
import com.example.asus.homemdopao.Adapter.TodaysdeliveryAdapter;
import com.example.asus.homemdopao.Commons;
import com.example.asus.homemdopao.Model.ProductBean;
import com.example.asus.homemdopao.Model.TodaysdeliveryBean;
import com.example.asus.homemdopao.R;
import com.example.asus.homemdopao.Server.InternetConnection;
import com.example.asus.homemdopao.Server.Webservice;
import com.example.asus.homemdopao.SessionManager;
import com.example.asus.homemdopao.UtilityMethod;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.asus.homemdopao.Activity.DashBoardActivity.mDrawerLayout;

/**
 * Created by Asus on 02/11/2016.
 */

public class Todays_delivery extends Fragment {
    public static FragmentTransaction ft1;
    static public FragmentManager fm;
    Fragment frag;
    static Context mContext;
    Context context;
    static FragmentManager mfragmentmanager;
    static Fragment mfFragment;
    ListView listView;
    TextView Heading_tx;
    ImageView menuIcon, headericon;
    SessionManager sm;
    public static String id, Date;
    ArrayList<TodaysdeliveryBean> todaysdeliveryBeen;
    ArrayList<ProductBean> productBeen;
    public static ArrayList<ProductBean> productBeen1;
    public static TodaysdeliveryAdapter todaysdeliveryAdapter;
    ProgressDialog progress;
    String formattedDate, date;
    SwipeRefreshLayout mSwipeRefreshLayout;
    public static int selectindex;
    TextView start_tv;
    Dialog dialog;
    public boolean isClickedFirstTime = true;

    public static Fragment getInstance(Context ct, FragmentManager fm) {
        mContext = ct;
        mfragmentmanager = fm;
        mfFragment = new Todays_delivery();

        return mfFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_todaydelivery, container, false);
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c.getTime());
        //Toast.makeText(getActivity(),"date"+formattedDate,Toast.LENGTH_SHORT).show();

        DashBoardActivity.heading_txt.setVisibility(View.GONE);
        sm = new SessionManager(getActivity());
        id = sm.getid();

        context = getActivity();
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.activity_main_swipe_refresh_layout);
        listView = (ListView) view.findViewById(R.id.todaylist);

        start_tv = (TextView) view.findViewById(R.id.start_delivery);
        headericon = (ImageView) view.findViewById(R.id.headericon);
        DashBoardActivity.back_icon.setVisibility(View.GONE);
        DashBoardActivity.menu_icon.setVisibility(View.VISIBLE);

        if(UtilityMethod.isStringNullOrBlank(sm.getDate())&& UtilityMethod.isStringNullOrBlank(sm.getFlag())){
            start_tv.setText("Iniciar");
        }
        else {
            if(sm.getDate().equals(formattedDate)){
                if(sm.getFlag().equals("1"))
                {
                    start_tv.setText("Parar");
                }
                else
                {
                    start_tv.setText("Iniciar");
                }
            }
            else
            {
                start_tv.setText("Parar");
            }
        }
        start_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UtilityMethod.isStringNullOrBlank(sm.getDate())&& UtilityMethod.isStringNullOrBlank(sm.getFlag())){
                    ProcessStatus("1");
                }
                else {
                    if(sm.getFlag().equals("1")){
                        ProcessStatus("0");
                    }
                    else {
                        ProcessStatus("1");
                    }
                }

               /* if(isClickedFirstTime)
                {
                    alertConfirmation(getActivity(),"Are You Sure You Want To START Deliveries");
                    isClickedFirstTime=false;
                }
                else
                {
                    alertConfirmation(getActivity(),"Are You Sure You Want To STOP Deliveries");
                    isClickedFirstTime=true;
                }*/

            }
        });


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                todaysdeliveryAdapter.notifyDataSetChanged();
                listView.setAdapter(todaysdeliveryAdapter);
                mSwipeRefreshLayout.setRefreshing(false);

            }
        });

        orderlist();
        return view;

    }
   /* private void refreshContent(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                todaysdeliveryAdapter.notifyDataSetChanged();
                listView.setAdapter(todaysdeliveryAdapter);
                mSwipeRefreshLayout.setRefreshing(false);
            }):


    }*/

    public void orderlist() {
       // todaysdeliveryBeen.clear();
        if (InternetConnection.isInternetOn(getActivity())) {
           /* if(firsttime.equals("1")){
                showProgress();
            }*/
            hideProgress();
            showProgress();
            // date="2017-01-04";
//        Toast.makeText(mContext, "listtttt", Toast.LENGTH_SHORT).show();
            Ion.with(this).load(Webservice.Today_delivery)
                    .setBodyParameter("OrderDliveryBoyID", id)
                    .setBodyParameter("date", formattedDate)
                    .asString().setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {
                    //Log.d("result", result);
                    hideProgress();
                    if (UtilityMethod.isStringNullOrBlank(result)) {

                        UtilityMethod.alertforServerError(mContext);
                    } else {

                        try {
                            todaysdeliveryBeen = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(result);
                            String status = jsonObject.getString("status");

                            if (status.equals("1")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("orderlist");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jojbect = jsonArray.getJSONObject(i);
                                    String OrderID = jojbect.getString("OrderID");
//                              Toast.makeText(context, "lisiid"+OrderID, Toast.LENGTH_SHORT).show();
                                    String OrderUserID = jojbect.getString("OrderUserID");
                                    String DliveryBoyID = jojbect.getString("DliveryBoyID");
                                    String OrderDeliveryDate = jojbect.getString("OrderDeliveryDate");
                                    String OrderDeliveryID = jojbect.getString("OrderDeliveryID");
                                    String users_id = jojbect.getString("users_id");
                                    String UserFullName = jojbect.getString("UserFullName");
                                    String UserAddress = jojbect.getString("UserAddress");
                                    String UserAddressPostalCode = jojbect.getString("PostalCode");
//                              Toast.makeText(context, "name"+UserFullName, Toast.LENGTH_SHORT).show();
                                    String email = jojbect.getString("email");
                                    String UserMobileNo = jojbect.getString("UserMobileNo");
                                    String OrderDeliveryStatus = jojbect.getString("OrderDeliveryStatus");
                                    JSONArray jsonArray1 = jojbect.getJSONArray("order");

                                    productBeen = new ArrayList<ProductBean>();
                                    // JSONArray marray = new JSONArray();
                                    // ArrayList<ProductBean> productlist = new ArrayList<ProductBean>();
                                    for (int j = 0; j < jsonArray1.length(); j++) {
                                        // JSONObject obj = new JSONObject();


                                        // ProductBean bean = new ProductBean();
                                        JSONObject jojbect1 = jsonArray1.getJSONObject(j);
                                  /*  obj.put("ProductID", jojbect1.optInt("ProductID"));
                                    obj.put("ProductName", jojbect1.optString("ProductName"));
                                    obj.put("ProductImage", jojbect1.optString("ProductImage"));
                                    obj.put("ItemQty",jojbect1.optString("ItemQty"));
                                    marray.put(obj);*/
                                        String ProductID = jojbect1.getString("ProductID");
                                        String ProductName = jojbect1.getString("ProductName");
                                        String ProductImage = jojbect1.getString("ProductImage");
                                        String ItemQty = jojbect1.getString("ItemQty");

                                        productBeen.add(new ProductBean(ProductID, ProductName, ItemQty, ProductImage, OrderID));

                                    }
                                    todaysdeliveryBeen.add(new TodaysdeliveryBean(OrderID, OrderUserID, DliveryBoyID, OrderDeliveryDate, OrderDeliveryID, users_id,
                                            UserFullName, email, UserMobileNo, OrderDeliveryStatus, UserAddress, UserAddressPostalCode, productBeen));


                                    hideProgress();
                                }
                            } else if (status.equals("0")) {
                                UtilityMethod.alertforNoData(mContext);
                                hideProgress();
                            }
                            todaysdeliveryAdapter = new TodaysdeliveryAdapter(todaysdeliveryBeen, getContext());
                            listView.setAdapter(todaysdeliveryAdapter);
                            Commons.setListViewHeightBasedOnChildren(listView);
                            todaysdeliveryAdapter.notifyDataSetChanged();
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Bundle b = new Bundle();
                                    productBeen1 = todaysdeliveryBeen.get(position).getProductlist();
                                    // selectindex=position;
              /*  todaysdeliveryAdapter.notifyDataSetChanged();
                todaysdeliveryAdapter.holder.productList.setVisibility(View.VISIBLE);*/

                                    String orderid = todaysdeliveryBeen.get(position).getOrderID();
//                String orderuserid = todaysdeliveryBeen.get(position).getOrderUserID();
//                String dliveryboyid = todaysdeliveryBeen.get(position).getDliveryBoyID();
                                    String orderdiliverydate = todaysdeliveryBeen.get(position).getOrderDeliveryDate();

//                String userid = todaysdeliveryBeen.get(position).getUsers_id();
                                    String userfullname = todaysdeliveryBeen.get(position).getUserFullName();
                                    String email = todaysdeliveryBeen.get(position).getEmail();
                                    String usermobileno = todaysdeliveryBeen.get(position).getUserMobileNo();
//                String Productid = todaysdeliveryBeen.get(position).getProductID();


                                    String orderdiliverystatus = todaysdeliveryBeen.get(position).getOrderDeliveryStatus();
                                    String useraddress = todaysdeliveryBeen.get(position).getUserAddress();
                                    String useraddresspostal = todaysdeliveryBeen.get(position).getUserAddressPostal();


                                    b.putString("OrderID", orderid);
//                b.putString("OrderUserID",orderuserid);
//                b.putString("DliveryBoyID",dliveryboyid);
                                    b.putString("OrderDeliveryDate", orderdiliverydate);
//                b.putString("users_id",userid);
                                    b.putString("UserFullName", userfullname);
                                    b.putString("email", email);
                                    b.putString("UserMobileNo", usermobileno);
//                b.putString("ProductID",Productid);

                                    //  b.putString("ProductImage", Productimage);
//                Toast.makeText(getActivity(), "dett"+Productimage, Toast.LENGTH_SHORT).show();
                                    //  b.putString("ItemQty",Itemqty);
                                    b.putString("OrderDeliveryStatus", orderdiliverystatus);
                                    b.putString("UserAddress", useraddress);
                                    b.putString("UserAddressPostalCode", useraddresspostal);
                                    Intent i = new Intent(getActivity(), Details.class);
                                    i.putExtras(b);
                                    if (useraddress.equals("null")||useraddress.equals(""))
                                    {
                                        Toast.makeText(context, "UserAdress Empty", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        startActivity(i);
                                        getActivity().finish();
                                    }
                                 //   Toast.makeText(context, ""+useraddress, Toast.LENGTH_LONG).show();
                                }
                            });
                        } catch (JSONException e1) {
                            hideProgress();
                          //  Toast.makeText(mContext, "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        } else {
            UtilityMethod.NoInternet(mDrawerLayout);
        }
    }

    public void ProcessStatus(final String flag) {
        if (InternetConnection.isInternetOn(getActivity())) {
           // hideProgress();
            showProgress();
            //// date="2017-01-04";
//        Toast.makeText(mContext, "listtttt", Toast.LENGTH_SHORT).show();
            Ion.with(this).load(Webservice.StatusProcess)
                    .setBodyParameter("OrderDliveryBoyID", id)
                    .setBodyParameter("OrderDeliveryStatus", flag)
                    .setBodyParameter("OrderDeliveryDate", formattedDate)
                    .asString().setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {
                    //Log.d("result", result);
                   hideProgress();
                    if (UtilityMethod.isStringNullOrBlank(result)) {

                        UtilityMethod.alertforServerError(mContext);
                    } else {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            String status = jsonObject.getString("status");

                            if (status.equals("1")) {
                                String msg = jsonObject.getString("msg");
                             //   UtilityMethod.showToast(""+msg, getActivity());
                                  //  hideProgress();
                                sm.flagstartorstop(formattedDate,flag);

                                if(sm.getFlag().equals("1"))
                                {
                                    start_tv.setText("Parar");
                                }
                                else
                                {
                                    start_tv.setText("Iniciar");
                                }
                                orderlist();
                                hideProgress();
                            } else if (status.equals("0")) {
                                hideProgress();
                                String msg = jsonObject.getString("msg");
                              //  UtilityMethod.showToast(""+msg, getActivity());
                               // hideProgress();
                            }
                        }
                        catch (JSONException e1)
                        {
                            hideProgress();
                           // hideProgress();
                          //  Toast.makeText(mContext, "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
        else {
            UtilityMethod.NoInternet(mDrawerLayout);
        }
    }
    public void hideProgress() {
        if (progress != null) {
            try {
                progress.dismiss();
            } catch (Exception e) {

            }
        }
    }

    public void showProgress() {
        try
        {
            progress = new ProgressDialog(mContext);
            progress.setMessage("A carregar...");
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            progress.show();
        }
        catch (Exception e)
        {

        }
    }
    public void alertConfirmation(Context ctx,String msg)
    {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);

        // Setting Dialog Title
        alertDialog.setTitle("Confirmation...");

        // Setting Dialog Message
        alertDialog.setMessage(msg);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                ProcessStatus("1");
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        // Setting Negative "NO" Button

        // Showing Alert Message
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
}
