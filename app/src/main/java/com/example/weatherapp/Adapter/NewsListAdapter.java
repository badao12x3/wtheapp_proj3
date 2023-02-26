package com.example.weatherapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.Models.News;
import com.example.weatherapp.NewsWebClickedListener;
import com.example.weatherapp.R;

import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private Context mContext;
    private List<News> newsList;
    private NewsWebClickedListener newsWebClickedListener;

    public NewsListAdapter(List<News> newsList, NewsWebClickedListener newsWebClickedListener) {
        this.newsList = newsList;
        this.newsWebClickedListener = newsWebClickedListener;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<News> _newsList){
        newsList.clear();
        newsList.addAll(_newsList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list,parent,false);
        mContext = parent.getContext();
        return new NewsListAdapter.ViewHolder(view, newsWebClickedListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = newsList.get(position);

        String text = news.getPublishAt() ;
        // Lấy ra chuỗi ngày tháng
        String dateStr = text.substring(text.indexOf("ngày") + 4, text.indexOf("ngày") + 10);
        // Output: "09/02/2023"

        // Lấy ra chuỗi giờ phút
        String timeStr = text.substring(text.indexOf("ngày") + 15, text.indexOf("ngày") + 21);
        // Output: "19:15"
        holder.tvCreateNews.setText(timeStr + "\n"+dateStr);
        holder.tvTitleNews.setText(news.getDescribe());

        String coverImg = news.getCoverImg();
        Glide.with(mContext).load(coverImg).into(holder.ivNews);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder {

        private TextView tvTitleNews;
        private TextView tvCreateNews;
        private ImageView ivNews;
        private NewsWebClickedListener _newsWebClickedListener;

        public ViewHolder(@NonNull View itemView, NewsWebClickedListener __newsWebClickedListener) {
            super(itemView);

            tvTitleNews = itemView.findViewById(R.id.tvTitleNews);
            tvCreateNews = itemView.findViewById(R.id.tvCreateNews);
            ivNews = itemView.findViewById(R.id.ivNews);

            _newsWebClickedListener = __newsWebClickedListener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (_newsWebClickedListener != null){
                        _newsWebClickedListener.onItemNewsClicked(getAdapterPosition());
                    }
                }
            });

        }
    }
}
