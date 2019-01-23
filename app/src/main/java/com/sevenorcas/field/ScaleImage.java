package com.sevenorcas.field;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.ImageView;

public class ScaleImage {

    static public void scale(ImageView iv, int pic, AppCompatActivity a){
        Display screen = a.getWindowManager().getDefaultDisplay();
        BitmapFactory.Options o = new BitmapFactory.Options();

        o.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(a.getResources(), pic, o);

        int iw = o.outWidth;
        int sw = screen.getWidth();
        if (iw > sw){
            int r = Math.round((float)iw / (float) sw);
            o.inSampleSize = r;
        }
        o.inJustDecodeBounds = false;
        Bitmap simg = BitmapFactory.decodeResource(a.getResources(), pic, o);
        iv.setImageBitmap(simg);
    }
}
