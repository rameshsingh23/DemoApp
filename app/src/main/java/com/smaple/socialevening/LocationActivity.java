package com.smaple.socialevening;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity implements GeoLocationManager.locationChangeListener{

    private static final int IDENTITY_REQUEST = 100;
    TextView mLocationTextView;
    ImageView mGroupImageView;
    EditText mGroupName;
    Bitmap mGroupImage;
    Button mCreateGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        mLocationTextView = (TextView)findViewById(R.id.locationText);
        mGroupImageView = (ImageView)findViewById(R.id.imageView);
        mGroupName = (EditText) findViewById(R.id.editText);
        mCreateGroup = (Button)findViewById(R.id.createGroup);
        mGroupImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent identity  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(identity, IDENTITY_REQUEST);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        GeoLocationManager.getInstance().startLocationUpdates(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == IDENTITY_REQUEST){
                mGroupImage = (Bitmap)data.getExtras().get("data");
                mGroupImageView.setImageBitmap(GraphicsUtil.getRoundedShape(mGroupImage, 80, 80));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onLocationChanged(Location location) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getLocality();
            String postalCode = addresses.get(0).getPostalCode();
            mLocationTextView.setText("Locaiton: " + address + " | " + postalCode);
        } catch(Exception e) {

        }
    }

    @Override
    protected void onStop() {
        GeoLocationManager.getInstance().stopLocationUpdates();
        super.onStop();
    }
}
