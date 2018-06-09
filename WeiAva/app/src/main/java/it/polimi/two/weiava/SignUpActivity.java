package it.polimi.two.weiava;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.security.BasicPermission;
import java.util.Calendar;

import it.polimi.two.weiava.activities.BaseActivity;
import it.polimi.two.weiava.activities.MainActivity;
import it.polimi.two.weiava.activities.SignInActivity;
import it.polimi.two.weiava.models.User;

public class SignUpActivity extends BaseActivity {
    private static final String TAG = "SignUpActivity";
    private EditText emailField, passwordField, nameField, dobField, bwField;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private RadioGroup radioGender;
    private ProgressBar progressBar;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference dbRef;

    final SignUpActivity self = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Get Firebase auth instance
        mFirebaseAuth = FirebaseAuth.getInstance();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        emailField = (EditText) findViewById(R.id.field_email);
        passwordField = (EditText) findViewById(R.id.field_password);
        nameField = (EditText)findViewById(R.id.field_name);
        dobField = (EditText)findViewById(R.id.field_dob);
        radioGender = (RadioGroup)findViewById(R.id.radioGender);
        bwField = (EditText)findViewById(R.id.field_bodyweight);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();

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

                mFirebaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(self, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                                hideProgressDialog();
                                progressBar.setVisibility(View.GONE);

                                if (task.isSuccessful()){
                                    onAuthSuccess(task.getResult().getUser());
                                    finish();
                                }
                                else {
                                    Toast.makeText(SignUpActivity.this, "Sign Up Failed",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();

        //Check auth on Activity start
        if(mFirebaseAuth.getCurrentUser() != null){
            onAuthSuccess(mFirebaseAuth.getCurrentUser());
        }
    }
    private void onAuthSuccess(FirebaseUser user){
        String username = usernameFromEmail(user.getEmail());
        long dobmillisecond = System.currentTimeMillis(); //TODO: convert from dobField
        boolean gender = true;//radioGender.getId(); //TODO: convert to Gender
        float bodyweight = 89.7f;//Double.valueOf(bwField.getText().toString());

        //write new user
        writeNewUser(user.getUid(), username, user.getEmail(), nameField.getText().toString(),dobmillisecond,gender,bodyweight);
        //writeNewUser(user.getUid(),username,user.getEmail());

        //go to drawer activity
        //startActivity(new Intent(SignUpActivity.this, MainActivity.class));
        finish();
    }

//    private void writeNewUser(String userId, String username, String email) {
//          User user = new User(username, email);
    private void writeNewUser(String userId, String username, String email, String name, long DOBmilliseconds, boolean gender, float bodyweight) {
        User user = new User(username, email);
        user.setName(name);
        user.setDOBmilliseconds(DOBmilliseconds);
        user.setGender(gender);
        user.setBodyweight(bodyweight);
        //try {
            dbRef.child("users").child(userId).setValue(user);
//        } catch (Exception e) {
//            Log.e(TAG, e.getMessage());
//            mFirebaseAuth.signOut();
//            finish();
        //}
    }

    private String usernameFromEmail(String email){
        if(email.contains("@")){
            return email.split("@")[0];
        }
        else {
            return email;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}