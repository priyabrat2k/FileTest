package net.appifiedtech.appmemorytest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Priyabrat on 16-12-2015.
 */
public class HTTP {

    public static String URL_DOWNLOAD = "http://fastcovai.com/wp-content/uploads/2014/07/Vicks-Vaporub-10g.png";
    public static String FILE_NAME = "Vicks-Vaporub-10g.png";
    private static String TAG = "HTTP";

    static class HttpFileDownloader extends AsyncTask<Void, Integer, Void> {

        private Activity activity;
        private ProgressDialog progressDialog;

        public HttpFileDownloader(Activity activity){
            this.activity = activity;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Downloading");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL(URL_DOWNLOAD);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream(),1024);
                File fileRoot = activity.getDir("priyabrat", Context.MODE_PRIVATE);
                File filePath = new File(fileRoot,FILE_NAME);
                OutputStream outputStream = new FileOutputStream(filePath);

                int count,total = 0;
                byte [] byteArray = new byte[1024];
                int fileLength = httpURLConnection.getContentLength();
                while ((count = inputStream.read(byteArray)) != -1)
                {
                    total = total+count;
                    outputStream.write(byteArray, 0, count);
                    publishProgress((int)((total*100)/fileLength));
                }
                inputStream.close();
                outputStream.flush();
                outputStream.close();

            } catch (MalformedURLException e) {
                Log.d(TAG,"Download error due to "+e);
                e.printStackTrace();
            } catch (IOException e) {
                Log.d(TAG,"Download error due to "+e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }
    }
}
