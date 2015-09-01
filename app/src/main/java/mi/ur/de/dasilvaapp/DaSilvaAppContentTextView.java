package mi.ur.de.dasilvaapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by blu on 27.08.2015.
 */
public class DaSilvaAppContentTextView extends AppCompatTextView {
    public DaSilvaAppContentTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/AgencyR.ttf");
        this.setTypeface(tf);
    }
    public DaSilvaAppContentTextView(Context context) {
        super(context);
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/AgencyR.ttf");
        this.setTypeface(tf);
    }

    public DaSilvaAppContentTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/AgencyR.ttf");
        this.setTypeface(tf);
    }

}
