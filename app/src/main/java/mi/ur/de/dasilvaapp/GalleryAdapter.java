package mi.ur.de.dasilvaapp;

import android.content.Context;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by blu on 01.09.2015.
 */
public class GalleryAdapter extends ArrayAdapter<DaSilvaGallery> {
    private Context context;
    private ArrayList<DaSilvaGallery> galleryItems;
    private int galleryItemView;

    public GalleryAdapter(Context context, ArrayList<DaSilvaGallery> galleryItems){
        super(context, R.layout.gallery_item, galleryItems);
        this.context = context;
        this.galleryItems = galleryItems;
        this.galleryItemView = R.layout.gallery_item;
    }

    @Override
    public int getCount() {
        return galleryItems.size();
    }

    @Override
    public DaSilvaGallery getItem(int position) {
        return galleryItems.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View actualGalleryItemView;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            actualGalleryItemView = inflater.inflate(galleryItemView, null);
        }
        else{
            actualGalleryItemView = convertView;

        }

        final DaSilvaGallery daSilvaGalleryItem = galleryItems.get(position);

        if(daSilvaGalleryItem != null) {

            ProgressBar progressBar = (ProgressBar) actualGalleryItemView.findViewById(R.id.gallery_item_progress_bar);
            ImageView image = (ImageView) actualGalleryItemView.findViewById(R.id.gallery_item_image);
            DaSilvaAppTitleTextView date = (DaSilvaAppTitleTextView) actualGalleryItemView.findViewById(R.id.gallery_date);
            DaSilvaAppTitleTextView name = (DaSilvaAppTitleTextView) actualGalleryItemView.findViewById(R.id.gallery_event);
            date.setText(daSilvaGalleryItem.getEventDate());
            name.setText(daSilvaGalleryItem.getEventName());
            new ImageLoadTask(daSilvaGalleryItem.getImageURL(), image, progressBar).execute();
        }

        return actualGalleryItemView;
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

}
