package com.rehammetwally.mvvmrecyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rehammetwally.mvvmrecyclerview.R;
import com.rehammetwally.mvvmrecyclerview.models.NicePlace;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    private List<NicePlace> list;
    private Context context;

    public RecyclerAdapter(List<NicePlace> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        NicePlace place=list.get(position);

        RequestOptions defaultOptions=new RequestOptions()
                .error(R.drawable.ic_launcher_background);
        Glide.with(context).setDefaultRequestOptions(defaultOptions).load(place.getImageUrl()).into(holder.image);
        holder.text.setText(place.getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        CircleImageView image;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            text=itemView.findViewById(R.id.text);
            image=itemView.findViewById(R.id.image);
        }
    }
}
