/**
 *
 * @author sleepygarden
 *
 */
package edu.coppola.galleryapp;

import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class GalleryActivity extends Activity {

	LinearLayout gallery;
	Button next;
	Button prev;
	HorizontalScrollView hScrollView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);

		gallery = (LinearLayout) findViewById(R.id.imgs_gallery);
		next = (Button) findViewById(R.id.gallery_next_btn);
		prev = (Button) findViewById(R.id.gallery_prev_btn);
		hScrollView = (HorizontalScrollView) findViewById(R.id.gallery_scroll_view);

		next.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				for (Drawable d : ImageCache.getIterable()) {
					gallery.addView(insertPhoto(d));
				}
			}

		});

		prev.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				hScrollView.scrollBy(hScrollView.getRight(), 0);
			}

		});

		DownloadSubGallery download = new DownloadSubGallery(
				GalleryActivity.this, Singleton.getInstance().getApi(),
				Constants.PHOTO_DIR);
		download.execute();

	}

	View insertPhoto(Drawable d) {

		RelativeLayout layout = new RelativeLayout(getApplicationContext());
		
		RelativeLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		ImageView imageView = new ImageView(getApplicationContext());
		imageView.setLayoutParams(new LayoutParams(220, 220));
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setImageDrawable(d);

		layout.addView(imageView);
		return layout;
	}
}
