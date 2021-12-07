package com.example.beatwalk;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.example.beatwalk.R;
import com.example.beatwalk.data.RegistrationStore;

public class LandingPageActivity extends AppCompatActivity {
    TextView centerMessage;
    RegistrationStore rs;
    String userEmail;
    Intent starterIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

       // rs = RegistrationStoreMongo.getInstance();
        starterIntent = getIntent();
        centerMessage = findViewById(R.id.centerMessage);
        userEmail = this.getIntent().getStringExtra("Email");

      //  centerMessage.setText("Success!!!  Welcome " + rs.getName(userEmail) + ", Member of Class of " + rs.getClassYear(userEmail) + " with phone " + rs.getPhone(userEmail));
    }

    public void moveToModify(View view) {
        Intent i = new Intent(this, ModifyPageActivity.class);
        i.putExtra("Email", userEmail);
        startActivityForResult(i, 2);
    }

    public void moveToUsers(View view) {
        Intent i = new Intent(this, ViewAllActivity.class);
        i.putExtra("Email", userEmail);
        startActivityForResult(i, 2);
    }

    public void moveToPosts(View view) {
//        Intent i = new Intent(this, CreatePostsActivity.class);
//        i.putExtra("Email", userEmail);
//        startActivityForResult(i, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recreate();
    }
}
