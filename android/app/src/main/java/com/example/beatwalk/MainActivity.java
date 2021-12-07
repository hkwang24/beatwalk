package com.example.beatwalk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

import com.example.beatwalk.R;
import com.example.beatwalk.Retrofit.IMyService;
import com.example.beatwalk.Retrofit.RetrofitClient;
import com.example.beatwalk.data.RegistrationStore;
import com.example.beatwalk.data.RegistrationStoreMongo;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private EditText userEmail;
    private EditText password;
    private Button login;
    private Button createAcct;
    private RegistrationStore registered;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;


    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        userEmail = (EditText) findViewById(R.id.email);
//        password = (EditText) findViewById(R.id.password);
//        login = (Button) findViewById(R.id.login);
//        createAcct = (Button) findViewById(R.id.createAcct);
        registered = RegistrationStoreMongo.getInstance();


        //Init service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

    }

    public void loginAttempt(View view) {
        String userName = userEmail.getText().toString();
        String passString = password.getText().toString();

        if (validate(userName, passString)) {
            Intent i = new Intent(this, LandingPageActivity.class);
            i.putExtra("Email", userName);
            startActivityForResult(i, 2);
        } else {
            Toast.makeText(
                    this,
                    "Username or password incorrect",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    private boolean validate (String userName, String passString) {
        return registered.verifyLogin(userName, passString);
    }

    public void jumpToLogin(View view) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivityForResult(i, 2);
    }

    public void jumpToRegistration(View view) {
        Intent i = new Intent(this, RegistrationActivity.class);
        startActivityForResult(i, 2);
    }




}
