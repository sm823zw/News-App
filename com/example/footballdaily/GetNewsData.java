package com.example.footballdaily;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class GetNewsData extends AsyncTask<String, Void, List<News>> implements OnDownloadComplete {
    private static final String TAG = "GetNewsData";
    private String mBaseUrl;
    private String mCategory;
    private String mCountry;
    private List<News> mNewsList = null;
    private final OnDataAvailable mcallback;

    interface OnDataAvailable {
        void onDataAvailable(List<News> list, DownloadStatus downloadStatus);
    }

    GetNewsData(OnDataAvailable callback, String baseUrl, String country, String category) {
        this.mBaseUrl = baseUrl;
        this.mCountry = country;
        this.mCategory = category;
        this.mcallback = callback;
    }

    /* Access modifiers changed, original: protected */
    public void onPostExecute(List<News> list) {
        OnDataAvailable onDataAvailable = this.mcallback;
        if (onDataAvailable != null) {
            onDataAvailable.onDataAvailable(this.mNewsList, DownloadStatus.OK);
        }
    }

    /* Access modifiers changed, original: protected|varargs */
    public List<News> doInBackground(String... strings) {
        new GetRawData(this).runInSameThread(createUri(this.mCountry, this.mCategory));
        return this.mNewsList;
    }

    private String createUri(String country, String category) {
        return Uri.parse(this.mBaseUrl).buildUpon().appendQueryParameter("country", country).appendQueryParameter("category", category).appendQueryParameter("pageSize", "50").appendQueryParameter("language", "en").appendQueryParameter("apikey", "8d7627fa553a4110a5362c66a695c88b").build().toString();
    }

    public void onDownloadComplete(String data, DownloadStatus status) {
        JSONException e;
        StringBuilder stringBuilder;
        String str;
        String str2 = "null";
        String str3 = TAG;
        if (status == DownloadStatus.OK) {
            this.mNewsList = new ArrayList();
            try {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray jsonArray = jsonObject.getJSONArray("articles");
                    int i = 0;
                    while (i < jsonArray.length()) {
                        JSONObject jsonArticle = jsonArray.getJSONObject(i);
                        String author = jsonArticle.getString("author");
                        String title = jsonArticle.getString("title");
                        String description = jsonArticle.getString("description");
                        String linkURL = jsonArticle.getString("url");
                        String imageURL = jsonArticle.getString("urlToImage");
                        String content = jsonArticle.getString("content");
                        JSONObject jsonObject2 = jsonObject;
                        jsonObject = jsonArticle.getJSONObject("source").getString("name");
                        News news = new News();
                        news.setAuthor(author);
                        news.setTitle(title);
                        news.setSourceName(jsonObject);
                        boolean equalsIgnoreCase = description.equalsIgnoreCase(str2);
                        JSONObject sourceName = jsonObject;
                        jsonObject = BuildConfig.FLAVOR;
                        if (equalsIgnoreCase) {
                            news.setDescription(jsonObject);
                        } else {
                            news.setDescription(description);
                        }
                        if (content.equalsIgnoreCase(str2)) {
                            news.setContent(jsonObject);
                        } else {
                            news.setContent(content);
                        }
                        news.setImageUrl(imageURL);
                        news.setNewsUrl(linkURL);
                        this.mNewsList.add(news);
                        jsonObject = new StringBuilder();
                        String str4 = str2;
                        jsonObject.append("onDownloadComplete: ");
                        jsonObject.append(news.toString());
                        Log.d(str3, jsonObject.toString());
                        i++;
                        DownloadStatus downloadStatus = status;
                        jsonObject = jsonObject2;
                        str2 = str4;
                    }
                    return;
                } catch (JSONException e2) {
                    e = e2;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("onDownloadComplete: JSONException");
                    stringBuilder.append(e.getMessage());
                    Log.e(str3, stringBuilder.toString());
                    return;
                }
            } catch (JSONException e3) {
                e = e3;
                str = data;
                stringBuilder = new StringBuilder();
                stringBuilder.append("onDownloadComplete: JSONException");
                stringBuilder.append(e.getMessage());
                Log.e(str3, stringBuilder.toString());
                return;
            }
        }
        str = data;
    }
}
