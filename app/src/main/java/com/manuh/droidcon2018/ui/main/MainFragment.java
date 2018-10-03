package com.manuh.droidcon2018.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.manuh.droidcon2018.AddPostActivity;
import com.manuh.droidcon2018.ApolloClient;
import com.manuh.droidcon2018.PostsQuery;
import com.manuh.droidcon2018.R;
import com.manuh.droidcon2018.adapters.PostAdapter;
import com.manuh.droidcon2018.models.Post;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    RecyclerView recyclerView;
    PostAdapter mAdapter;
    FloatingActionButton floatingActionButton;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.btnAddPost);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddPostActivity.class);
                startActivity(intent);
            }
        });
        getPosts();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPosts();
    }

    private void getPosts() {
        ApolloClient.getApolloClient().query(PostsQuery.builder().build()).enqueue(new ApolloCall.Callback<PostsQuery.Data>() {
            @Override
            public void onResponse(@NotNull final Response<PostsQuery.Data> response) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<Post> data = new ArrayList<>();
                        for (int i = 0; i < response.data().allPosts().size(); ++i) {
                            Post extra = new Post();
                            extra.setDescription(response.data().allPosts().get(i).description());
                            extra.setImageUrl(response.data().allPosts().get(i).imageUrl());
                            extra.setTitle(response.data().allPosts().get(i).title());
                            data.add(extra);
                        }
                        recyclerView = (RecyclerView) getView().findViewById(R.id.rvPosts);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);
                        mAdapter = new PostAdapter(getActivity(), data);
                        recyclerView.setAdapter(mAdapter);
                        layoutManager.setReverseLayout(true);
                        layoutManager.setStackFromEnd(true);

                        Toast.makeText(getContext(), "" + response.data().allPosts().get(0).title(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
