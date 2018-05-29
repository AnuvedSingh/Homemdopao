package com.example.asus.homemdopao.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.homemdopao.Activity.DashBoardActivity;
import com.example.asus.homemdopao.Adapter.ProductsNameListAdapter;
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

public class Products_list_fragment extends Fragment {
    public static FragmentTransaction ft1;
    static public FragmentManager fm;
    Fragment frag;
    static Context mContext;
    Context context;
    static  FragmentManager mfragmentmanager;
    static Fragment mfFragment;
    ListView listView;
    TextView Heading_tx;
    ImageView menuIcon,headericon;
    SessionManager sm;
    public static  String id,Date;

    public  static  ArrayList<ProductBean> productBeen;
    public  static ProductsNameListAdapter productsNameListAdapter;
    ProgressDialog progress;
    String formattedDate,data;
    SwipeRefreshLayout mSwipeRefreshLayout;
    public static   int selectindex;

    public  static Fragment getInstance(Context ct, FragmentManager fm){
        mContext = ct;
        mfragmentmanager = fm;
        mfFragment=new Products_list_fragment();

        return mfFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.product_list,container,false);

        DashBoardActivity.heading_txt.setVisibility(View.GONE);
        sm = new SessionManager(getActivity());
        id = sm.getid();

        context = getActivity();

        listView = (ListView) view.findViewById(R.id.products);
       /* menuIcon=(ImageView) view.findViewById(R.id.menuIcon);
        headericon=(ImageView) view.findViewById(R.id.headericon);
        DashBoardActivity.back_icon.setVisibility(View.GONE);
        DashBoardActivity.menu_icon.setVisibility(View.VISIBLE);
        orderlist();*/
       // @SuppressWarnings("unchecked")
        Bundle b=this.getArguments();
      //  data=b.getSerializable("ProductName");
        productBeen=Todays_delivery.productBeen1;
       // ArrayList<ProductBean> localities = savedInstanceState.getParcelableArrayList("");
     //   productBeen = new ArrayList<>();
        listView.setAdapter(new ProductsNameListAdapter(productBeen, getActivity()));
      //  listView.setAdapter(productsNameListAdapter);
      //  Commons.setListViewHeightBasedOnChildren(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectindex=position;
              /*  todaysdeliveryAdapter.notifyDataSetChanged();
                todaysdeliveryAdapter.holder.productList.setVisibility(View.VISIBLE);*/

             /*   String orderid =  todaysdeliveryBeen.get(position).getOrderID();
//                String orderuserid = todaysdeliveryBeen.get(position).getOrderUserID();
//                String dliveryboyid = todaysdeliveryBeen.get(position).getDliveryBoyID();
                String orderdiliverydate = todaysdeliveryBeen.get(position).getOrderDeliveryDate();

//                String userid = todaysdeliveryBeen.get(position).getUsers_id();
                String userfullname = todaysdeliveryBeen.get(position).getUserFullName();
                String email = todaysdeliveryBeen.get(position).getEmail();
                String usermobileno = todaysdeliveryBeen.get(position).getUserMobileNo();
//                String Productid = todaysdeliveryBeen.get(position).getProductID();
            *//*    String Prductname = todaysdeliveryBeen.get(position).getProductName();
                String Productimage = todaysdeliveryBeen.get(position).getProductImage();
                String Itemqty = todaysdeliveryBeen.get(position).getItemQty();*//*
                String orderdiliverystatus = todaysdeliveryBeen.get(position).getOrderDeliveryStatus();
                String useraddress = todaysdeliveryBeen.get(position).getUserAddress();
                String useraddresspostal = todaysdeliveryBeen.get(position).getUserAddressPostal();
*/
                /*fm=getActivity().getSupportFragmentManager();
                ft1=fm.beginTransaction();
                frag = new Details();*/
             /*   Bundle b = new Bundle();
                b.putString("OrderID",orderid);
//                b.putString("OrderUserID",orderuserid);
//                b.putString("DliveryBoyID",dliveryboyid);
                b.putString("OrderDeliveryDate",orderdiliverydate);
//                b.putString("users_id",userid);
                b.putString("UserFullName",userfullname);
                b.putString("email",email);
                b.putString("UserMobileNo",usermobileno);
//                b.putString("ProductID",Productid);
                b.putString("ProductName",Prductname);
                b.putString("ProductImage", Productimage);
//                Toast.makeText(getActivity(), "dett"+Productimage, Toast.LENGTH_SHORT).show();
                b.putString("ItemQty",Itemqty);
                b.putString("OrderDeliveryStatus",orderdiliverystatus);
                b.putString("UserAddress",useraddress);
                b.putString("UserAddressPostalCode",useraddresspostal);
                Intent i=new Intent(getActivity(),Details.class);
                i.putExtras(b);
                startActivity(i);
                getActivity().finish();*/
               // new DashBoardActivity().displayView(DashBoardActivity.Detail, b);
              /*  frag.setArguments(b);
                ft1.replace(R.id.Cantinerdesh,frag);
                ft1.addToBackStack(null);
                ft1.commit();*/

            }
        });
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
            progress.setMessage("Loading...");
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            progress.show();
        }
        catch (Exception e)
        {

        }
    }
}
