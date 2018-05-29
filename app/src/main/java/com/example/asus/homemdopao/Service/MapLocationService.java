package com.example.asus.homemdopao.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.asus.homemdopao.Server.InternetConnection;
import com.example.asus.homemdopao.Server.Webservice;
import com.example.asus.homemdopao.SessionManager;
import com.example.asus.homemdopao.UtilityMethod;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 17-11-2016.
 */

public class MapLocationService  extends Service
{
    public static final int Three_MINUTES = 10000; // 120 seconds
    public static Boolean isRunning = false;
    public LocationManager mLocationManager;
    public LocationUpdaterListener mLocationListener;
    public Location previousBestLocation = null;
    String id;
    SessionManager sm;
    public static double lat,lng;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationUpdaterListener();
        sm = new SessionManager(getBaseContext());
        id = sm.getid();
        super.onCreate();
        //Toast.makeText(this, "latitude"+lat, Toast.LENGTH_SHORT).show();
        //Toast.makeText(MapLocationService.this, "longitude"+lng, Toast.LENGTH_SHORT).show();
    }

    Handler mHandler = new Handler();
    Runnable mHandlerTask = new Runnable(){
        @Override
        public void run() {
            if (!isRunning) {
                startListening();
            }
           // Toast.makeText(getBaseContext(), "service", Toast.LENGTH_SHORT).show();
            mHandler.postDelayed(mHandlerTask, Three_MINUTES);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandlerTask.run();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        stopListening();
        mHandler.removeCallbacks(mHandlerTask);
        super.onDestroy();
    }

    private void startListening() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (mLocationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER))
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);

            if (mLocationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER))
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
        }
        isRunning = true;
    }

    private void stopListening() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationManager.removeUpdates(mLocationListener);
        }
        isRunning = false;
    }

    public class LocationUpdaterListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            if (isBetterLocation(location, previousBestLocation)) {
                previousBestLocation = location;
                lat=location.getLatitude();
                lng=location.getLongitude();

             //  Toast.makeText(MapLocationService.this, "latitude"+lat, Toast.LENGTH_SHORT).show();
             //   Toast.makeText(MapLocationService.this, "longitude"+lng, Toast.LENGTH_SHORT).show();
                try {
                    SetLocation();
                    // Script to post location data to server..
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    stopListening();
                }
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }

        protected boolean isBetterLocation(Location location, Location currentBestLocation) {
            if (currentBestLocation == null) {
                // A new location is always better than no location
                return true;
            }

            // Check whether the new location fix is newer or older
            long timeDelta = location.getTime() - currentBestLocation.getTime();
            boolean isSignificantlyNewer = timeDelta > Three_MINUTES;
            boolean isSignificantlyOlder = timeDelta < -Three_MINUTES;
            boolean isNewer = timeDelta > 0;

            // If it's been more than two minutes since the current location, use the new location
            // because the user has likely moved
            if (isSignificantlyNewer) {
                return true;
                // If the new location is more than two minutes older, it must be worse
            } else if (isSignificantlyOlder) {
                return false;
            }

            // Check whether the new location fix is more or less accurate
            int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
            boolean isLessAccurate = accuracyDelta > 0;
            boolean isMoreAccurate = accuracyDelta < 0;
            boolean isSignificantlyLessAccurate = accuracyDelta > 200;

            // Check if the old and new location are from the same provider
            boolean isFromSameProvider = isSameProvider(location.getProvider(), currentBestLocation.getProvider());

            // Determine location quality using a combination of timeliness and accuracy
            if (isMoreAccurate) {
                return true;
            } else if (isNewer && !isLessAccurate) {
                return true;
            } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
                return true;
            }
            return false;
        }

        /**
         * Checks whether two providers are the same
         */
        private boolean isSameProvider(String provider1, String provider2) {
            if (provider1 == null) {
                return provider2 == null;
            }
            return provider1.equals(provider2);
        }
        public void SetLocation() {
            if (InternetConnection.isInternetOn(getBaseContext())) {
                  //  Toast.makeText(getBaseContext(), "inffg", Toast.LENGTH_SHORT).show();
                Ion.with(getBaseContext()).load(Webservice.Location)
                        .setBodyParameter("id", id)
                      //  .setBodyParameter("longitude", String.valueOf(lng))
                      //  .setBodyParameter("latitude", String.valueOf(lat))
                        .setBodyParameter("longitude", Double.toString(lng))
                         .setBodyParameter("latitude", Double.toString(lat))
                        .asString().setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                      //  Log.d("result", result);

                     //   Toast.makeText(MapLocationService.this, ""+id, Toast.LENGTH_SHORT).show();
                      //  Toast.makeText(MapLocationService.this, "serice"+lng, Toast.LENGTH_SHORT).show();
                      //  Toast.makeText(MapLocationService.this, "serice"+lat, Toast.LENGTH_SHORT).show();
                       // Toast.makeText(MapLocationService.this, ""+lng, Toast.LENGTH_SHORT).show();
                        if (UtilityMethod.isStringNullOrBlank(result)) {
                            try {
                                UtilityMethod.alertforServerError(getApplicationContext());
                            }
                            catch (Exception e1){
                               // UtilityMethod.showToast("Something Wrong",getBaseContext());
                            }

                           //
                        } else {
                            try {
                                    //Toast.makeText(getBaseContext(), "lat"+lng, Toast.LENGTH_SHORT).show();
                                    //Toast.makeText(getBaseContext(), "lng"+lat, Toast.LENGTH_SHORT).show();
                                JSONObject jsonObject = new JSONObject(result);

                                String status = jsonObject.getString("status");
                                String msg = jsonObject.getString("msg");

                                if (status.equals("1")) {
                        /* if(i==1){
                             status_txt.setText("Progress");
                         }
                        // lm.setOrderDeliveryStatus("Completed");
                         status_txt.setText("Completed");*/
                                    //  todaysdeliveryBeen.notifyAll();
                                    //     Toast.makeText(getBaseContext(), ""+msg, Toast.LENGTH_SHORT).show();
                                } else if (status.equals("0")) {

                                }
                            } catch (JSONException e1) {
                                //Toast.makeText(getBaseContext(), "" + e1.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }else {
                UtilityMethod.showToast("Verifique a ligação de internet",getBaseContext());
            }
        }
    }


}