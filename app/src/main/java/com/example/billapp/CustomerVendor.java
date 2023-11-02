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
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView ndfTxt, radioTxtbox;
    String id;
    RadioGroup radioGroup;
    RadioButton selectedRadioBtn;
    String selectedText, collection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_vendor);

        // sharedPreference
        sharedPreferences = getSharedPreferences("sharedData", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        id = sharedPreferences.getString("sharedEmail", "");

        loadingDialog ld = new loadingDialog(CustomerVendor.this);

        getAllData();

        addBtn = findViewById(R.id.addBtn);
        layout = findViewById(R.id.addForm);
        rcvData = findViewById(R.id.rcvData);

        cnameET = findViewById(R.id.comname);
        cnoET = findViewById(R.id.cno);
        emailET = findViewById(R.id.email);
        addressET = findViewById(R.id.address);
        noteET = findViewById(R.id.note);
        ndfTxt = findViewById(R.id.ndfTxt);
        radioTxtbox = findViewById(R.id.radiotxtbox);

        radioGroup = findViewById(R.id.rdg);

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
                String cname = cnameET.getText().toString().trim();
                String cno = cnoET.getText().toString().trim();
                String email = emailET.getText().toString().trim();
                String address = addressET.getText().toString().trim();
                String note = noteET.getText().toString().trim();

                int selectedRadioBtnID = radioGroup.getCheckedRadioButtonId();

                if (selectedRadioBtnID == -1) {
                    radioTxtbox.setError("Select a/c Type");
                    return;
                }
                if (selectedRadioBtnID != -1) {
                    selectedRadioBtn = findViewById(selectedRadioBtnID);
                    selectedText = selectedRadioBtn.getText().toString();
                }

                if (cname.isEmpty()) {
                    cnameET.setError("Required");
                    return;
                }

                if (cno.length() != 10) {
                    cnoET.setError("length must be 10");
                    return;
                }

                if (!cno.matches("[0-9]*")) {
                    cnoET.setError("Enter numbers only");
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
                CustVendModel data = new CustVendModel(cname, cno, email, address, note, id, selectedText);

                if (selectedText.equals("Customer")) {
                    collection = "customer";
                } else if (selectedText.equals("Vendor")) {
                    collection = "vendor";
                } else {
                    collection = "customerVendor";
                }

                FirebaseFirestore.getInstance().collection(collection).document().set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
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

        // customer
        FirebaseFirestore.getInstance().collection("customer").whereEqualTo("idEmail", id).addSnapshotListener(new EventListener<QuerySnapshot>() {
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

        // vendor
        FirebaseFirestore.getInstance().collection("vendor").whereEqualTo("idEmail", id).addSnapshotListener(new EventListener<QuerySnapshot>() {
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

        // vendor
        FirebaseFirestore.getInstance().collection("customerVendor").whereEqualTo("idEmail", id).addSnapshotListener(new EventListener<QuerySnapshot>() {
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