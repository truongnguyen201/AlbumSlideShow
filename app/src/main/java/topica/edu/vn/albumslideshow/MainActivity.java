package topica.edu.vn.albumslideshow;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ImageView imgHinh;
    CheckBox chkTuDong;
    ImageButton btnPrevius,btnNext;
    int curenposition=-1;
    ArrayList<String>dsLink;
    Timer timer=null;
    TimerTask timerTask=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();

    }

    private void addEvents() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLiXemHinhKeTiep();

            }
        });
        btnPrevius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLiXemHinhSau();
;            }
        });
        chkTuDong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                xuliChecked(isChecked);
            }
        });
    }

    private void xuliChecked(boolean isChecked) {
        if(isChecked==true)
        {
            btnPrevius.setEnabled(false);
            btnNext.setEnabled(false);
            xuLiTudongchayHinh();
        }
        else
        {
            btnPrevius.setEnabled(true);
            btnNext.setEnabled(true);
            if(timer!=null)
                timer.cancel();
        }

    }

    private void xuLiTudongchayHinh() {
        timerTask =new TimerTask() {
            @Override
            public void run() {
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       curenposition++;
                       if(curenposition==dsLink.size())
                           curenposition=0;
                       ImgeTask task=new ImgeTask();
                       task.execute(dsLink.get(curenposition));

                   }
               });
            }
        };
        timer=new Timer();
        timer.schedule(timerTask,0,5000);

    }

    private void xuLiXemHinhSau() {
        curenposition--;
        if(curenposition==-1);
            curenposition=dsLink.size()-1;
        ImgeTask  task=new ImgeTask();
        task.execute(dsLink.get(curenposition));
    }

    private void xuLiXemHinhKeTiep() {
       curenposition++;
       if(curenposition==(dsLink.size()))
           curenposition=0;
       ImgeTask task=new ImgeTask();
       task.execute(dsLink.get(curenposition));
    }

    private void addControls() {
        imgHinh=findViewById(R.id.imgHInh);
        btnNext=findViewById(R.id.btnNext);
        btnPrevius=findViewById(R.id.btnPrevius);
        chkTuDong=findViewById(R.id.chkTuDong);
        dsLink=new ArrayList<>();
        String s5="https://ss-images.saostar.vn/wp700/2019/08/10/5819424/37849324_1977488555616858_6034302061581959168_o.jpg";
        String s6="https://www.tmrwmagazine.com/wp-content/uploads/2019/05/Press-Shot-2-min.jpg";
        String s="https://i.pinimg.com/564x/fb/dc/c6/fbdcc6e2bde05d5a61ff419b7bfe30f5.jpg";
        String s2="https://ss-images.saostar.vn/2019/08/10/5819424/tcs-newpromo2019_0112_165444-0962_dlg.jpg";
        String s3="https://ss-images.saostar.vn/2019/08/10/5819424/37849324_1977488555616858_6034302061581959168_o.jpg";
        String s4="https://assets.capitalfm.com/2017/08/the-chainsmokers---paris-video-1487586997-list-handheld-0.png";
        String s7="https://is1-ssl.mzstatic.com/image/thumb/Features114/v4/b1/7b/77/b17b7755-ed0b-5685-0cc1-014bd3a66420/mzl.xrwukjdy.png/800x800bb.jpeg";
        String s8="https://celebmix.com/wp-content/uploads/2018/12/celebmix-exclusive-interview-new-hope-club-01.jpg";
        String s9="https://dynamicmedia.livenationinternational.com/Media/u/h/d/d0033b2e-2460-43dd-b131-fb4d68f37e57.jpg";
        dsLink.add(s);dsLink.add(s2);dsLink.add(s3);dsLink.add(s4);dsLink.add(s5);
        dsLink.add(s6);dsLink.add(s7);dsLink.add(s8);dsLink.add(s9);
        curenposition=0;
        ImgeTask task=new ImgeTask();
        task.execute(dsLink.get(curenposition));

    }
    class ImgeTask extends AsyncTask<String,Void, Bitmap>
    {
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imgHinh.setImageBitmap(bitmap);
            super.onPostExecute(bitmap);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap=null;
            String s=strings[0];
            try {
                bitmap= BitmapFactory.decodeStream((InputStream) new URL(s).getContent());
                return bitmap;
            }
            catch (Exception ex)
            {
                Log.e("Loi",ex.toString());
            }

            return null;
        }
    }

}