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

public class ChefVerifyPhone extends AppCompatActivity {

    private String verificationId;
    private FirebaseAuth fAuth;
    private Button verify, resend;
    private TextView txtt;
    private EditText entercode;
    private String phoneno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_verify_phone);

        phoneno = getIntent().getStringExtra("phonenumber").trim();

        entercode = findViewById(R.id.code);
        txtt = findViewById(R.id.text);
        resend = findViewById(R.id.resendotp);
        verify = findViewById(R.id.Verify);
        fAuth = FirebaseAuth.getInstance();

        resend.setVisibility(View.INVISIBLE);
        txtt.setVisibility(View.INVISIBLE);

        sendVerificationCode(phoneno);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = entercode.getText().toString().trim();
                resend.setVisibility(View.INVISIBLE);

                if (code.isEmpty() || code.length() < 6) {
                    entercode.setError("Enter Code");
                    entercode.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });

        startResendCountdown();

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resend.setVisibility(View.INVISIBLE);  // Hide the resend button during countdown
                entercode.setText("");  // Clear the input field
                resendOtp(phoneno);
                startResendCountdown();
            }
        });
    }

    private void resendOtp(String phonenum) {
        sendVerificationCode(phonenum);
    }

    private void sendVerificationCode(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                this,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                entercode.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(ChefVerifyPhone.this, e.getMessage(), Toast.LENGTH_LONG).show();
            resetUIAfterFailure();  // Reset UI on failure
        }

        @Override
        public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(verificationId, forceResendingToken);
            ChefVerifyPhone.this.verificationId = verificationId;
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        linkCredential(credential);
    }

    private void linkCredential(PhoneAuthCredential credential) {
        fAuth.getCurrentUser().linkWithCredential(credential).addOnCompleteListener(ChefVerifyPhone.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(ChefVerifyPhone.this, MainMenu.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    ReusableCodeForAll.ShowAlert(ChefVerifyPhone.this, "Error", task.getException().getMessage());
                }
            }
        });
    }

    private void startResendCountdown() {
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtt.setVisibility(View.VISIBLE);
                txtt.setText("Resend Code Within " + millisUntilFinished / 1000 + " Seconds");
            }

            @Override
            public void onFinish() {
                resend.setVisibility(View.VISIBLE);
                txtt.setVisibility(View.INVISIBLE);
            }
        }.start();
    }

    private void resetUIAfterFailure() {
        // Reset UI elements after verification fails
        entercode.setText("");
        txtt.setVisibility(View.INVISIBLE);
        resend.setVisibility(View.VISIBLE);
    }
}
