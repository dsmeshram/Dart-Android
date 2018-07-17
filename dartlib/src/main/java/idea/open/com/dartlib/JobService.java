package idea.open.com.dartlib;

import android.app.job.JobParameters;

import org.json.JSONObject;

public class JobService extends android.app.job.JobService {
    private static final String TAG = JobService.class.getSimpleName();
    boolean isWorking = false;
    boolean jobCancelled = false;

    // Called by the Android system when it's time to run the job

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        isWorking = true;

        // We need 'jobParameters' so we can call 'jobFinished'

        startWorkOnNewThread(jobParameters); // Services do NOT run on a separate thread
        return isWorking;
    }

    private void startWorkOnNewThread(final JobParameters jobParameters) {
        new Thread(new Runnable() {
            public void run() {
                doWork(jobParameters);
            }
        }).start();
    }

    private void doWork(final JobParameters jobParameters) {
        new ServiceCall(getBaseContext(),jobParameters.getExtras().getString("URL")).getUpdateMapper(new ServiceCall.onResponse() {
            @Override
            public void getMapper(JSONObject objects) {
                isWorking = false;
                Dartlib.getInstance().setResponse(objects);
                boolean needsReschedule = false;
                jobFinished(jobParameters, needsReschedule);
            }
        });
    }

    // Called if the job was cancelled before being finished
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        jobCancelled = true;
        boolean needsReschedule = isWorking;
        jobFinished(jobParameters, needsReschedule);
        return needsReschedule;
    }
}