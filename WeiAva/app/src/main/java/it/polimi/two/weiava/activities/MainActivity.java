package it.polimi.two.weiava.activities;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import java.net.URI;
import java.util.Calendar;
import java.util.Iterator;
import java.util.TimeZone;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Reminders;
import android.provider.CalendarContract.Events;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import it.polimi.two.weiava.models.Schedule;
import it.polimi.two.weiava.R;
import it.polimi.two.weiava.services.DailyNotificationReceiver;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int ASK_MULTIPLE_PERMISSION_REQUEST_CODE = 12345;
    private static final String[] permissionsArray = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR
    };

    public static final String EXTRA_MESSAGE = "it.polimi.two.weiava.MESSAGE";
    public static final int REQUEST_CODE = 0;
    // Projection array. Creating indices for this array instead of doing
// dynamic lookups improves performance.
    public static final String[] EVENT_PROJECTION = new String[]{
            Calendars._ID,                           // 0
            Calendars.ACCOUNT_NAME,                  // 1
            Calendars.CALENDAR_DISPLAY_NAME,         // 2
            Calendars.OWNER_ACCOUNT                  // 3
    };

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;


    final MainActivity self = this;

    private static final String TAG = "MainActivity";
    private TextView userName;


    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDBRef;
    private String mUserId;
    //private String testType;
    NavigationView navigationView;

    private Query query1;
    private int notID = 0;

    private LinearLayout frgbuttons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headView = navigationView.getHeaderView(0);//inflateHeaderView(R.layout.nav_header_main);
        navigationView.setNavigationItemSelectedListener(this);

        frgbuttons=findViewById(R.id.frgbuttons);

        boolean permissionRequestNeeded = false;

        for (String s : permissionsArray) {
            if (ContextCompat.checkSelfPermission(this, s)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionRequestNeeded = true;
                break;
            }
        }

        if (permissionRequestNeeded) {
            //getSupportFragmentManager();
            ActivityCompat.requestPermissions(this, permissionsArray,
                    ASK_MULTIPLE_PERMISSION_REQUEST_CODE);
        } //else {

        //check internet
        if (!isOnline()) {
            Toast.makeText(self, "Please check your connection. \n " +
                    "Database update failed!", Toast.LENGTH_SHORT).show();
        }

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        userName = headView.findViewById(R.id.nav_username);

        if (mFirebaseUser == null) {
            // TODO: Not signed in, launch the Sign In activity
            loadLogInView();
        } else {
            userName.setText(mFirebaseUser.getEmail());
            mUserId = mFirebaseUser.getUid();
/*            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }*/
        }


           /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                *//*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*//*
                    Intent intent = new Intent(MainActivity.this, EmailActivity.class);
                    startActivity(intent);

                }
            });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //TODO: try to start the alarm service
        //Check if alarm is set; if not call the set Alarm function (for walking time)
        SetAlarm();
        //CheckLastTests("ADL");
        CheckLastTests("ADL", new MyCallback() {
            @Override
            public void onCallback(Long timestamp) {
                SetCalendar("ADL", timestamp, 30);
            }
        });
        CheckLastTests("GDS", new MyCallback() {
            @Override
            public void onCallback(Long timestamp) {
                SetCalendar("GDS", timestamp, 14);
            }
        });
        CheckLastTests("GripForce", new MyCallback() {
            @Override
            public void onCallback(Long timestamp) {
                SetCalendar("GripForce", timestamp, 30);
            }
        });
        CheckLastTests("BodyWeight", new MyCallback() {
            @Override
            public void onCallback(Long timestamp) {
                SetCalendar("BodyWeight", timestamp, 7);
            }
        });
        //}
    }

    public void FragmentQnrClick(View view) {
        Fragment myfragment;
        myfragment = new QnrFragment();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_switch, myfragment);
        fragmentTransaction.commit();

    }

    public void FragmentMsrClick(View view) {
        Fragment myfragment;
        myfragment = new MsrFragment();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_switch, myfragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_qnr) {
/*            Intent intent = new Intent(self, QnrActivity.class);
            startActivity(intent);*/
            Fragment myfragment;
            myfragment = new QnrFragment();

            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_switch, myfragment);
            fragmentTransaction.commit();

            // Handle the camera action
        } else if (id == R.id.nav_measure) {
/*            Intent intent = new Intent(self, MeasureActivity.class);
            startActivity(intent);*/
            Fragment myfragment;
            myfragment = new MsrFragment();

            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_switch, myfragment);
            fragmentTransaction.commit();

        }  else if (id == R.id.nav_Report) {
           /* Intent intent = new Intent(self,ReportActivity.class);
            startActivity(intent);*/
            frgbuttons.setVisibility(View.GONE);
            Fragment myfragment;
            myfragment = new ReportActivity();

            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_switch, myfragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_mydoc) {
           /* Intent intent = new Intent(self,DocProfActivity.class);
            startActivity(intent);*/
           frgbuttons.setVisibility(View.GONE);
            Fragment myfragment;
            myfragment = new DocProfActivity();

            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_switch, myfragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_logout) {
            //TODO: replace the SignInActivity
            mFirebaseAuth.signOut();
            startActivity(new Intent(self, SignInActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadLogInView() {
        //TODO: replace the SignInActivity
        Intent intent = new Intent(self, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //only for daily notification of walking time
    protected void SetAlarm() {
        Calendar curTime = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 2);

        if (curTime.after(calendar)) {
            calendar.add(Calendar.DATE, 1);
        }
        Intent intent = new Intent(getApplicationContext(), DailyNotificationReceiver.class);
        intent.putExtra(DailyNotificationReceiver.NOTIFICATION_ID, ++notID);
        //intent.putExtra(AlarmReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    /*
    Find the record of last tests if the next tests are in 3 days
     set up the alarm.
     */
    private void CheckLastTests(String qType, final MyCallback myCallback) {
        if (mUserId != null) {
            mDBRef = FirebaseDatabase.getInstance().getReference();
            query1 = mDBRef.child("Schedule")
                    .child(mUserId)
                    .orderByChild("qType")
                    .equalTo(qType)
                    .limitToLast(1);

            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Iterator<DataSnapshot> eventsIterator = dataSnapshot.getChildren().iterator();
                    if (eventsIterator.hasNext()) {
                        DataSnapshot firstChild = eventsIterator.next();
                        Schedule schedule = firstChild.getValue(Schedule.class);
                        //DateFormat.getDateInstance().format(resultdate).toString();
                        myCallback.onCallback(schedule.getTimestamp());
                        //Log.e(TAG, schedule.getTimestamp().toString());
                    } else {
                        //TODO: no record found, do it now??
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, databaseError.getMessage());
                }
            });
        }
    }

    private void SetCalendar(String testType, long testTimeStamp, int interval) {
        long calID = 1; //make sure it is add to the local calender
        String calOwner = "";//int [] calIds;
        long startMillis = 0;
        Calendar curTime = Calendar.getInstance();
        String evTitle;
        long evTime = 0;
        Calendar evDate = Calendar.getInstance();

        evTitle = testType + " test";

        Calendar testDate = Calendar.getInstance();
        testDate.setTimeInMillis(testTimeStamp);
        testDate.add(Calendar.DATE, interval);
        startMillis = testDate.getTimeInMillis();

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission..READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR}, MY_CAL_REQ);
//        }
        Cursor cur = null;
        String[] projection = {"_id", Calendars.ACCOUNT_TYPE, Calendars.OWNER_ACCOUNT, Calendars.ACCOUNT_NAME};
        String selection = Calendars.ACCOUNT_TYPE + " = ?";
        String[] selectionArgs = new String[]{CalendarContract.ACCOUNT_TYPE_LOCAL};
        Uri calendars;
        calendars = Uri.parse("content://com.android.calendar/calendars");

        ContentResolver cr = getContentResolver();
        //ContentResolver contentResolver = c.getContentResolver();
        //cur = cr.query(Calendars.CONTENT_URI, projection, selection, selectionArgs, null);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        cur = cr.query(Calendars.CONTENT_URI, projection, null, null, null);

        if (cur.moveToFirst()) {
            calID = cur.getLong(cur.getColumnIndex(Calendars._ID));
            calOwner = cur.getString(cur.getColumnIndex(Calendars.OWNER_ACCOUNT));
            cur.close();
        }
        //calID = cur.getLong(cur.getColumnIndex(Calendars._ID));
        //String calOwner = cur.getString(cur.getColumnIndex(Calendars.OWNER_ACCOUNT));

        String[] mProjection =
                {
                        "_id",
                        Events.TITLE,
                        Events.EVENT_LOCATION,
                        Events.DTSTART,
                        Events.DTEND,
                };
        String selection2 = Events.TITLE + " = ? ";
        String[] selectionArgs2 = new String[]{evTitle};


        Uri uri = Events.CONTENT_URI;
        cur = cr.query(uri, mProjection, selection2, selectionArgs2, null);

        if (curTime.before(testDate)) {
            //if next test day is in the future, check if the event exists in calendar
            if (cur.moveToNext()) {
                cur.moveToLast();
                evTime = Long.valueOf(cur.getString(cur.getColumnIndex(Events.DTSTART)));
                evDate.setTimeInMillis(evTime);
                //Log.i("StartTime", evTime);
                if (testDate.get(Calendar.YEAR) != evDate.get(Calendar.YEAR) ||
                        testDate.get(Calendar.DAY_OF_YEAR) != evDate.get(Calendar.DAY_OF_YEAR)) {
                    //if the testdate and the event date is the same day, do nothing;
                    SetEvent(cr, startMillis, evTitle, calID, calOwner);
                }
            } else {
                SetEvent(cr, startMillis, evTitle, calID, calOwner);
            }
        } else {//set the notification now
            NotificationManager notificationManager = (NotificationManager) self.getSystemService(Context.NOTIFICATION_SERVICE);
            Intent repeatintent;
            switch (testType) {
                case "GDS":
                    repeatintent = new Intent(self, QuizeActivity.class);
                    break;
                case "ADL":
                    repeatintent = new Intent(self, QuizeADLActivity.class);
                    break;
                case "BodyWeight":
                    repeatintent = new Intent(self, MeasureActivity.class);
                    break;
                case "GripForce":
                    repeatintent = new Intent(self, MeasureActivity.class);
                    break;
                default:
                    repeatintent = new Intent(self, MainActivity.class);
            }
            repeatintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //TODO: cancel the alarm?

            PendingIntent pendingIntent = PendingIntent.getActivity(self, 0, repeatintent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); //Set default notification ringtone
            Notification.Builder builder = new Notification.Builder(self)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent)
                    .setContentTitle(evTitle)//Title of the app
                    .setContentText("It is time to do the test.") //TODO: change to String constant
                    .setSound(defaultSoundUri)
                    .setAutoCancel(true);
            notificationManager.notify(++notID, builder.build());
        }

    }

    private void SetEvent(ContentResolver cr, long startTime, String evTitle, long calID, String calendarOwner) {
        try {
            ContentValues values = new ContentValues();
            TimeZone timeZone = TimeZone.getDefault();
            values.put(Events.DTSTART, startTime);
            values.put(Events.DTEND, startTime);
            values.put(Events.TITLE, evTitle);
            values.put(Events.DESCRIPTION, "It is time to do the test.");
            values.put(Events.CALENDAR_ID, calID);
            values.put(Events.ALL_DAY, true);
            values.put(Events.HAS_ALARM, 1);

            values.put(Events.ORGANIZER, calendarOwner);
            values.put(Events.HAS_ATTENDEE_DATA, 1);
            values.put(Events.AVAILABILITY, Events.AVAILABILITY_BUSY);
            values.put(Events.EVENT_TIMEZONE, timeZone.getID());

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Uri uri = cr.insert(Events.CONTENT_URI, values);
            Log.e(TAG, "Calender event added");

            // get the event ID that is the last element in the Uri
            int eventID = Integer.parseInt(uri.getLastPathSegment());
            //Set reminder for this event
            ContentValues reminders = new ContentValues();
            reminders.put(Reminders.MINUTES, 15);
            reminders.put(Reminders.EVENT_ID, eventID);
            reminders.put(Reminders.METHOD, Reminders.METHOD_ALERT);
            Uri uri2 = cr.insert(Reminders.CONTENT_URI, reminders);
            Log.e(TAG, "Event reminder added");
        } catch (Exception e) {
            //e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
    }

    private interface MyCallback {
        void onCallback(Long timestamp);
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}



