package com.smaple.socialevening;

import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    LocationManager mLocationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        checkGPS();
    }

    private void checkGPS(){
        boolean gps_provider = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if ((gps_provider == false) || (provider.equals(""))){
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        } else {
            GeoLocationManager.getInstance().initializeConnectLocation(getApplicationContext());
            Thread startActivity = new Thread(){
                public void run(){
                    try{
                        Thread.sleep(5000);
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    } catch(Exception ex) {

                    }
                }
            };
            startActivity.start();
        }
    }
}
