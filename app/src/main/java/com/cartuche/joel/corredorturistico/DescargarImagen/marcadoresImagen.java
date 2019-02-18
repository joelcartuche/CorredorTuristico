package com.cartuche.joel.corredorturistico.DescargarImagen;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class marcadoresImagen extends AsyncTask<String, Void, Bitmap> {


    Bitmap bmImage;

    public marcadoresImagen(Bitmap bmImage) {

        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }


    protected void onPostExecute(Bitmap result) {
        if (result != null) {
            result = Bitmap.createScaledBitmap(result, 480, 800, false);
        }
        this.bmImage = result;
    }
}