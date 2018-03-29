package app.ie.mymagiccards.activity;

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

import app.ie.mymagiccards.R;

public class Login extends AppCompatActivity {

    private FirebaseAuth                    mAuth;
    private FirebaseAuth.AuthStateListener  mAuthListener;
    private FirebaseUser                    user;
    private Button                          createAccount, login;
    private EditText                        emailLogin, passwordLogin;


    /*{
  "rules": {
    ".read": "auth != null",
    ".write": "auth != null"
  }
}
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login =         findViewById(R.id.loginBtn);
        createAccount = findViewById(R.id.createAccountBtn);
        emailLogin =    findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);

        mAuth = FirebaseAuth.getInstance();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();

                //Log.i("User ID", user.toString());
                if(user != null){
                    Toast.makeText(Login.this, "Signed in", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Login.this, MyDecks.class));
                    finish();
                }else{
                    Toast.makeText(Login.this, "Signed out", Toast.LENGTH_LONG).show();
                }

            }
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(emailLogin.getText().toString())
                        && !TextUtils.isEmpty(passwordLogin.getText().toString())){
                    String email = emailLogin.getText().toString();
                    String password = passwordLogin.getText().toString();
                    login(email,password);
                }else{

                }
            }
        });
    }

    private void login(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "signed in", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();
                        }else{
                            Toast.makeText(Login.this, "not in", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuth != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
