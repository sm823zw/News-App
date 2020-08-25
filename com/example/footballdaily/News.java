package com.example.footballdaily;

import java.io.Serializable;

public class News implements Serializable {
    private static final long serialVersionUID = 1;
    private String author;
    private String content;
    private String description;
    private String imageUrl;
    private String newsUrl;
    private String sourceName;
    private String title;

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getNewsUrl() {
        return this.newsUrl;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public String getSourceName() {
        return this.sourceName;
    }

    public String getContent() {
        return this.content;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("News{author='");
        stringBuilder.append(this.author);
        stringBuilder.append('\'');
        stringBuilder.append(", title='");
        stringBuilder.append(this.title);
        stringBuilder.append('\'');
        stringBuilder.append(", description='");
        stringBuilder.append(this.description);
        stringBuilder.append('\'');
        stringBuilder.append(", newsUrl='");
        stringBuilder.append(this.newsUrl);
        stringBuilder.append('\'');
        stringBuilder.append(", imageUrl='");
        stringBuilder.append(this.imageUrl);
        stringBuilder.append('\'');
        stringBuilder.append(", sourceName='");
        stringBuilder.append(this.sourceName);
        stringBuilder.append('\'');
        stringBuilder.append(", content='");
        stringBuilder.append(this.content);
        stringBuilder.append('\'');
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
