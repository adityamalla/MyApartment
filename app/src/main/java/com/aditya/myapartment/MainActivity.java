package com.aditya.myapartment;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Base64;

public class MainActivity extends AppCompatActivity {
    public EditText phonenumber;
    public EditText password;
    public Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        phonenumber = (EditText) findViewById(R.id.phonenumber);
        password = (EditText) findViewById(R.id.password);
        login_button = (Button) findViewById(R.id.login_button);
        TextView newUser = (TextView)findViewById(R.id.newUser);
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent myIntent = new Intent(MainActivity.this,
                        RegistrationActivity.class);
                startActivity(myIntent);
            }
        });
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validatePhoneNumber() || !validatePassword()){
                    return;
                }else{
                    isUser();
                }
                final Intent myIntent = new Intent(MainActivity.this,
                        RegistrationActivity.class);
                startActivity(myIntent);
            }
        });
    }

    private void isUser() {
        String userEnteredPhoneNumber = phonenumber.getText().toString().trim();
        String userEnteredPassword = password.getText().toString().trim();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = databaseReference.orderByChild("phone").equalTo(userEnteredPhoneNumber);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    phonenumber.setError(null);
                    String passwordFromDb = snapshot.child(userEnteredPhoneNumber).child("password").getValue(String.class);
                    Base64.Decoder decoder = Base64.getDecoder();
                    String decodedPwd = new String(decoder.decode(passwordFromDb));
                    if(decodedPwd.equals(userEnteredPassword)){
                        password.setError(null);
                        String nameFromDb = snapshot.child(userEnteredPhoneNumber).child("name").getValue(String.class);
                        String secondaryFromDb = snapshot.child(userEnteredPhoneNumber).child("secondaryPhone").getValue(String.class);
                        String flatNoFromDb = snapshot.child(userEnteredPhoneNumber).child("flatNo").getValue(String.class);
                        String vehicleNosFromDb = snapshot.child(userEnteredPhoneNumber).child("vehicleNos").getValue(String.class);
                        String ownerOrTenant = snapshot.child(userEnteredPhoneNumber).child("ownerOrTenant").getValue(String.class);
                        final Intent myIntent = new Intent(MainActivity.this,
                                RegistrationActivity.class);
                        myIntent.putExtra("name", nameFromDb);
                        myIntent.putExtra("phone", userEnteredPhoneNumber);
                        myIntent.putExtra("flatNo", flatNoFromDb);
                        myIntent.putExtra("vehicleNosFromDb", vehicleNosFromDb);
                        myIntent.putExtra("secondaryPhone", secondaryFromDb);
                        myIntent.putExtra("ownerOrTenant", ownerOrTenant);
                        startActivity(myIntent);
                    }else{
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                }else{
                    phonenumber.setError("Phone Number not registered");
                    phonenumber.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean validatePhoneNumber(){
        String val = phonenumber.getText().toString();
        if(val.isEmpty()){
            phonenumber.setError("Please enter phone number");
            return false;
        }else if(val.trim().length()<10){
            phonenumber.setError("Please enter valid phone number");
            return false;
        }else{
            phonenumber.setError(null);
            return  true;
        }
    }
    private boolean validatePassword(){
        String val = password.getText().toString();
        if(val.isEmpty()){
            password.setError("Please enter password");
            return false;
        }else{
            password.setError(null);
            return  true;
        }
    }
}