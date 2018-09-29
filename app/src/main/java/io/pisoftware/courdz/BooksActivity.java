package io.pisoftware.courdz;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import  io.pisoftware.courdz.bookFragments.sst ;

public class BooksActivity extends AppCompatActivity {
    SharedPreferences preferences ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        if ( MainActivity.sharedPreferences .getString("sho3ba" , null) == "3olom"){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(new sst(), "sst")
                    .commit() ; }
        }
}
