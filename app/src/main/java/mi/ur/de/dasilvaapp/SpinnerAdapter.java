package mi.ur.de.dasilvaapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by blu on 10.09.2015.
 */
public class SpinnerAdapter<T> extends ArrayAdapter<T> {

    public SpinnerAdapter(Context context, T[] objects) {

        super(context, R.layout.spinner_item_dropdown, objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }
}
