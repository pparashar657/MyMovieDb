package com.example.pawan.moviedb;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Pawan on 26-10-2017.
 */

public class MovieCustomAdapter extends RecyclerView.Adapter<MovieCustomAdapter.ExpenseHolder> {

    ArrayList<Movies.Movie> mMovie;
    Context mContext;
    ClickListener mListener;

    public MovieCustomAdapter(ArrayList<Movies.Movie> mMovie, Context mContext, ClickListener mListener) {
        this.mMovie = mMovie;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    public ExpenseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.movie_row_layout,parent,false);
        return new ExpenseHolder(view,mListener);

    }

    @Override
    public void onBindViewHolder(ExpenseHolder holder, int position) {
        Movies.Movie m=mMovie.get(position);
        holder.title.setText(m.getTitle());
        MoviesFragment.temp++;
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w342/"+m.getPoster_path()).resize(2000,700).centerInside().placeholder(R.drawable.loadingimage).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mMovie.size();
    }

    public static class ExpenseHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        ImageView imageView;
        View itemView;
        ClickListener listener;

        public ExpenseHolder(View itemView,ClickListener listener) {
            super(itemView);
            this.itemView=itemView;
            this.listener=listener;
            title=(TextView)itemView.findViewById(R.id.title);
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
