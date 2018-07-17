package idea.open.com.dartlib;

import android.app.Activity;
import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dartlib {

    private static  Dartlib instance = null;

    public void setResponse(JSONObject response) {
        Dartlib.response = response;
    }
    private Map<String,ArrayList<View>> details = new HashMap<>();
    private static JSONObject response;
    private static String TAG = "DartLib";

    private Dartlib(){

    }

    public static Dartlib getInstance() {
        if(instance == null){
            instance = new Dartlib();
        }
        return instance;
    }

    public void init(Application app,String url) {

        PersistableBundle bundle = new PersistableBundle();
        bundle.putString("URL",url);

        ComponentName
                componentName = new ComponentName(app, JobService.class);

        JobInfo jobInfo = new JobInfo.Builder(12, componentName)
                .setRequiresCharging(true)
                .setPeriodic(5 * 1000)
                .setExtras(bundle)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .build();

        JobScheduler jobScheduler = (JobScheduler) app.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        int resultCode = jobScheduler.schedule(jobInfo);

        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled!");
        }
        else {
            Log.d(TAG, "Job not scheduled");
        }



        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.d(TAG, "Activity "+activity.getLocalClassName()+" name 2"+activity.getPackageName());
                details.put(activity.getPackageName()+"."+activity.getLocalClassName(),new ArrayList<View>());
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    public String getStringValueWithAlt(String key,String alt){
        try {
            if(response !=null) {
                return response.getString(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return alt;
    }

    public int getIntValueWithAlt(String key,int alt){
        try {
            if(response !=null) {
                return response.getInt(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return alt;
    }
}
