package com.example.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    EditText txtID, txtName, txtAdd, txtConNo;
    Button btnSave, btnShow, btnUpdate, btnDelete;
    DatabaseReference dbRef;
    Student std;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtID = findViewById(R.id.EtID);
        txtName = findViewById(R.id.EtName);
        txtAdd = findViewById(R.id.EtAddress);
        txtConNo = findViewById(R.id.EtConNo);

        btnSave = findViewById(R.id.BtnSave);
        btnShow = findViewById(R.id.Btnshow);
        btnUpdate = findViewById(R.id.BtnUpdate);
        btnDelete = findViewById(R.id.BtnDelete);

        std = new Student();


    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, Card.class);
        startActivity(intent);
    }

    private void clearControls() {
        txtID.setText("");
        txtName.setText("");
        txtAdd.setText("");
        txtConNo.setText("");
    }

    public void onClick(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        startActivity(intent);
        dbRef = FirebaseDatabase.getInstance().getReference().child("Student");
            try {
                if (TextUtils.isEmpty(txtID.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please enter an ID", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(txtName.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please enter a Name", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(txtAdd.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please enter an Address", Toast.LENGTH_SHORT).show();
                else {
                    std.setID((txtID.getText().toString().trim()));
                    std.setName(txtName.getText().toString().trim());
                    std.setAddress(txtAdd.getText().toString().trim());
                    std.setConNo(Integer.parseInt(txtConNo.getText().toString().trim()));

                    //dbRef.push().setValue(std);
                    dbRef.child("-LoZ6BGIi2XuQ1afudr0").setValue(std);
                    Toast.makeText(getApplicationContext(), "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                    clearControls();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Invalid Contact Number", Toast.LENGTH_SHORT).show();
            }
        }

        public void onClick1(View view){
            Intent intent = new Intent(this, DisplayMessageActivity.class);
            startActivity(intent);
        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Student").child("-LoZ6BGIi2XuQ1afudr0");
        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    txtID.setText(dataSnapshot.child("id").getValue().toString());
                    txtName.setText(dataSnapshot.child("name").getValue().toString());
                    txtAdd.setText(dataSnapshot.child("address").getValue().toString());
                    txtConNo.setText(dataSnapshot.child("conNo").getValue().toString());
                }
                else
                    Toast.makeText(getApplicationContext(),"No Source to Display", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        }


        public void onClick2(View view){

        DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Student");
        updRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("-LoZ6BGIi2XuQ1afudr0")){
                    try{
                        std.setID(txtID.getText().toString().trim());
                        std.setName(txtName.getText().toString().trim());
                        std.setAddress(txtAdd.getText().toString().trim());
                        std.setConNo(Integer.parseInt(txtConNo.getText().toString().trim()));

                        dbRef = FirebaseDatabase.getInstance().getReference().child("Student").child("-LoZ6BGIi2XuQ1afudr0");
                        dbRef.setValue(std);
                        clearControls();

                        Toast.makeText(getApplicationContext(),"Data updated Successfully",Toast.LENGTH_SHORT).show();
                    }
                    catch (NumberFormatException e){
                        Toast.makeText(getApplicationContext(),"Invalid Contact Number",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(getApplicationContext(),"No Source to Update",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        }

        public void onClick3(View view){
        DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Student");
        delRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("-LoZ6BGIi2XuQ1afudr0")){
                dbRef = FirebaseDatabase.getInstance().getReference().child("Student").child("-LoZ6BGIi2XuQ1afudr0");
                dbRef.removeValue();
                clearControls();
                Toast.makeText(getApplicationContext(), "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
            }
            else
                 Toast.makeText(getApplicationContext(), "No Source Delete", Toast.LENGTH_SHORT).show();
        }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        }
}