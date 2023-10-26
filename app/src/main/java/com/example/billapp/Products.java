package com.example.billapp;

import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

public class Products extends AppCompatActivity {
    LinearLayout layout;
    Button addBtn, addData;
    EditText pnameET, pdescET, priceET;
    ArrayList<ItemModel> dataList;
    RecyclerView rcvData;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView ndfTxt;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        // sharedPreference
        sharedPreferences = getSharedPreferences("sharedData", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        id = sharedPreferences.getString("sharedEmail", "");

        loadingDialog ld = new loadingDialog(Products.this);
        getAllData();

        addBtn = findViewById(R.id.addBtn);
        layout = findViewById(R.id.addForm);
        rcvData = findViewById(R.id.rcvData);

        pnameET = findViewById(R.id.pnameET);
        pdescET = findViewById(R.id.pdescET);
        priceET = findViewById(R.id.priceET);
        ndfTxt = findViewById(R.id.ndfTxt);

        addData = findViewById(R.id.addData);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layout.getVisibility() == view.GONE) {
                    layout.setVisibility(VISIBLE);
                } else {
                    layout.setVisibility(view.GONE);
                }
            }
        });

        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pname = pnameET.getText().toString().trim();
                String pdesc = pdescET.getText().toString().trim();
                String price = priceET.getText().toString().trim();

                if (pname.isEmpty()) {
                    pnameET.setError("Required");
                    return;
                }
                //check email format and emptiness
                if (pdesc.isEmpty()) {
                    pdescET.setError("Required");
                    return;
                }
                if (price.isEmpty()) {
                    priceET.setError("Required");
                    return;
                }
                if (!price.matches("[0-9]*")) {
                    priceET.setError("Enter valid price");
                    return;
                }

                ld.startLoading();
                ItemModel data = new ItemModel(pname, pdesc, price, id);

                FirebaseFirestore.getInstance().collection("products").document().set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            ld.dismissDialog();
                            Toast.makeText(Products.this, "successfully Added", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Products.class));
                            finish();
                        } else {
                            ld.dismissDialog();
                            Toast.makeText(Products.this, "Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void getAllData() {
        dataList = new ArrayList<>();
        dataList.clear();
        FirebaseFirestore.getInstance().collection("products").whereEqualTo("id", id).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null) {
                    List<ItemModel> data = value.toObjects(ItemModel.class);
                    dataList.addAll(data);
                    rcvData.setLayoutManager(new LinearLayoutManager(Products.this));
                    rcvData.setAdapter(new ReadItemDataAdapter(Products.this, dataList));
                }
                if (value.isEmpty()) {
                    ndfTxt.setVisibility(VISIBLE);
                }
            }
        });
    }

}