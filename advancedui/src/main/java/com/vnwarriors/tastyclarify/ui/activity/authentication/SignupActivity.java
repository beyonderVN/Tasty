package com.vnwarriors.tastyclarify.ui.activity.authentication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vnwarriors.tastyclarify.R;
import com.vnwarriors.tastyclarify.model.UserModel;
import com.vnwarriors.tastyclarify.ui.activity.BrowserActivity;


public class SignupActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    static final String USER_REFERENCE = "usermodel";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
                                    auth.zza(auth.getCurrentUser(),
                                            builder.setDisplayName(convertEmailToName(auth.getCurrentUser().getEmail()))
                                                    .setPhotoUri(Uri.parse("http://fanexpodallas.com/wp-content/uploads/550w_soaps_silhouettesm2.jpg"))
                                                    .build());
                                    UserModel userModel = new UserModel(convertEmailToName(auth.getCurrentUser().getEmail()),
                                            auth.getCurrentUser().getPhotoUrl()!=null?auth.getCurrentUser().getPhotoUrl().toString():"http://fanexpodallas.com/wp-content/uploads/550w_soaps_silhouettesm2.jpg",
                                            auth.getCurrentUser().getUid());
                                    DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
                                    mFirebaseDatabaseReference.child(USER_REFERENCE).child(auth.getCurrentUser().getUid()).setValue(userModel, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                            if(databaseError==null){
                                                Log.d("signup", "isSuccessful");
                                                startActivity(new Intent(SignupActivity.this, BrowserActivity.class));
                                                finish();
                                            }else{
                                                Log.d("signup", "isFailed: "+databaseError);
                                                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                            }

                                        }
                                    });

                                }
                            }
                        });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
    private String convertEmailToName(String email) {
        String[] s = email.split("@");
        return  s.length>0? s[0]:email;
    }
}
