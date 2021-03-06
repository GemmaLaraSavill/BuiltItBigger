package com.udacity.gradle.builditbigger;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.nanodegree.gemma.jokeactivity.JokeActivity;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import com.udacity.gradle.jokes.Joker;

import java.io.IOException;

/**
 * Created by Gemma S. Lara Savill on 01/04/2016.
 * Task that queries the GCE backend for a joke
 */
public class GetJokeAsyncTask extends AsyncTask<Context, Integer, String> {

    private static MyApi myApiService = null;
    private Context context;
    private ProgressDialog loadingDialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        Log.d("GetJokeTask", "onPreExecute");
        // show the loading indicator
        loadingDialog = new ProgressDialog(context);
        loadingDialog.setMessage(context.getString(R.string.loading));
        loadingDialog.show();
    }

    @Override
    protected String doInBackground(Context... params) {
//        Log.d("GetJokeTask", "doInBackground");
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver

                    // this works for emulator
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")

                            // my computers IP in local network to test on real device
//                        .setRootUrl("http://192.168.1.205:8080/_ah/api/")

                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        context = params[0];

        try {
//            Log.d("GetJokeTask", "request joke");
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

     /**
     * Shows the joke to the user when task finished
     * @param result
     */
    @Override
    protected void onPostExecute(String result) {
//        Log.d("GetJokeTask", "onPostExecute joke "+result);
        // hide the loading indicator
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
//            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        if (result != null) {
            Intent intent = new Intent(context, JokeActivity.class);
            Joker joker = new Joker();
            String joke = joker.getAJoke();
            intent.putExtra(JokeActivity.JOKE_KEY, joke);
            context.startActivity(intent);
        } else {
            Log.d("GetJokeTask", "Joke result is null");
        }
    }


    public void setContext(Context context) {
        this.context = context;
    }
}
