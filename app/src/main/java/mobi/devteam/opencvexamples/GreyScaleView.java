package mobi.devteam.opencvexamples;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

/**
 * Created by sontieu
 * Date: 7/6/17
 */

public class GreyScaleView extends View {
    private Bitmap mBitmap;
    private long mStartTime;

    // implementend by libplasma.so
    @SuppressWarnings("JniMissingFunction")
    private static native void renderGreyScale(Bitmap  bitmap, long time_ms);

    public GreyScaleView(Context context, int width, int height) {
        super(context);
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        mStartTime = System.currentTimeMillis();
    }

    @Override protected void onDraw(Canvas canvas) {
        renderGreyScale(mBitmap, System.currentTimeMillis() - mStartTime);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        // force a redraw, with a different time-based pattern.
        invalidate();
    }
}