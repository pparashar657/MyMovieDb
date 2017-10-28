package com.example.pawan.moviedb;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Pawan on 27-10-2017.
 */


public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ResultViewHolder>{

    private Context mContext;
    private Movies.Movie mSearchResults[];

    public SearchResultAdapter(Context mContext, Movies.Movie mSearchResults[]) {
        this.mContext = mContext;
        this.mSearchResults = mSearchResults;
    }

    @Override
    public SearchResultAdapter.ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ResultViewHolder(LayoutInflater.from(mContext).inflate(R.layout.search_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final ResultViewHolder holder, int position) {
        if (mSearchResults[position].getName() != null )
        {
            Log.i("string","setname :"+mSearchResults[position].getName());
            holder.nameTextView.setText(mSearchResults[position].getName());}
        else {
            holder.nameTextView.setText("");
        }
        if (mSearchResults[position].getMedia_type() != null && mSearchResults[position].getMedia_type().equals("movie"))
        { Log.i("string","movie");
            holder.mediaTypeTextView.setText("Movie");
            holder.nameTextView.setText(mSearchResults[position].getTitle());
        }
        else if (mSearchResults[position].getMedia_type() != null && mSearchResults[position].getMedia_type().equals("tv"))
        {   Log.i("string","tv show");
            holder.mediaTypeTextView.setText("TV Show");}
        else if (mSearchResults[position].getMedia_type() != null && mSearchResults[position].getMedia_type().equals("person"))
        {   Log.i("string","Person");
            holder.mediaTypeTextView.setText("Person");}
        else
            holder.mediaTypeTextView.setText("");

        if (mSearchResults[position].getOverview() != null && !mSearchResults[position].getOverview().trim().isEmpty())
        {Log.i("string","overview");
            holder.overviewTextView.setText(mSearchResults[position].getOverview());}
        else
            holder.overviewTextView.setText("");

        if (mSearchResults[position].getRelease_date() != null && !mSearchResults[position].getRelease_date().trim().isEmpty()) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
            try {
                Date releaseDate = sdf1.parse(mSearchResults[position].getRelease_date());
                holder.yearTextView.setText(sdf2.format(releaseDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            holder.yearTextView.setText("");
        }
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w342/" + mSearchResults[position].getPoster_path()).placeholder(R.drawable.loadingimage).error(mContext.getResources().getDrawable(R.drawable.image_not_found)).into(holder.posterImageView);
    }

    @Override
    public int getItemCount() {
        return mSearchResults.length;
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public ImageView posterImageView;
        public TextView nameTextView;
        public TextView mediaTypeTextView;
        public TextView overviewTextView;
        public TextView yearTextView;

        public ResultViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view_search);
            posterImageView = (ImageView) itemView.findViewById(R.id.image_view_poster_search);
            nameTextView = (TextView) itemView.findViewById(R.id.text_view_name_search);
            mediaTypeTextView = (TextView) itemView.findViewById(R.id.text_view_media_type_search);
            overviewTextView = (TextView) itemView.findViewById(R.id.text_view_overview_search);
            yearTextView = (TextView) itemView.findViewById(R.id.text_view_year_search);

            posterImageView.getLayoutParams().width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.31);
            posterImageView.getLayoutParams().height = (int) ((mContext.getResources().getDisplayMetrics().widthPixels * 0.31) / 0.66);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, mSearchResults[getAdapterPosition()].getTitle(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
