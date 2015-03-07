package com.raygo.hashimage;

import java.util.Locale;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class FileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

    
        Button bchange = (Button) findViewById(R.id.btchange);
        final ImageView src = (ImageView) findViewById(R.id.imageView1);
        final ImageView dst = (ImageView) findViewById(R.id.imageView2);
        final TextView info = (TextView) findViewById(R.id.tvHashString);
        
        bchange.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//通过位图工厂，创建一个位图
				final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ass);
		        src.setImageBitmap(bitmap);
				Bitmap nbm = ImageHash.convertGreyImg(bitmap);
				dst.setImageBitmap(nbm);
				
				String sss = ImageHash.imageToHash(bitmap);
				info.setText(sss);
			}        	
        });
    }

}
