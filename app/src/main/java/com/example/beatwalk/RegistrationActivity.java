package com.example.beatwalk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beatwalk.R;
import com.example.beatwalk.data.RegistrationStore;
import com.example.beatwalk.data.RegistrationStoreMongo;

public class RegistrationActivity extends AppCompatActivity {
    RegistrationStore users;
    EditText userName, name;
    EditText password, confirmPassword;
    EditText phone;
    EditText year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        users = RegistrationStoreMongo.getInstance();
        userName = findViewById(R.id.newUsername);
        password = findViewById(R.id.newPassword);
        confirmPassword = findViewById(R.id.newPasswordConfirm);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        year = findViewById(R.id.year);
    }

    public void checkCreateAccount(View view) {
        String newPassword = password.getText().toString();
        String newPasswordConfirm = confirmPassword.getText().toString();
        String newEmail = userName.getText().toString();
        String newName = name.getText().toString();
        String phoneNum = phone.getText().toString();
        String classYear = year.getText().toString();
        if (newEmail.length() < 10 || !newEmail.substring(newEmail.length()-9).equals("upenn.edu")){
            printMessage("Please use a valid Penn Email");
        } else if (users.accountExists(newEmail)) {
            printMessage("Email already in use");
//            Intent i = new Intent(this, MainActivity.class);
//            startActivityForResult(i, 2);
        } else if (!newPassword.equals(newPasswordConfirm)) {
            printMessage("Passwords must match");
        } else if (newPassword.length() < 8) {
            printMessage("Passwords must be at least 8 characters in length");
        } else if (newPassword.length() < 8) {
            printMessage("Passwords must be at least 8 characters in length");
        } else if (newName.trim().length() < 3) {
            printMessage("Please enter your name");
        } else if (phoneNum.length() < 10) {
            printMessage("Please enter a valid phone number");
        } else if (classYear.length() != 4) {
            printMessage("Please enter your class year as ####");
        } else {

            users.addUser(newEmail.trim(), newPassword, newName.trim(), new Integer(classYear), phoneNum);
            printMessage("Success!");
            finishActivity(3);
            finish();
        }

    }

    private void printMessage(String message) {
        Toast.makeText(
                this,
                message,
                Toast.LENGTH_LONG
        ).show();
    }
}
