package com.example.mp3app.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mp3app.activity.PlayNhacActivity;
import com.example.mp3app.model.BaiHat;
import com.example.mp3app.R;
import com.example.mp3app.service.APIService;
import com.example.mp3app.service.DataService;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachBaiHatAdapter extends RecyclerView.Adapter<DanhSachBaiHatAdapter.ViewHolder> {
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private ProgressDialog mProgressDialog;

    Context context;
    ArrayList<BaiHat> listBaiHat;

    public DanhSachBaiHatAdapter(Context context, ArrayList<BaiHat> listBaiHat) {
        this.context = context;
        this.listBaiHat = listBaiHat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.line_danh_sach_bai_hat, parent, false);
        return new DanhSachBaiHatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baiHat = listBaiHat.get(position);
        holder.txtIndexDanhSach.setText(position + 1 + "");
        holder.txtNameBaiHat.setText(baiHat.getTenBaiHat());
        holder.txtAuthorBaiHat.setText(baiHat.getCaSi());
        Picasso.get().load(baiHat.getHinhCaSi()).into(holder.imgBaiHat);
    }

    @Override
    public int getItemCount() {
        return listBaiHat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtIndexDanhSach, txtNameBaiHat, txtAuthorBaiHat;
        ImageView imgBaiHat, imgLove, imgDownload;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIndexDanhSach = itemView.findViewById(R.id.txtIndexDanhSach);
            txtNameBaiHat = itemView.findViewById(R.id.txtNameBaiHat);
            txtAuthorBaiHat = itemView.findViewById(R.id.txtAuthorBaiHat);
            imgBaiHat = itemView.findViewById(R.id.imgBaiHat);
            imgLove = itemView.findViewById(R.id.imgLove);
            imgDownload = itemView.findViewById(R.id.imgDownload1);

            // s??? ki???n khi nh???n v??o icon download b??i h??t
            imgDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, listBaiHat.get(getPosition()).getLinkBaiHat() + "", Toast.LENGTH_LONG).show();
                    String urlSong = listBaiHat.get(getPosition()).getLinkBaiHat();
                    new DownloadFileAsync().execute(urlSong);
                }
            });

            // s??? ki???n khi nh???n v??o icon lu???t th??ch (y chang ph???n tr?????c) ==> copy, paste
            imgLove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, listBaiHat.get(getPosition()).getTenBaiHat(), Toast.LENGTH_LONG).show();
                    // khi click v??o th?? s??? ?????i th??nh tr??i tim ?????
                    imgLove.setImageResource(R.drawable.iconloved);
                    // t????ng t??c l??n Server update l???i l?????t th??ch
                    DataService dataService = APIService.getService();
                    // ch??? update 1 l???n 1 l?????t th??ch, update theo id b??i h??t
                    Call<String> callBack = dataService.updateLuotThich("1", listBaiHat.get(getPosition()).getIdBaiHat());
                    // g???i l??n Server
                    callBack.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            // k???t qu??? tr??? v???
                            String result = response.body();
                            if (response.equals("Sucess")) {
                                Toast.makeText(context, "Loved!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(context, "Error!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(context, "Error: " + call.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                    // sau khi ???? th??ch th?? set  l???i disable
                    imgLove.setEnabled(false);
                }
            });
            // x??? l?? khi nh???n v??o t???ng item b??i h??t
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // chuy???n d??? li???u qua m??n h??nh play nh???c
                    Intent intent = new Intent(context, PlayNhacActivity.class);
                    intent.putExtra("baihat", listBaiHat.get(getPosition())); // truy???n nguy??n ?????i t?????ng b??i h??t qua m??n h??nh Play nhac
                    context.startActivity(intent);
                }
            });
        }
    }

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_PROGRESS:
                mProgressDialog = new ProgressDialog(context);
                mProgressDialog.setMessage("Downloading mp3...");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                return mProgressDialog;
            default:
                return null;
        }
    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            onCreateDialog(DIALOG_DOWNLOAD_PROGRESS);
        }

        @Override
        protected String doInBackground(String... aurl) {
            int count;
            try {
                URL url = new URL(aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();
                int lenghtOfFile = conexion.getContentLength();
                Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);
                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream("res/raw/abc.mp3");
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
            }
            return null;
        }

        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC", progress[0]);
            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String unused) {
            onCreateDialog(DIALOG_DOWNLOAD_PROGRESS);
        }
    }
}
