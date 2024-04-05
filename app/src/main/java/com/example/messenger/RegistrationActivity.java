package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextEmailRegistration;
    private EditText editTextPasswordRegistration;
    private EditText editTextNameRegistration;
    private EditText editTextLastNameRegistration;
    private EditText editTextAgeRegistration;
    private Button buttonRegistration;

    private RegistrationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initViews();
        viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        observeViewModel();

        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = getTrimmedValue(editTextEmailRegistration);
                String password = getTrimmedValue(editTextPasswordRegistration);
                String name = getTrimmedValue(editTextNameRegistration);
                String lastName = getTrimmedValue(editTextLastNameRegistration);
                int age = Integer.parseInt(getTrimmedValue(editTextAgeRegistration));
                viewModel.signUp(email, password, name, lastName, age);
            }
        });
    }

    private void observeViewModel() {
        viewModel.getException().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String e) {
                if (e != null) {
                    Toast.makeText(RegistrationActivity.this, e, Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    Intent intent = UsersActivity.newIntent(RegistrationActivity.this, firebaseUser.getUid());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void initViews() {
        editTextEmailRegistration = findViewById(R.id.editTextEmailRegistration);
        editTextPasswordRegistration = findViewById(R.id.editTextPasswordRegistration);
        editTextNameRegistration = findViewById(R.id.editTextNameRegistration);
        editTextLastNameRegistration = findViewById(R.id.editTextLastNameRegistration);
        editTextAgeRegistration = findViewById(R.id.editTextAgeRegistration);
        buttonRegistration = findViewById(R.id.buttonRegistration);
    }

    private String getTrimmedValue(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }
}