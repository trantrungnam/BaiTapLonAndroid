package com.example.baitaplonandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.baitaplonandroid.ui.User.User_Detail;
import com.example.baitaplonandroid.ui.User.User_Login;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ImageView imageView;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        View headerView = navigationView.getHeaderView(0);

        // CONFIG FRAGMENT

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        // TEXT USER NAME
        TextView txtUserName = headerView.findViewById(R.id.edtHeaderUserName);
        SharedPreferences sharedPreferences = getSharedPreferences("restaurant", 0);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
        String username = sharedPreferences.getString("username", "").equals("") ? "Login Here" : sharedPreferences.getString("username", "");
        txtUserName.setText(username);

        // TEXT EMAIL
        TextView textViewEmail = headerView.findViewById(R.id.textViewEmail);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
        String email = sharedPreferences.getString("username", "").equals("") ? "email@gmail.com" : sharedPreferences.getString("username", "") + "@gmail.com";
        textViewEmail.setText(email);

        // HANDLE IMAGE HEADER //
        imageView = headerView.findViewById(R.id.buttonView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Click here", Toast.LENGTH_SHORT)
                        .show();
                SharedPreferences sharedPreferences = getSharedPreferences(
                        "restaurant",
                        Context.MODE_PRIVATE);
                int userId = sharedPreferences.getInt("id", -1);
                String username = sharedPreferences.getString("username", "");

                if ((userId == -1 && username.equals(""))) {

                    transaction = manager.beginTransaction();
                    User_Login user_login = new User_Login();
                    Toast.makeText(MainActivity.this, "Show", Toast.LENGTH_SHORT).show();
                    transaction.replace(R.id.nav_host_fragment, user_login);
                    transaction.addToBackStack(null);
                } else {
                    transaction = manager.beginTransaction();
                    sharedPreferences.getString("username", "");
                    sharedPreferences.getInt("id", -1);
                    User_Detail userDetail = new User_Detail();
                    transaction.replace(R.id.nav_host_fragment, userDetail);
                    transaction.addToBackStack(null);
                    Toast.makeText(MainActivity.this, "Connected Success", Toast.LENGTH_SHORT).show();
                }
                transaction.commit();
            }
        });


        //

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_food , R.id.nav_bill, R.id.nav_share, R.id.nav_send, R.id.nav_management)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment);


        NavigationUI.setupActionBarWithNavController(this, navController,
                mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
