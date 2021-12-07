//package com.example.beatwalk;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.ScrollView;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.os.Bundle;
//import android.view.View;
//
//import java.io.Serializable;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.Date;
//import java.util.List;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.beatwalk.R;
//import com.example.beatwalk.data.PostStore;
//import com.example.beatwalk.data.PostStoreMongo;
//
//public class CreatePostsActivity extends AppCompatActivity {
//    PostStore postList;
//    Spinner food;
//    EditText description;
//    Spinner locationFood;
//    MainActivity email = new MainActivity();
//    private Button submitButton;
//    String userEmail;
//
//    private ArrayList<Post> posts;
//    private int counter = 0;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.create_posts_activity);
//        postList = PostStoreMongo.getInstance();
//        userEmail = this.getIntent().getStringExtra("Email");
//
//        food = (Spinner) findViewById(R.id.food);
//        String[] dataViews = new String[] {"Chinese", "Indian", "Mexican", "Italian", "American", "Japanese", "Thai", "Other"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, dataViews);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        food.setAdapter(adapter);
//
//        description = (EditText) findViewById(R.id.description);
//
//        locationFood = (Spinner) findViewById(R.id.locationFood);
//        String[] dataViews1 = new String[] {"Huntsman", "Stommons", "Harrison", "Rodin", "Harnwell", "Van Pelt", "Williams", "Levine", "Towne", "Other"};
//        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, dataViews1);
//        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        locationFood.setAdapter(adapter1);
//
//        submitButton = (Button) findViewById(R.id.submitButton);
//
//        if (getIntent().getSerializableExtra("RESULT") == null){
//            posts = new ArrayList<Post>();
//        }
//        else {
//            posts = (ArrayList<Post>) getIntent().getSerializableExtra("RESULT");
//          //  counter = posts.size();
//        }
//
//        submitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                submitButtonClicked();
//            }
//        });
//
//
//    }
//
//    private void submitButtonClicked() {
//        Date current = Calendar.getInstance().getTime();
//        Post p = new Post(food.getSelectedItem().toString(), description.getText().toString(),
//                locationFood.getSelectedItem().toString(), current, counter);
//
//        postList.addPost(current.toString(), food.getSelectedItem().toString(), description.getText().toString(), userEmail+ Integer.toString(counter), locationFood.getSelectedItem().toString(), userEmail, "");
//
//        counter++;
//        //food.getSelectedItem().clear();
//        description.getText().clear();
//        //locationFood.getSelectedItem().clear();
//
//        posts.add(p);
//    }
//
//    public void moveToSeeAllPosts(View view) {
//        Intent i = new Intent(this, AllPostsActivity.class);
//        i.putExtra("Email", userEmail);
//        i.putExtra("RESULT", posts);
//
//        startActivityForResult(i, 2);
//    }
//
//    public void moveToSeeYourPosts(View view) {
//        Intent i = new Intent(this, YourPostActivity.class);
//        i.putExtra("Email", userEmail);
//        i.putExtra("RESULT", posts);
//
//        startActivityForResult(i, 2);
//    }
//
//    public void sortRecentPosts(View view) {
//
//        Collections.sort(posts, new Comparator<Post>() {
//            @Override
//            public int compare(Post post1, Post post2) {
//                if (post1.getDate().getTime() < post2.getDate().getTime()) {
//                    return 1;
//                }
//                else if (post1.getDate().getTime() == post2.getDate().getTime()) {
//                    return 0;
//                }
//                else {
//                    return -1;
//                }
//            }
//        });
//
//    }
//
//
//}
