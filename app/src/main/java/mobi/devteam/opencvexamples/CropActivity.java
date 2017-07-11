package mobi.devteam.opencvexamples;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.sontieu.snappik.PhotoSDK;
import timber.log.Timber;

/**
 * Created by sontieu
 * Date: 7/8/17
 */

public class CropActivity extends AppCompatActivity {

    @BindView(R.id.iv)
    ImageView iv;

    Mat matOri;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        ButterKnife.bind(this);
        Timber.tag(CropActivity.class.getSimpleName());

        long matOriAddr = getIntent().getExtras().getLong("matAddr");
        matOri = new Mat(matOriAddr);

        testCrop();
    }


    private void testCrop() {
        Mat matDst = new Mat(matOri.rows(), matOri.cols(), matOri.type());

        Timber.d("Mat src: %d %d", matDst.rows(), matDst.cols());
        Timber.d("MatDst address: %d", matDst.getNativeObjAddr());

        PhotoSDK.crop(matOri.getNativeObjAddr(), matDst.getNativeObjAddr(), 0, 0, 650, 650);
        Bitmap bmCrop = Bitmap.createBitmap(matDst.width(), matDst.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(matDst, bmCrop);

        Timber.d("bmCrop " + bmCrop.getWidth() + " x " + bmCrop.getHeight());
        iv.setImageBitmap(bmCrop);

        matDst.release();
    }
}
