package com.example.asus.homemdopao.Activity;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.homemdopao.Adapter.ProductsNameListAdapter;
import com.example.asus.homemdopao.Adapter.TodaysdeliveryAdapter;
import com.example.asus.homemdopao.FontManager;
import com.example.asus.homemdopao.Fragment.Todays_delivery;
import com.example.asus.homemdopao.Map.Constants;
import com.example.asus.homemdopao.Map.GeocodeAddressIntentService;
import com.example.asus.homemdopao.Model.ProductBean;
import com.example.asus.homemdopao.R;
import com.example.asus.homemdopao.Service.MapLocationService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Asus on 02/11/2016.
 */

public class Details extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    Location mLastLocation;
    String user_full_address;
    Marker mCurrLocationMarker;
    double lat, lng,dest_lat,dest_long;
    TextView name, address, phone, orderid, delivryboyid, orderdeliverydate,
            userid, userfullname, email, usermobileno, orderdeliverystatus, productid, productname, itemqty;

    String ordeid_set, userpostalcode, dliverboyid_set, orderdelidate_set, userid_set, userfullname_set, email_set,
            usermobile_set, productid_set, productname_set, itemqty_set, image_set, name_set, orderdelivstatus_set, user_address;
    TextView item_text, back_icon;
    TextView menuIcon;
    ImageView map_image, call_iv;
    AddressResultReceiver mResultReceiver;
    double deslat,user_lat;
    double deslng,user_long;
    double slat;
    double slng;
    static double dlng;
    static double dlat;
    String concat_address_postal;
    ListView listView;
    public static ListAdapter listAdapter;
    public static ArrayList<ProductBean> productBeen;
    public static ProductsNameListAdapter productsNameListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        startService(new Intent(getBaseContext(), MapLocationService.class));
        mResultReceiver = new AddressResultReceiver(null);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Typeface iconFont = FontManager.getTypeface(this, FontManager.FONTAWESOME);

        back_icon = (TextView) findViewById(R.id.back);
        FontManager.markAsIconContainer(back_icon, iconFont);
        back_icon.setText(R.string.fa_back);
        back_icon.setVisibility(View.VISIBLE);
        listView = (ListView) findViewById(R.id.products);
        productBeen = new ArrayList<>();
        productBeen = Todays_delivery.productBeen1;
        // ArrayList<ProductBean> localities = savedInstanceState.getParcelableArrayList("");
        //   productBeen = new ArrayList<>();
        listView.setAdapter(new ProductsNameListAdapter(productBeen, this));
        //  listView.setAdapter(productsNameListAdapter);
        //   setListViewHeightBasedOnChildren(listView);

        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Details.this, DashBoardActivity.class);
                startActivity(i);
                finish();
            }
        });

        DashBoardActivity.heading_txt.setVisibility(View.GONE);
        map_image = (ImageView) findViewById(R.id.mapid);
        /*item_image=(ImageView)findViewById(R.id.item_image);
        orderid = (TextView) findViewById(R.id.orderid);*/

        orderdeliverydate = (TextView) findViewById(R.id.orderdeliverydate);

        userfullname = (TextView) findViewById(R.id.username);
        email = (TextView) findViewById(R.id.email);
        address = (TextView) findViewById(R.id.useraddress);
        usermobileno = (TextView) findViewById(R.id.phoneno);
        call_iv = (ImageView) findViewById(R.id.call_iv);
        // item_text=(TextView)findViewById(R.id.item_name_detail_txt);
        orderdeliverystatus = (TextView) findViewById(R.id.orderdeliverystatus);
       // user_full_address = "303,anmol tower,greater kailash road old plasia,indore";
        // phone=(TextView)findViewById(R.id.phoneno);

        // infoText = (TextView) findViewById(R.id.infoText);

        //   productname=(TextView)findViewById(R.id.productname);

        menuIcon = (TextView) findViewById(R.id.menuIcon);
        menuIcon.setVisibility(View.GONE);

        Intent b = this.getIntent();
      /*  ordeid_set=b.getStringExtra("OrderID");
        orderid.setText(ordeid_set);
*/
        orderdelidate_set = b.getStringExtra("OrderDeliveryDate");
        orderdeliverydate.setText(orderdelidate_set);

        userfullname_set = b.getStringExtra("UserFullName");
        userfullname.setText(userfullname_set);

        email_set = b.getStringExtra("email");
        email.setText(email_set);

        usermobile_set = b.getStringExtra("UserMobileNo");
        usermobileno.setText(usermobile_set);

      /*  productname_set = b.getStringExtra("ProductName");
        item_text.setText(productname_set);*/

        user_address = b.getStringExtra("UserAddress");
        if (user_address.equals("null")) {
            address.setText("");
        } else {
            address.setText(user_address.trim());
        }


        userpostalcode = b.getStringExtra("UserAddressPostalCode");

        concat_address_postal = user_address.concat(userpostalcode);

        ;
      /*  image_set = b.getStringExtra("ProductImage");
        try
        {
            if (image_set!=null&!image_set.equals("ProductImage"))
            {
              Picasso.with(Details.this).load(image_set).into(item_image);
            }
        }
        catch (Exception ae)
        {

        }*/

        orderdelivstatus_set = b.getStringExtra("OrderDeliveryStatus");
        orderdeliverystatus.setText(orderdelivstatus_set);

        call_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call(usermobile_set);
            }
        });
        usermobileno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call(usermobile_set);
            }
        });
        Intent intent = new Intent(this, GeocodeAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        // intent.putExtra(Constants.FETCH_TYPE_EXTRA, fetchType);

        intent.putExtra(Constants.LOCATION_NAME_DATA_EXTRA, userpostalcode);

        startService(intent);


        user_address = user_address.replace("\r\n", "").trim();
        getLocationFromAddress(this,user_address);

       // Toast.makeText(this, "Addrss----"+user_address, Toast.LENGTH_LONG).show();

       // Toast.makeText(this, "UserAddd"+user_address, Toast.LENGTH_SHORT).show();
      //  Toast.makeText(this, "latt"+dest_lat, Toast.LENGTH_SHORT).show();
       // Toast.makeText(this, "Long"+dest_long, Toast.LENGTH_SHORT).show();
       // Toast.makeText(this, "destination"+deslat+"  "+deslng, Toast.LENGTH_LONG).show();

        map_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slat = MapLocationService.lat;
                slng = MapLocationService.lng;
                Toast.makeText(Details.this, ""+slat+ ""+slng, Toast.LENGTH_SHORT).show();
                if (dest_lat == 0.000 || dest_lat == 0.00||dest_lat==0.0) {
                    alertforNoAddress();
                }
                else {
                    //   LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                   Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + slat + "," + slng + "&daddr=" + dest_lat + "," + dest_long));
                  //  Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("https://maps.googleapis.com/maps/api/directions?origin=" + slat + "," + slng + "&destination=" + dest_lat + "," + dest_long));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent);
                 // Toast.makeText(Details.this, "destination"+dest_lat+dest_long, Toast.LENGTH_SHORT).show();
                }
               /* Intent i=new Intent(Details.this,CurrentLoactionMapActivity.class);
                startActivity(i);
                finish();*/

            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(Details.this, DashBoardActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;


        //Place current location marker
        lat = location.getLatitude();
        lng = location.getLongitude();
        // SetLocation();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
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

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {


                    }

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, final Bundle resultData) {
            if (resultCode == Constants.SUCCESS_RESULT) {
                final Address address = resultData.getParcelable(Constants.RESULT_ADDRESS);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        /* infoText.setText("Latitude: " + address.getLatitude() + "\n" +
                                "Longitude: " + address.getLongitude() + "\n" +
                                "Address: " + resultData.getString(Constants.RESULT_DATA_KEY));*/

                        deslat = address.getLatitude();
                        deslng = address.getLongitude();
                    }
                });
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        // infoText.setVisibility(View.VISIBLE);
                        //  infoText.setText(resultData.getString(Constants.RESULT_DATA_KEY));
                    }
                });
            }
        }
    }


    public void alertforNoAddress() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Details.this);

        alertDialog.setMessage("Morada não encontrada");
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // DashBoardActivity.this.finish();
                // Write your code here to invoke YES event

            }
        });


        // Showing Alert Message
        alertDialog.setCancelable(false);
        alertDialog.show();
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

    public void Call(String no) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + no));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(callIntent);
    }

    public void getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        LatLng p1 = null;

        strAddress = strAddress.replace("(\\r|\\n)", "");

        try {
            // May throw an IOException
            List<android.location.Address> address = coder.getFromLocationName(strAddress, 5);
            if (address == null && address.size() < 0) {
                Toast.makeText(context, "Morada não encontrada", Toast.LENGTH_SHORT).show();

            }
            android.location.Address location = address.get(0);
            deslat = location.getLatitude();
            deslng = location.getLongitude();
            p1 = new LatLng(location.getLatitude(), location.getLongitude());

        } catch (IOException ex) {

            ex.printStackTrace();
        }

    }
}
