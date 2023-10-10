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
    Button logout;
    TextView username;
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

        // variables
//        logout = findViewById(R.id.logoutBtn);
//        username = findViewById(R.id.userName);

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
                    Toast.makeText(Dashboard.this, "Dashboard", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.custVend) {
                    startActivity(new Intent(getApplicationContext(), CustomerVendor.class));
                } else if (id == R.id.product) {
                    Toast.makeText(Dashboard.this, "Product", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.saleInvoice) {
                    Toast.makeText(Dashboard.this, "Sale Invoice", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.purchaseInvoice) {
                    Toast.makeText(Dashboard.this, "Purchase Invoice", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.inwardP) {
                    Toast.makeText(Dashboard.this, "Inward Payment", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.outwardP) {
                    Toast.makeText(Dashboard.this, "Outward Payment", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.quotation) {
                    Toast.makeText(Dashboard.this, "Quotation", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.proformaInvoice) {
                    Toast.makeText(Dashboard.this, "Proforma Invoice", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.creditNote) {
                    Toast.makeText(Dashboard.this, "Credit Note", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.debitNote) {
                    Toast.makeText(Dashboard.this, "Debit Note", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        // sharedPreference
        sharedPreferences =

                getSharedPreferences("sharedData", MODE_PRIVATE);

        editor = sharedPreferences.edit();

        String userName = sharedPreferences.getString("sharedEmail", "");

//        username.setText(userName);

        // logout button for logout user from app
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                editor.clear();
//                editor.commit();
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                finish();
//            }
//        });
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