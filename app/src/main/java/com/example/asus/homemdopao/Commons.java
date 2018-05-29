package com.example.asus.homemdopao;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 05-11-2015.
 */
public class Commons {
    private static ListAdapter listAdapter;

    public static String cuttentDate() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c.getTime());
        // formattedDate have current date/time

        return formattedDate;
    }
    public static String cuttentTime() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());
        Date date= c.getTime();
        date.getHours();
        date.getMinutes();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = date.getHours()+":"+ date.getMinutes()+":"+date.getSeconds();
        // formattedDate have current date/time

        return formattedDate;
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
                        AbsListView.LayoutParams.WRAP_CONTENT));
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        if (listAdapter.getCount() >= 2) {
            params.height = (totalHeight + (1 * (listAdapter.getCount() - 1)) / 2);
        } else {
            params.height = (totalHeight + (1 * (listAdapter.getCount() - 1))) / 2;
        }
        listView.setLayoutParams(params);
    }
}
