package com.hsynates.savenoteandsharephoto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PostHolder> {
    private final ArrayList<String> emailList;
    private final ArrayList<String> titleList;
    private final ArrayList<String> imageList;

    public RecyclerAdapter(ArrayList<String> emailList, ArrayList<String> titleList, ArrayList<String> imageList) {
        this.emailList = emailList;
        this.titleList = titleList;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_view, parent, false);
        return new PostHolder(view);
    }

    @Override
    public int getItemCount() {
        return emailList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.textViewRecyclerEmail.setText(emailList.get(position));
        holder.textViewRecyclerTitle.setText(titleList.get(position));
        Picasso.get().load(imageList.get(position)).into(holder.imageViewRecycler);
    }

    class PostHolder extends RecyclerView.ViewHolder {

        TextView textViewRecyclerEmail, textViewRecyclerTitle;
        ImageView imageViewRecycler;

        public PostHolder(@NonNull View itemView) {
            super(itemView);

            textViewRecyclerEmail = itemView.findViewById(R.id.textViewEmailRecycler);
            textViewRecyclerTitle = itemView.findViewById(R.id.textViewTitleRecycler);
            imageViewRecycler = itemView.findViewById(R.id.imageViewRecycler);
        }
    }
}
