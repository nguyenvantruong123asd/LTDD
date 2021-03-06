package com.example.mp3app.activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.mp3app.R;
import com.example.mp3app.adapter.ViewPagerList;
import com.example.mp3app.fragment.Fragment_Disk;
import com.example.mp3app.fragment.Fragment_Loi_Bai_Hat;
import com.example.mp3app.fragment.Fragment_Play_Danh_Sach_Bai_Hat;
import com.example.mp3app.model.BaiHat;
import com.example.mp3app.service.OnClearFromRecentService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PlayNhacActivity extends AppCompatActivity implements Playable {
    Toolbar toolbar;
    public ViewPager viewPagerPlayNhac;
    TextView txtTimeSong, txtTimeTotal;
    SeekBar skSong;
    ImageButton imgPre, imgPlay, imgPause, imgNext, imgRepeat, imgShuff;
    public static ArrayList<BaiHat> listSong = new ArrayList<>();
    public static ViewPagerList adapterNhac;
    Fragment_Disk fragment_disk;
    Fragment_Play_Danh_Sach_Bai_Hat fragment_play_danh_sach_bai_hat;
    Fragment_Loi_Bai_Hat fragment_loi_bai_hat;
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    int position = 0, old_position = 0;
    boolean repeat = false;
    boolean checkRandom = false;
    boolean next = false;

    NotificationManager notificationManager;
    static boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_nhac);
        // ki???m tra t??n hi???u m???ng
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);
        getDataFromIntent();    //PH???I L???Y D??? LI???U TR?????C KHI addControls() TH?? M???I C?? B??I H??T ????? PH??T
        CreateNotification.createNotification(getApplicationContext(), listSong.get(position),
                R.drawable.ic_pause_black_24dp, position, listSong.size() - 1);
        isPlaying = true;
//        unregisterReceiver(broadcastReceiver);
        if (mediaPlayer != null) {
            Log.d("fff", "aaaaaaaaa");
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        getDataFromIntent();    //PH???I L???Y D??? LI???U TR?????C KHI addControls() TH?? M???I C?? B??I H??T ????? PH??T
        addControls();
        addEvents();

        //Music player on notification
        notification();

    }

    private void notification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
            registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
            startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
        }
        onTrackPlay();

    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CreateNotification.CHANNEL_ID,
                    "KOD Dev", NotificationManager.IMPORTANCE_LOW);

            notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("ABC", "receiveeeeeeeeed");
            String action = intent.getExtras().getString("actionname");

            switch (action) {
                case CreateNotification.ACTION_PREVIUOS:
                    onTrackPrevious();
                    break;
                case CreateNotification.ACTION_PLAY:
                    Log.d("ABC", "playyyyyyyy");
                    if (isPlaying) {
                        onTrackPause();
                    } else {
                        onTrackPlay();
                    }
                    break;
                case CreateNotification.ACTION_NEXT:
                    onTrackNext();
                    break;
            }
        }
    };

    @Override
    public void onTrackPrevious() {
//        position--;
        old_position = position;
        if (listSong.size() > 0) {
            if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            if (repeat) {
                position = position;
            } else {
                position--;
                if (position == -1) {
                    position = listSong.size() - 1;
                }
                if (checkRandom) {
                    Random random = new Random();
                    int viTriRandom = random.nextInt(listSong.size());
                    position = viTriRandom;
                }
            }
            new PlayMP3().execute(listSong.get(position).getLinkBaiHat());
            imgPlay.setImageResource(R.drawable.iconpause1);
            fragment_disk.playNhac(listSong.get(position).getHinhCaSi());
            fragment_disk.txtNameCaSi.setText(listSong.get(position).getCaSi());
            fragment_disk.txtTenBaiHat.setText(listSong.get(position).getTenBaiHat());
            getSupportActionBar().setTitle(listSong.get(position).getTenBaiHat());
            updateTime();
            if (fragment_disk.objectAnimator != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    fragment_disk.objectAnimator.resume();
                }
            }
        }
        CreateNotification.createNotification(getApplicationContext(), listSong.get(position),
                R.drawable.ic_pause_black_24dp, position, listSong.size() - 1);
        isPlaying = true;
    }

    @Override
    public void onTrackPlay() {
        // mediaPlayer.stop();
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
        imgPlay.setImageResource(R.drawable.iconpause1);
        // gi??? m??n h??nh kh??ng ??c t???t khi play nh???c
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (fragment_disk.objectAnimator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                fragment_disk.objectAnimator.resume();
            }
        }
//        if (listSong.size() > 0) {
        CreateNotification.createNotification(getApplicationContext(), listSong.get(position),
                R.drawable.ic_pause_black_24dp, position, listSong.size() - 1);
        isPlaying = true;
//        }

    }

    @Override
    public void onTrackPause() {
        // n???u ??ang m??? m?? click v?? th?? t???m d???ng
        mediaPlayer.pause();
        imgPlay.setImageResource(R.drawable.iconplay1);
        if (fragment_disk.objectAnimator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                fragment_disk.objectAnimator.pause();
            }
        }
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        CreateNotification.createNotification(getApplicationContext(), listSong.get(position),
                R.drawable.ic_play_arrow_black_24dp, position, listSong.size() - 1);
        isPlaying = false;
    }

    @Override
    public void onTrackNext() {
//        position++;

        old_position = position;
        // ki???m tra n???u c?? d??? li???u
        if (listSong.size() > 0) {
            if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            if (repeat) {
                position = position;
            } else {
                position++;
                if (position == listSong.size()) {
                    position = 0;
                }
                if (checkRandom) {
                    Random random = new Random();
                    int viTriRandom = random.nextInt(listSong.size());
                    position = viTriRandom;
                }
            }
            new PlayMP3().execute(listSong.get(position).getLinkBaiHat());
            imgPlay.setImageResource(R.drawable.iconpause1);
            fragment_disk.playNhac(listSong.get(position).getHinhCaSi());
            fragment_disk.txtNameCaSi.setText(listSong.get(position).getCaSi());
            fragment_disk.txtTenBaiHat.setText(listSong.get(position).getTenBaiHat());
            getSupportActionBar().setTitle(listSong.get(position).getTenBaiHat());
            updateTime();
            if (fragment_disk.objectAnimator != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    fragment_disk.objectAnimator.resume();
                }
            }
        }
        CreateNotification.createNotification(getApplicationContext(), listSong.get(position),
                R.drawable.ic_pause_black_24dp, position, listSong.size() - 1);
        isPlaying = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Log.d("ABC", "11111111");
//        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                Log.d("ABC", "receive11111111");
//                String action = intent.getExtras().getString("actionname");
//
//                switch (action){
//                    case CreateNotification.ACTION_PREVIUOS:
//                        onTrackPrevious();
//                        break;
//                    case CreateNotification.ACTION_PLAY:
//                        Log.d("ABC", "play1111111");
//                        if (isPlaying){
//                            onTrackPause();
//                        } else {
//                            onTrackPlay();
//                        }
//                        break;
//                    case CreateNotification.ACTION_NEXT:
//                        onTrackNext();
//                        break;
//                }
//            }
//        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.cancelAll();
        }

        unregisterReceiver(broadcastReceiver);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            Log.d("ABC", "222222222");
//            createChannel();
//            registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
//            startService(new Intent(getApplicationContext(), OnClearFromRecentService.class));
//        }
    }

    public void playBaiHatCuaFragmentPlayDSBH(int viTri) {
        old_position = position;
        position = viTri;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        new PlayMP3().execute(listSong.get(position).getLinkBaiHat());
        imgPlay.setImageResource(R.drawable.iconpause1);
        fragment_disk.playNhac(listSong.get(position).getHinhCaSi());
        fragment_disk.txtNameCaSi.setText(listSong.get(position).getCaSi());
        fragment_disk.txtTenBaiHat.setText(listSong.get(position).getTenBaiHat());
        getSupportActionBar().setTitle(listSong.get(position).getTenBaiHat());
        updateTime();
        if (fragment_disk.objectAnimator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                fragment_disk.objectAnimator.resume();
            }
        }
        CreateNotification.createNotification(PlayNhacActivity.this, listSong.get(position),
                R.drawable.ic_pause_black_24dp, position, listSong.size() - 1);
        isPlaying = true;
    }

    private void getDataFromIntent() {
        // l???y intent t??? BaiHatAdapter v?? DanhSachBaiHatAdapter qua
        Intent intent = getIntent();
        if (listSong != null) {
            listSong.clear();
        } else listSong = new ArrayList<>();
        if (intent != null) { // n???u c?? data
            if (intent.hasExtra("baihat")) {
                // l???y BaiHat ra
                BaiHat baiHat = intent.getParcelableExtra("baihat"); // l???y data d???ng Object
                //Toast.makeText(PlayNhacActivity.this, baiHat.getTenBaiHat(), Toast.LENGTH_LONG).show();
                listSong.add(baiHat);
            }
            // l???y intent t??? DanhSachBaiHatActivity qua (danh s??ch b??i h??t)
            if (intent.hasExtra("listsong")) {
                // l???y danh s??ch b??i h??t
                ArrayList<BaiHat> arrayList = intent.getParcelableArrayListExtra("listsong");
                //listSong = arrayList;
                // duy???t v??ng for ????? l???y c??c b??i h??t trong danh s??ch
//                for (int i = 0; i < arrayList.size(); i++) {
//                    //Toast.makeText(PlayNhacActivity.this, baiHat.getTenBaiHat(), Toast.LENGTH_LONG).show();
//                    listSong.add(arrayList.get(i));
//                    //Toast.makeText(PlayNhacActivity.this, listSong.size(), Toast.LENGTH_LONG).show();
//                }
                if (arrayList != null) {
                    for (BaiHat baiHat : arrayList) {
                        //Toast.makeText(PlayNhacActivity.this, baiHat.getLinkBaiHat(), Toast.LENGTH_LONG).show();
                        listSong.add(baiHat);
                    }
                }

            }
        }

    }

    private void addEvents() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (adapterNhac.getItem(1) != null) {
                    if (listSong.size() > 0) {
                        fragment_disk.playNhac(listSong.get(position).getHinhCaSi());
                        fragment_disk.txtNameCaSi.setText(listSong.get(position).getCaSi());
                        fragment_disk.txtTenBaiHat.setText(listSong.get(position).getTenBaiHat());

                        RecyclerView.ViewHolder viewHolder = fragment_play_danh_sach_bai_hat.rvPlayNhac.findViewHolderForAdapterPosition(old_position);
                        if (viewHolder != null) {
                            View view = viewHolder.itemView;
                            view.setBackgroundColor(Color.TRANSPARENT);
                        }
                        RecyclerView.ViewHolder viewHolder1 = fragment_play_danh_sach_bai_hat.rvPlayNhac.findViewHolderForAdapterPosition(position);
                        if (viewHolder1 != null) {
                            View view1 = viewHolder1.itemView;
                            view1.setBackgroundColor(Color.argb(100, 255, 0, 255));
                        }
                        fragment_loi_bai_hat.setLoiBaiHatLenGiaoDien(listSong.get(position).getLoiBaiHat());
                        //handler.removeCallbacks(this);
                        handler.postDelayed(this, 300);

                    } else {
                        // n???a gi??y c???p nh???t r???i ch???y l???i
                        handler.postDelayed(this, 300);
                    }
                }


                //handler.postDelayed(this, 500); // n???a gi??y c???p nh???t r???i ch???y l???i
            }
        }, 500);

        // s??? ki???n nh???n n??t play
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // n???u ??ang m??? m?? click v?? th?? t???m d???ng
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    imgPlay.setImageResource(R.drawable.iconplay1);
                    if (fragment_disk.objectAnimator != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            fragment_disk.objectAnimator.pause();
                        }
                    }
                    CreateNotification.createNotification(PlayNhacActivity.this, listSong.get(position),
                            R.drawable.ic_play_arrow_black_24dp, position, listSong.size() - 1);
                    isPlaying = false;
                    //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                } else { // ng?????c l???i
                    // mediaPlayer.stop();
                    mediaPlayer.start();
                    imgPlay.setImageResource(R.drawable.iconpause1);
                    // gi??? m??n h??nh kh??ng ??c t???t khi play nh???c
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    if (fragment_disk.objectAnimator != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            fragment_disk.objectAnimator.resume();
                        }
                    }
                    CreateNotification.createNotification(PlayNhacActivity.this, listSong.get(position),
                            R.drawable.ic_pause_black_24dp, position, listSong.size() - 1);
                    isPlaying = true;
                }
            }
        });

        imgRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (repeat) {
                    repeat = false;
                    imgRepeat.setImageResource(R.drawable.icon_repeat_one_white);
                } else {
                    repeat = true;
                    checkRandom = false; //v?? m??nh mu???n t???i 1 th???i ??i???m ch??? c?? th??? ch???n 1 c??i: repeat ho???c random
                    imgRepeat.setImageResource(R.drawable.icon_repeat_one_purple);
                    imgShuff.setImageResource(R.drawable.icon_shuffle_white);
                }
            }
        });
        imgShuff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkRandom == false) {
                    if (repeat == true) {
                        repeat = false;
                        imgShuff.setImageResource(R.drawable.icon_shuffle_purple);
                        imgRepeat.setImageResource(R.drawable.icon_repeat_one_white);
                    }
                    imgShuff.setImageResource(R.drawable.icon_shuffle_purple);
                    checkRandom = true;
                } else {
                    imgShuff.setImageResource(R.drawable.iconsuffle1);
                    checkRandom = false;

                }
            }
        });
        skSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                old_position = position;
                // ki???m tra n???u c?? d??? li???u
                if (listSong.size() > 0) {
                    if (mediaPlayer.isPlaying() && mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (repeat) {
                        position = position;
                    } else {
                        position++;
                        if (position == listSong.size()) {
                            position = 0;
                        }
                        if (checkRandom) {
                            Random random = new Random();
                            int viTriRandom = random.nextInt(listSong.size());
                            position = viTriRandom;
                        }
                    }
                    new PlayMP3().execute(listSong.get(position).getLinkBaiHat());
                    imgPlay.setImageResource(R.drawable.iconpause1);
                    fragment_disk.playNhac(listSong.get(position).getHinhCaSi());
                    fragment_disk.txtNameCaSi.setText(listSong.get(position).getCaSi());
                    fragment_disk.txtTenBaiHat.setText(listSong.get(position).getTenBaiHat());
                    getSupportActionBar().setTitle(listSong.get(position).getTenBaiHat());
                    updateTime();
                    if (fragment_disk.objectAnimator != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            fragment_disk.objectAnimator.resume();
                        }
                    }
                    CreateNotification.createNotification(PlayNhacActivity.this, listSong.get(position),
                            R.drawable.ic_pause_black_24dp, position, listSong.size() - 1);
                    isPlaying = true;
                }
                imgPre.setClickable(false);
                imgNext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgPre.setClickable(true);
                        imgNext.setClickable(true);
                    }
                }, 5000);
            }
        });
        imgPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                old_position = position;
                if (listSong.size() > 0) {
                    if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (repeat) {
                        position = position;
                    } else {
                        position--;
                        if (position == -1) {
                            position = listSong.size() - 1;
                        }
                        if (checkRandom) {
                            Random random = new Random();
                            int viTriRandom = random.nextInt(listSong.size());
                            position = viTriRandom;
                        }
                    }
                    new PlayMP3().execute(listSong.get(position).getLinkBaiHat());
                    imgPlay.setImageResource(R.drawable.iconpause1);
                    fragment_disk.playNhac(listSong.get(position).getHinhCaSi());
                    fragment_disk.txtNameCaSi.setText(listSong.get(position).getCaSi());
                    fragment_disk.txtTenBaiHat.setText(listSong.get(position).getTenBaiHat());
                    getSupportActionBar().setTitle(listSong.get(position).getTenBaiHat());
                    updateTime();
                    if (fragment_disk.objectAnimator != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            fragment_disk.objectAnimator.resume();
                        }
                    }
                    CreateNotification.createNotification(PlayNhacActivity.this, listSong.get(position),
                            R.drawable.ic_pause_black_24dp, position, listSong.size() - 1);
                    isPlaying = true;

                }
                imgPre.setClickable(false);
                imgNext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgPre.setClickable(true);
                        imgNext.setClickable(true);
                    }
                }, 5000);
            }
        });


    }

    private void addControls() {
        toolbar = (Toolbar) findViewById(R.id.tbPlayNhac);
        txtTimeSong = (TextView) findViewById(R.id.txtTimeSong);
        txtTimeTotal = (TextView) findViewById(R.id.txtTimeTotal);
        skSong = (SeekBar) findViewById(R.id.seekBarSong);
        imgShuff = (ImageButton) findViewById(R.id.imgButtonShuff);
        imgPre = (ImageButton) findViewById(R.id.imgButtonPre);
        imgPlay = (ImageButton) findViewById(R.id.imgButtonPlay);
        imgNext = (ImageButton) findViewById(R.id.imgButtonNext);
        imgRepeat = (ImageButton) findViewById(R.id.imgButtonRepeat);
        viewPagerPlayNhac = (ViewPager) findViewById(R.id.vpNhac);
        //imgDisk = (ImageView) findViewById(R.id.imgDisk);


        // v?? ???? xo?? action bar n??n thay th??? b???ng SupportAction
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PlayNhacActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
//                finish();
//                mediaPlayer.stop();
//                listSong.clear();

            }
        });
        toolbar.setTitleTextColor(Color.WHITE);
        // g???n ViewPager
        adapterNhac = new ViewPagerList(getSupportFragmentManager());
        fragment_disk = new Fragment_Disk(); // new ra Fragment ????a nh???c
        fragment_play_danh_sach_bai_hat = new Fragment_Play_Danh_Sach_Bai_Hat(); // new ra Fragment danh s??ch b??i h??t
        fragment_loi_bai_hat = new Fragment_Loi_Bai_Hat();
        // th??m v??o View Pager (View Pager ????? chuy???n qua l???i c??c m??n h??nh)

        // Fragment danh s??ch s??? n???m b??n tr??i Fragment ????a nh???c
        adapterNhac.addFragment(fragment_play_danh_sach_bai_hat);   //V??? TR?? 0
        adapterNhac.addFragment(fragment_disk);                     //V??? TR?? 1
        adapterNhac.addFragment(fragment_loi_bai_hat);              //V??? TR?? 2
        viewPagerPlayNhac.setAdapter(adapterNhac);
        viewPagerPlayNhac.setCurrentItem(1);

        fragment_play_danh_sach_bai_hat = (Fragment_Play_Danh_Sach_Bai_Hat) adapterNhac.getItem(0);
        fragment_disk = (Fragment_Disk) adapterNhac.getItem(1);
        fragment_loi_bai_hat = (Fragment_Loi_Bai_Hat) adapterNhac.getItem(2);
        // ki???m tra danh s??ch b??i h??t m?? c?? d??? li???u th?? s??? play ca kh??c ?????u ti??n
        if (listSong.size() > 0) {
            // set l???i title b??i h??t ????
            getSupportActionBar().setTitle(listSong.get(position).getTenBaiHat());
            new PlayMP3().execute(listSong.get(position).getLinkBaiHat());
            // sau khi b??i h??t ???? play th?? set l???i icon th??nh ??ang play nh???c
            imgPlay.setImageResource(R.drawable.iconpause1);

        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent i = new Intent(PlayNhacActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
    }

    public class PlayMP3 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String baihat) {
            super.onPostExecute(baihat);
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC); // play nh???c d?????i d???ng online
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();

                    }
                });

                mediaPlayer.setDataSource(baihat); // kh???i t???o ???????ng link b??i h??t
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            timeSong();
            updateTime();
        }
    }

    private void timeSong() {
        // l???y time hi???n t???i c???p nh???t l???i ph??t gi??y
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTimeTotal.setText(simpleDateFormat.format(mediaPlayer.getDuration())); // l???y v??? tr?? hi???n t???i c???a Media Player
        // g??n max c???a thanh Seekbar = t???ng th???i gian b??i h??t (getDuration)
        skSong.setMax(mediaPlayer.getDuration());
    }

    private void updateTime() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // n???u c?? d??? li???u
                if (mediaPlayer != null) {
                    skSong.setProgress(mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    txtTimeSong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition())); // l???y v??? tr?? hi???n t???i c???a Media Player
                    handler.postDelayed(this, 300); // l???ng nghe li??n t???c m???i 0.3s
                    // ki???m tra cu???i b??i ch??a
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            next = true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }, 300);

        //T??? ?????ng chuy???n b??i h??t k??? ti???p khi ph??t xong b??i h??t hi???n t???i
        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (next == true) {
                    old_position = position;
                    // ki???m tra n???u c?? d??? li???u
                    if (listSong.size() > 0) {
                        if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            mediaPlayer = null;
                        }
                        if (repeat) {
                            position = position;
                        } else {
                            position++;
                            if (position == listSong.size()) {
                                position = 0;
                            }
                            if (checkRandom) {
                                Random random = new Random();
                                int viTriRandom = random.nextInt(listSong.size());
                                position = viTriRandom;
                            }
                        }
                        new PlayMP3().execute(listSong.get(position).getLinkBaiHat());
                        imgPlay.setImageResource(R.drawable.iconpause1);
                        fragment_disk.playNhac(listSong.get(position).getHinhCaSi());
                        fragment_disk.txtNameCaSi.setText(listSong.get(position).getCaSi());
                        fragment_disk.txtTenBaiHat.setText(listSong.get(position).getTenBaiHat());
                        getSupportActionBar().setTitle(listSong.get(position).getTenBaiHat());
                        updateTime();
                        CreateNotification.createNotification(PlayNhacActivity.this, listSong.get(position),
                                R.drawable.ic_pause_black_24dp, position, listSong.size() - 1);
                        isPlaying = true;
                    }

                    imgPre.setClickable(false);
                    imgNext.setClickable(false);
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imgPre.setClickable(true);
                            imgNext.setClickable(true);
                        }
                    }, 5000);
                    next = false;
                    handler1.removeCallbacks(this); // xo?? c??i c?? ??i
                } else {
                    handler1.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }


}