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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Asus on 03/11/2016.
 */

public class ProductsNameListAdapter extends BaseAdapter {
    ArrayList<ProductBean> productBeen;
    Context context;
    SessionManager sm;
    public static ViewHolder holder;
    ProductBean lm;
    View view;

    public ProductsNameListAdapter(ArrayList<ProductBean> productBeen, Context context) {
        this.productBeen = productBeen;
        this.context = context;

    }

    @Override
    public int getCount() {
        return productBeen.size();
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

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.products_list_item, null);

            holder.tv = (TextView) view.findViewById(R.id.productNameTxt);
            holder.qty = (TextView) view.findViewById(R.id.prdct_qty);
            holder.image=(ImageView)view.findViewById(R.id.img);
            view.setTag(holder);

        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }
        lm = productBeen.get(position);
        holder.tv.setText(lm.getProductName());
        holder.qty.setText(lm.getItemQty());
        try {
            if (lm.getProductImage() != null & !lm.getProductImage().equals("")) {

                Picasso.with(context).load("" + lm.getProductImage()).error(R.drawable.iconproduct).placeholder(R.drawable.iconproduct).into(holder.image);
//              Toast.makeText(DetailsActivity.this, "details..im1."+image1, Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception ae)
        {

        }
            return view;
        }

    public static class ViewHolder
    {
        ImageView image,imagear;
        TextView tv,qty;

    }

}
