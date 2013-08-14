package nz.ac.waikato.ssc10.BlindAssistant;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Simon
 * Date: 14/08/13
 * Time: 12:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class MediaButtonIntentReceiver extends BroadcastReceiver {
    private static String TAG = "MediaButtonIntentReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();

        if (Intent.ACTION_MEDIA_BUTTON.equals(intentAction)) {
            KeyEvent event = (KeyEvent) intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);

            if (event != null) {
                int action = event.getAction();

                if (action == KeyEvent.ACTION_DOWN) {
                    Log.i(TAG, "Media button pressed");

                    Intent service = new Intent(context, BlindAssistantService.class);
                    service.setAction(BlindAssistantService.ACTION_START_LISTEN);
                    context.startService(service);
                }
            }
        }
    }
}
