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
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhone extends AppCompatActivity {

    String verificationId;
    FirebaseAuth fAuth;
    private Button verifyYy, resendotpPp;
    private TextView txtt;
    private EditText codeEe;
    String phoneno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);


        phoneno = getIntent().getStringExtra("phonenumber").trim();

        codeEe = findViewById(R.id.codeEe);
        txtt = findViewById(R.id.text);
        resendotpPp = findViewById(R.id.resendotpPp);
        verifyYy = findViewById(R.id.VerifyYy);
        fAuth = FirebaseAuth.getInstance();

        resendotpPp.setVisibility(View.INVISIBLE);
        txtt.setVisibility(View.INVISIBLE);

        sendverificationcode(phoneno);

        verifyYy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = codeEe.getText().toString().trim();
                resendotpPp.setVisibility(View.INVISIBLE);

                if (code.isEmpty() && code.length()<6){
                    codeEe.setError("Enter code");
                    codeEe.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });
        new CountDownTimer(60000,1000){

            @Override
            public void onTick(long millisUntilFinished) {

                txtt.setVisibility(View.VISIBLE);
                txtt.setText("Resend Code Within"+millisUntilFinished/1000+"Seconds");

            }

            /**
             * Callback fired when the time is up.
             */
            @Override
            public void onFinish() {
                resendotpPp.setVisibility(View.VISIBLE);
                txtt.setVisibility(View.INVISIBLE);

            }
        }.start();

        resendotpPp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resendotpPp.setVisibility(View.INVISIBLE);
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
                        resendotpPp.setVisibility(View.VISIBLE);
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
                codeEe.setText(code);  // Auto Verification
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerifyPhone.this , e.getMessage(),Toast.LENGTH_LONG).show();

        }

        @Override
        public void onCodeSent(String s , PhoneAuthProvider.ForceResendingToken forceResendingToken){
            super.onCodeSent(s,forceResendingToken);

            verificationId = s;

        }
    };

    private void verifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId , code);
        linkCredential(credential);
    }

    private void linkCredential(PhoneAuthCredential credential) {

        fAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(VerifyPhone.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Intent intent = new Intent(VerifyPhone.this , MainMenu.class);
                            startActivity(intent);
                            finish();
                        }else{
                            ReusableCodeForAll.ShowAlert(VerifyPhone.this,"Error",task.getException().getMessage());
                        }
                    }
                });

    }

}