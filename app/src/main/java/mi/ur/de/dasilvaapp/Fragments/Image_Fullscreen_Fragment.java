package mi.ur.de.dasilvaapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import mi.ur.de.dasilvaapp.R;

/**
 * Created by blu on 17.08.2015.
 */
public class Image_Fullscreen_Fragment extends Fragment {

    ImageView fullScreenImage;
    int imageResource;

    //key for bundle
    public static final String IMAGE_RESOURCE = "0";

    @Override
    public void onStart() {
        super.onStart();
        initUI();
        imageResource();
        initFullScreenImage();
    }

    private void initUI() {
        fullScreenImage = (ImageView) getView().findViewById(R.id.image_fullscreen);
    }

    private void imageResource() {
        Bundle bundleImageResource = getArguments();
        if (bundleImageResource != null) {
            imageResource = bundleImageResource.getInt(IMAGE_RESOURCE);
        }
    }

    private void initFullScreenImage() {
        fullScreenImage.setImageResource(imageResource);
        fullScreenImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_fullscreen, container, false);
    }

    public static Image_Fullscreen_Fragment newInstance(Bundle bundle) {
        Image_Fullscreen_Fragment fragment = new Image_Fullscreen_Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }

}
