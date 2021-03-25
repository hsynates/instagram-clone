package com.hsynates.savenoteandsharephoto;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    String email, password;
    Button buttonSignIN, buttonSignUP;
    AlertDialog.Builder alert;
    Intent intent;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSignIN = findViewById(R.id.buttonSignIN);
        buttonSignUP = findViewById(R.id.buttonSignUP);

        alert = new AlertDialog.Builder(SignActivity.this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            intent = new Intent(SignActivity.this, FeedActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void signIN(View view) {
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();

        if (email.matches("") || password.matches("")) {
            alert.setTitle("WARNING");
            alert.setMessage("Email Address and Password cannot be empty.");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            }).setNegativeButton("", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            alert.show();
        } else {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    buttonSignIN.setEnabled(false);
                    intent = new Intent(SignActivity.this, FeedActivity.class);
                    Toast.makeText(getApplicationContext(), "Welcome!", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    alert.setTitle("WARNING");
                    alert.setMessage(e.getLocalizedMessage());
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }).setNegativeButton("", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    alert.show();
                }
            });
        }
    }

    public void signUP(View view) {
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();

        if (email.matches("") || password.matches("")) {
            alert.setTitle("WARNING");
            alert.setMessage("E-mail address and password cannot be blank.");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            }).setNegativeButton("", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            alert.show();
        } else {
            alert.setTitle("CREATE ACCOUNT");
            alert.setMessage("Do you want to register with this email?");
            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            intent = new Intent(SignActivity.this, FeedActivity.class);
                            Toast.makeText(getApplicationContext(), "Registration successful.", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            alert.setTitle("WARNING!");
                            alert.setMessage(e.getLocalizedMessage());
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).setNegativeButton("", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                            alert.show();
                        }
                    });
                }
            }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            alert.show();
        }
    }
}