package com.example.fooddeliveryapk;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ChefSendOtp extends AppCompatActivity {

    private String verificationId;
    private FirebaseAuth fAuth;
    private Button verify, resend;
    private TextView txtt;
    private EditText entercode;
    private String phoneno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_send_otp);

        phoneno = getIntent().getStringExtra("phonenumber");



        // Initialize UI elements
        entercode = findViewById(R.id.code);
        txtt = findViewById(R.id.text);
        resend = findViewById(R.id.resendotp);
        verify = findViewById(R.id.Verify);
        fAuth = FirebaseAuth.getInstance();

        resend.setVisibility(View.INVISIBLE);
        txtt.setVisibility(View.INVISIBLE);

        sendverificationcode(phoneno);

        // Verify button click listener
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = entercode.getText().toString().trim();
                resend.setVisibility(View.INVISIBLE);

                if (code.isEmpty() && code.length()<6){
                    entercode.setError("Enter code");
                    entercode.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });

        new CountDownTimer(60000,1000){

            @Override
            public void onTick(long millisUntilFinished) {

                txtt.setVisibility(View.VISIBLE);
                txtt.setText("Resend Code Within "+millisUntilFinished/1000+" Seconds");

            }

            /**
             * Callback fired when the time is up.
             */
            @Override
            public void onFinish() {
                resend.setVisibility(View.VISIBLE);
                txtt.setVisibility(View.INVISIBLE);

            }
        }.start();

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resend.setVisibility(View.INVISIBLE);
                Resendotp(phoneno);

                new CountDownTimer(60000,1000){

                    @Override
                    public void onTick(long millisUntilFinished) {

                        txtt.setVisibility(View.VISIBLE);
                        txtt.setText("Resend Code Within "+millisUntilFinished/1000+" Seconds");

                    }

                    /**
                     * Callback fired when the time is up.
                     */
                    @Override
                    public void onFinish() {
                        resend.setVisibility(View.VISIBLE);
                        txtt.setVisibility(View.INVISIBLE);

                    }
                }.start();
            }
        });

    }

    private void Resendotp(String phonenum) {

        sendverificationcode(phonenum);
    }


    private void sendverificationcode(String number) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(

                number,
                60,
                TimeUnit.SECONDS,
                this,
                mcallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallBack=new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if(code != null){
                entercode.setText(code);  // Auto Verification
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(ChefSendOtp.this , e.getMessage(),Toast.LENGTH_LONG).show();

        }

        @Override
        public void onCodeSent(String s , PhoneAuthProvider.ForceResendingToken forceResendingToken){
            super.onCodeSent(s,forceResendingToken);

            verificationId = s;

        }
    };

    private void verifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId , code);
        signInWithPhone(credential);
    }

    private void signInWithPhone(PhoneAuthCredential credential) {

        fAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            startActivity(new Intent(ChefSendOtp.this,ChefFoodPanel1_BottomNavigation.class));
                            finish();

                        }else{
                            ReusableCodeForAll.ShowAlert(ChefSendOtp.this,"Error",task.getException().getMessage());
                        }

                    }
                });

    }

}
