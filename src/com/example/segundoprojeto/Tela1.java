package com.example.segundoprojeto;

import java.io.InputStream;
import java.net.URL;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Tela1 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela1);

        Button btn = (Button) findViewById(R.id.buttonExibir);
        btn.setOnClickListener(mClickListener);
    }

    View.OnClickListener mClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            String url = "https://s3-sa-east-1.amazonaws.com/treinaweb-cursos/prod/56/arquivos/logo-treinaweb-cursos.png";

            new DownloadImagem().execute(url);
        }

    };

    protected Bitmap loadImageFromNetwork(String url) {
        // TODO Auto-generated method stub
        Bitmap bitmap = null;
        try{
            URL src = new URL(url);
            InputStream is = (InputStream) src.getContent();
            bitmap = BitmapFactory.decodeStream(is);
        }catch (Exception e){
            //Log.e("Error Image: ", e.getMessage());
            Log.e("Error Image: ", e.getClass().getName());
        }
        return bitmap;
    }

    private class DownloadImagem extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... params) {
            return loadImageFromNetwork(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            ImageView imgView = (ImageView) findViewById(R.id.imageViewLogo);
            imgView.setImageBitmap(result);
        }
    }
}