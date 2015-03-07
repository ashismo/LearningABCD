package com.app.ashish.ujanlearning;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.app.ashish.singleton.UserSettingsSingleton;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ashis_000 on 3/7/2015.
 */
public class ColorGridAdapter extends BaseAdapter implements ListAdapter {
    private Context context;
    private Set<String> colorFiles = new LinkedHashSet<String>();
    private List<String> colorFilesList = new ArrayList<>();
//    private Map<String>
//    int colorImages[] = {
//            R.drawable.color_1, R.drawable.color_2,
//            R.drawable.color_3, R.drawable.color_4,
//            R.drawable.color_5, R.drawable.color_6,
//            R.drawable.color_7, R.drawable.color_8
//    };
    public ColorGridAdapter(Context applicationContext) {
        context = applicationContext;

        // Read file name started with color
        try {
            String fileNames [] = context.getAssets().list("");
            if(fileNames != null) {

                int noOfFiles = 0;
                for (String fileName : fileNames) {
                    if (fileName != null && fileName.startsWith("color_")) {
                        colorFiles.add(fileName);
                    }
                }
                colorFilesList.addAll(colorFiles);  // set to list conversion
                UserSettingsSingleton.getUserSettings().setColorFilesList(colorFilesList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return colorFiles.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView iv = null;
        try {
            if (convertView != null) {
                iv = (ImageView) convertView;
            } else {
                iv = new ImageView(context);
                iv.setLayoutParams(new GridView.LayoutParams(300, 250));
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                iv.setPadding(1, 1, 1, 1);
            }
            InputStream si1 = context.getAssets().open(colorFilesList.get(position));
            Bitmap image = BitmapFactory.decodeStream(si1);
            Bitmap scaledImage = Bitmap.createScaledBitmap(image, 115, 115, true);
            iv.setImageBitmap(scaledImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iv;
    }

}
