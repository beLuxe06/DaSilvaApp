package mi.ur.de.dasilvaapp;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by blu on 27.08.2015.
 */
public class DaSilvaAppTitleTextView extends AppCompatTextView {
    public DaSilvaAppTitleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/SixCaps.ttf");
        this.setTypeface(tf);
    }
    public DaSilvaAppTitleTextView(Context context) {
        super(context);
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/SixCaps.ttf");
        this.setTypeface(tf);
    }

    public DaSilvaAppTitleTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/SixCaps.ttf");
        this.setTypeface(tf);
    }


}
