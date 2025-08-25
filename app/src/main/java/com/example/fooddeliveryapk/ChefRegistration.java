package com.example.fooddeliveryapk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class ChefRegistration extends AppCompatActivity {
    private TextInputLayout FirstName, MobileNumber, LastName, eMail, pwd, cpwd, HouseNo, area, PinCode;
    private Button Signup, email, Phone;
    private Spinner state, Citys;
    private CountryCodePicker Cpp;
    private String firname, lasname, emaillid, passsword, confdpassword, mobile, house, Area, Pincode, role = "Chef", statee, cityy;
    private FirebaseAuth fAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private ConstraintLayout backgroundimage;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_registration);

        // Background animation
        AnimationDrawable animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img1), 2000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img4), 2000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img6), 2000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.alaaa), 2000);
        animationDrawable.setOneShot(false);
        animationDrawable.setEnterFadeDuration(900);
        animationDrawable.setExitFadeDuration(1600);
        backgroundimage = findViewById(R.id.backgroundimage);
        backgroundimage.setBackground(animationDrawable);
        animationDrawable.start();

        // Initialize UI elements
        FirstName = findViewById(R.id.FirstName);
        LastName = findViewById(R.id.LastName);
        eMail = findViewById(R.id.eMail);
        pwd = findViewById(R.id.pwd);
        cpwd = findViewById(R.id.cpwd);
        MobileNumber = findViewById(R.id.MobileNumber);
        HouseNo = findViewById(R.id.HouseNo);
        area = findViewById(R.id.area);
        PinCode = findViewById(R.id.PininCode);
        Signup = findViewById(R.id.signuppp);
        email = findViewById(R.id.emailbutt);
        Phone = findViewById(R.id.Phonebutt);
        Cpp = findViewById(R.id.Country);
        state = findViewById(R.id.state);
        Citys = findViewById(R.id.Citys);

        // State and City spinners
        ArrayAdapter<CharSequence> stateAdapter = ArrayAdapter.createFromResource(this,
                R.array.indian_states, android.R.layout.simple_spinner_item);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(stateAdapter);

        ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource(this,
                R.array.indian_cities, android.R.layout.simple_spinner_item);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Citys.setAdapter(cityAdapter);

        // Firebase setup
        fAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Chef");

        // Signup button listener
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firname = FirstName.getEditText().getText().toString().trim();
                lasname = LastName.getEditText().getText().toString().trim();
                emaillid = eMail.getEditText().getText().toString().trim();
                mobile = MobileNumber.getEditText().getText().toString().trim();
                passsword = pwd.getEditText().getText().toString().trim();
                confdpassword = cpwd.getEditText().getText().toString().trim();
                Area = area.getEditText().getText().toString().trim();
                house = HouseNo.getEditText().getText().toString().trim();
                Pincode = PinCode.getEditText().getText().toString().trim();
                statee = state.getSelectedItem().toString();
                cityy = Citys.getSelectedItem().toString();

                // Validate input fields
                if (isValid()) {
                    mDialog = new ProgressDialog(ChefRegistration.this);
                    mDialog.setCancelable(false);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setMessage("Registration in process, please wait...");
                    mDialog.show();

                    fAuth.createUserWithEmailAndPassword(emaillid, passsword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String useridd = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                databaseReference = FirebaseDatabase.getInstance().getReference("User").child(useridd);
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("Role", role);
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
                                        hashMap1.put("House", house);
                                        FirebaseDatabase.getInstance().getReference("Chef")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        mDialog.dismiss();
                                                        fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
//                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(ChefRegistration.this);
//                                                                    builder.setMessage("You Have Registered! Make Sure to Verify Your Email");
//                                                                    builder.setCancelable(false);
//                                                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                                                        @Override
//                                                                        public void onClick(DialogInterface dialog, int which) {
//                                                                            dialog.dismiss();
//                                                                            String phonenumber = Cpp.getSelectedCountryCodeWithPlus() + mobile;
//                                                                            Intent b = new Intent(ChefRegistration.this, ChefVerifyPhone.class);
//                                                                            b.putExtra("phonenumber", phonenumber);
//                                                                            String value = getIntent().getStringExtra("key");
//                                                                            if (value != null) {
//                                                                                 Proceed with using 'value'
//                                                                            }
//                                                                            startActivity(b);
//                                                                        }
//                                                                    })
//                                                                                                                             };
//                                                                    AlertDialog alert = builder.create();
//                                                                    alert.show();
                                                                } else {
                                                                    mDialog.dismiss();
                                                                    ReusableCodeForAll.ShowAlert(ChefRegistration.this, "Error", task.getException().getMessage());
                                                                }
                                                            }
                                                        });
                                                    }
                                                });
                                    }
                                });
                            } else {
                                mDialog.dismiss();
                                ReusableCodeForAll.ShowAlert(ChefRegistration.this, "Error", task.getException().getMessage());
                            }
                        }
                    });
                }
            }
        });

        // Email button listener
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChefRegistration.this, Cheflogin.class));
            }
        });

        // Phone button listener
        Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChefRegistration.this, Chefloginphone.class));
            }
        });
    }

    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private boolean isValid() {
        boolean isValid = true;

        // Validate First Name
        if (TextUtils.isEmpty(firname)) {
            FirstName.setError("Enter First Name");
            isValid = false;
        } else {
            FirstName.setError(null);
        }

        // Validate Last Name
        if (TextUtils.isEmpty(lasname)) {
            LastName.setError("Enter Last Name");
            isValid = false;
        } else {
            LastName.setError(null);
        }

        // Validate Email
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (TextUtils.isEmpty(emaillid)) {
            eMail.setError("Email Is Required");
            isValid = false;
        } else if (!emaillid.matches(emailPattern)) {
            eMail.setError("Enter a Valid Email Id");
            isValid = false;
        } else {
            eMail.setError(null);
        }

        // Validate Password
        if (passsword.length() < 8) {
            pwd.setError("Password Is Weak");
            isValid = false;
        } else {
            pwd.setError(null);
        }

        // Validate Confirm Password
        if (TextUtils.isEmpty(confdpassword)) {
            cpwd.setError("Enter Password Again");
            isValid = false;
        } else if (!passsword.equals(confdpassword)) {
            cpwd.setError("Password Doesn't Match");
            isValid = false;
        } else {
            cpwd.setError(null);
        }

        // Validate Mobile Number
        if (TextUtils.isEmpty(mobile)) {
            MobileNumber.setError("Mobile Number Is Required");
            isValid = false;
        } else {
            MobileNumber.setError(null);
        }

        // Validate Area
        if (TextUtils.isEmpty(Area)) {
            area.setError("Enter Area");
            isValid = false;
        } else {
            area.setError(null);
        }

        // Validate Pincode
        if (TextUtils.isEmpty(Pincode)) {
            PinCode.setError("Please Enter Pincode");
            isValid = false;
        } else {
            PinCode.setError(null);
        }

        // Validate House Number
        if (TextUtils.isEmpty(house)) {
            HouseNo.setError("Enter House Number");
            isValid = false;
        } else {
            HouseNo.setError(null);
        }

        return isValid;
    }
}
