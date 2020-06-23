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

public class SignUpActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    AppCompatButton button;

    String name, password, email;

    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mFirebaseAuth= FirebaseAuth.getInstance();
        mFirebaseUser= mFirebaseAuth.getCurrentUser();

        nameEditText = findViewById(R.id.nameEditTextViewUp);
        emailEditText = findViewById(R.id.emailEditTextViewUp);
        passwordEditText = findViewById(R.id.passwordEditTextViewUp);
        button = findViewById(R.id.registerAppCompatButtonUp);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                email= emailEditText.getText().toString().trim();
                name= nameEditText.getText().toString().trim();
                password= passwordEditText.getText().toString().trim();

                if(name.equals("")){
                    Toast.makeText(SignUpActivity.this,"Enter Name",Toast.LENGTH_SHORT).show();
                }else if(email.equals("")) {
                    Toast.makeText(SignUpActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                }else if(password.equals("")){
                    Toast.makeText(SignUpActivity.this,"Enter Password",Toast.LENGTH_SHORT).show();
                }else {
                    mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                            } else {
                                Toast.makeText(SignUpActivity.this, "SignUp has failed!".toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
