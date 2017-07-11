package mobi.devteam.opencvexamples;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.sontieu.snappik.PhotoSDK;
import timber.log.Timber;

public class ContrastBrightnessActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.skContrast)
    SeekBar skContrast;
    @BindView(R.id.skBrightness)
    SeekBar skBrightness;

    Mat matOri;
    Mat matDst;
    double contrast = 2.0f;
    int brightness = 50;
    Bitmap bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrast_brightness);
        ButterKnife.bind(this);

        Timber.tag(ContrastBrightnessActivity.class.getSimpleName());
        matOri = new Mat(getIntent().getLongExtra("matAddr", 0));
        matDst = new Mat(matOri.rows(), matOri.cols(), matOri.type());

        skContrast.setProgress(50);
        skBrightness.setProgress(50);

        skContrast.setOnSeekBarChangeListener(this);
        skBrightness.setOnSeekBarChangeListener(this);

        Timber.d("Before: matSrc: %d, matDst: %d", matOri.getNativeObjAddr(), matDst.getNativeObjAddr());

        PhotoSDK.setBrightnessContrast(matOri.getNativeObjAddr(), matDst.getNativeObjAddr(),
                contrast, brightness);

        Timber.d("After: matSrc: %d, matDst: %d", matOri.getNativeObjAddr(), matDst.getNativeObjAddr());

        bm = Bitmap.createBitmap(matOri.width(), matOri.height(), Bitmap.Config.ARGB_8888);
        Utils.bitmapToMat(bm, matDst);
        iv.setImageBitmap(bm);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.skContrast:
                break;
            case R.id.skBrightness:
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
