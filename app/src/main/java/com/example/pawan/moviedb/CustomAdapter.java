package com.example.pawan.moviedb;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Pawan on 26-10-2017.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ExpenseHolder> {

    Movies.Movie mMovie[];
    Context mContext;
    ClickListener mListener;

    public CustomAdapter(Movies.Movie mMovie[], Context mContext, ClickListener mListener) {
        this.mMovie = mMovie;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    public ExpenseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.row_layout,parent,false);
        return new ExpenseHolder(view,mListener);

    }

    @Override
    public void onBindViewHolder(ExpenseHolder holder, int position) {
        Movies.Movie m=mMovie[position];
        holder.title.setText(m.getTitle());
        holder.desc.setText(m.getOverview());
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w342/"+m.getPoster_path()).resize(2000,900).centerInside().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mMovie.length;
    }

    public static class ExpenseHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        TextView desc;
        ImageView imageView;
        View itemView;
        ClickListener listener;

        public ExpenseHolder(View itemView,ClickListener listener) {
            super(itemView);
            this.itemView=itemView;
            this.listener=listener;
            title=(TextView)itemView.findViewById(R.id.title);
            desc=(TextView)itemView.findViewById(R.id.desc);
            imageView=(ImageView)itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int position=getAdapterPosition();
            if (view == itemView){
                listener.onItemClick(position);
            }
        }
    }

    public interface ClickListener{
        void onItemClick(int position);
    }
}
