package mobi.devteam.opencvexamples;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.sontieu.snappik.PhotoSDK;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        if (!OpenCVLoader.initDebug()) {
            Log.e("MainActivity", "OpenCV not loaded");
        } else {
            Log.e("MainActivity", "OpenCV loaded");
            System.loadLibrary("opencv_java3");
            System.loadLibrary("native-snappik");
        }
    }

    @BindView(R.id.rlMainContent)
    RelativeLayout rlMainContent;
    @BindView(R.id.iv)
    ImageView iv;

    private static final String TAG = MainActivity.class.getSimpleName();
    private Bitmap bmOri;
    private Mat matOri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Timber.tag(TAG);

        Timber.d("Device cpu: %s", PhotoSDK.abiInfo());

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;

        bmOri = BitmapFactory.decodeResource(getResources(), R.drawable.lena, bmOptions);
        Log.d(TAG, "bmOri: " + bmOri.getWidth() + " x " + bmOri.getHeight());


        matOri = new Mat(bmOri.getHeight(), bmOri.getWidth(), CvType.CV_8UC4);
        Utils.bitmapToMat(bmOri, matOri);

        testLoadBitmap();
    }



    private void testLoadBitmap() {
        Bitmap bmSalt = Bitmap.createBitmap(matOri.width(), matOri.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(matOri, bmSalt);

        iv.setImageBitmap(bmSalt);

        Log.e(TAG, String.format("bmOri: %d * %d, mat: %d * %d", bmOri.getWidth(), bmOri.getHeight(),
                matOri.width(), matOri.height()));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuCrop:
                Intent iCrop = new Intent(this, CropActivity.class);
                iCrop.putExtra("matAddr", matOri.getNativeObjAddr());
                startActivity(iCrop);
                break;
            case R.id.menuRotate:
                Intent iRotate = new Intent(this, RotateActivity.class);
                iRotate.putExtra("matAddr", matOri.getNativeObjAddr());
                startActivity(iRotate);
                break;
            case R.id.menuContrast:
                Intent iContrast = new Intent(this, ContrastBrightnessActivity.class);
                iContrast.putExtra("matAddr", matOri.getNativeObjAddr());
                startActivity(iContrast);
                break;
        }



        return true;
    }
}
