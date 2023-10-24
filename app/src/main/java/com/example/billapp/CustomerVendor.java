package com.example.billapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CustomerVendor extends AppCompatActivity {
    LinearLayout layout;
    Button addBtn, addData;
    EditText cnameET, cnoET, emailET, addressET, noteET;
    ArrayList<CustVendModel> dataList;
    RecyclerView rcvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_vendor);

        getAllData();

        loadingDialog ld = new loadingDialog(CustomerVendor.this);

        addBtn = findViewById(R.id.addcustBtn);
        layout = findViewById(R.id.addForm);
        rcvData = findViewById(R.id.rcvData);

        cnameET = findViewById(R.id.comname);
        cnoET = findViewById(R.id.cno);
        emailET = findViewById(R.id.email);
        addressET = findViewById(R.id.address);
        noteET = findViewById(R.id.note);

        addData = findViewById(R.id.addData);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layout.getVisibility() == view.GONE) {
                    layout.setVisibility(view.VISIBLE);
                } else {
                    layout.setVisibility(view.GONE);
                }
            }
        });
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cname = cnameET.getText().toString().trim();
                String cno = cnoET.getText().toString().trim();
                String email = emailET.getText().toString().trim();
                String address = addressET.getText().toString().trim();
                String note = noteET.getText().toString().trim();

                if (cname.isEmpty()) {
                    cnameET.setError("Required");
                    return;
                }

                if (cno.length() != 10) {
                    cnoET.setError("length must be 10");
                    return;
                }

                if (!cno.matches("[0-9]*")) {
                    cnoET.setError("Enter ");
                    return;
                }

                //check email format and emptiness
                if (email.isEmpty()) {
                    emailET.setError("Required");
                    return;
                }
                if (!email.matches("^[a-z0-9+_.-]+@(.+)$")) {
                    emailET.setError("Enter valid Email");
                    return;
                }
                if (address.isEmpty()) {
                    addressET.setError("Required");
                    return;
                }
                if (note.isEmpty()) {
                    noteET.setError("Required");
                    return;
                }

                ld.startLoading();
                CustVendModel data = new CustVendModel(cname, cno, email, address, note);

                FirebaseFirestore.getInstance().collection("customerVendor").document().set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            ld.dismissDialog();
                            Toast.makeText(CustomerVendor.this, "successfully Added", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), CustomerVendor.class));
                            finish();
                        } else {
                            ld.dismissDialog();
                            Toast.makeText(CustomerVendor.this, "Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void getAllData() {
        dataList = new ArrayList<>();
        dataList.clear();
        FirebaseFirestore.getInstance().collection("customerVendor").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error == null) {
                    List<CustVendModel> data = value.toObjects(CustVendModel.class);
                    dataList.addAll(data);
                    rcvData.setLayoutManager(new LinearLayoutManager(CustomerVendor.this));
                    rcvData.setAdapter(new ReadDataAdapter(CustomerVendor.this, dataList));
                }
            }
        });
    }
}