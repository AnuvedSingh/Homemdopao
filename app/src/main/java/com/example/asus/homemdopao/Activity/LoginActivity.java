package com.example.asus.homemdopao.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.homemdopao.Model.LoginModel;
import com.example.asus.homemdopao.R;
import com.example.asus.homemdopao.Server.InternetConnection;
import com.example.asus.homemdopao.Server.Webservice;
import com.example.asus.homemdopao.SessionManager;
import com.example.asus.homemdopao.UtilityMethod;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Asus on 02/11/2016.
 */

public class LoginActivity extends AppCompatActivity {
   EditText email_edit,password_edit;
    Button login_btn;
    String emailid,password,userid;
    SessionManager sm;
    ArrayList<LoginModel> loginModelArrayList;
    ProgressDialog progress;
    String id;
    LocationManager lm;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        lm = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();
        }
        else
        {
            if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
                showGPSDisabledAlertToUser();
            }
        }


       // checkLocationPermission();

      /*  if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // Build the alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Location Services Not Active");
            builder.setMessage("Please enable Location Services and GPS");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Show location settings when the user acknowledges the alert dialog
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            Dialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }*/
        email_edit = (EditText)findViewById(R.id.email_edit);
        password_edit=(EditText)findViewById(R.id.password_edit);
        login_btn = (Button) findViewById(R.id.login_btn);
        sm= new SessionManager(this);
        loginModelArrayList= new ArrayList<LoginModel>();
        id=sm.getid();
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(LoginActivity.this, "loginbtn", Toast.LENGTH_SHORT).show();
//                Intent in = new Intent(LoginActivity.this,DashBoardActivity.class);
//                startActivity(in);
                 loginapi();

            }
        });
    }

    /////...for login api  method use.....

    private void loginapi() {
        emailid = email_edit.getText().toString();
        password = password_edit.getText().toString();
        if (InternetConnection.isInternetOn(this)) {
            if (UtilityMethod.isStringNullOrBlank(emailid))
            {
                UtilityMethod.showToast("Por favor Introduza UserName",this);
            } else if (UtilityMethod.isStringNullOrBlank(password))
            {
                UtilityMethod.showToast("Por favor Introduza a palavra-passe", this);
            }else
            {
                showProgress();
                Ion.with(this).load(Webservice.login)
                        .setBodyParameter("email", emailid)
                        .setBodyParameter("pwd", password)
                        .asString().setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                      //  Log.d("result", result);

                        if (UtilityMethod.isStringNullOrBlank(result))
                        {
                            UtilityMethod.alertforServerError(LoginActivity.this);
                        } else {
                            try {
                                JSONObject jobj = new JSONObject(result);
                                String Status = jobj.getString("status");
                                JSONObject jsonObject = jobj.getJSONObject("Userdetails");
//                    String msg= jobj.getString("msg");


                                if (Status.equals("1")) {
                                    LoginModel lm = new LoginModel();
                                    lm.setId(jsonObject.getString("id"));
                                    lm.setUserZoneID(jsonObject.getString("UserZoneID"));
                                    lm.setUserPostalCodeID(jsonObject.getString("UserPostalCodeID"));
                                    lm.setUserTitle(jsonObject.getString("UserTitle"));
                                    lm.setUserFullName(jsonObject.getString("UserFullName"));
                                    lm.setUserGender(jsonObject.getString("UserGender"));
                                    lm.setUserDOB(jsonObject.getString("UserDOB"));
                                    lm.setUserProfileImage(jsonObject.getString("UserProfileImage"));
                                    lm.setUserAddress(jsonObject.getString("UserAddress"));
                                    lm.setUserMobileNo(jsonObject.getString("UserMobileNo"));
                                    lm.setUserDeliveryNote(jsonObject.getString("UserDeliveryNote"));
                                    lm.setUsername(jsonObject.getString("username"));
                                    lm.setPassword(jsonObject.getString("password"));
                                    lm.setEmail(jsonObject.getString("email"));
                                    lm.setActivated(jsonObject.getString("activated"));
                                    lm.setBanned(jsonObject.getString("banned"));
                                    lm.setBan_reason(jsonObject.getString("ban_reason"));
                                    lm.setNew_password_key(jsonObject.getString("new_password_key"));
                                    lm.setNew_password_requested(jsonObject.getString("new_password_requested"));
                                    lm.setNew_email(jsonObject.getString("new_email"));
                                    lm.setNew_email_key(jsonObject.getString("new_email_key"));
                                    lm.setLast_ip(jsonObject.getString("last_ip"));
                                    lm.setLast_login(jsonObject.getString("last_login"));
                                    lm.setUsertype(jsonObject.getString("usertype"));
                                    lm.setSubscriptionID(jsonObject.getString("SubscriptionID"));
                                    lm.setCreated(jsonObject.getString("created"));
                                    lm.setModified(jsonObject.getString("modified"));

                                    sm.save(lm);

//                        loginModelArrayList.add(lm);
//                        userid = sm.getid();

                                    Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
//                        intent.putExtra("id", userid);
                                    startActivity(intent);
                                    finish();
                                    hideProgress();
                                    Toast.makeText(LoginActivity.this, "Login com Sucesso", Toast.LENGTH_SHORT).show();

                                } else if (Status.equals("0")) {
                                    hideProgress();
                                    Toast.makeText(LoginActivity.this, "Email ou palavra-passe inválido", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e1) {
                                hideProgress();
                                Toast.makeText(LoginActivity.this, "Email ou palavra-passe inválido", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }

        } else
        {
            UtilityMethod.showToast("Verifique a ligação de internet", this);
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
            progress = new ProgressDialog(this);
            progress.setMessage("A carregar...");
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            progress.show();
        } catch (Exception e) {

        }
    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                //TODO:
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                //(just doing it here for now, note that with this code, no explanation is shown)
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
                    {
                        showGPSDisabledAlertToUser();
                    }


                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {


                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "acesso negado", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    private void showGPSDisabledAlertToUser()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("A Geolocalização encontra-se inativa no seu dispositivo.Pretende ativar?")
                .setCancelable(false)
                .setPositiveButton("Definições", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);

                      //  mapFrag.getMapAsync(MainActivity.this);
                    }
                });
        alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

}
