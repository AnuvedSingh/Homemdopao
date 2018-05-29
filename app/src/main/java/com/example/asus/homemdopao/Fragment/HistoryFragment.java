package com.example.asus.homemdopao.Fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.homemdopao.Activity.DashBoardActivity;
import com.example.asus.homemdopao.Adapter.HistoryAdapter;
import com.example.asus.homemdopao.FontManager;
import com.example.asus.homemdopao.Model.HistoryBean;
import com.example.asus.homemdopao.Model.ProductBean;
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

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.asus.homemdopao.Activity.DashBoardActivity.mDrawerLayout;

/**
 * Created by Asus on 16/11/2016.
 */

public class HistoryFragment extends Fragment {
    static Context mContext;
    Context context;
    static FragmentManager mfragmentmanager;
    static Fragment mfFragment;
    ListView listView;
    TextView cal_icon;
    ImageView menuIcon, headericon;
    SessionManager sm;
    public static String id;
    public static ArrayList<HistoryBean> historyBeen;
    public static HistoryAdapter historyAdapter;
    ArrayList<ProductBean> productBeen;
    ProgressDialog progress;
    Typeface iconfont;
    DatePickerFragment datePickerFragment;
    String[] Days = {"Last 30 Days", "Last 60 Days", "Last 90 Days"};
    int day;
    String date="";
    public static Fragment getInstance(Context ct, FragmentManager fm) {
        mContext = ct;
        mfragmentmanager = fm;
        mfFragment = new HistoryFragment();

        return mfFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_list, container, false);
        iconfont = FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME);
        DashBoardActivity.heading_txt.setVisibility(View.GONE);
        sm = new SessionManager(getActivity());
        id = sm.getid();
        context = getActivity();
        // day=30;
        // Spinner spin = (Spinner) view.findViewById(R.id.sp_days);
/*
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              //  Toast.makeText(context,i+" "+Days[i] ,Toast.LENGTH_LONG).show();
                if(i==0){
                    day=30;
                  //  listView.setAdapter(historyAdapter);

                    orderlistcomplete();
                   // historyAdapter.notifyDataSetChanged();
                }
                if(i==1){
                    day= 60;
                    //historyAdapter.notifyDataSetChanged();
                    orderlistcomplete();
                }
                if(i==2){
                    day= 90;
                   // historyAdapter.notifyDataSetChanged();
                    orderlistcomplete();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/
        //  spin.setOnItemSelectedListener(getActivity());

        //Creating the ArrayAdapter instance having the country list
      /*  ArrayAdapter aa = new ArrayAdapter(context,android.R.layout.simple_spinner_item,Days);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);*/

        listView = (ListView) view.findViewById(R.id.historylist);
        menuIcon = (ImageView) view.findViewById(R.id.menuIcon);
        headericon = (ImageView) view.findViewById(R.id.headericon);
        DashBoardActivity.back_icon.setVisibility(View.GONE);
        DashBoardActivity.menu_icon.setVisibility(View.VISIBLE);
        cal_icon = (TextView) view.findViewById(R.id.calander);
        FontManager.markAsIconContainer(cal_icon, iconfont);
        cal_icon.setText(R.string.fa_cal);

        // historyAdapter.notifyDataSetChanged();
        orderlistcomplete();

        cal_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDatePicker();
            }
        });


/*   // Set dialog title
                //dialog.setTitle("Custom Dialog");
                CalendarView cal_v=(CalendarView)dialog.findViewById(R.id.calendar_v);
                cal_v.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                     //   dateDisplay.setText("Date: " + i2 + " / " + i1 + " / " + i);

                        Toast.makeText(getActivity(), "Selected Date:\n" + "Day = " + i2 + "\n" + "Month = " + i1 + "\n" + "Year = " + i, Toast.LENGTH_LONG).show();
                    }
                });
                //  .setBodyParameter("days", String.valueOf(day))
                // set values for custom dialog components - text, image and button

                dialog.show();
*/
        return view;

    }
    public void orderlistcomplete(){
        historyBeen = new ArrayList<>();
        if(InternetConnection.isInternetOn(getActivity())) {
            showProgress();
//        Toast.makeText(mContext, "listtttt", Toast.LENGTH_SHORT).show();
            Ion.with(this).load(Webservice.History)
                    .setBodyParameter("OrderDliveryBoyID", id)
                    .setBodyParameter("days","7")
                    .setBodyParameter("date", date)
                    .asString().setCallback(new FutureCallback<String>() {

                @Override
                public void onCompleted(Exception e, String result) {
                    //showProgress();
                    hideProgress();
                    if (UtilityMethod.isStringNullOrBlank(result)) {
                        hideProgress();
                        UtilityMethod.alertforServerError(mContext);
                    } else {
                        try {
                            hideProgress();
                            Log.d("result", result);
                            historyBeen.clear();
                            JSONObject jsonObject = new JSONObject(result);
                            String status = jsonObject.getString("status");

                            if (status.equals("1")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("orderlist");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jojbect = jsonArray.getJSONObject(i);
                                    String OrderID = jojbect.getString("OrderID");
//                            Toast.makeText(context, "lisiid"+OrderID, Toast.LENGTH_SHORT).show();
                                    String OrderUserID = jojbect.getString("OrderUserID");
                                    String DliveryBoyID = jojbect.getString("DliveryBoyID");
                                    String OrderDeliveryDate = jojbect.getString("OrderDeliveryDate");
                                    String users_id = jojbect.getString("users_id");
                                    String UserFullName = jojbect.getString("UserFullName");
//                            Toast.makeText(context, "name"+UserFullName, Toast.LENGTH_SHORT).show();
                                    String email = jojbect.getString("email");
                                    String UserMobileNo = jojbect.getString("UserMobileNo");
                                    //  String ProductID = jojbect.getString("ProductID");
                                    // String ProductName = jojbect.getString("ProductName");
                                    //String ProductImage = jojbect.getString("ProductImage");
                                    //  String ItemQty = jojbect.getString("ItemQty");
                                    String OrderDeliveryStatus = jojbect.getString("OrderDeliveryStatus");
                                    String commnettxt = jojbect.getString("status_comment");

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
                                    String items = String.valueOf(productBeen.size());
                                    historyBeen.add(new HistoryBean(OrderID, OrderUserID, DliveryBoyID, OrderDeliveryDate, users_id,
                                            UserFullName, email, UserMobileNo, OrderDeliveryStatus, commnettxt, productBeen, items));

                                    hideProgress();


                                    historyAdapter = new HistoryAdapter(historyBeen, getContext());
                                    listView.setAdapter(historyAdapter);
                                }


                            } else if (status.equals("0")) {
                                UtilityMethod.alertforNoData(mContext);
                                hideProgress();
                            }
                            hideProgress();
                        } catch (JSONException e1) {
                            hideProgress();
                            Toast.makeText(mContext, "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
            }

            });
        }else {
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
        try {
            progress = new ProgressDialog(getActivity());
            progress.setMessage("A carregar...");
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            progress.show();
        } catch (Exception e) {

        }
    }
    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
             date= String.valueOf(year) + "-" + String.valueOf(monthOfYear+1)
                    + "-" + String.valueOf(dayOfMonth);
            historyBeen.clear();
            orderlistcomplete();
           /* Toast.makeText(
                  getActivity(),
                     date,
                    Toast.LENGTH_LONG).show();*/
        }
    };

}
