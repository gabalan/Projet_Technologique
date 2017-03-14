package android.projettechnologique.fr.capteurgps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.UnknownHostException;

import fr.norips.busAPI.Bus;
import fr.norips.busAPI.Capteur;

public class MainActivity extends AppCompatActivity  implements LocationListener {
    private Bus bus = null;
    private Capteur c = null;
    private Thread thread = null;
    public static final String IPV4_REGEX = "\\A(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}\\z";
    Handler handler=null;
    private final int REQUEST_PERMISSION_FINE_LOCATION=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_PERMISSION_FINE_LOCATION);
            }
        } else {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            try {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

        Handler handler=new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    Toast.makeText(MainActivity.this, "Unknown Host", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            }
        };
        final Button bConnect = (Button) findViewById(R.id.btConnect);
        bConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CapteurGPS","Click !");
                bConnect.setEnabled(false);
                TextView tv = (TextView) findViewById(R.id.etServer);
                String s = tv.getText().toString();
                Log.d("CapteurGPS",s);
                if (s.matches(IPV4_REGEX)) {
                    Log.d("CapteurGPS","Valid !");
                    thread = new Thread() {
                        @Override
                        public void run() {

                            try {
                                bus = new Bus(s, 7182);
                                c = new Capteur("GPS", "GPS android", bus);
                                Log.d("CapteurGPS","Capteur OK");
                            } catch (UnknownHostException e) {
                                handler.sendEmptyMessage(0);
                                e.printStackTrace();
                            } catch (IOException e) {
                                handler.sendEmptyMessage(1);
                                e.printStackTrace();
                            }
                        }
                    };

                    thread.start();
                }


            }
        });
        final Button bDeconnect = (Button) findViewById(R.id.btDeconnect);
        bDeconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bConnect.setEnabled(true);
                c = null;
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        Log.i("Geo_Location", "Latitude: " + latitude + ", Longitude: " + longitude);
        JSONObject obj = new JSONObject();
        try {
            obj.put("lat", latitude);
            obj.put("lng", longitude);
        }catch (JSONException e) {
            e.printStackTrace();
        }
        if(c != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    c.send(obj);
                }
            }).start();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_FINE_LOCATION: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    try {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to access to GPS", Toast.LENGTH_SHORT).show();
                    Log.d("CapteurGPS",String.valueOf(grantResults[0]));
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
