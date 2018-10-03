package com.manuh.droidcon2018;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;

public class AddPostActivity extends AppCompatActivity {

    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPost();
            }
        });

    }

    private void addPost() {
        TextView txtPostTitle = (TextView) findViewById(R.id.edtPostTitle);
        TextView txtDescription = (TextView) findViewById(R.id.edtPostDescription);

        ApolloClient.getApolloClient().mutate(PostMutation.builder()
                .title(txtPostTitle.getText().toString())
                .description(txtDescription.getText().toString())
                .build()
        ).enqueue(new ApolloCall.Callback<PostMutation.Data>() {
            @Override
            public void onResponse(@NotNull final Response<PostMutation.Data> response) {
                AddPostActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AddPostActivity.this, "" + response.data().createPost.id(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                AddPostActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(AddPostActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
