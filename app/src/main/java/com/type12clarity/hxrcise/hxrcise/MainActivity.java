package com.type12clarity.hxrcise.hxrcise;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import android.R.*;
import android.bluetooth.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import zephyr.android.HxMBT.*;

import com.type12clarity.hxrcise.hxrcise.Helpers.MediaMetadata;
import com.type12clarity.hxrcise.hxrcise.Services.BTService;
import com.type12clarity.hxrcise.hxrcise.Services.DBHandler;
import com.type12clarity.hxrcise.hxrcise.Helpers.Buttons;
import com.type12clarity.hxrcise.hxrcise.Services.MediaService;


public class MainActivity extends AppCompatActivity {

    BluetoothAdapter adapter = null;
    BTClient _bt;
    ZephyrProtocol _protocol;
    BTService _BTService;
    private final int HEART_RATE = 0x100;
    private final int INSTANT_SPEED = 0x101;
    private final int DISTANCE = 0x102;
    private DBHandler dbh;
    String currentSongPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getReadPerm();
        setContentView(R.layout.activity_main);
        IntentFilter filter = new IntentFilter("android.bluetooth.device.action.PAIRING_REQUEST");
        this.getApplicationContext().registerReceiver(new BTBroadcastReceiver(), filter);
        IntentFilter filter2 = new IntentFilter("android.bluetooth.device.action.BOND_STATE_CHANGED");
        this.getApplicationContext().registerReceiver(new BTBondReceiver(), filter2);
        TextView tv = (TextView) findViewById(R.id.TVHR);
        Button btnConnect = (Button) findViewById(R.id.ButtonStart);
        dbh = DBHandler.getInstance(this);
        if (btnConnect != null) {
            btnConnect.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    String BhMacID = "00:07:80:9D:8A:E8";
                    //String BhMacID = "00:07:80:88:F6:BF";
                    adapter = BluetoothAdapter.getDefaultAdapter();

                    Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();

                    if (pairedDevices.size() > 0) {
                        for (BluetoothDevice device : pairedDevices) {
                            if (device.getName().startsWith("HXM")) {
                                BluetoothDevice btDevice = device;
                                BhMacID = btDevice.getAddress();
                                break;

                            }
                        }
                    }

                    //BhMacID = btDevice.getAddress();
                    BluetoothDevice Device = adapter.getRemoteDevice(BhMacID);
                    String DeviceName = Device.getName();
                    _bt = new BTClient(adapter, BhMacID);
                    _BTService = new BTService(Newhandler, Newhandler);
                    _bt.addConnectedEventListener(_BTService);

                    TextView tv1 = /*(EditText)*/ findViewById(R.id.TVHR);
                    tv1.setText("000");

                    tv1 = /*(EditText)*/ findViewById(R.id.TVHR);
                    tv1.setText("0.0");

                    //tv1 = 	(EditText)findViewById(R.id.labelSkinTemp);
                    //tv1.setText("0.0");

                    //tv1 = 	(EditText)findViewById(R.id.labelPosture);
                    //tv1.setText("000");

                    //tv1 = 	(EditText)findViewById(R.id.labelPeakAcc);
                    //tv1.setText("0.0");
                    if (_bt.IsConnected()) {
                        _bt.start();
                        //TextView tv = (TextView) findViewById(R.id.TVHR);
                        //String ErrorText = "Connected to HxM " + DeviceName;
                        //tv.setText(ErrorText);

                        //Reset all the values to 0s

                    } else {
                        /*
                        TextView tv = (TextView) findViewById(R.id.TVHR);
                        String ErrorText = "Unable to Connect !";
                        tv.setText(ErrorText);
                        */
                    }
                }
            });
        }
        Button btnDisconnect = (Button) findViewById(R.id.ButtonStop);
        if (btnDisconnect != null) {
            btnDisconnect.setOnClickListener(new OnClickListener() {
                @Override
                /*Functionality to act if the button DISCONNECT is touched*/
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    /*Reset the global variables*/

                    /*
                    TextView tv = (TextView) findViewById(R.id.TVHR);
                    String ErrorText = "Disconnected from HxM!";
                    tv.setText(ErrorText);
                    */

                    /*This disconnects listener from acting on received messages*/
                    _bt.removeConnectedEventListener(_BTService);
                    /*Close the communication with the device & throw an exception if failure*/
                    _bt.Close();

                }
            });
        }
        TextView uritv = (TextView) findViewById(R.id.TVUri);
        uritv.setText(String.valueOf(mmd.getURI().get(2)));
        currentSongPath = String.valueOf(mmd.getURI().get(2));
        Button btnMPlay = (Button) findViewById(R.id.ButtonMPlay);
        if (btnMPlay != null) {
            btnMPlay.setOnClickListener(new OnClickListener() {
                @Override

                public void onClick(View v) {
                    startService(new Intent(this, mmd.getURI().get(2)));

                }
            });
        }
        Button btnMStop = (Button) findViewById(R.id.ButtonMStop);
        if (btnMStop != null) {
            btnMStop.setOnClickListener(new OnClickListener() {
                @Override

                public void onClick(View v) {


                }
            });
        }

        Button btnMSwitch = (Button) findViewById(R.id.ButtonMSwitch);
        if (btnMSwitch != null) {
            btnMSwitch.setOnClickListener(new OnClickListener() {
                @Override

                public void onClick(View v) {


                }
            });
        }

    }

    private void getReadPerm() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }

    private class BTBondReceiver extends BroadcastReceiver {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle b = intent.getExtras();
                BluetoothDevice device = adapter.getRemoteDevice(b.get("android.bluetooth.device.extra.DEVICE").toString());
                Log.d("Bond state", "BOND_STATED = " + device.getBondState());
            }
        }

        private class BTBroadcastReceiver extends BroadcastReceiver {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("BTIntent", intent.getAction());
                Bundle b = intent.getExtras();
                Log.d("BTIntent", b.get("android.bluetooth.device.extra.DEVICE").toString());
                Log.d("BTIntent", b.get("android.bluetooth.device.extra.PAIRING_VARIANT").toString());
                try {
                    BluetoothDevice device = adapter.getRemoteDevice(b.get("android.bluetooth.device.extra.DEVICE").toString());
                    Method m = BluetoothDevice.class.getMethod("convertPinToBytes", new Class[] {String.class} );
                    byte[] pin = (byte[])m.invoke(device, "1234");
                    m = device.getClass().getMethod("setPin", new Class [] {pin.getClass()});
                    Object result = m.invoke(device, pin);
                    Log.d("BTTest", result.toString());
                } catch (SecurityException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (NoSuchMethodException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }


        final  Handler Newhandler = new Handler(){
            public void handleMessage(Message msg)
            {
                TextView tv;
                switch (msg.what)
                {
                    case HEART_RATE:
                        String HeartRatetext = msg.getData().getString("HeartRate");
                        tv = /*(EditText)*/findViewById(R.id.TVHR);
                        System.out.println("Heart Rate Info is "+ HeartRatetext);
                        if (tv != null)tv.setText(HeartRatetext);
                        break;

                    case INSTANT_SPEED:
                        String InstantSpeedtext = msg.getData().getString("InstantSpeed");
                        tv = /*(EditText)*/findViewById(R.id.TVSpd);
                        if (tv != null)tv.setText(InstantSpeedtext);
                        break;

                    case DISTANCE:
                        String Distancetext = msg.getData().getString("Distance");
                        tv = /*(EditText)*/findViewById(R.id.TVDist);
                        if (tv != null)tv.setText(Distancetext);
                        break;

                }
            }

        };
    }
