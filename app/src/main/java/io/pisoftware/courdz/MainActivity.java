package io.pisoftware.courdz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import io.pisoftware.courdz.objects.user ;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
   public static final int RC_SIGN_IN = 0 ;
    BottomNavigationView navigationView ;
    SpaceNavigationView spaceNavigationView ;
    FirebaseAuth firebaseAuth ;
    FirebaseAuth.AuthStateListener  authStateListener ;
    FirebaseDatabase usersDatabase ;
    FirebaseUser user ;
    DatabaseReference usersRefrence ;
    String User_Id ;
    public static SharedPreferences sharedPreferences ;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //------------------------------------------------------------------------------------------------------
        // الشغل بتاع الشريط اللي تحت
        spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.setCentreButtonIcon(R.drawable.baseline_home_white_24);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("Notifications", R.drawable.baseline_add_alert_white_24));
        spaceNavigationView.addSpaceItem(new SpaceItem("Web", R.drawable.baseline_language_white_24));
        spaceNavigationView.addSpaceItem(new SpaceItem("Calendar", R.drawable.baseline_calendar_today_white_24));
        spaceNavigationView.addSpaceItem(new SpaceItem("Calculator", R.drawable.calc));
        spaceNavigationView.showIconOnly();
        spaceNavigationView.setCentreButtonIconColorFilterEnabled(false);
        spaceNavigationView.changeCurrentItem(4);
        spaceNavigationView.setCentreButtonColor(ContextCompat.getColor(this, R.color.colorPrimary));
        // كدا شغل الشريط اللي تحت خلص
        //------------------------------------------------------------------------------------------------------

        //------------------------------------------------------------------------------------------------------
        // شغل الشريط اللي عالجنب
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        // كدا شغل الشريط الل تحت خلص
        //------------------------------------------------------------------------------------------------------
        // مدعكة الفايربيز تبدأ من هنا ومش هتنتهي XD
        firebaseAuth = LoginActivity.auth ;
        usersDatabase = FirebaseDatabase.getInstance() ;
        usersRefrence = usersDatabase.getReference().child("users") ;
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                 user = firebaseAuth.getCurrentUser() ;
                if (user != null){
                    // المستخدم مسجل دخوله

                }

                else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();

                }


            }
        };

        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
        if (isFirstRun)
        {
            FirebaseUser user = firebaseAuth.getCurrentUser() ;
            User_Id =  user.getUid() ;
            user user1 = new user("false" , User_Id) ;
            usersRefrence.push().setValue(user1) ;
            SharedPreferences.Editor editor = wmbPreference.edit();
            editor.putBoolean("FIRSTRUN", false);
            editor.commit();
        }
        // this listener will be called when there is change in firebase user session
        sharedPreferences = getSharedPreferences("sho3ba" , MODE_PRIVATE) ;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.signout) {
                firebaseAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.aolom) {
            SharedPreferences.Editor editor=  sharedPreferences.edit() ;
            editor.putString("sho3ba" , "3olom") ;
            editor.commit() ;
            // Handle the camera action
        } else if (id == R.id.maths) {
            SharedPreferences.Editor editor=  sharedPreferences.edit() ;
            editor.putString("sho3ba" , "maths") ;
            editor.commit() ;
        } else if (id == R.id.software) {
            SharedPreferences.Editor editor=  sharedPreferences.edit() ;
            editor.putString("sho3ba" , "software") ;
            editor.commit() ;
        } else if (id == R.id.economy) {
            SharedPreferences.Editor editor=  sharedPreferences.edit() ;
            editor.putString("sho3ba" , "economy") ;
            editor.commit() ;
        } else if (id == R.id.philo){
            SharedPreferences.Editor editor=  sharedPreferences.edit() ;
            editor.putString("sho3ba" , "philo") ;
            editor.commit() ;
        }  else if (id == R.id.foregin){
        SharedPreferences.Editor editor=  sharedPreferences.edit() ;
        editor.putString("sho3ba" , "lo8a") ;
        editor.commit() ;
    }


    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        spaceNavigationView.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
