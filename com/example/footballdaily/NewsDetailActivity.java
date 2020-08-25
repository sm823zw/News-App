package com.example.footballdaily;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends BaseActivity {
    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        activateToolbar(true);
        News news = (News) getIntent().getSerializableExtra("NEWS_TRANSFER");
        if (news != null) {
            TextView newsSource = (TextView) findViewById(R.id.news_source);
            TextView newsTitle = (TextView) findViewById(R.id.news_title);
            ImageView newsImage = (ImageView) findViewById(R.id.news_image);
            TextView newsLink = (TextView) findViewById(R.id.news_link);
            TextView newsDescription = (TextView) findViewById(R.id.news_description);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Source- ");
            stringBuilder.append(news.getSourceName());
            newsSource.setText(stringBuilder.toString());
            newsTitle.setText(news.getTitle());
            String textLink = new StringBuilder();
            textLink.append("<a href=");
            textLink.append(news.getNewsUrl());
            textLink.append("> Tap here for more details </a>");
            textLink = textLink.toString();
            newsLink.setClickable(true);
            newsLink.setMovementMethod(LinkMovementMethod.getInstance());
            newsLink.setText(Html.fromHtml(textLink, 63));
            newsDescription.setText(news.getDescription());
            Picasso.get().load(news.getImageUrl()).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(newsImage);
        }
    }
}
