package mi.ur.de.dasilvaapp.Gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import mi.ur.de.dasilvaapp.DateAndTime.ActualCalendarProperties;
import mi.ur.de.dasilvaapp.NewsFeed.ImageLoadTask;
import mi.ur.de.dasilvaapp.R;


/**
 * Created by blu on 01.09.2015.
 */
public class GalleryDetailAdapter extends ArrayAdapter<DaSilvaGallery> {
    private Context context;
    private ArrayList<DaSilvaGallery> galleryDetailItems;
    private int galleryDetailItemView;
    private ActualCalendarProperties calendarProperties;

    public GalleryDetailAdapter(Context context, ArrayList<DaSilvaGallery> galleryDetailItems){
        super(context, R.layout.gallery_detail_item, galleryDetailItems);
        this.context = context;
        this.galleryDetailItems = galleryDetailItems;
        this.galleryDetailItemView = R.layout.gallery_detail_item;
    }

    @Override
    public int getCount() {
        return galleryDetailItems.size();
    }

    @Override
    public DaSilvaGallery getItem(int position) {
        return galleryDetailItems.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View actualGalleryItemView;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            actualGalleryItemView = inflater.inflate(galleryDetailItemView, null);
        }
        else{
            actualGalleryItemView = convertView;

        }

        final DaSilvaGallery daSilvaGalleryItem = galleryDetailItems.get(position);

        if(daSilvaGalleryItem != null) {

            ProgressBar progressBar = (ProgressBar) actualGalleryItemView.findViewById(R.id.gallery_detail_item_progress_bar);
            ImageView image = (ImageView) actualGalleryItemView.findViewById(R.id.gallery_detail_item_image);
            new ImageLoadTask(daSilvaGalleryItem.getImageURL(), image, progressBar).execute();
        }

        return actualGalleryItemView;
    }
}
