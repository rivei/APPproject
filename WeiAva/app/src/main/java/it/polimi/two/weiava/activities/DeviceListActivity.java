package it.polimi.two.weiava.activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import it.polimi.two.weiava.R;
import it.polimi.two.weiava.models.Schedule;

public class DeviceListActivity extends AppCompatActivity {
    //widgets
    private Button btnPaired;
    //private ListView devicelist;
    private Button btnStart;
    private Button btnSave;
    private TextView resultView;
    private TextView btstatusView;

    //Bluetooth
    private BluetoothAdapter myBluetooth = null;

    private ProgressDialog progress;
    private BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    Handler bluetoothIn;
    final int handlerState = 0;        				 //used to identify handler message
    private StringBuilder recDataString = new StringBuilder();

    // SPP UUID service
    private static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ConnectedThread mConnectedThread;

    // MAC-address of Bluetooth module (you must edit this line)
    private static String mBTaddress = "20:16:09:07:83:73";


    private DatabaseReference newRef;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDBRef;
    private Schedule schedule;
    private String mUserId;

    private int mValue;
    private String mTestType;


    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        Intent intent = getIntent();
        mTestType = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);


        //Calling widgets
        btnPaired = (Button)findViewById(R.id.button_connect);
        //devicelist = (ListView)findViewById(R.id.btlistView);
        btnStart = (Button)findViewById(R.id.button_start);
        btnSave = (Button)findViewById(R.id.button_save);
        resultView = (TextView)findViewById(R.id.result_text);
        btstatusView = (TextView)findViewById(R.id.btstatus);

        btnStart.setEnabled(false);
        btnSave.setEnabled(false);

        bluetoothIn = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == handlerState) {										//if message is what we want
                    String readMessage = (String) msg.obj;                                                                // msg.arg1 = bytes from connect thread
                    Log.e("Receiving1",readMessage);
                    recDataString.append(readMessage);      								//keep appending to string until ~
                    int endOfLineIndex = recDataString.indexOf("e");                    // determine the end-of-line
                    if (endOfLineIndex > 0) {                                           // make sure there data before ~
                        String dataInPrint = recDataString.substring(0, endOfLineIndex);    // extract string
                        Log.e("Receiving2",dataInPrint);
                        if (recDataString.charAt(0) == 'b')								//if it starts with # we know it is what we are looking for
                        {
                            String result = dataInPrint.substring(1, 3);             //get sensor value from string between indices 1-5
                            resultView.setText(" Result is " + result );	//update the textviews with sensor values
                            mValue = Integer.valueOf(result);
                            btnSave.setEnabled(true);
                        }
                        recDataString.delete(0, recDataString.length()); 					//clear all string data
                    }
                }
            }
        };

        //if the device has bluetooth
        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        if(myBluetooth == null)
        {
            //Show a mensag. that the device has no bluetooth adapter
            Toast.makeText(getApplicationContext(), "Bluetooth Device Not Available", Toast.LENGTH_LONG).show();

            //finish apk
            finish();
        }
        else if(!myBluetooth.isEnabled())
        {
            //Ask to the user turn the bluetooth on
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBTon,1);
        }

        if (!isOnline()) {
            Toast.makeText(DeviceListActivity.this, "Please check your connection. \n " +
                    "Database update failed!", Toast.LENGTH_SHORT).show();
        }

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDBRef = FirebaseDatabase.getInstance().getReference();

        if(mFirebaseUser == null){
            //TODO: load login view
        }
        else{
            mUserId = mFirebaseUser.getUid();
        }

        btnPaired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //pairedDevicesList();
                new ConnectBT().execute(); //Call the class to connect
            }
        });


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send the command
                sendBTCommand();
                btnStart.setEnabled(false);
                //And wait for the returned data
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                schedule = new Schedule(System.currentTimeMillis(),mTestType);
                schedule.setScore(mValue);
                writeDatabase();
                Toast.makeText(DeviceListActivity.this, mTestType+" Successfully saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    @Override
    protected void onStop() {
        Disconnect();
        super.onStop();
    }

    @Override
    public void onPause()
    {
        Disconnect();
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();

/*        //Get MAC address from DeviceListActivity via intent
        Intent intent = getIntent();

        //Get the MAC address from the DeviceListActivty via EXTRA
        //address = "20:16:09:07:83:73";//intent.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);

        //create device and set the MAC address
        BluetoothDevice device = myBluetooth.getRemoteDevice("20:16:09:07:83:73");

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }
        // Establish the Bluetooth socket connection.
        try
        {
            btSocket.connect();
        } catch (IOException e) {
            try
            {
                btSocket.close();
            } catch (IOException e2)
            {
                //insert code to deal with this
            }
        }
        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        mConnectedThread.write("x");*/
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(myUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    private void sendBTCommand()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("MD".getBytes());
            }
            catch (IOException e)
            {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void writeDatabase(){
        String testId;

        newRef = mDBRef.child("Schedule").child(mUserId).push();
        testId = newRef.getKey();
        mDBRef.child("Schedule").child(mUserId).child(testId).setValue(schedule);
    }


    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            try
            {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_LONG).show();}
        }
        //finish(); //return to the first layout

    }

    @SuppressLint("StaticFieldLeak")
    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(DeviceListActivity.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(mBTaddress);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            } catch (IOException e) {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess) {
                Toast.makeText(getApplicationContext(),"Connection Failed. Is it a SPP Bluetooth? Try again.",Toast.LENGTH_LONG).show();
                finish();
            } else {
                btstatusView.setText("Device Connected");
                btnStart.setEnabled(true);
                btnPaired.setEnabled(false);
                Toast.makeText(getApplicationContext(),"Connected.",Toast.LENGTH_LONG).show();
                isBtConnected = true;
                mConnectedThread = new ConnectedThread(btSocket);
                mConnectedThread.start();
            }
            progress.dismiss();
        }
    }

    //create new class for connect thread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }


        public void run() {
            byte[] buffer = new byte[256];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);        	//read bytes from input buffer

                    String readMessage = new String(buffer, 0, bytes);
                    //Log.e("Receiving",readMessage);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }
        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                finish();

            }
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
