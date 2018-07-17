package idea.open.com.dartlib;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by damodhar.meshram on 8/1/2017.
 */

public class Snipping
{
    private Handler handler;
    private String sessionDir;
    private File sessionfile;
    private HandlerThread hThread;
    public Snipping(){
        sessionDir = System.currentTimeMillis()+"";
    }

    public void startSnipping(Activity activity){
        if(activity !=null){
            hThread = new HandlerThread("HandlerThread");
            hThread.start();
            handler = new Handler(hThread.getLooper());

            final View root = activity.getWindow().getDecorView().getRootView();
            ViewTreeObserver vto = root.getViewTreeObserver();
            vto.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("snapping","....");
                            root.setDrawingCacheEnabled(true);
                            Bitmap btm = Bitmap.createBitmap(root.getDrawingCache());
                            root.setDrawingCacheEnabled(false);
                            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                            saveSnap(path, btm,root);
                            handler.post(this);
                        }
                    });
                }
            });
        }
    }

    public void stopSnipping( Activity activity){
       if(hThread!=null){
           hThread = null;
       }
        if(handler!=null){
            handler = null;
        }
    }

    private void saveSnap(String filepath, Bitmap bitmap, View view){
        String sessionDirmake = filepath +"/"+sessionDir;
        sessionfile = new File(sessionDirmake);
        if(!sessionfile.exists()){
            sessionfile.mkdirs();
        }
        String filename =sessionfile.getAbsolutePath()+"/"+ System.currentTimeMillis()+".jpg";
        File imageFile = new File(filename);
        try {
            //imageFile.mkdirs();
            imageFile.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            Log.i("snapper","take snap"+imageFile.getAbsoluteFile());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
