package com.raygo.hashimage;


import java.util.HashMap;
import java.util.Map;

import com.raygo.hashimage.OpenFileDialog.FileSelectView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity  extends Activity {
	static private int fileDialogId = 0;
	private ImageView src;
	private ImageView dst;
	private TextView info;
	private Dialog dialog;
	private Bitmap srcbmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        Button bchange = (Button) findViewById(R.id.btchange);
        src = (ImageView) findViewById(R.id.imageView1);
        dst = (ImageView) findViewById(R.id.imageView2);
        info = (TextView) findViewById(R.id.tvHashString);
        
        bchange.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//ͨ��λͼ����������һ��λͼ
				Bitmap bitmap = ((BitmapDrawable) src.getDrawable()).getBitmap();  
				Bitmap nbm = ImageHash.convertGreyImg(bitmap);
				dst.setImageBitmap(nbm);
				
				String sss = ImageHash.imageToHash(bitmap);
				info.setText(sss);
			}        	
        });

        src.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDialog(fileDialogId);
			}
        	
        });
        
        dst.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//show_dlg();				
			}
        	
        });
    }
    @Override
	protected Dialog onCreateDialog(int id) {
		if(id==fileDialogId){
			Map<String, Integer> images = new HashMap<String, Integer>();
			// ���漸�����ø��ļ����͵�ͼ�꣬ ��Ҫ���Ȱ�ͼ����ӵ���Դ�ļ���
			images.put(OpenFileDialog.sRoot, R.drawable.rooticon);	// ��Ŀ¼ͼ��
			images.put(OpenFileDialog.sParent, R.drawable.shangicon);	//������һ���ͼ��
			images.put(OpenFileDialog.sFolder, R.drawable.foldericon);	//�ļ���ͼ��
			images.put("png", R.drawable.imgicon);	//png�ļ�ͼ��
			images.put("jpg", R.drawable.imgicon);	//jpg�ļ�ͼ��
			images.put(OpenFileDialog.sEmpty, R.drawable.rooticon);
			Dialog dialog = OpenFileDialog.createDialog(id, this, "���ļ�", new CallbackBundle() {
				@Override
				public void callback(Bundle bundle) {
					String filepath = bundle.getString("path");
					info.setText(filepath);
					srcbmp = BitmapFactory.decodeFile(filepath);
					src.setImageBitmap(srcbmp);
				}
			}, 
			".png;.jpg;",
			images);
			//���öԻ���λ��
	        LayoutParams lp = dialog.getWindow().getAttributes();
	        lp.width = 300;  
	        lp.height = 480;  
	        dialog.getWindow().setAttributes(lp);
			return dialog;
		}
		return null;
	}

	private void show_dlg()
    {
		Map<String, Integer> images = new HashMap<String, Integer>();
		// ���漸�����ø��ļ����͵�ͼ�꣬ ��Ҫ���Ȱ�ͼ����ӵ���Դ�ļ���
		images.put(OpenFileDialog.sRoot, R.drawable.rooticon);	// ��Ŀ¼ͼ��
		images.put(OpenFileDialog.sParent, R.drawable.shangicon);	//������һ���ͼ��
		images.put(OpenFileDialog.sFolder, R.drawable.foldericon);	//�ļ���ͼ��
		images.put("png", R.drawable.imgicon);	//png�ļ�ͼ��
		images.put("jpg", R.drawable.imgicon);	//jpg�ļ�ͼ��
		images.put(OpenFileDialog.sEmpty, R.drawable.rooticon);
       
        Context ctx = this.getBaseContext();
        FileSelectView fview = new FileSelectView(ctx, fileDialogId, new CallbackBundle() {
			@Override
			public void callback(Bundle bundle) {
				String filepath = bundle.getString("path");
				info.setText(filepath);
				Bitmap bmp = BitmapFactory.decodeFile(filepath);
				src.setImageBitmap(bmp);
			}
		}, 
		".png;.jpg;",
		images);

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setView(fview);
		dialog = builder.create();
		dialog.setTitle("Open File Dialog");
        //���öԻ���λ��
        LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = 240;  
        lp.height = 480;  
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }
}
