package com.example.billapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Dashboard extends AppCompatActivity {
    //added drawer
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    // Drawer variable
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // drawer variable assignment
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.dashboard) {
                    drawerLayout.close();
                } else if (id == R.id.custVend) {
                    drawerLayout.close();
                    startActivity(new Intent(getApplicationContext(), CustomerVendor.class));
                } else if (id == R.id.product) {
                    drawerLayout.close();
                    startActivity(new Intent(getApplicationContext(), Products.class));
                } else if (id == R.id.saleInvoice) {
                    Toast.makeText(Dashboard.this, "Sale Invoice", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.purchaseInvoice) {
                    Toast.makeText(Dashboard.this, "Purchase Invoice", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.logout) {
                    editor.clear();
                    editor.commit();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
                return false;
            }
        });

        // sharedPreference
        sharedPreferences = getSharedPreferences("sharedData", MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}