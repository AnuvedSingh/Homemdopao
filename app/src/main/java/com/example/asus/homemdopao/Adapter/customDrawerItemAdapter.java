package com.example.asus.homemdopao.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.homemdopao.FontManager;
import com.example.asus.homemdopao.Model.DashboardDrawerItemBean;
import com.example.asus.homemdopao.R;
import com.norbsoft.typefacehelper.TypefaceHelper;

import java.util.ArrayList;

/**
 * Created by Asus on 02/11/2016.
 */

public class customDrawerItemAdapter extends BaseAdapter {
    ArrayList<DashboardDrawerItemBean> drawerItemList;
    Context mContext;
    TextView dash_drawer_icon;
    //	SessionManeger sm;
    public customDrawerItemAdapter(Context mContext) {
        //sm= new SessionManeger(mContext);-
        this.mContext = mContext;
        drawerItemList = new ArrayList<DashboardDrawerItemBean>();
        drawerItemList.add(new DashboardDrawerItemBean("Inicio"));
        drawerItemList.add(new DashboardDrawerItemBean("Histórico"));
        drawerItemList.add(new DashboardDrawerItemBean("Alterar Palavra-Passe"));
        drawerItemList.add(new DashboardDrawerItemBean("Sair"));
    }
    @Override
    public int getCount() {
        return drawerItemList.size();
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
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dash_draw_item, null);
        }

        Typeface iconFont = FontManager.getTypeface(mContext, FontManager.FONTAWESOME);

        TextView textDraw = (TextView)convertView.findViewById(R.id.txt_dash_drawer_item);
        dash_drawer_icon= (TextView)convertView.findViewById(R.id.dash_drawer_icon);
        FontManager.markAsIconContainer(dash_drawer_icon,iconFont);
        dash_drawer_icon.setTextSize(24.0f);
        DashboardDrawerItemBean mydrawerItem = drawerItemList.get(position);
        textDraw.setText(""+mydrawerItem.item_name);
       // dash_drawer_icon.setImageResource(mydrawerItem.image);
        if (position == 0){

            dash_drawer_icon.setText(R.string.fa_home);
        }
        if (position == 1){

            dash_drawer_icon.setText(R.string.fa_history);
        }
        if (position == 2){

            dash_drawer_icon.setText(R.string.fa_unlock_alt);
        }
        if (position == 3){

            dash_drawer_icon.setText(R.string.fa_sign_out);
        }


        return convertView;
    }
}
