package com.example.asus.homemdopao.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.homemdopao.Adapter.customDrawerItemAdapter;
import com.example.asus.homemdopao.FontManager;
import com.example.asus.homemdopao.Fragment.HistoryFragment;
import com.example.asus.homemdopao.Fragment.Products_list_fragment;
import com.example.asus.homemdopao.Fragment.Todays_delivery;
import com.example.asus.homemdopao.R;
import com.example.asus.homemdopao.Server.InternetConnection;
import com.example.asus.homemdopao.Server.Webservice;
import com.example.asus.homemdopao.Service.MapLocationService;
import com.example.asus.homemdopao.SessionManager;
import com.example.asus.homemdopao.UtilityMethod;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Asus on 02/11/2016.
 */

public class DashBoardActivity extends AppCompatActivity {
    public static LinearLayout lldrawercontent;
    static Context Mcontext;
    public static FragmentTransaction ft1;
    static public FragmentManager fm;
    Fragment frag;
    ListView mDrawerList;
    public static DrawerLayout mDrawerLayout;
    public static boolean isDrawerOpen = false;
    public static  int Home=100, Detail=101,Map=102,logout=13;
    LinearLayout layout_frame_main;
    ActionBar actionBar;
    public  static TextView menu_icon ;
    public  static TextView back_icon;
    public boolean Drawerstatus=true;
    ProgressDialog progress;
    ImageView headerlogin;
    String userid;
    SessionManager sm;

    public static TextView  login_user_name,heading_txt;
    public  static  ImageView search_icon,cross_icon;
    public static EditText edit_txt;

    String id,newpass,email_id;
    EditText newpassword_edit,id_edit;
    Button submitbtn,cancelbtn;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        Typeface iconFont = FontManager.getTypeface(this, FontManager.FONTAWESOME);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Mcontext = this;
        startService(new Intent(getBaseContext(), MapLocationService.class));
        Intent i = getIntent();
        userid = i.getStringExtra("id");
        sm = new SessionManager(Mcontext);
        menu_icon = (TextView) findViewById(R.id.menuIcon);
        FontManager.markAsIconContainer(menu_icon, iconFont);
        menu_icon.setText(R.string.fa_nav);
        back_icon = (TextView) findViewById(R.id.back);
        FontManager.markAsIconContainer(back_icon, iconFont);
        back_icon.setText(R.string.fa_back);
        headerlogin = (ImageView) findViewById(R.id.headericon);
        heading_txt = (TextView) findViewById(R.id.Heading_tx);
        login_user_name = (TextView) findViewById(R.id.loginname_txt);
        login_user_name.setText(sm.email());
        fm = getSupportFragmentManager();
        lldrawercontent = (LinearLayout) findViewById(R.id.lldrawercontent);
        mDrawerList = (ListView) findViewById(R.id.drawer_list);
        layout_frame_main = (LinearLayout) findViewById(R.id.layout_frame_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        customDrawerItemAdapter CustomDrawerItemAdapter = new customDrawerItemAdapter(Mcontext);
        mDrawerList.setAdapter(CustomDrawerItemAdapter);

      /*  fm=DashBoardActivity.this.getSupportFragmentManager();
        ft1=fm.beginTransaction();
//        WorkouttimeFragment fragment=new WorkouttimeFragment();
        frag = new Todays_delivery();
        ft1.add(R.id.Cantinerdesh,frag);
        ft1.commit();
*/
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle b = new Bundle();
                displayView(position, b);
                mDrawerLayout.closeDrawers();

            }
        });
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerStateChanged(int arg0) {
                // DashBoradActvity.Heading_tx.setText("Home");

            }

            @Override
            public void onDrawerSlide(View arg0, float arg1) {

                layout_frame_main.setX((arg0.getWidth() * arg1));
            }
            @Override
            public void onDrawerOpened(View arg0) {
                isDrawerOpen = true;

            }

            @Override
            public void onDrawerClosed(View arg0) {

                isDrawerOpen = false;

            }

        });
        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isDrawerOpen) {
                    isDrawerOpen = false;
                    mDrawerLayout.closeDrawers();
                } else {
                    isDrawerOpen = true;

                    mDrawerLayout.openDrawer(lldrawercontent);
                }
            }
        });
        Bundle b = new Bundle();
        displayView(Home, b);

    }

    //Testing Git test for isys merge
    public void displayView(int id,Bundle b) {
		/*
		 * if (isDrawerOpen) { closeDrawer(); }
		 */
        Fragment fragment = null;
        String title = null;

        switch (id) {
            case 100:

                fragment = null;
                ft1 = fm.beginTransaction();
                fragment = Todays_delivery.getInstance(Mcontext, fm);
                if (fragment != null) {
                    fragment.setArguments(b);
                    ft1.replace(R.id.Cantinerdesh, fragment, "home");
                    ft1.addToBackStack(null);
                    ft1.commit();

                }
                break;
            case 101:

                fragment = null;
                ft1 = fm.beginTransaction();
                fragment = Products_list_fragment.getInstance(Mcontext, fm);
                if (fragment != null) {
                    fragment.setArguments(b);
                    ft1.replace(R.id.Cantinerdesh, fragment, "home");
                    ft1.addToBackStack(null);
                    ft1.commit();
                }
                break;

            case 0:
                fragment = null;
                ft1 = fm.beginTransaction();
                fragment = Todays_delivery.getInstance(Mcontext, fm);
                if (fragment != null) {
                    fragment.setArguments(b);
                    ft1.replace(R.id.Cantinerdesh, fragment, "home");
                    ft1.addToBackStack(null);
                    //				if (currentState != id) {
                    //					currentState = id;
                    //				}
                    ft1.commit();

                }
                break;
            case 1:
                fragment = null;
                ft1 = fm.beginTransaction();
                fragment = HistoryFragment.getInstance(Mcontext, fm);
                if (fragment != null) {
                    fragment.setArguments(b);
                    ft1.replace(R.id.Cantinerdesh, fragment, "User Profile");
                    ft1.addToBackStack(null);
                    ft1.commit();
                }
                break;

            case  2:
                alertChangePass();

                break;
            case 3:
                alertforLogout();

                break;

        }
    }

    @Override
    public void onBackPressed() {
        if (isDrawerOpen = true)
        {
            mDrawerLayout.closeDrawers();
        }
        Fragment HomeFragment = fm.findFragmentByTag("home");
        if (HomeFragment!=null)
        {
            if (HomeFragment.isVisible())
            {
                alertforExitApp();
                 //finish();
            }
            else
            {
                super.onBackPressed();
            }
        }

    }

    public void showProgress() {
        try {
            progress = new ProgressDialog(Mcontext);
            progress.setMessage("Loading...");
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            progress.show();
        } catch (Exception e) {

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
    public void alertforLogout()
    {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Mcontext);

        alertDialog.setMessage("Pretende sair?");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                if (InternetConnection.isInternetOn(Mcontext)) {
                    sm.logout();
                    UtilityMethod.showToast(" com sucesso Sair",Mcontext);
                    Intent i=new Intent(Mcontext,LoginActivity.class);
                    startActivity(i);
                    finish();
                }
                else {
                    UtilityMethod.NoInternet(mDrawerLayout);
                }
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
public void alertChangePass(){


    dialog = new Dialog(Mcontext);

    dialog.setContentView(R.layout.custom_change_pass_alert);


    newpassword_edit=(EditText)dialog.findViewById(R.id.newpassword_edit);
    id_edit=(EditText)dialog.findViewById(R.id.id_edit);
    submitbtn=(Button)dialog.findViewById(R.id.submit_btn);
    cancelbtn=(Button)dialog.findViewById(R.id.cancel_btn);
    // set values for custom dialog components - text, image and button
    dialog.setCanceledOnTouchOutside(false);
    dialog.show();


    // if decline button is clicked, close the custom dialog
    submitbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            changepassword();

        }
    });
    cancelbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

              dialog.dismiss();
        }
    });


}
    private void changepassword() {
//        sm.getid();

        id = sm.getid();
        email_id=id_edit.getText().toString();
        newpass = newpassword_edit.getText().toString();
//        Toast.makeText(getActivity(), "changepswd"+id +""+newpass+"", Toast.LENGTH_SHORT).show();
        if (InternetConnection.isInternetOn(Mcontext)) {
            if (UtilityMethod.isStringNullOrBlank(email_id))
            {
                UtilityMethod.showSnackbar("Por Introduza o Email",mDrawerLayout);
               UtilityMethod.showToast("Por Introduza o Email",DashBoardActivity.this);
            }
            else if (UtilityMethod.isStringNullOrBlank(newpass))
            {
                UtilityMethod.showSnackbar("Introduza a nova palavra-passe",mDrawerLayout);
                UtilityMethod.showToast("Introduza a nova palavra-passe",DashBoardActivity.this);
            }
            else if (UtilityMethod.isStringFirstSpace(email_id))
            {
                UtilityMethod.showSnackbar("O primeiro caractere não pode ser vazio",mDrawerLayout);
                UtilityMethod.showToast("O primeiro caractere não pode ser vazio",DashBoardActivity.this);
            }
            else if (UtilityMethod.isStringFirstSpace(newpass))
            {
                UtilityMethod.showSnackbar("O primeiro caractere não pode ser vazio",mDrawerLayout);
                UtilityMethod.showToast("Por favor introduza a palavra-passe correta", DashBoardActivity.this);
            }
            else
            {
                Ion.with(Mcontext).load(Webservice.changepassword)
                        .setBodyParameter("id", id)
                        .setBodyParameter("newpassword", newpass)
                        .setBodyParameter("email", email_id)
                        .asString().setCallback(new FutureCallback<String>()
                {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (UtilityMethod.isStringNullOrBlank(result))
                        {
                            UtilityMethod.alertforServerError(Mcontext);
                            // UtilityMethod.showToast("Please enter Email Id",DashBoardActivity.this);
                        }
                        else
                        {
                            Log.d("result", result);

                            try
                            {
                                JSONObject jobject = new JSONObject(result);
                                String status1 = jobject.getString("status");
                                String msg1 = jobject.getString("msg");
                                if (status1.equals("1"))
                                {
                                    Toast.makeText(Mcontext, "Palavra-passe atualizada", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    Intent i=new Intent(DashBoardActivity.this,LoginActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                                else if (status1.equals("0"))
                                {
                                    Toast.makeText(Mcontext, "Por favor introduza um email e/ou palavra-passe validos", Toast.LENGTH_SHORT).show();

                                }
                            }
                            catch (JSONException e1)
                            {
                                Toast.makeText(Mcontext, "Por favor introduza um email e/ou palavra-passe validos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        }else
        {
            UtilityMethod.NoInternet(mDrawerLayout);
        }
    }
    public void alertforExitApp()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Mcontext);

        alertDialog.setMessage("Tem a certeza de que pretende sair da aplicação?");
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                DashBoardActivity.this.finish();
                // Write your code here to invoke YES event

            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.setCancelable(false);
        alertDialog.show();
    }
}
