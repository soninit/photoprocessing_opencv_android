package mobi.devteam.opencvexamples;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        if (!OpenCVLoader.initDebug()) {
            Log.e("MainActivity", "OpenCV not loaded");
        } else {
            Log.e("MainActivity", "OpenCV loaded");
            System.loadLibrary("opencv_java3");
            System.loadLibrary("native-opencv");
        }
    }

    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Example of a call to a native method
        tv.setText("ABI: " + abiInfo());

        Bitmap bMap= BitmapFactory.decodeResource(getResources(),R.drawable.demo);
        Mat iMat = new Mat(bMap.getHeight(), bMap.getWidth(), CvType.CV_8UC1);
        //             iMat = Utils.loadResource(this, R.drawable.demo, CvType.CV_8UC4);
        Utils.bitmapToMat(bMap, iMat);


        Bitmap b = Bitmap.createBitmap(iMat.width(), iMat.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(iMat, b, false);
        Log.e("MainActivity", "iMat "  + (iMat == null ? "null" : "not null"));

        iv.setImageBitmap(b);
    }


    /**
     *
     * Method from native-opencv
     */
    public native String abiInfo();
    public native String stringFromJNI();
}
