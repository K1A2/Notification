package com.k1a2.notification

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.widget.Button
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.widget.Toast
import android.support.v4.app.NotificationManagerCompat




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val isPermissionAllowed = isNotiPermissionAllowed()

        if (!isPermissionAllowed) {
            val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
            startActivity(intent)
        }

        (findViewById(R.id.notification) as Button).setOnClickListener {
            if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1){
                /**
                 * 누가버전 이하 노티처리
                 */
                Toast.makeText(getApplicationContext(),"누가버전이하",Toast.LENGTH_SHORT).show();
                val intent = Intent()
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                val bitmapDrawable = getResources().getDrawable(R.mipmap.ic_launcher) as BitmapDrawable
                val bitmap = bitmapDrawable.getBitmap()

                val pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT)

                val builder = NotificationCompat.Builder(getApplicationContext()).
                    setLargeIcon(bitmap)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis()).
                        setShowWhen(true).
                        setAutoCancel(true).setPriority(NotificationCompat.PRIORITY_MAX)
                    .setContentTitle("노티테스트!!")
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setFullScreenIntent(pendingIntent,true)
                    .setContentIntent(pendingIntent)

                val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(0,builder.build())

            }
            else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                //Toast.makeText(getApplicationContext(),"오레오이상",Toast.LENGTH_SHORT).show();
                /**
                 * 오레오 이상 노티처리
                 */
//                    BitmapDrawable bitmapDrawable = (BitmapDrawable)getResources().getDrawable(R.mipmap.ic_launcher);
//                    Bitmap bitmap = bitmapDrawable.getBitmap();
                /**
                 * 오레오 버전부터 노티를 처리하려면 채널이 존재해야합니다.
                 */

                val importance = NotificationManager.IMPORTANCE_HIGH;
                val Noti_Channel_ID = "Noti";
                val Noti_Channel_Group_ID = "Noti_Group";

                val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val notificationChannel = NotificationChannel(Noti_Channel_ID,Noti_Channel_Group_ID,importance)

//                    notificationManager.deleteNotificationChannel("testid"); 채널삭제

                /**
                 * 채널이 있는지 체크해서 없을경우 만들고 있으면 채널을 재사용합니다.
                 */
                if(notificationManager.getNotificationChannel(Noti_Channel_ID) != null){
                    //Toast.makeText(getApplicationContext(),"채널이 이미 존재합니다.",Toast.LENGTH_SHORT).show();
                }
                else{
                    //Toast.makeText(getApplicationContext(),"채널이 없어서 만듭니다.",Toast.LENGTH_SHORT).show();
                    notificationManager.createNotificationChannel(notificationChannel);
                }

                notificationManager.createNotificationChannel(notificationChannel);
//                    Log.e("로그확인","===="+notificationManager.getNotificationChannel("testid1"));
//                    notificationManager.getNotificationChannel("testid");


                val builder = NotificationCompat.Builder(getApplicationContext(),Noti_Channel_ID)
                    .setLargeIcon(null).setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis()).setShowWhen(true).
                        setAutoCancel(true).setPriority(NotificationCompat.PRIORITY_MAX)
                    .setContentTitle("노티테스트!!");
//                            .setContentIntent(pendingIntent);

//                    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0,builder.build());

            }
        }
    }

    private fun isNotiPermissionAllowed(): Boolean {
        val notiListenerSet = NotificationManagerCompat.getEnabledListenerPackages(this)
        val myPackageName = packageName

        for (packageName in notiListenerSet) {
            if (packageName == null) {
                continue
            }
            if (packageName == myPackageName) {
                return true
            }
        }

        return false
    }
}
