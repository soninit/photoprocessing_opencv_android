package mobi.devteam.opencvexamples;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.sontieu.snappik.PhotoSDK;
import timber.log.Timber;

public class RotateActivity extends AppCompatActivity {
    Mat matOri;

    @BindView(R.id.iv)
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate);
        ButterKnife.bind(this);

        Timber.tag(RotateActivity.class.getSimpleName());

        matOri = new Mat(getIntent().getLongExtra("matAddr", 0));
        testRotate();
    }

    private void testRotate() {
        Mat matDst = new Mat(matOri.height(), matOri.width(), CvType.CV_8UC3);

        Point center = new Point(matOri.width() / 2, matOri.height() / 2);

        double angle = 90.0f;
        double scale = 0.6;

        Timber.tag(String.format("Java: Point2f: %f, %f, angle: %f, scale: %f",
                center.x, center.y, angle, scale));
        PhotoSDK.rotate(matOri.getNativeObjAddr(), matDst.getNativeObjAddr()
                , (int)center.x, (int)center.y, angle, scale);

        Bitmap bmRotate = Bitmap.createBitmap(matDst.width(), matDst.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(matDst, bmRotate);

        Timber.d("Bitmap rotate size: " + bmRotate.getWidth() + " x " + bmRotate.getHeight());

        iv.setImageBitmap(bmRotate);

        matDst.release();
    }
}
