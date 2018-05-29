package com.example.asus.homemdopao.Adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.homemdopao.Activity.DashBoardActivity;
import com.example.asus.homemdopao.Model.ProductBean;
import com.example.asus.homemdopao.Model.TodaysdeliveryBean;
import com.example.asus.homemdopao.R;
import com.example.asus.homemdopao.Server.Webservice;
import com.example.asus.homemdopao.SessionManager;
import com.example.asus.homemdopao.UtilityMethod;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Asus on 03/11/2016.
 */

public class TodaysdeliveryAdapter extends BaseAdapter {
    ArrayList<TodaysdeliveryBean> todaysdeliveryBeen;
    ArrayList<ProductBean> productBeen;
    Context context;
    String orderid, orderstatus;
    SessionManager sm;
    public static ViewHolder holder;
    int i;
    TodaysdeliveryBean lm;
    ImageView image, imagear;
    TextView tv, quantity, status_txt;
    CheckBox checkboxcomplete, chechboxpregress;
    RadioGroup status_radio;
    RadioButton progress_radio, complete_radio;

    public static boolean[] checkBoxState;
    ArrayList<Boolean> checked;
    ProgressDialog progress;
    Dialog dialog;
    EditText comment_edit,id_edit;
    Button submit_btn,cancel_btn;

    View view;
    String comment;
    public boolean isClickedFirstTime = true;

    ProductsNameListAdapter productsNameListAdapter;
    public TodaysdeliveryAdapter(ArrayList<TodaysdeliveryBean> todaysdeliveryBeen, Context context) {
        this.todaysdeliveryBeen = todaysdeliveryBeen;
        this.context = context;
        checkBoxState=new boolean[todaysdeliveryBeen.size()];

        checked= new ArrayList<Boolean>();
        for (int i = 0; i < this.getCount(); i++)
        {
            checked.add(i, false);
        }

    }

    @Override
    public int getCount() {
        return todaysdeliveryBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        view = convertView;
        sm = new SessionManager(context);
        holder = new ViewHolder();
//      Toast.makeText(context, "in adapter", Toast.LENGTH_SHORT).show();
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_delivery, null);


            holder.tv = (TextView) view.findViewById(R.id.orderidtxt);

            holder.status_txt = (TextView) view.findViewById(R.id.complain_status);
          //  holder.productList=(ListView)view.findViewById(R.id.products);
            holder.checkboxcomplete = (CheckBox) view.findViewById(R.id.checkboxcomplete);
            holder.checkboxcomplete.setTag(holder);
           // holder.productList.setTag(holder);
            view.setTag(holder);
            // chechboxpregress= (CheckBox) view.findViewById(R.id.checkboxprogress);
            //progress_radio = (RadioButton) view.findViewById(R.id.progress);
            // complete_radio = (RadioButton) view.findViewById(R.id.completed);
            // checked = ((RadioButton) view).isChecked();


        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }
        lm = todaysdeliveryBeen.get(position);
    /*    holder.tv.setText(lm.getProductName());
        holder.quantity.setText(lm.getItemQty());*/
        holder.status_txt.setText(lm.getOrderDeliveryStatus());
        holder.tv.setText(lm.getOrderID());
        orderstatus = lm.getOrderDeliveryStatus();
        holder.checkboxcomplete.setChecked(false);

      //  holder.checkboxcomplete.setChecked(positionArray.get(position));
//      Toast.makeText(context, ""+orderstatus, Toast.LENGTH_SHORT).show();
       // orderid = todaysdeliveryBeen.get(position).getOrderID();

      //  holder.productList.setAdapter(new ProductsNameListAdapter(todaysdeliveryBeen.get(position).get(),context));
   /*     productBeen=new ArrayList<>();
        holder.productList.setAdapter(new ProductsNameListAdapter(productBeen, context));
        Commons.setListViewHeightBasedOnChildren(holder.productList);*/

       /* if (Todays_delivery.selectindex==position)
        {
            ViewHolder holder = (ViewHolder) view.getTag();
            holder.productList.setVisibility(View.VISIBLE);
            notifyDataSetChanged();
        }
        else
        {
            ViewHolder holder = (ViewHolder) view.getTag();
            holder.productList.setVisibility(View.GONE);
            notifyDataSetChanged();
        }*/
        holder.checkboxcomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewHolder holder = (ViewHolder) view.getTag();
                if(isClickedFirstTime)
                {
                    System.out.println(position+"--- :)");
                   // holder.productList.setVisibility(View.VISIBLE);
                    orderid = todaysdeliveryBeen.get(position).getOrderDeliveryID();
//                  Toast.makeText(context, "in processss", Toast.LENGTH_SHORT).show();
                    confirmAlert();
                    isClickedFirstTime=false;
                    //positionArray.add(position, true);
                    notifyDataSetChanged();
                }
                else
                {
                    i=0;
                   // holder.productList.setVisibility(View.GONE);
                    notifyDataSetChanged();
                    isClickedFirstTime=true;
                }

            }
        });

      /*  if (position==0){
            //vh.checkbox_ok.setVisibility(View.GONE);
            holder.checkboxcomplete.setVisibility(View.GONE);
        }else{
            holder.checkboxcomplete.setVisibility(View.VISIBLE);
        }*/

      /*  holder.checkboxcomplete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //confirmAlert();
                ViewHolder holder = (ViewHolder) view.getTag();
                if(isClickedFirstTime)
                {
                    System.out.println(position+"--- :)");
                    orderid = todaysdeliveryBeen.get(position).getOrderDeliveryID();
//                   Toast.makeText(context, "in processss", Toast.LENGTH_SHORT).show();
                    confirmAlert();
                    isClickedFirstTime=false;
                 *//*   checkBoxState[position]=true;
                    checked.set(position,true);

                    selecedcalss.add(todaysdeliveryBeen.get(position).getOrderDeliveryID());*//*
                  //  positionArray.add(position, true);
                    notifyDataSetChanged();
                }
                else
                {
                    i=0;
                    notifyDataSetChanged();
                    isClickedFirstTime=true;
                }

               // holder.checkboxcomplete.setChecked(true);
                  //  positionArray.add(position, false);
            }
        });*/

        if(todaysdeliveryBeen.get(position).getOrderDeliveryStatus().equals("Completado")){
            holder.checkboxcomplete.setChecked(true);
            holder.checkboxcomplete.setClickable(false);
            hideProgress();
        }
        // holder.image.setImageResource(Integer.parseInt(lm.getProductImage()));
            return view;
        }

    public static class ViewHolder
    {
        ImageView image,imagear;
        TextView tv,quantity,status_txt;
        CheckBox checkboxcomplete;
        public static ListView productList;
    }

    public void statuschange(String commenttxt)
     {
        showProgress();
         // Toast.makeText(context, "inffg", Toast.LENGTH_SHORT).show();
         Ion.with(context).load(Webservice.Status)
                 .setBodyParameter("OrderDeliveryID",orderid)
                 .setBodyParameter("OrderDeliveryStatus",String.valueOf(i))
                 .setBodyParameter("OrderStatuscomment",commenttxt)
                 .asString().setCallback(new FutureCallback<String>()
         {
             @Override
             public void onCompleted(Exception e, String result)
             {
                 hideProgress();
                 if (UtilityMethod.isStringNullOrBlank(result)) {

                     UtilityMethod.alertforServerError(context);
                 } else {
                     try {
                         // Log.d("result", result);
                         // Toast.makeText(context, ""+orderid, Toast.LENGTH_SHORT).show();
                         // Toast.makeText(context, ""+i, Toast.LENGTH_SHORT).show();
                         JSONObject jsonObject = new JSONObject(result);
                         String status = jsonObject.getString("status");
                         String msg = jsonObject.getString("msg");

                         if (status.equals("1")) {

                             //holder.checkboxcomplete.setChecked(true);
                         /* if(i==1){
                             status_txt.setText("Progress");
                         }
                         // lm.setOrderDeliveryStatus("Completed");
                         status_txt.setText("Completed");*/
                             //  todaysdeliveryBeen.notifyAll();
                             // holder.checkboxcomplete.setVisibility(View.GONE);
                             //Toast.makeText(context, "Delivery Confirmed", Toast.LENGTH_SHORT).show();
                             dialog.dismiss();

                             notifyDataSetChanged();
                             hideProgress();
                             Bundle b=new Bundle();
                             new DashBoardActivity().displayView(0,b);
                         } else if (status.equals("0")) {
                             // holder.checkboxcomplete.setVisibility(View.VISIBLE);
                         }
                     } catch (JSONException e1) {
                         Toast.makeText(context, "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                     }
                 }
             }
         });


     }
    public void hideProgress()
    {
        if (progress != null)
        {
            try
            {
                progress.dismiss();
            }
            catch (Exception e)
            {

            }
        }
    }

    public void showProgress()
    {
        try
        {
            progress = new ProgressDialog(context);
            progress.setMessage("A carregar...");
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            progress.show();
        }
        catch (Exception e)
        {

        }
    }
    public void confirmAlert() {
        dialog = new Dialog(context);
        // Include dialog.xml file
        dialog.setContentView(R.layout.custom_complete_status_alert);
        // Set dialog title
        // dialog.setTitle("CHANGE PASSWORD");

        comment_edit = (EditText) dialog.findViewById(R.id.comment_edittxt);

        submit_btn = (Button) dialog.findViewById(R.id.submit_btn);
        // set values for custom dialog components - text, image and button
        cancel_btn=(Button)dialog.findViewById(R.id.cancel_btn);
        dialog.show();


        // if decline button is clicked, close the custom dialog
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i = 2;
                comment=comment_edit.getText().toString();
                statuschange(comment);
                // Close dialog
                //  dialog.dismiss();
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i = 1;
                //statuschange();
                // Close dialog
                dialog.dismiss();
            }
        });

    }
}
