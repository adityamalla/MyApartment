package com.aditya.myapartment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HomeActivity extends AppCompatActivity {
    public EditText helloText;
    public Button signOut;
    String name = "";
    String phone = "";
    String secondaryPhone = "";
    String flatNo = "";
    String vehicleNo = "";
    String ownerortenant = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        helloText = (EditText)findViewById(R.id.helloText);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        secondaryPhone = intent.getStringExtra("secondaryPhone");
        flatNo = intent.getStringExtra("flatNo");
        vehicleNo = intent.getStringExtra("vehicleNosFromDb");
        ownerortenant = intent.getStringExtra("ownerOrTenant");
        helloText.setText("Hello "+name+"");
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent myIntent = new Intent(HomeActivity.this,
                        MainActivity.class);
                startActivity(myIntent);
            }
        });

    }
}