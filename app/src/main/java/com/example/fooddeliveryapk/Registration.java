package com.example.fooddeliveryapk;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class Registration extends AppCompatActivity {

    private TextInputLayout FName, MobileNumberpls, LName, eMailid, pwdrega, spwd, Laddress, area, PininCode;
    private Button signupho, emailbutt, Phonebutt;
    private Spinner state, Citys;
    private CountryCodePicker Country;
    private String firname, lasname, emaillid, passsword, confdpassword, mobile, house, Area, Pincode, role = "Customer", statee, cityy;
    private FirebaseAuth fAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);

        FName = findViewById(R.id.FName);
        LName = findViewById(R.id.LName);
        eMailid = findViewById(R.id.eMailid);
        pwdrega = findViewById(R.id.pwdrega);
        spwd= findViewById(R.id.spwd);
        MobileNumberpls = findViewById(R.id.MobileNumberpls);
        Laddress = findViewById(R.id.Laddress);
        area = findViewById(R.id.area);
        PininCode = findViewById(R.id.PininCode);
        signupho = findViewById(R.id.signupho);
        emailbutt = findViewById(R.id.emailbutt);
        Phonebutt = findViewById(R.id.Phonebutt);
        Country = findViewById(R.id.Country);
        state = findViewById(R.id.state);
        Citys = findViewById(R.id.Citys);

        ArrayAdapter<CharSequence> stateAdapter = ArrayAdapter.createFromResource(this,
                R.array.indian_states, android.R.layout.simple_spinner_item);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(stateAdapter);

        ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource(this,
                R.array.indian_cities, android.R.layout.simple_spinner_item);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Citys.setAdapter(cityAdapter);

        fAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Customer");


        signupho.setOnClickListener(v -> {

            firname = FName.getEditText().getText().toString().trim();
            lasname = LName.getEditText().getText().toString().trim();
            emaillid = eMailid.getEditText().getText().toString().trim();
            mobile = MobileNumberpls.getEditText().getText().toString().trim();
            passsword = pwdrega.getEditText().getText().toString().trim();
            confdpassword = spwd.getEditText().getText().toString().trim();
            Area = area.getEditText().getText().toString().trim();
            house = Laddress.getEditText().getText().toString().trim();
            Pincode = PininCode.getEditText().getText().toString().trim();
            statee = state.getSelectedItem().toString();
            cityy = Citys.getSelectedItem().toString();

            if (isValid()){
                final ProgressDialog mDialog = new ProgressDialog(Registration.this);
                mDialog.setCancelable(false);
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.setMessage("Registration in progress please wait......");
                mDialog.show();

                fAuth.createUserWithEmailAndPassword(emaillid,passsword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            String useridd = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            databaseReference = FirebaseDatabase.getInstance().getReference("User").child(useridd);
                            final HashMap<String , String> hashMap = new HashMap<>();
                            hashMap.put("Role",role);
                            databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                        HashMap<String, String> hashMap1 = new HashMap<>();
                                        hashMap1.put("Mobile No", mobile);
                                        hashMap1.put("First Name", firname);
                                        hashMap1.put("Last Name", lasname);
                                        hashMap1.put("EmailId", emaillid);
                                        hashMap1.put("City", cityy);
                                        hashMap1.put("Area", Area);
                                        hashMap1.put("Password", passsword);
                                        hashMap1.put("Pincode", Pincode);
                                        hashMap1.put("State", statee);
                                        hashMap1.put("Confirm Password", confdpassword);
                                        hashMap1.put("Local Address", house);

                                    firebaseDatabase.getInstance().getReference("Customer")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    mDialog.dismiss();

                                                    fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            if(task.isSuccessful()){
                                                                AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                                                                builder.setMessage("You Have Registered! Make Sure To Verify Your Email");
                                                                builder.setCancelable(false);
                                                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialog, int which) {

                                                                        dialog.dismiss();

                                                                        String phonenumber = Country.getSelectedCountryCodeWithPlus() + mobile;
                                                                        Intent b = new Intent(Registration.this,VerifyPhone.class);
                                                                        b.putExtra("phonenumber",phonenumber);
                                                                        startActivity(b);

                                                                    }
                                                                });
                                                                AlertDialog Alert = builder.create();
                                                                Alert.show();
                                                            }else{
                                                                mDialog.dismiss();
                                                                ReusableCodeForAll.ShowAlert(Registration.this,"Error",task.getException().getMessage());
                                                            }
                                                        }
                                                    });

                                                }
                                            });

                                }
                            });
                        }else{
                            mDialog.dismiss();
                            ReusableCodeForAll.ShowAlert(Registration.this, "Error", task.getException().getMessage());
                        }
                    }
                });
            }


    });

        emailbutt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(Registration.this,Login.class));
            finish();
        }
    });
        Phonebutt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(Registration.this,Loginphone.class));
            finish();
        }
    });

}

    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public boolean isValid(){
        eMailid.setErrorEnabled(false);
        eMailid.setError("");
        FName.setErrorEnabled(false);
        FName.setError("");
        LName.setErrorEnabled(false);
        LName.setError("");
        pwdrega.setErrorEnabled(false);
        pwdrega.setError("");
        MobileNumberpls.setErrorEnabled(false);
        MobileNumberpls.setError("");
        spwd.setErrorEnabled(false);
        spwd.setError("");
        area.setErrorEnabled(false);
        area.setError("");
        Laddress.setErrorEnabled(false);
        Laddress.setError("");
        PininCode.setErrorEnabled(false);
        PininCode.setError("");

        boolean isValid=false,isValidlocaladd=false,isValidlname=false,isValidname=false,isValidemail=false,isValidpassword=false,isValidconfpassword=false,isValidmobilenum=false,isValidarea=false,isValidpincode=false;

        if (TextUtils.isEmpty(firname)) {
            FName.setError("First Name is required");
            return false;
        }
        if (TextUtils.isEmpty(lasname)) {
            LName.setError("Last Name is required");
            return false;
        }
        if (TextUtils.isEmpty(emaillid)) {
            eMailid.setError("Email is required");
            return false;
        }
        if (TextUtils.isEmpty(passsword)) {
            pwdrega.setError("Password is required");
            return false;
        }
        if (TextUtils.isEmpty(confdpassword)) {
            spwd.setError("Confirm Password is required");
            return false;
        }
        if (!passsword.equals(confdpassword)) {
            spwd.setError("Passwords do not match");
            return false;
        }
        if (TextUtils.isEmpty(mobile)) {
            MobileNumberpls.setError("Mobile Number is required");
            return false;
        }
        if (TextUtils.isEmpty(Area)) {
            area.setError("Area is required");
            return false;
        }
        if (TextUtils.isEmpty(house)) {
            Laddress.setError("Local Address is required");
            return false;
        }
        if (TextUtils.isEmpty(Pincode)) {
            PininCode.setError("Pincode is required");
            return false;
        }
        isValid = (isValidarea && isValidconfpassword && isValidpassword && isValidpincode && isValidemail && isValidmobilenum && isValidname && isValidlocaladd && isValidlname) ? true : false;
        return true;
    }
}
