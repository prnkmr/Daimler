import android.app.Activity;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Karthick on 7/30/2015.
 */
public class LocationTracking  implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
Double lattitude,longitude;
GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
 LocationTracking(Activity caller){

     buildGoogleApiClient(caller);
     mGoogleApiClient.connect();


 }
    protected synchronized void buildGoogleApiClient(Activity caller) {
        mGoogleApiClient = new GoogleApiClient.Builder(caller)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if(mLastLocation !=null){
           lattitude=mLastLocation.getLatitude();
           longitude=mLastLocation.getLongitude();

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
