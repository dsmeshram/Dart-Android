package idea.open.com.dartapp;

import android.app.Application;

import idea.open.com.dartlib.Dartlib;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Dartlib.getInstance().init(this,"http://172.18.13.232/rest/response.json");
    }
}
