package com.example.fooddeliveryapk;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class DeliveryRegistration extends AppCompatActivity {
    private TextInputLayout fNAme, MobileNumbers, lName, EMAilid, pwdredgear, showpwd, Hoeaddress, Arrea, PnCode;
    private Button Regster, emailidnm, Phoneno;
    private Spinner state, Citys;
    private CountryCodePicker CntryCode;
    private String firname, lasname, emaillid, passsword, confdpassword, mobile, house, Area, Pincode, role = "DeliveryPerson", statee, cityy;
    private FirebaseAuth fAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private ConstraintLayout main;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delivery_registration);
        AnimationDrawable animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img1), 2000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img4), 2000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img6), 2000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.alaaa), 2000);
        animationDrawable.setOneShot(false);
        animationDrawable.setEnterFadeDuration(900);
        animationDrawable.setExitFadeDuration(1600);
        main = findViewById(R.id.main);
        main.setBackground(animationDrawable);
        animationDrawable.start();

        // Initialize UI elements
        fNAme = findViewById(R.id.fName);
        lName = findViewById(R.id.lName);
        EMAilid = findViewById(R.id.EMAilid);
        pwdredgear = findViewById(R.id.pwdredgear);
        showpwd = findViewById(R.id.showpwd);
        MobileNumbers = findViewById(R.id.MobileNumbers);
        Hoeaddress = findViewById(R.id.Hoeaddress);
        Arrea = findViewById(R.id.Arrea);
        PnCode = findViewById(R.id.PnCode);
        Regster = findViewById(R.id.Regster);
        emailidnm = findViewById(R.id.emailidnm);
        Phoneno = findViewById(R.id.Phoneno);
        CntryCode = findViewById(R.id.CntryCode);
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
        databaseReference = FirebaseDatabase.getInstance().getReference("DeliveryPerson");

        Regster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firname = fNAme.getEditText().getText().toString().trim();
                lasname = lName.getEditText().getText().toString().trim();
                emaillid = EMAilid.getEditText().getText().toString().trim();
                mobile = MobileNumbers.getEditText().getText().toString().trim();
                passsword = pwdredgear.getEditText().getText().toString().trim();
                confdpassword = showpwd.getEditText().getText().toString().trim();
                Area = Arrea.getEditText().getText().toString().trim();
                house = Hoeaddress.getEditText().getText().toString().trim();
                Pincode = PnCode.getEditText().getText().toString().trim();
                statee = state.getSelectedItem().toString();
                cityy = Citys.getSelectedItem().toString();

                if (isValid()) {
                    mDialog = new ProgressDialog(DeliveryRegistration.this);
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
                                        FirebaseDatabase.getInstance().getReference("DeliveryPerson")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        mDialog.dismiss();
                                                        fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(DeliveryRegistration.this);
                                                                    builder.setMessage("You Have Registered! Make Sure to Verify Your Email");
                                                                    builder.setCancelable(false);
                                                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            dialog.dismiss();
                                                                            String phonenumber = CntryCode.getSelectedCountryCodeWithPlus() + mobile;
                                                                            Intent b = new Intent(DeliveryRegistration.this, DeliveryVerifyPhone.class);
                                                                            b.putExtra("phonenumber", phonenumber);
                                                                    startActivity(b);
                                                                            }
                                                                    });
                                                                    AlertDialog alert = builder.create();
                                                                    alert.show();
                                                                } else {
                                                                    mDialog.dismiss();
                                                                    ReusableCodeForAll.ShowAlert(DeliveryRegistration.this, "Error", task.getException().getMessage());
                                                                }
                                                            };
                                                        });
                                };
                                                });
                                    }
                                });
                            } else {
                                mDialog.dismiss();
                                ReusableCodeForAll.ShowAlert(DeliveryRegistration.this, "Error", task.getException().getMessage());
                            }
                        }
                    });
                }
            }
        });


        emailidnm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeliveryRegistration.this, Deliverylogin.class));
            }
        });

        // Phone button listener
        Phoneno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DeliveryRegistration.this, Deliveryloginphone.class));
            }
        });


    };
    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private boolean isValid() {
        boolean isValid = true;

        // Validate First Name
        if (TextUtils.isEmpty(firname)) {
            fNAme.setError("Enter First Name");
            isValid = false;
        } else {
            fNAme.setError(null);
        }

        // Validate Last Name
        if (TextUtils.isEmpty(lasname)) {
            lName.setError("Enter Last Name");
            isValid = false;
        } else {
            lName.setError(null);
        }

        // Validate Email
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (TextUtils.isEmpty(emaillid)) {
            emailidnm.setError("Email Is Required");
            isValid = false;
        } else if (!emaillid.matches(emailPattern)) {
            emailidnm.setError("Enter a Valid Email Id");
            isValid = false;
        } else {
            emailidnm.setError(null);
        }

        // Validate Password
        if (passsword.length() < 8) {
            pwdredgear.setError("Password Is Weak");
            isValid = false;
        } else {
            pwdredgear.setError(null);
        }

        // Validate Confirm Password
        if (TextUtils.isEmpty(confdpassword)) {
            showpwd.setError("Enter Password Again");
            isValid = false;
        } else if (!passsword.equals(confdpassword)) {
            showpwd.setError("Password Doesn't Match");
            isValid = false;
        } else {
            showpwd.setError(null);
        }

        // Validate Mobile Number
        if (TextUtils.isEmpty(mobile)) {
            MobileNumbers.setError("Mobile Number Is Required");
            isValid = false;
        } else {
            MobileNumbers.setError(null);
        }

        // Validate Area
        if (TextUtils.isEmpty(Area)) {
            Arrea.setError("Enter Area");
            isValid = false;
        } else {
            Arrea.setError(null);
        }

        // Validate Pincode
        if (TextUtils.isEmpty(Pincode)) {
            PnCode.setError("Please Enter Pincode");
            isValid = false;
        } else {
            PnCode.setError(null);
        }

        // Validate House Number
        if (TextUtils.isEmpty(house)) {
            Hoeaddress.setError("Enter House Number");
            isValid = false;
        } else {
            Hoeaddress.setError(null);
        }

        return isValid;
    }
    }
