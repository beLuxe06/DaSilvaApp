package mi.ur.de.dasilvaapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import mi.ur.de.dasilvaapp.Fragments.Image_Fullscreen_Fragment;

// View Pager
// http://www.androidbegin.com/tutorial/android-viewpager-gallery-images-and-texts-tutorial/))

public class ViewPagerAdapter extends PagerAdapter {
    // Declare Variables
    Context context;
    int[] image;
    LayoutInflater inflater;

    public ViewPagerAdapter(Context context, int[] image) {
        this.context = context;
        this.image = image;
    }

    @Override
    public int getCount() {
        return image.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        // Declare Variables
        ImageView locationImage;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_item, container,
                false);

        // Locate the ImageView in viewpager_item.xml
        locationImage = (ImageView) itemView.findViewById(R.id.location_image);
        // Capture position and set to the ImageView
        locationImage.setImageResource(image[position]);

        // Add viewpager_item.xml to ViewPager
        container.addView(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle imageResourceBundle = new Bundle();
                imageResourceBundle.putInt(Image_Fullscreen_Fragment.IMAGE_RESOURCE, image[position]);
                Fragment newFragment = Image_Fullscreen_Fragment.newInstance(imageResourceBundle);
                FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                fragmentManager.beginTransaction().addToBackStack("fullscreen").replace(R.id.container, newFragment).commit();

            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        container.removeView((RelativeLayout) object);

    }
}