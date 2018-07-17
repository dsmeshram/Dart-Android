package idea.open.com.dartlib;

import android.content.Context;
import android.os.AsyncTask;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServiceCall
{
    private Context mContext;
    private String serviceUrl = "";

    public ServiceCall(Context context,String serviceUrl){
        this.mContext = context;
        this.serviceUrl = serviceUrl;
    }

    public interface onResponse {
        void getMapper(JSONObject objects);
    }

    public void getUpdateMapper(onResponse callback){
        new getUpdatedMapper(callback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,this.serviceUrl);
    }

    class getUpdatedMapper extends AsyncTask<String,Void,JSONObject>{
        private onResponse callback;

        public getUpdatedMapper(onResponse response){
            this.callback = response;
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);

                urlConnection = (HttpURLConnection) url
                        .openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-length", "0");
                urlConnection.setUseCaches(false);
                urlConnection.setAllowUserInteraction(false);
                urlConnection.setConnectTimeout(100000);
                urlConnection.setReadTimeout(100000);

                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                return new JSONObject(sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if(this.callback !=null){
                this.callback.getMapper(jsonObject);
            }
        }
    }
}
