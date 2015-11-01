package com.smaple.socialevening;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class GeoLocationManager implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener{

	private static GeoLocationManager mInstance;
	
	private static final long INTERVAL = 1000 * 10;
	private static final long FASTEST_INTERVAL = 1000 * 5;
	private static GoogleApiClient mClientAPI;
	private Location mLocation;
	private LocationRequest locationRequest;
	private locationChangeListener listener;
	
	public static GeoLocationManager getInstance(){
		if (mInstance == null){
			mInstance = new GeoLocationManager();
		}
		return mInstance;
	}
	
	private GeoLocationManager(){
		
	}
	
	public void initializeConnectLocation(Context context){
		if (mClientAPI == null) {
			mClientAPI = new GoogleApiClient.Builder(context)
					.addConnectionCallbacks(mInstance)
					.addOnConnectionFailedListener(mInstance)
					.addApi(LocationServices.API)
					.build();
			mClientAPI.connect();
		}
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		mLocation = LocationServices.FusedLocationApi.getLastLocation(mClientAPI);
	}
	
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		
	}
	
	public Location getLastLocation(){
		return mLocation;
	}
	
	private void initializeLocationUpdates(){
		if (locationRequest == null){
			locationRequest = new LocationRequest();
			locationRequest.setInterval(INTERVAL);
			locationRequest.setMaxWaitTime(FASTEST_INTERVAL);
			locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
		}
	}

	public void startLocationUpdates(locationChangeListener listener){
		this.listener = listener;
		initializeLocationUpdates();
		LocationServices.FusedLocationApi.requestLocationUpdates(mClientAPI, locationRequest, this);
	}
	
	public void stopLocationUpdates(){
		LocationServices.FusedLocationApi.removeLocationUpdates(mClientAPI, this);
	}
	
	@Override
	public void onConnectionSuspended(int cause) {
		
	}

	@Override
	public void onLocationChanged(Location location) {
		listener.onLocationChanged(location);
	}
	
	public static interface locationChangeListener{
		void onLocationChanged(Location location);
	}
	
	public void clearAllLocationUpdates(){
		stopLocationUpdates();
		mClientAPI = null;
		mLocation = null;
		locationRequest = null;
		listener = null;
	}
}
