package upc.edu.pe.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import upc.edu.pe.proyecto.HistorialActivity;
import upc.edu.pe.proyecto.R;
import upc.edu.pe.receiver.GCMBroadcastReceiver;

/**
 * Created by alumnos on 08/02/2016.
 */
public class GCMIntentService extends IntentService {

    private static final int NOTIF_ALERTA_ID = 1;
    private long[] vibrate = {0,100,200,300};

    public GCMIntentService() {
        super("GCMIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);
        Bundle extras = intent.getExtras();

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                mostrarNotification(extras.getString("MENSAJE"));
            }
        }
        GCMBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void mostrarNotification(String msg) {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                                                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                                                .setAutoCancel(true)
                                                .setVibrate(vibrate)
                                                .setContentTitle("Notificación - Pidelo")
                                                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                                                .setContentText(msg);

        Intent notIntent = new Intent(this, HistorialActivity.class);
        PendingIntent contIntent = PendingIntent.getActivity(this, 0, notIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(contIntent);
        mNotificationManager.notify(NOTIF_ALERTA_ID, mBuilder.build());
    }

}
