package rabbitescape.ui.swing;

import rabbitescape.engine.config.Config;
import rabbitescape.render.BitmapCacheProxy;

public class SwingGameInit implements Runnable
{
    public class WaitForUi
    {
        private synchronized void notifyUiReady()
        {
            assert whenUiReady != null;
            notify();
        }

        public synchronized WhenUiReady waitForUi()
        {
            while ( whenUiReady == null )
            {
                //noinspection EmptyCatchBlock
                try
                {
                    synchronized( this )
                    {
                        wait();
                    }
                }
                catch ( InterruptedException e )
                {
                }
            }

            return whenUiReady;
        }
    }

    /**
     * Stuff we have made by the time we notify that the UI is ready
     */
    public static class WhenUiReady
    {
        public final GameUi jframe;
        public final BitmapCacheProxy<SwingBitmap> bitmapCacheProxy;

        public WhenUiReady(
            GameUi jframe, BitmapCacheProxy<SwingBitmap> bitmapCacheProxy )
        {
            this.jframe = jframe;
            this.bitmapCacheProxy = bitmapCacheProxy;
        }
    }

    public final WaitForUi waitForUi = new WaitForUi();
    private WhenUiReady whenUiReady = null;

    private final BitmapCacheProxy<SwingBitmap> bitmapCacheProxy;
    private final Config uiConfig;
    public final MainJFrame frame;
    private final MenuUi menuUi;

    public SwingGameInit(
        BitmapCacheProxy<SwingBitmap> bitmapCacheProxy,
        Config uiConfig,
        MainJFrame frame,
        MenuUi menuUi
    )
    {
        this.bitmapCacheProxy = bitmapCacheProxy;
        this.uiConfig = uiConfig;
        this.frame = frame;
        this.menuUi = menuUi;
    }

    @Override
    public void run()
    {
        //noinspection UnnecessaryLocalVariable
        GameUi jframe = new GameUi( uiConfig, bitmapCacheProxy, frame, menuUi );

        // Populate the cache with images in a worker thread?

        this.whenUiReady = new WhenUiReady( jframe, bitmapCacheProxy );

        waitForUi.notifyUiReady();
    }
}
