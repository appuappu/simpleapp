package com.example.apoorvanaikodi.location;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int REQUEST_LOCATION=1;
    Button button;
    TextView textView;
    LocationManager locationManager;
    String latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);

        textView=(TextView)findViewById(R.id.text_location);

        button=(Button)findViewById(R.id.button_location);

        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            buildAlertMessageNoGps();
        }else if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            getLocation();
        }
    }

    private void getLocation(){
        if (ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission

                (MainActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);

        }else{

             Location location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location!=null){

                double latti=location.getLatitude();
                double longi=location.getLongitude();
                latitude=String.valueOf(latti);
                longitude=String.valueOf(longi);

                textView.setText("your current location is" + "\n" + "Latitude="+ latitude + "\n" + "Longitude=" +longitude);
            }else {
                Toast.makeText(this,"Unable to trace your location",Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected void buildAlertMessageNoGps(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Please turn ON your GPS connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog,final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert=builder.create();
        alert.show();
    }
}
