/**
 *
 * @author sleepygarden
 *
 */
package edu.coppola.galleryapp;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends Activity {

	LinearLayout thumbsGallery;
	Button dispThumbs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);

		thumbsGallery = (LinearLayout) findViewById(R.id.thumbs_gallery);
		dispThumbs = (Button) findViewById(R.id.disp_thumbs_btn);
		dispThumbs.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				for (Drawable d : ImageCache.getIterable()) {
					thumbsGallery.addView(insertThumb(d));
				}
			}

		});

		DownloadSubGallery download = new DownloadSubGallery(MenuActivity.this,
				Singleton.getInstance().getApi(), Constants.PHOTO_DIR);
		download.execute();

	}

	View insertThumb(Drawable d) {

		RelativeLayout layout = new RelativeLayout(getApplicationContext());
		layout.setLayoutParams(new LayoutParams(250, 250));
		layout.setGravity(Gravity.CENTER);

		ImageView iv = new ImageButton(getApplicationContext());
		iv.setLayoutParams(new LayoutParams(220, 220));
		iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		iv.setImageDrawable(d);
		iv.setBackgroundColor(Color.TRANSPARENT);
		iv.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(MenuActivity.this, GalleryActivity.class);
				startActivity(i);
			}

		});

		TextView tv = new TextView(getApplicationContext());

		RelativeLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		
		params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

		tv.setLayoutParams(params);
		tv.setTextColor(Color.BLACK);
		tv.setTypeface(Typeface.DEFAULT_BOLD);
		tv.setText("GALLERY");

		layout.addView(iv);
		layout.addView(tv);
		
		return layout;
	}
}
