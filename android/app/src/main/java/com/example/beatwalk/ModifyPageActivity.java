package com.example.beatwalk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beatwalk.R;
import com.example.beatwalk.data.RegistrationStore;
import com.example.beatwalk.data.RegistrationStoreMongo;

public class ModifyPageActivity extends AppCompatActivity {
    //Confirms password before redirecting to the actual page to modify your data

    RegistrationStore rs;
    EditText centerPasswordModifyConfirm;
    String userName;
    int failedLogins;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_password_confirm_activity);
        userName = this.getIntent().getStringExtra("Email");
        rs = RegistrationStoreMongo.getInstance();
        centerPasswordModifyConfirm = findViewById(R.id.modifyConfirmPassword);
        failedLogins = 0;
        Log.v("Success", "Made it to ModifyPageActivity");

    }

    public void jumptToRegistration(View view) {
        String enteredPassword = centerPasswordModifyConfirm.getText().toString();
        if (rs.verifyLogin(userName, enteredPassword)) {
            Log.v("Success", "Success!!!!");
            Intent i = new Intent(this, ModifyActivity.class);
            i.putExtra("Email", userName);
            startActivityForResult(i, 2);
            finish();
        } else {
            failedLogins += 1;
            if (failedLogins > 5) {
                printMessage("Too many failed attempts.  Returning to previous page.");
                finishActivity(6);
                finish();
            } else {
                printMessage("Incorrect Password");
            }
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
