package com.amirnaghibi.facedetector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    public static final int IMAGE_GALLERY_REQUEST = 20;
    private FaceOverlayView mFaceOverlayView;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFaceOverlayView = (FaceOverlayView) findViewById(R.id.face_overlay);
/////////////////
//        InputStream stream = getResources().openRawResource(R.raw.face);
//        bitmap = BitmapFactory.decodeStream(stream);

//        Button scanBtn = (Button) findViewById(R.id.scanButton);
//        scanBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mFaceOverlayView.setBitmap(bitmap);
//            }
//        });
    }

    /**
     * This method will be invoked when Open gallery is clicked
     * @param v
     */
    public void onImageGalleryClicked(View v){
        // Invoke image gallery using implicit intent
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

        // where do we want to find the data
        // Get public directory where certain media stuff are stored
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String pictureDirectoryPath = pictureDirectory.getParent();

        // Get a URI representation
        Uri data = Uri.parse(pictureDirectoryPath);

        // Set the data and type. Get all image types
        photoPickerIntent.setDataAndType(data, "image/*");

        // we will invoke this activity and get something back from it.
        startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            // if we are here, everything processed successfully
            if(requestCode == IMAGE_GALLERY_REQUEST){
                // if we are here we are hearing back from image gallery

                // The address of the image on SD Card
                Uri imageUri = data.getData();

                // Declare a stream to get image data from SD card
                InputStream inputStream;

                // we are getting inputStream based on Uri of an image
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    // Get a bitmap from stream
                    bitmap = BitmapFactory.decodeStream(inputStream);

                    mFaceOverlayView.setBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this,"Unable to open image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
