package com.example.footballdaily;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetRawData extends AsyncTask<String, Void, String> {
    private static final String TAG = "GetRawData";
    private OnDownloadComplete mCallback;
    private DownloadStatus mDownloadStatus = DownloadStatus.IDLE;

    interface OnDownloadComplete {
        void onDownloadComplete(String str, DownloadStatus downloadStatus);
    }

    GetRawData(OnDownloadComplete callback) {
        this.mCallback = callback;
    }

    /* Access modifiers changed, original: 0000 */
    public void runInSameThread(String url) {
        onPostExecute(doInBackground(url));
    }

    /* Access modifiers changed, original: protected */
    public void onPostExecute(String s) {
        OnDownloadComplete onDownloadComplete = this.mCallback;
        if (onDownloadComplete != null) {
            onDownloadComplete.onDownloadComplete(s, this.mDownloadStatus);
        }
    }

    /* Access modifiers changed, original: protected|varargs */
    public String doInBackground(String... strings) {
        StringBuilder builder;
        IOException e;
        String str = "doInBackground: Error closing stream";
        String str2 = TAG;
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        if (strings == null) {
            this.mDownloadStatus = DownloadStatus.NOT_INITIALIZED;
            return null;
        }
        try {
            this.mDownloadStatus = DownloadStatus.PROCESSING;
            connection = (HttpURLConnection) new URL(strings[0]).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            builder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                builder.append(line);
                builder.append("\n");
            }
            this.mDownloadStatus = DownloadStatus.OK;
            String stringBuilder = builder.toString();
            if (connection != null) {
                connection.disconnect();
            }
            try {
                reader.close();
            } catch (IOException e2) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(str);
                stringBuilder2.append(e2.getMessage());
                Log.e(str2, stringBuilder2.toString());
            }
            return stringBuilder;
        } catch (MalformedURLException e3) {
            builder = new StringBuilder();
            builder.append("doInBackground: INVALID URL ");
            builder.append(e3.getMessage());
            Log.e(str2, builder.toString());
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e4) {
                    e = e4;
                    builder = new StringBuilder();
                }
            }
            this.mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
            return null;
        } catch (IOException e5) {
            builder = new StringBuilder();
            builder.append("doInBackground: IO exception ");
            builder.append(e5.getMessage());
            Log.e(str2, builder.toString());
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e6) {
                    e5 = e6;
                    builder = new StringBuilder();
                }
            }
            this.mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
            return null;
        } catch (SecurityException e7) {
            builder = new StringBuilder();
            builder.append("doInBackground: Security exception");
            builder.append(e7.getMessage());
            Log.e(str2, builder.toString());
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e8) {
                    e5 = e8;
                    builder = new StringBuilder();
                }
            }
            this.mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
            return null;
        } catch (Throwable th) {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e52) {
                    builder = new StringBuilder();
                    builder.append(str);
                    builder.append(e52.getMessage());
                    Log.e(str2, builder.toString());
                }
            }
        }
        builder.append(str);
        builder.append(e52.getMessage());
        Log.e(str2, builder.toString());
        this.mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
        return null;
    }
}
