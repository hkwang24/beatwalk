package com.example.beatwalk;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beatwalk.R;
import com.example.beatwalk.data.RegistrationStore;
import com.example.beatwalk.data.RegistrationStoreMongo;

public class ModifyActivity extends AppCompatActivity {
    RegistrationStore users;
    EditText userName, name;
    EditText phone;
    EditText year;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_info);
        users = RegistrationStoreMongo.getInstance();
        userName = findViewById(R.id.currentEmail);
        name = findViewById(R.id.modifyName);
        phone = findViewById(R.id.modifyPhone);
        year = findViewById(R.id.modifyYear);


        userEmail = this.getIntent().getStringExtra("Email");
        Log.v("Email: ", userEmail);
        userName.setText(userEmail);
        name.setText(users.getName(userEmail));
        phone.setText(users.getPhone(userEmail));
        year.setText(new Integer(users.getClassYear(userEmail)).toString());
    }

    public void modifyAccount(View view) {
        String newEmail = userName.getText().toString();
        String newName = name.getText().toString();
        String phoneNum = phone.getText().toString();
        String classYear = year.getText().toString();
        if (newEmail.length() < 10 || !newEmail.substring(newEmail.length()-9).equals("upenn.edu")){
            printMessage("Please use a valid Penn Email");
        } else if (newName.trim().length() < 3) {
            printMessage("Please enter your name");
        } else if (phoneNum.length() < 10) {
            printMessage("Please enter a valid phone number");
        } else if (classYear.length() != 4) {
            printMessage("Please enter your class year as ####");
        } else {

            users.modifyUser(newEmail, newName.trim(), new Integer(classYear), phoneNum);
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
