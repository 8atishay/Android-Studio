package eckovation.quizz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    AppCompatButton mNotAMember;
    AppCompatButton mLoginButton;

    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    //TextInputLayout mEmailTextInputLayout;
    //TextInputLayout mPasswordTextInputLayout;
    EditText mEmailEditText;
    EditText mPasswordEditText;

    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //mEmailTextInputLayout = (TextInputLayout) findViewById(R.id.emailTextInputLayoutIn);
        //mPasswordTextInputLayout =(TextInputLayout) findViewById(R.id.passwordTextInputViewIn);

        mEmailEditText = (EditText)findViewById(R.id.emailEditTextViewIn);
        mPasswordEditText =(EditText)findViewById(R.id.passwordEditTextLayoutIn);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        mNotAMember = (AppCompatButton) findViewById(R.id.notamembersignup);
        mLoginButton =(AppCompatButton) findViewById(R.id.login);

        mNotAMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });
        mLoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                password = mPasswordEditText.getText().toString().trim();
                email = mEmailEditText.getText().toString().trim();
                if(email.equals("")) {
                    Toast.makeText(LoginActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                }else if(password.equals("")){
                    Toast.makeText(LoginActivity.this,"Enter Password",Toast.LENGTH_SHORT).show();
                }else {
                    mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            } else {
                                Toast.makeText(LoginActivity.this, "Sign in failed!".toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
