package mi.ur.de.dasilvaapp;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
    public Object instantiateItem(ViewGroup container, int position) {

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

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        container.removeView((RelativeLayout) object);

    }
}