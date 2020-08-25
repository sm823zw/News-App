package com.example.footballdaily;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import java.util.List;

public class FeedAdapter extends ArrayAdapter {
    private final LayoutInflater layoutInflater;
    private final int layoutResource;
    private List<News> news;

    public class ViewHolder {
        final CardView mCardView;
        final TextView tvTitle;

        ViewHolder(View v) {
            this.tvTitle = (TextView) v.findViewById(R.id.tvTitle);
            this.mCardView = (CardView) v.findViewById(R.id.card);
        }
    }

    FeedAdapter(Context context, int resource, List<News> news) {
        super(context, resource, news);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.news = news;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = this.layoutInflater.inflate(this.layoutResource, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(((News) this.news.get(position)).getTitle());
        return convertView;
    }
}
