package com.manuh.droidcon2018.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.manuh.droidcon2018.R;
import com.manuh.droidcon2018.models.Post;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.DataViewHolder> {

    private FragmentActivity context;
    private List<Post> data;

    public PostAdapter(FragmentActivity context, List<Post> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_post, viewGroup, false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder dataViewHolder, int i) {
        DataViewHolder myHolder = (DataViewHolder) dataViewHolder;
        Post current = data.get(i);
        myHolder.title.setText(current.getTitle());
        myHolder.description.setText(current.getDescription());
//        URL url = null;
//        try {
//            url = new URL(current.getImageUrl());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        Bitmap bmp = null;
//        try {
//            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        myHolder.postImage.setImageBitmap(bmp);
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }


    static class DataViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        public ImageView postImage;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.txtPostTitle);
            description = (TextView) itemView.findViewById(R.id.txtPostDescription);
            postImage = (ImageView) itemView.findViewById(R.id.imgPost);
        }
    }
}
