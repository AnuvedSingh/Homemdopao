package com.example.asus.homemdopao.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.WindowManager;

import com.example.asus.homemdopao.R;
import com.example.asus.homemdopao.SessionManager;
import com.example.asus.homemdopao.UtilityMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 02/11/2016.
 */

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class SplashScreen  extends Activity {
    private static int SPLASH_TIME_OUT = 3000;
    SessionManager sm;
    Context mContext;
    public static final int MULTIPLE_PERMISSIONS = 10;
    String[] permissions= new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mContext = this;
        sm = new SessionManager(mContext);
        if (checkPermissions()){
            splasheed();
            //  permissions  granted.
        }


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent in = new Intent(SplashScreen.this, LoginActivity.class);
//                startActivity(in);
//
//
//            }
//        }, SPLASH_TIME_OUT);
    }
    private  void splasheed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (UtilityMethod.isStringNullOrBlank(sm.getid())) {
                    Intent in = new Intent(mContext, LoginActivity.class);
//                    Toast.makeText(SplashScreen.this, "userid =" + sm.getuserid(), Toast.LENGTH_SHORT).show();
                    startActivity(in);
                    finish();
                } else {
                    Intent in = new Intent(mContext, DashBoardActivity.class);
//                    Toast.makeText(SplashScreen.this, " else condition userid =" + sm.getuserid(), Toast.LENGTH_SHORT).show();
                    startActivity(in);
                    finish();
                }
//                Intent i = new Intent(SplashScreen.this,InternetConnection.class);
//                startActivity(i);
//                finish();


            }
        }, SPLASH_TIME_OUT);
    }
    private  boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(this,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    splasheed();
                }
                else
                {
                    checkPermissions();
                }
                return;
            }
        }
    }
}
