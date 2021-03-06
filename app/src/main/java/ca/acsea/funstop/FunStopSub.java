package ca.acsea.funstop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.material.navigation.NavigationView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.acsea.funstop.event.Event;
import ca.acsea.funstop.sponsorquiz.QuizStart;


public class FunStopSub extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FragmentTransaction transaction;

    FragmentManager fragmentManager = getSupportFragmentManager();

    ImageView imageView;
    FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference ref=FirebaseDatabase.getInstance().getReference();
    CheckBox station1;
    CheckBox station2;
    CheckBox station3;
    CheckBox station4;
    CheckBox station5;
    CheckBox station6;
    CheckBox station7;
    CheckBox station8;
    CheckBox station9;
    CheckBox station10;
    CheckBox station11;
    CheckBox station12;
    CheckBox Korean;
    CheckBox taiwanese;
    CheckBox chinese;
    CheckBox vietnamese;
    CheckBox jackPoole;

    Boolean station1B;
    Boolean station2B;
    Boolean station3B;
    Boolean station4B;
    Boolean station5B;
    Boolean station6B;
    Boolean station7B;
    Boolean station8B;
    Boolean station9B;
    Boolean station10B;
    Boolean station11B;
    Boolean station12B;
    Boolean KoreanB;
    Boolean taiwaneseB;
    Boolean chineseB;
    Boolean vietnameseB;
    Boolean jackPooleB;

    String qrValue = "";
    SharedPreferences prefs;
    int points;
    List<CheckBox> arrayList;
    List<Boolean> arrayListBool;

    // User data instance
    User mUser;
    Event event;
    Map map;
    QuizStart quiz;
    About about;



    public void onCreate(Bundle saveInstanceState){
        setTitle(Html.fromHtml("<font color='#e6b773'>LunarFun - Vancouver</font>"));

        super.onCreate(saveInstanceState);
        setContentView(R.layout.fragment_fun_stop_sub);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
        Intent intent = getIntent();



        //Initialize user object
//        mUser = (User) intent.getSerializableExtra("user");
        // Initialize page objects
        event = new Event(fragmentManager);
        map = new Map();
        quiz = new QuizStart(fragmentManager, mUser, ref);
        //myPoint = new MyPoint(currentUser);
        //funStop = new FunStop(fragmentManager, currentUser, ref);
        about = new About();



        if (intent.getStringExtra("source").equals("QrCodeScanner")) {
            qrValue = intent.getStringExtra("qrValue");
        }

        prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = prefs.getString("userObject", "");
        mUser = gson.fromJson(json, User.class);
        points=(int)mUser.getPoint();



        //Display user's email in the navigation bar
        if(mUser != null) {
            NavigationView navView = findViewById(R.id.nav_view);
            View header = navView.getHeaderView(0);
            TextView userEmail = header.findViewById(R.id.userEmail);
            userEmail.setText(mUser.getEmail());
        }


        System.out.println("What is the value: "+  mUser.getPoint()); //0


        station1 = findViewById(R.id.station1);
        station2 = findViewById(R.id.station2);
        station3 = findViewById(R.id.station3);
        station4 = findViewById(R.id.station4);
        station5 = findViewById(R.id.station5);
        station6 = findViewById(R.id.station6);
        station7 = findViewById(R.id.station7);
        station8 = findViewById(R.id.station8);
        station9 = findViewById(R.id.station9);
        station10 = findViewById(R.id.station10);
        station11 = findViewById(R.id.station11);
        station12 = findViewById(R.id.station12);
        Korean = findViewById(R.id.korean);
        taiwanese = findViewById(R.id.taiwanese);
        chinese = findViewById(R.id.chinese);
        vietnamese = findViewById(R.id.vietnamese);
        jackPoole = findViewById(R.id.jackPoole);



        // arrayList = Arrays.asList(Korean, chinese, loneWolf1);
        arrayList = new ArrayList<CheckBox>();
        arrayList.add(Korean);
        arrayList.add(chinese);
        arrayList.add(taiwanese);
        arrayList.add(vietnamese);

        arrayList.add(station1);
        arrayList.add(station2);
        arrayList.add(station3);
        arrayList.add(station4);
        arrayList.add(station5);
        arrayList.add(station6);
        arrayList.add(station7);
        arrayList.add(station8);
        arrayList.add(station9);
        arrayList.add(station10);
        arrayList.add(station11);
        arrayList.add(station12);
        arrayList.add(jackPoole);

        station1B = prefs.getBoolean("station1B", false);
        station2B = prefs.getBoolean("station2B", false);
        station3B = prefs.getBoolean("station3B", false);
        station4B = prefs.getBoolean("station4B", false);
        station5B = prefs.getBoolean("station5B", false);
        station6B = prefs.getBoolean("station6B", false);
        station7B = prefs.getBoolean("station7B", false);
        station8B = prefs.getBoolean("station8B", false);
        station9B = prefs.getBoolean("station9B", false);
        station10B = prefs.getBoolean("station10B", false);
        station11B = prefs.getBoolean("station11B", false);
        station12B = prefs.getBoolean("station12B", false);
        KoreanB = prefs.getBoolean("KoreanB", false);
        taiwaneseB = prefs.getBoolean("taiwaneseB", false);
        chineseB = prefs.getBoolean("chineseB", false);
        vietnameseB = prefs.getBoolean("vietnameseB", false);
        jackPooleB = prefs.getBoolean("jackPooleB", false);

        arrayListBool = new ArrayList<Boolean>();
        arrayListBool.add(KoreanB);
        arrayListBool.add(chineseB);
        arrayListBool.add(taiwaneseB);
        arrayListBool.add(vietnameseB);

        arrayListBool.add(station1B);
        arrayListBool.add(station2B);
        arrayListBool.add(station3B);
        arrayListBool.add(station4B);
        arrayListBool.add(station5B);
        arrayListBool.add(station6B);
        arrayListBool.add(station7B);
        arrayListBool.add(station8B);
        arrayListBool.add(station9B);
        arrayListBool.add(station10B);
        arrayListBool.add(station11B);
        arrayListBool.add(station12B);
        arrayListBool.add(jackPooleB);
//        points = prefs.getInt("point", 0);


        int i;
        for (i = 0; i < arrayList.size(); i++) {
            arrayList.get(i).setEnabled(false);
            if (arrayListBool.get(i)) {
                arrayList.get(i).setChecked(true);
            }
        }

        checkQRCodeValue();
        onClickQR();
    }

    public void onPause() {
        super.onPause();
        save();
        SharedPreferences sharedPreferences  = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor prefs = sharedPreferences.edit();
        mUser.setPoint(points);
        Gson gson = new Gson();
        String json = gson.toJson(mUser);
        prefs.putString("userObject", json);
        prefs.apply();
    }


    public void onClickQR() {
        imageView= findViewById(R.id.qrScanner);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("current user" + currentUser);
                System.out.println("ref" + ref);

                Intent i = new Intent(FunStopSub.this, QrCodeScanner.class);
                i.putExtra("previous", "FunStopSub");
                startActivity(i);

            }
        });
    }

    public void onBackPressed() {
        Intent intent = new Intent(FunStopSub.this, MainActivity.class);
        startActivity(intent);
    }


    public void save() {
        SharedPreferences.Editor prefsEditor = prefs.edit();

        prefsEditor.putBoolean("KoreanB", KoreanB);
        prefsEditor.putBoolean("chineseB", chineseB);
        prefsEditor.putBoolean("taiwaneseB", taiwaneseB);
        prefsEditor.putBoolean("vietnameseB", vietnameseB);

        prefsEditor.putBoolean("station1B", station1B);
        prefsEditor.putBoolean("station2B", station2B);
        prefsEditor.putBoolean("station3B", station3B);
        prefsEditor.putBoolean("station4B", station4B);
        prefsEditor.putBoolean("station5B", station5B);
        prefsEditor.putBoolean("station6B", station6B);
        prefsEditor.putBoolean("station7B", station7B);
        prefsEditor.putBoolean("station8B", station8B);
        prefsEditor.putBoolean("station9B", station9B);
        prefsEditor.putBoolean("station10B", station10B);
        prefsEditor.putBoolean("station11B", station11B);
        prefsEditor.putBoolean("station12B", station12B);
        prefsEditor.putBoolean("jackPooleB", jackPooleB);
        prefsEditor.putInt("points", points);

        prefsEditor.apply();
    }
    private void updatePoints(int point, String operation){
        if (operation.equals("Add")) {
            mUser.setPoint(mUser.getPoint()+point);
            points=(int) mUser.getPoint();
            System.out.println("What is the value of point in UpdatePoint method "+mUser.getPoint());
//            points = mUser.set() + point;
//            ref.child(currentUser.getUid()).child("point").setValue(points);
        }

    }
    public void setUp () {
        System.out.println("Station one status:" + station1B);
        int i;
        for (i = 0; i < arrayListBool.size(); i++) {
            arrayListBool.set(i, prefs.getBoolean("element" + i, false));
        }
        System.out.println("Station one status:" + station1B);
        points = prefs.getInt("point", 0);
    }


    private void checkQRCodeValue () {
       switch (qrValue) {
            case "station1":
                if(!station1B) {
                    updatePoints(15, "Add");
                }else {
                    Toast.makeText(this,"You have scanned this station!",Toast.LENGTH_SHORT).show();
                }
                station1.setChecked(true);
                station1B = true;
                break;
            case "station2":
                if(!station2B) {
                    updatePoints(15, "Add");
                }else {
                    Toast.makeText(this,"You have scanned this station!",Toast.LENGTH_SHORT).show();
                }
                station2.setChecked(true);
                station2B = true;
                break;
            case "station3":
                if(!station3B) {
                    updatePoints(15, "Add");
                }else {
                    Toast.makeText(this,"You have scanned this station!",Toast.LENGTH_SHORT).show();
                }
                station3.setChecked(true);
                station3B = true;
                break;
            case "station4":
                if(!station4B) {
                    updatePoints(15, "Add");
                }else {
                    Toast.makeText(this,"You have scanned this station!",Toast.LENGTH_SHORT).show();
                }
                station4.setChecked(true);
                station4B = true;
                break;
            case "station5":
                if(!station5B) {
                    updatePoints(15, "Add");
                }else {
                    Toast.makeText(this,"You have scanned this station!",Toast.LENGTH_SHORT).show();
                }
                station5.setChecked(true);
                station5B = true;
                break;
            case "station6":
                if(!station6B) {
                    updatePoints(15, "Add");
                }else {
                    Toast.makeText(this,"You have scanned this station!",Toast.LENGTH_SHORT).show();
                }
                station6.setChecked(true);
                station6B = true;
                break;
            case "station7":
                if(!station7B) {
                    updatePoints(15, "Add");
                }else {
                    Toast.makeText(this,"You have scanned this station!",Toast.LENGTH_SHORT).show();
                }
                station7.setChecked(true);
                station7B = true;
                break;
            case "station8":
                if(!station8B) {
                    updatePoints(15, "Add");
                }else {
                    Toast.makeText(this,"You have scanned this station!",Toast.LENGTH_SHORT).show();
                }
                station8.setChecked(true);
                station8B = true;
                break;
            case "station9":
                if(!station9B) {
                    updatePoints(15, "Add");
                }else {
                    Toast.makeText(this,"You have scanned this station!",Toast.LENGTH_SHORT).show();
                }
                station9.setChecked(true);
                station9B = true;
                break;
            case "station10":
                if(!station10B) {
                    updatePoints(15, "Add");
                }else {
                    Toast.makeText(this,"You have scanned this station!",Toast.LENGTH_SHORT).show();
                }
                station10.setChecked(true);
                station10B = true;
                break;
            case "station11":
                if(!station11B) {
                    updatePoints(15, "Add");
                }else {
                    Toast.makeText(this,"You have scanned this station!",Toast.LENGTH_SHORT).show();
                }
                station11.setChecked(true);
                station11B = true;
                break;
            case "station12":
                if(!station12B) {
                    updatePoints(15, "Add");
                }else {
                    Toast.makeText(this,"You have scanned this station!",Toast.LENGTH_SHORT).show();
                }
                station12.setChecked(true);
                station12B = true;
                break;
            case "chinese":
                if(!chineseB) {
                    updatePoints(15, "Add");
                }else {
                    Toast.makeText(this,"You have scanned this station!",Toast.LENGTH_SHORT).show();
                }
                chinese.setChecked(true);
                chineseB = true;
                break;
            case "Korean":
                if(!KoreanB) {
                    updatePoints(15, "Add");
                }else {
                    Toast.makeText(this,"You have scanned this station!",Toast.LENGTH_SHORT).show();
                }
                Korean.setChecked(true);
                KoreanB = true;
                break;
            case "taiwanese":
                if(!taiwaneseB) {
                    updatePoints(15, "Add");
                }else {
                    Toast.makeText(this,"You have scanned this station!",Toast.LENGTH_SHORT).show();
                }
                taiwanese.setChecked(true);
                taiwaneseB = true;
                break;
            case "vietnamese":
                if(!vietnameseB) {
                    updatePoints(15, "Add");
                }else {
                    Toast.makeText(this,"You have scanned this station!",Toast.LENGTH_SHORT).show();
                }
                vietnamese.setChecked(true);
                vietnameseB = true;
                break;
            case "JackPoole":
                if(!jackPooleB) {
                    updatePoints(40, "Add");
                }else {
                    Toast.makeText(this,"You have scanned this station!",Toast.LENGTH_SHORT).show();
                }
                jackPoole.setChecked(true);
                jackPooleB = true;
                break;
            default:
                if(!qrValue.isEmpty()) {
                    Toast.makeText(this,"This is not a valid QR code",Toast.LENGTH_SHORT).show();
                }
        }
    }



    @Override
    public boolean onNavigationItemSelected (MenuItem item){
        // Handle navigation view item clicks here.
        transaction = fragmentManager.beginTransaction();
        int id = item.getItemId();

        if (id == R.id.nav_event) {
//            transaction.replace(R.id.frameLayout, event).commitAllowingStateLoss();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_map) {

            //intent
            transaction.replace(R.id.frameLayout, map).commitAllowingStateLoss();
        } else if (id == R.id.nav_funstop) {
            Intent intent = new Intent(this, FunStop.class);
            startActivity(intent);

            //transaction.replace(R.id.frameLayout, funStop).commitAllowingStateLoss();
        } else if (id == R.id.nav_quiz) {
            transaction.replace(R.id.frameLayout, quiz).commitAllowingStateLoss();
        } else if (id == R.id.nav_point) {
            Intent intent = new Intent(this, MyPoint.class);
            intent.putExtra("source", "navbar");
            startActivity(intent);
            //  transaction.replace(R.id.frameLayout, myPoint).commitAllowingStateLoss();
        } else if (id == R.id.nav_about) {
            transaction.replace(R.id.frameLayout, about).commitAllowingStateLoss();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}