package rabbitescape.ui.android;

import android.os.Bundle;
import android.view.SurfaceHolder;

import rabbitescape.engine.LevelWinListener;
import rabbitescape.engine.World;
import rabbitescape.engine.config.Config;
import rabbitescape.render.BitmapCacheProxy;

public class Game
{
    public final AndroidGameLaunch gameLaunch;
    private Thread thread;

    public Game(
        BitmapCacheProxy<AndroidBitmap> bitmapCacheProxy,
        Config config,
        World world,
        LevelWinListener winListener,
        Bundle savedInstanceState
    )
    {
        gameLaunch = new AndroidGameLaunch(
            bitmapCacheProxy,
            config,
            world,
            winListener,
            savedInstanceState
        );
    }

    public void start( SurfaceHolder surfaceHolder )
    {
        gameLaunch.graphics.surfaceHolder = surfaceHolder;
        gameLaunch.readyToRun();

        thread = new Thread( gameLaunch, "GameLaunch" );
        thread.start();
    }

    public void stop()
    {
        gameLaunch.stopAndDispose();
        try
        {
            thread.join();
        }
        catch ( InterruptedException e )
        {
            // Should never happen
            e.printStackTrace();
        }

        thread = null;
        gameLaunch.graphics.surfaceHolder = null;
    }
}
