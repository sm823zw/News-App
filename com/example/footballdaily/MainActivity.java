package com.example.footballdaily;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity implements OnDataAvailable {
    private static final String SAVED_CATEGORY = "SAVED_CATEGORY";
    private static final String TAG = "MainActivity";
    private static String cachedCategory = "INVALIDATED";
    private static String category = BuildConfig.FLAVOR;
    private static boolean newCountryNewsFlag = false;
    private static List<News> news;
    private static boolean wrongCountryQueryFlag = false;
    private String baseURL = "https://newsapi.org/v2/top-headlines";
    private TextView categoryHeading;
    private int[] category_id = new int[]{R.id.mnuTopNews, R.id.mnuBusiness, R.id.mnuEntertainment, R.id.mnuHealth, R.id.mnuScience, R.id.mnuSports, R.id.mnuTechnology};
    private String[] category_types = new String[]{BuildConfig.FLAVOR, "Business", "Entertainment", "Health", "Science", "Sports", "Technology"};
    private String[] country_codes = new String[]{"ar", "au", "at", "be", "br", "bg", "ca", "cn", "co", "cu", "cz", "eg", "fr", "de", "gr", "hk", "hu", "in", "id", "ie", "il", "it", "jp", "lv", "lt", "my", "mx", "ma", "nl", "nz", "ng", "no", "ph", "pl", "pt", "ro", "ru", "sa", "rs", "sg", "sk", "za", "kr", "se", "ch", "tw", "th", "tr", "ae", "ua", "gb", "us", "ve"};
    private String[] country_names = new String[]{"argentina", "australia", "austria", "belgium", "brazil", "bulgaria", "canada", "china", "colombia", "cuba", "czech republic", "egypt", "france", "germany", "greece", "hong kong", "hungary", "india", "indonesia", "ireland", "israel", "italy", "japan", "latvia", "lithuania", "malaysia", "mexico", "morocco", "netherlands", "new zealand", "nigeria", "norway", "philippines", "poland", "portugal", "romania", "russia", "saudi arabia", "serbia", "singapore", "slovakia", "south africa", "south korea", "sweden", "switzerland", "taiwan", "thailand", "turkey", "uae", "ukraine", "united kingdom", "united states", "venezuela"};
    private ListView listNews;
    private HashMap<String, String> map = new HashMap();
    private String queryResult = "india";

    public MainActivity() {
    }

    /* Access modifiers changed, original: 0000 */
    public void obtainCountryCodes() {
        int i = 0;
        while (true) {
            String[] strArr = this.country_codes;
            if (i < strArr.length) {
                this.map.put(this.country_names[i], strArr[i]);
                i++;
            } else {
                String str = "gb";
                this.map.put("england", str);
                this.map.put("uk", str);
                String str2 = "us";
                this.map.put(str2, str2);
                this.map.put("usa", str2);
                this.map.put("great britain", str);
                return;
            }
        }
    }

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle savedInstanceState) {
        String str = TAG;
        Log.d(str, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activateToolbar(false);
        this.listNews = (ListView) findViewById(R.id.list_items);
        this.categoryHeading = (TextView) findViewById(R.id.tvMainCategory);
        obtainCountryCodes();
        if (savedInstanceState != null) {
            category = savedInstanceState.getString(SAVED_CATEGORY);
        }
        if (category != null) {
            downloadData(this.baseURL, (String) this.map.get(this.queryResult), category);
        }
        Log.d(str, "onCreate: ends");
    }

    /* Access modifiers changed, original: protected */
    public void onResume() {
        String str = TAG;
        Log.d(str, "onResume: starts");
        super.onResume();
        if (newCountryNewsFlag) {
            Log.d(str, "onResume: returning from SearchActivity.");
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String str2 = BuildConfig.FLAVOR;
            this.queryResult = sharedPreferences.getString("COUNTRY_QUERY", str2);
            this.queryResult = this.queryResult.toLowerCase();
            if (this.map.containsKey(this.queryResult)) {
                wrongCountryQueryFlag = false;
                cachedCategory = "INVALIDATED";
                downloadData(this.baseURL, (String) this.map.get(this.queryResult), category);
            } else {
                News dummyNews = new News();
                dummyNews.setTitle("No results available for this region. Try for another country.");
                news.clear();
                news.add(dummyNews);
                this.listNews.setAdapter(new FeedAdapter(this, R.layout.list_records, news));
                this.categoryHeading.setText(str2);
                wrongCountryQueryFlag = true;
            }
        }
        Log.d(str, "onResume: ends");
    }

    /* Access modifiers changed, original: protected */
    public void onSaveInstanceState(Bundle outState) {
        String str = TAG;
        Log.d(str, "onSaveInstanceState: starts");
        super.onSaveInstanceState(outState);
        outState.putString(SAVED_CATEGORY, category);
        Log.d(str, "onSaveInstanceState: ends");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.category_menu, menu);
        int i = 0;
        while (true) {
            String[] strArr = this.category_types;
            if (i >= strArr.length) {
                return true;
            }
            if (category.equals(strArr[i])) {
                menu.findItem(this.category_id[i]).setChecked(true);
            }
            i++;
        }
    }

    /* JADX WARNING: Missing block: B:12:0x004d, code skipped:
            if (wrongCountryQueryFlag != false) goto L_0x0060;
     */
    /* JADX WARNING: Missing block: B:13:0x004f, code skipped:
            downloadData(r5.baseURL, (java.lang.String) r5.map.get(r5.queryResult), category);
     */
    /* JADX WARNING: Missing block: B:14:0x0060, code skipped:
            return true;
     */
    public boolean onOptionsItemSelected(android.view.MenuItem r6) {
        /*
        r5 = this;
        r0 = r6.getItemId();
        r1 = 2131361987; // 0x7f0a00c3 float:1.8343742E38 double:1.0530327366E-314;
        r2 = 1;
        if (r0 == r1) goto L_0x0061;
    L_0x000a:
        switch(r0) {
            case 2131361932: goto L_0x0043;
            case 2131361933: goto L_0x003b;
            case 2131361934: goto L_0x0033;
            case 2131361935: goto L_0x002e;
            case 2131361936: goto L_0x0026;
            case 2131361937: goto L_0x001e;
            case 2131361938: goto L_0x0016;
            case 2131361939: goto L_0x000e;
            default: goto L_0x000d;
        };
    L_0x000d:
        goto L_0x006d;
    L_0x000e:
        r1 = "";
        category = r1;
        r6.setChecked(r2);
        goto L_0x004b;
    L_0x0016:
        r1 = "Technology";
        category = r1;
        r6.setChecked(r2);
        goto L_0x004b;
    L_0x001e:
        r1 = "Sports";
        category = r1;
        r6.setChecked(r2);
        goto L_0x004b;
    L_0x0026:
        r1 = "Science";
        category = r1;
        r6.setChecked(r2);
        goto L_0x004b;
    L_0x002e:
        r1 = "INVALIDATED";
        cachedCategory = r1;
        goto L_0x004b;
    L_0x0033:
        r1 = "Health";
        category = r1;
        r6.setChecked(r2);
        goto L_0x004b;
    L_0x003b:
        r1 = "Entertainment";
        category = r1;
        r6.setChecked(r2);
        goto L_0x004b;
    L_0x0043:
        r1 = "Business";
        category = r1;
        r6.setChecked(r2);
    L_0x004b:
        r1 = wrongCountryQueryFlag;
        if (r1 != 0) goto L_0x0060;
    L_0x004f:
        r1 = r5.baseURL;
        r3 = r5.map;
        r4 = r5.queryResult;
        r3 = r3.get(r4);
        r3 = (java.lang.String) r3;
        r4 = category;
        r5.downloadData(r1, r3, r4);
    L_0x0060:
        return r2;
    L_0x0061:
        r1 = new android.content.Intent;
        r3 = com.example.footballdaily.SearchActivity.class;
        r1.<init>(r5, r3);
        newCountryNewsFlag = r2;
        r5.startActivity(r1);
    L_0x006d:
        r1 = super.onOptionsItemSelected(r6);
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.footballdaily.MainActivity.onOptionsItemSelected(android.view.MenuItem):boolean");
    }

    /* Access modifiers changed, original: 0000 */
    public void downloadData(String baseURL, String countryCode, String category) {
        String str = TAG;
        Log.d(str, "downloadData: starts");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("downloadData: ");
        stringBuilder.append(category);
        stringBuilder.append(" == or != ");
        stringBuilder.append(cachedCategory);
        Log.d(str, stringBuilder.toString());
        if (category.equals(BuildConfig.FLAVOR)) {
            this.categoryHeading.setText(String.format("Top News - %s", new Object[]{this.queryResult.toUpperCase()}));
        } else {
            this.categoryHeading.setText(String.format("%s - %s", new Object[]{category, this.queryResult.toUpperCase()}));
        }
        if (category.equalsIgnoreCase(cachedCategory)) {
            Log.d(str, "downloadData: Category is not changed.");
            onDataAvailable(news, DownloadStatus.OK);
        } else {
            GetNewsData getNewsData = new GetNewsData(this, baseURL, countryCode, category);
            Log.d(str, "downloadData: getting new feed. (1)");
            getNewsData.execute(new String[0]);
            cachedCategory = category;
        }
        Log.d(str, "downloadData: ends");
    }

    public void onDataAvailable(final List<News> data, DownloadStatus status) {
        String str = TAG;
        Log.d(str, "onDataAvailable: yes available");
        if (status == DownloadStatus.OK) {
            news = data;
            this.listNews.setAdapter(new FeedAdapter(this, R.layout.list_records, data));
            this.listNews.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, NewsDetailActivity.class);
                    intent.putExtra("NEWS_TRANSFER", (Serializable) data.get(position));
                    MainActivity.newCountryNewsFlag = false;
                    MainActivity.this.startActivity(intent);
                }
            });
            return;
        }
        Log.d(str, "onDownloadComplete: Download failed !!!!!!!!!!!!!!!!");
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, SplashScreen.class);
        intent.setFlags(67108864);
        intent.putExtra("EXIT", true);
        startActivity(intent);
        super.onBackPressed();
    }
}
