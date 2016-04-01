package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.nanodegree.gemma.jokeactivity.JokeActivity;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import com.udacity.gradle.jokes.Joker;


public class MainActivity extends AppCompatActivity {

    private static MyApi myApiService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Method called when user requests a joke
     * It displays as a Toast
     * @param view
     */
    public void tellJoke(View view){
        Joker joker = new Joker();
        String joke = joker.getAJoke();
        Toast.makeText(this, joke, Toast.LENGTH_SHORT).show();
    }

    /**
     * Gets a joke from the java joke library
     * Passes the joke to the Android Joke Activity library
     * via an intent
     */
    public void launchJokeActivity(){
        Intent intent = new Intent(this, JokeActivity.class);
        Joker joker = new Joker();
        String joke = joker.getAJoke();
        intent.putExtra(JokeActivity.JOKE_KEY, joke);
        startActivity(intent);
    }

    /**
     * Starts a task to receive a joke from the Java Library via a GCE module
     */
    public void startJokeRetreivalTask(View view) {

        new GetJokeAsyncTask().execute(this);
    }

    /**
     * Task that will retrieve a joke from the backend
     */
//    public class GetJokeAsyncTask extends AsyncTask<Void, Void, String> {
//
//        private Context context;
//
//
//
//        @Override
//        protected String doInBackground(Void... params) {
//            if(myApiService == null) {  // Only do this once
//                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
//                        new AndroidJsonFactory(), null)
//                        // options for running against local devappserver
//                        // - 10.0.2.2 is localhost's IP address in Android emulator
//                        // - turn off compression when running against local devappserver
//
//                        // this works for emulator
//                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
//
//                        // my computers IP in local network to test on real device
////                        .setRootUrl("http://192.168.1.103:8080/_ah/api/")
//
//                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
//                            @Override
//                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
//                                abstractGoogleClientRequest.setDisableGZipContent(true);
//                            }
//                        });
//                // end options for devappserver
//
//                myApiService = builder.build();
//            }
//
//
//            try {
//                return myApiService.tellJoke().execute().getData();
//            } catch (IOException e) {
//                return e.getMessage();
//            }
//        }
//
//        /**
//         * Shows the joke to the user when task finished
//         * @param result
//         */
//        @Override
//        protected void onPostExecute(String result) {
////            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
//            if (result != null) {
//                launchJokeActivity();
//            } else {
//                Log.d("GetJokeTask", "Joke result is null");
//            }
//        }
//    }

}


