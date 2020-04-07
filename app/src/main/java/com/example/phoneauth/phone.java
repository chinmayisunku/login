package com.example.phoneauth;

import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class phone extends AppCompatActivity {

    EditText editTextPhone, editTextCode;

    FirebaseAuth mAuth;

    String codeSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_otp);

        mAuth = FirebaseAuth.getInstance();

        editTextCode = findViewById(R.id.phnumber);
        editTextPhone = findViewById(R.id.otp);

        findViewById(R.id.sendotp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode();
            }
        });


        findViewById(R.id.verify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifySignInCode();
            }
        });
    }

    private void verifySignInCode(){
        String code = editTextPhone.getText().toString();
        System.out.println(code);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            next();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                System.out.println("exception occurred");
                            }
                        }
                    }
                });
    }
    public void next(){
        Intent i = new Intent(this,registration.class);
        startActivity(i);
    }
    private void sendVerificationCode(){

        String phone = "+91"+editTextCode.getText().toString().trim();
        System.out.println(phone);
        if(phone.isEmpty()){
            editTextCode.setError("Phone number is required");
            editTextCode.requestFocus();
            return;
        }

        if(phone.length() < 10 ){
            editTextCode.setError("Please enter a valid phone");
            editTextCode.requestFocus();
            return;
        }


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);
    }



    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            System.out.println("successful "+phoneAuthCredential.toString() );


        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            System.out.println(e);
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            System.out.println(s);
            codeSent = s;
        }
    };


}