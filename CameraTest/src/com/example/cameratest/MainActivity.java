package com.example.cameratest;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	Uri imageFileUri;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ImageButton button = (ImageButton) findViewById(R.id.TakeAPhoto);
		OnClickListener listener = new OnClickListener() {
			public void onClick(View v) {
				takeAPhoto();
			}
		};
		button.setOnClickListener(listener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

	public void takeAPhoto() {
		// TODO: Create an intent with the action
		// MediaStore.ACTION_IMAGE_CAPTURE
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// ComponentName cn = new ComponentName("es.softwareprocess.bogopicgen",
		// "es.softwareprocess.bogopicgen.BogoPicGenActivity");
		// ComponentName cn = new ComponentName("com.android.camera",
		// "com.android.camera.Camera");
		// intent.setComponent(cn);

		// Create a folder to store pictures
		String folder = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/tmp";
		File folderF = new File(folder);
		if (!folderF.exists()) {
			folderF.mkdir();
		}

		// Create an URI for the picture file
		String imageFilePath = folder + "/"
				+ String.valueOf(System.currentTimeMillis()) + ".jpg";
		File imageFile = new File(imageFilePath);
		imageFileUri = Uri.fromFile(imageFile);
		
		
		// TODO: Put in the intent in the tag MediaStore.EXTRA_OUTPUT the URI
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
		
		// TODO: Start the activity (expecting a result), with the code
		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		
		// CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE
		
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO: Handle the results from CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE
		
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
			//do stuff
			TextView tv = (TextView) findViewById(R.id.status);
			// TODO: Handle the cases for RESULT_OK, RESULT_CANCELLED, and others
			
			// When the result is OK, set text "Photo OK!" in the status
			//		and set the image in the Button with:
			//		button.setImageDrawable(Drawable.createFromPath(imageFileUri.getPath()));
			// When the result is CANCELLED, set text "Photo canceled" in the status
			// Otherwise, set text "Not sure what happened!" with the resultCode
			if (resultCode == RESULT_OK){
				tv.setText("Photo OK!");
				ImageButton button = (ImageButton) findViewById(R.id.TakeAPhoto);
				button.setImageDrawable(Drawable.createFromPath(imageFileUri.getPath()));
				if(data!=null){
					if (data.getStringExtra(data.EXTRA_TEXT) != null){
						tv.setText(data.getStringExtra(data.EXTRA_TEXT));
					}
				}
				
			} else if (resultCode == RESULT_CANCELED){
				tv.setText("Photo canceled");
			} else {
				tv.setText("Not sure what happened!");
				
			}
		}
		
	}
}
