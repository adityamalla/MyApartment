package com.aditya.myapartment;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Base64;


@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class RegistrationActivity extends AppCompatActivity {
    ConstraintLayout header;
    EditText personName;
    EditText personPhone;
    EditText personSecondaryPhone;
    EditText flatNo;
    EditText password;
    EditText vehicleNo;
    EditText confirmPassword;
    RadioGroup ownedOrrented;
    Button regitser;
    Button addVehicleNo;
    private FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    User user;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.headerColor)));
        getSupportActionBar().setCustomView(R.layout.header);
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        header = (ConstraintLayout) findViewById(R.id.header);
        personName = (EditText)findViewById(R.id.personName);
        personPhone = (EditText)findViewById(R.id.personPhone);
        personSecondaryPhone = (EditText)findViewById(R.id.personSecondaryPhone);
        flatNo = (EditText)findViewById(R.id.personFlat);
        password = (EditText)findViewById(R.id.passwordReg);
        confirmPassword = (EditText)findViewById(R.id.confirmPasswordReg);
        vehicleNo = (EditText)findViewById(R.id.vehicleNumbers);
        regitser = (Button) findViewById(R.id.register);
        addVehicleNo = (Button) findViewById(R.id.vehicleNumberAddButton);
        ownedOrrented = (RadioGroup) findViewById(R.id.ownedOrrented);
        ChipGroup chipGroup = (ChipGroup) findViewById(R.id.vehicleNumbersChipView);
        regitser.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                try {
                    Log.e("testClick0>>","**");
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference("users");
                    String name = personName.getText().toString();
                    String phone = personPhone.getText().toString();
                    String secondaryPhone = personSecondaryPhone.getText().toString();
                    String flat = flatNo.getText().toString();
                    String pwd = "";
                    String confrmPwd = confirmPassword.getText().toString();
                    String vehicleNos = "";
                    String owner = "";
                    int count = ownedOrrented.getChildCount();
                    for (int r = 0; r < count; r++) {
                        View o = ownedOrrented.getChildAt(r);
                        if (o instanceof RadioButton) {
                            if(((RadioButton) o).isChecked()){
                                if (((RadioButton) o).getText().equals("Owner")) {
                                    owner = "1";
                                }else{
                                    owner = "0";
                                }
                            }
                        }
                    }
                    int chipCount = chipGroup.getChildCount();
                    for(int i=0;i<chipCount;i++){
                        View o = chipGroup.getChildAt(i);
                        if (o instanceof Chip) {
                            vehicleNos = vehicleNos+((Chip) o).getText().toString()+",";
                        }
                    }
                    if(vehicleNos.length()>0)
                    vehicleNos = vehicleNos.substring(0,vehicleNos.length()-1);
                    // Getting encoder

                        Base64.Encoder encoder = Base64.getEncoder();
                        // Creating byte array
                        byte byteArr[] = {1,2};
                        // encoding byte array
                        byte byteArr2[] = encoder.encode(byteArr);
                        byte byteArr3[] = new byte[5];                // Make sure it has enough size to store copied bytes
                        int x = encoder.encode(byteArr,byteArr3);    // Returns number of bytes written
                        pwd = encoder.encodeToString(password.getText().toString().getBytes());
                        // Getting decoder
                        // Base64.Decoder decoder = Base64.getDecoder();
                        // Decoding string
                        //String dStr = new String(decoder.decode(str));
                        //System.out.println("Decoded string: "+dStr);
                    Log.e("testClick1>>",name+"--"+phone+"--"+pwd);
                    if(name.trim().length()==0){
                        Toast.makeText(getApplicationContext(),"Please Enter Name",Toast.LENGTH_SHORT).show();
                    }else if(phone.trim().length()<10){
                        Toast.makeText(getApplicationContext(),"Phone Number should be 10 digits",Toast.LENGTH_SHORT).show();
                    }else if(secondaryPhone.trim().length()<10){
                        Toast.makeText(getApplicationContext(),"Phone Number should be 10 digits",Toast.LENGTH_SHORT).show();
                    }else if(flat.trim().length()==0){
                        Toast.makeText(getApplicationContext(),"Please enter flat details",Toast.LENGTH_SHORT).show();
                    }else if(owner.trim().length()==0){
                        Toast.makeText(getApplicationContext(),"Please select either Owner or Tenant",Toast.LENGTH_SHORT).show();
                    }else if(!password.getText().toString().equals(confrmPwd)){
                        Toast.makeText(getApplicationContext(),"Both Passwords should match",Toast.LENGTH_SHORT).show();
                    }else{
                        user = new User(name,phone,secondaryPhone,flat,pwd,vehicleNos,owner);
                        databaseReference.child(phone).setValue(user);
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(RegistrationActivity.this);
                        dlgAlert.setMessage("Registration Successful!! Redirecting to Login Page");
                        dlgAlert.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        final Intent myIntent = new Intent(RegistrationActivity.this,
                                                MainActivity.class);
                                        startActivity(myIntent);
                                    }
                                });
                        dlgAlert.create().show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        addVehicleNo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                    Chip chip = new Chip(RegistrationActivity.this);
                    chip.setText(vehicleNo.getText().toString());
                    chip.setChipBackgroundColorResource(R.color.colorPrimary);
                    chip.setCloseIconVisible(false);
                    chip.setTextColor(getResources().getColor(R.color.white));
                    chip.setTextAppearance(R.style.ChipTextAppearance);
                    chipGroup.addView(chip);
                    vehicleNo.setText("");
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}