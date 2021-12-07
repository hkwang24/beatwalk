//package com.example.beatwalk;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.beatwalk.R;
//import com.example.beatwalk.data.PostStore;
//import com.example.beatwalk.data.PostStoreMongo;
//import com.example.beatwalk.data.Posts;
//
//import java.util.Map;
//
//public class WriteCommentActivity extends AppCompatActivity {
//
//    PostStore postList;
//
//    String comments;
//    String id;
//    TextView commentsScreen;
//    EditText addComment;
//    TextView idNumber;
//    Map<String, Posts> all;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.write_comment);
//
//        postList = PostStoreMongo.getInstance();
//        all = postList.getUsers();
//
//        id = this.getIntent().getStringExtra("ID").toString();
//        comments = all.get(id).getComments();
//
//        commentsScreen = (TextView) findViewById(R.id.comments);
//        commentsScreen.setText(comments);
//
//        addComment = (EditText) findViewById(R.id.addComment);
//
//        idNumber = (TextView) findViewById(R.id.idNum);
//
//        idNumber.setText("on Post with ID " + id);
//
//    }
//
//    public void submitButton(View view) {
//        comments += "\n" + addComment.getText().toString();
//
//        commentsScreen.setText(comments);
//
//        postList.updateComments(comments, id);
//
//        addComment.getText().clear();
//
//        //AllPostsActivity obj = new AllPostsActivity();
//        //obj.updateComments(comments, Integer.parseInt(id.toString()));
//    }
//
//
//}
