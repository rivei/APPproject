package it.polimi.two.weiava.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import it.polimi.two.weiava.R;
import it.polimi.two.weiava.models.User;

public class SignInActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "SignInActivity";

    private DatabaseReference dbRef;
    private FirebaseAuth auth;

    private EditText emailField;
    private EditText passwordField;
    private Button signInButton;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        dbRef = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        //Views
        emailField = findViewById(R.id.field_email);
        passwordField = findViewById(R.id.field_password);
        signInButton = findViewById(R.id.button_sign_in);
        signUpButton = findViewById(R.id.button_sign_up);

        //Click listeners
        signInButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onStart(){
        super.onStart();

        //Check auth on Activity start
        if(auth.getCurrentUser() != null){
            onAuthSuccess(auth.getCurrentUser());
        }
    }

    private void signIn(){
        Log.e(TAG, "signIn");
        if(!validateForm()){
            return;
        }

        showProgressDialog();
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.e(TAG, "signIn:onComplete:" + task.isSuccessful());
                        hideProgressDialog();

                        if (task.isSuccessful()){
                            onAuthSuccess(task.getResult().getUser());
                        }
                        else {
                            Toast.makeText(SignInActivity.this, "Sign In Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signUp(){
        Log.d(TAG, "signUp");
        if(!validateForm()){
            return;
        }

        showProgressDialog();
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());
                        hideProgressDialog();

                        if (task.isSuccessful()){
                            onAuthSuccess(task.getResult().getUser());
                        }
                        else {
                            Toast.makeText(SignInActivity.this, "Sign Up Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user){
        String username = usernameFromEmail(user.getEmail());

        //write new user
        writeNewUser(user.getUid(), username, user.getEmail());

        //go to drawer activity
        startActivity(new Intent(SignInActivity.this, MainActivity.class));
        finish();
    }

    private String usernameFromEmail(String email){
        if(email.contains("@")){
            return email.split("@")[0];
        }
        else {
            return email;
        }
    }

    private boolean validateForm(){
        boolean result = true;
        if(TextUtils.isEmpty(emailField.getText().toString())){
            emailField.setError("Required");
            result = false;
        }
        else{
            emailField.setError(null);
        }
        if(TextUtils.isEmpty(passwordField.getText().toString())){
            passwordField.setError("Required");
            result = false;
        }
        else {
            passwordField.setError(null);
        }

        return result;
    }

    //Start write
    private void writeNewUser(String userId, String name, String email){
        User user = new User(name, email);
        dbRef.child("users").child(userId).setValue(user);
    }
    //end

    @Override
    public void onClick(View v){
        int i= v.getId();
        if (i==R.id.button_sign_in){
            signIn();
        }
        else if (i==R.id.button_sign_up){
            signUp();
        }
    }
}
