package yummy_dvice.com;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ManageImage {

    public static void dlibitmap(Context c, String image_id) throws IOException {

        String url;
        String name = "";
        if (image_id.length() > 10) {

            String addr = "http://93.12.245.177:8000/image?img=";

            url = addr+ image_id + ".jpg";
            name = image_id;

            //Log.d("imagess", addr + url);
        }

        else{

            url = "http://93.12.245.177:8000/image?img=random"+String.valueOf(2)+".jpg";
            name = String.valueOf(2)+".jpg";

        }

        Picasso.get().load(url).get();


    }

    public static void dli(Context c, String image_id){

        String url;
        String name = "";
        if (image_id.length() > 10) {

            String addr = "http://93.12.245.177:8000/image?img=";

            url = addr+ image_id + ".jpg";
            name = image_id;

            //Log.d("imagess", addr + url);
        }

        else{

            url = "http://93.12.245.177:8000/image?img=random"+String.valueOf(2)+".jpg";
            name = String.valueOf(2)+".jpg";

        }

        Picasso.get().load(url).resize(100, 100).into(ManageImage.picassoImageTarget(c, "imageDir", name));
    }


    public static Target picassoImageTarget(Context context, final String imageDir, final String image_id) {

        Log.d("picassoImageTarget", " picassoImageTarget");
        ContextWrapper cw = new ContextWrapper(context);
        final File directory = cw.getDir(imageDir, Context.MODE_PRIVATE); // path to /data/data/yourapp/app_imageDir
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final File myImageFile = new File(directory, image_id); // Create image file
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(myImageFile);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.i("image", "image saved to >>>" + myImageFile.getAbsolutePath());

                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable != null) {
                }
            }
        };
    }
}
