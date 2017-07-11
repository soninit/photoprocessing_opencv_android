package mobi.devteam.opencvexamples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

import org.opencv.core.Mat;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ContrastBrightnessActivity extends AppCompatActivity {

    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.skContrast)
    SeekBar skContrast;
    @BindView(R.id.skBrightness)
    SeekBar skBrightness;

    Mat matOri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrast_brightness);
        ButterKnife.bind(this);

        Timber.tag(ContrastBrightnessActivity.class.getSimpleName());
        matOri = new Mat(getIntent().getLongExtra("matAddr", 0));
    }


}
