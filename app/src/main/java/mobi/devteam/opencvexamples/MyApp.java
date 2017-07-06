package mobi.devteam.opencvexamples;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by sontieu
 * Date: 7/5/17
 */

public class MyApp extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
