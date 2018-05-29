package com.example.asus.homemdopao.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asus.homemdopao.FontManager;
import com.example.asus.homemdopao.Model.HistoryBean;
import com.example.asus.homemdopao.R;

import java.util.ArrayList;

/**
 * Created by Asus on 16/11/2016.
 */

public class HistoryAdapter extends BaseAdapter {
    ArrayList<HistoryBean> historyBeen;
    Context context;
    public boolean isClickedFirstTime = true;
    public HistoryAdapter(ArrayList<HistoryBean> historyBeen, Context context){
        this.historyBeen = historyBeen;
        this.context = context;
    }
    @Override
    public int getCount() {
        return historyBeen.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        Typeface iconFont = FontManager.getTypeface(context, FontManager.FONTAWESOME);

        ViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.history_list_item, null);
            holder = new ViewHolder();
          //  holder.image = (ImageView) view.findViewById(R.id.iconproducthistory);
            holder.customertv = (TextView) view.findViewById(R.id.cusromer_tv);
            holder.quantity = (TextView) view.findViewById(R.id.quantity_tv);
            holder.orderid=(TextView)view.findViewById(R.id.orderid_tv);
            holder.readmore=(LinearLayout)view.findViewById(R.id.readmorell);
            holder.commentll=(LinearLayout)view.findViewById(R.id.statusll);
            holder.readmoretv=(TextView)view.findViewById(R.id.readmore_tv);
            holder.datetv=(TextView)view.findViewById(R.id.date_header_tv);
           // holder.comment_img_txt=(TextView)view.findViewById(R.id.comment_img) ;
           // FontManager.markAsIconContainer(holder.comment_img_txt,iconFont);
           // holder.comment_img_txt.setText(R.string.fa_comments);

            holder.comment_txtv=(TextView)view.findViewById(R.id.message_tv);
            holder.readmoretv.setTag(holder);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final HistoryBean lm = historyBeen.get(position);
     //   String pName=lm.getProductName();
       /* if(pName.length()>=14){
            holder.comment_txtv.setText(pName.substring(0,14));
        }
        if
        else {
            holder.comment_txtv.setText(lm.getProductName());
        }*/
        String  previoussdate="",currentdate="";
        if (position==0){

        }else {
            previoussdate=historyBeen.get(position-1).getOrderDeliveryDate().substring(0,10);
            currentdate =historyBeen.get(position).getOrderDeliveryDate().substring(0,10);;
        }

        if (position==0){
            holder.datetv.setVisibility(View.VISIBLE);
        }
        else if(previoussdate.equals(currentdate)){
            holder.datetv.setVisibility(View.GONE);
        }else {
            holder.datetv.setVisibility(View.VISIBLE);
        }
        holder.quantity.setText(lm.getItems());
        holder.orderid.setText(lm.getOrderID());
        holder.customertv.setText(lm.getUserFullName());
        String message=lm.getComment();
        if(message.length()==0){
           holder.commentll.setVisibility(View.GONE);
        }
        else if(message.length()>=30){
            holder.commentll.setVisibility(View.VISIBLE);
            holder.comment_txtv.setText(message.substring(0,30));
            holder.readmore.setVisibility(View.VISIBLE);
        }
        else {
            holder.commentll.setVisibility(View.VISIBLE);
            holder.comment_txtv.setText(lm.getComment());
            holder.readmore.setVisibility(View.GONE);
        }
        holder.datetv.setText(lm.getOrderDeliveryDate().substring(0,10));
      /*  try{
            if (lm.getProductImage()!=null&!lm.getProductImage().equals("") )
            {

                Picasso.with(context).load("" + lm.getProductImage()).error(R.drawable.iconproduct).placeholder(R.drawable.iconproduct).into(holder.image);
//                Toast.makeText(DetailsActivity.this, "details..im1."+image1, Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception ae)
        {
        }*/
        holder.readmoretv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewHolder holder = (ViewHolder) view.getTag();
                holder.comment_txtv.setText(lm.getComment());
                holder.readmore.setVisibility(View.GONE);
              /*  if(isClickedFirstTime){
                    String comtxt=holder.comment_txtv.getText().toString();
                    if(UtilityMethod.isStringNullOrBlank(comtxt)) {
                        UtilityMethod.showToast("There Is No Comment", context);
                        notifyDataSetChanged();
                    }
                    else {
                        holder.comment_txtv.setVisibility(View.VISIBLE);
                        isClickedFirstTime = false;
                        notifyDataSetChanged();
                    }
                }
                else {
                    holder.comment_txtv.setVisibility(View.GONE);
                    isClickedFirstTime=true;
                    notifyDataSetChanged();
                }
*/

            }
        });
//        holder.image.setImageResource(Integer.parseInt(lm.getProductImage()));

        return view;
    }
    public static class ViewHolder {
        ImageView image,imagear;
        TextView customertv,quantity,orderid,comment_txtv,readmoretv,datetv;
        LinearLayout readmore,commentll;
    }
}
