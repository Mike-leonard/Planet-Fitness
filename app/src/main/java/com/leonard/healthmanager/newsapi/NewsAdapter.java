package com.leonard.healthmanager.newsapi;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leonard.healthmanager.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.leonard.healthmanager.newsapi.NewsModel.NEWS_IMAGE_TYPE;
import static com.leonard.healthmanager.newsapi.NewsModel.NEWS_WITHOUT_IMAGE_TYPE;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NewsModel> newsList;

    public NewsAdapter(List<NewsModel> newsList) {
        this.newsList = newsList;
    }
    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case NEWS_IMAGE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_align_child, parent, false);
                return new NewsWithImageViewHolder(view);
            case NEWS_WITHOUT_IMAGE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_align_child, parent, false);
                return new NewsWithImageViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        NewsModel newsModel = newsList.get(position);
        switch (newsModel.getType()) {
            case NEWS_IMAGE_TYPE:
                ((NewsWithImageViewHolder) holder).txtTitle.setText(newsModel.getTitle());
                ((NewsWithImageViewHolder) holder).txtNwTimes.setText(newsModel.getPublishedTime());
                Picasso.get()
                        .load(newsModel.getUrlToImage())
                        .resize(200,
                                200)
                        .centerCrop()
                        .into(((NewsWithImageViewHolder) holder).img_v_news);
                Log.e("NewsAdapter", "dukce");

                /*((NewsWithImageViewHolder) viewHolder).tv_news_with_image_item.setText(news.getTitle());
                Picasso.get()
                        .load(news.getUrlToImage())
                        .resize(200, 200)
                        .centerCrop()
                        .into(((NewsWithImageViewHolder) viewHolder).iv_news);*/
                break;
            case NEWS_WITHOUT_IMAGE_TYPE:
                ((NewsWithImageViewHolder) holder).txtTitle.setText(newsModel.getTitle());
                ((NewsWithImageViewHolder) holder).txtNwTimes.setText(newsModel.getPublishedTime());
                Picasso.get()
                        .load(R.mipmap.drawer_app)
                        .resize(200,
                                200)
                        .centerCrop()
                        .into(((NewsWithImageViewHolder) holder).img_v_news);
                Log.e("NewsAdapter", "dukce na");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
    @Override
    public int getItemViewType(int position) {

        NewsModel newsModel = newsList.get(position);

        if (newsModel != null) {
            return newsModel.getType();
        }

        return 0;
    }

    public class NewsWithImageViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtNwTimes;
        ImageView img_v_news;

        public NewsWithImageViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_news);
            txtNwTimes = itemView.findViewById(R.id.txt_nw_time);
            img_v_news = itemView.findViewById(R.id.news_image);

        }
    }

  /*  public class NewsWithOutImageViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtNwTimes;
        ImageView img_v_news;

        public NewsWithOutImageViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_news);
            txtNwTimes = itemView.findViewById(R.id.txt_nw_time);
            img_v_news = itemView.findViewById(R.id.news_image);
        }
    }*/

}
