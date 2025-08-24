package com.xiliu.XTClock;


import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FloatingClockService extends Service {

    private WindowManager windowManager;
    private TextView clockTextView;
    private SharedPreferences sharedPreferences;
    private int initialX;
    private int initialY;
    private float initialTouchX;
    private float initialTouchY;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        sharedPreferences = getSharedPreferences("ClockSettings", MODE_PRIVATE);

        // 创建悬浮时钟视图
        clockTextView = new TextView(this);
        clockTextView.setTextSize(24);
        clockTextView.setGravity(Gravity.CENTER);

        // 应用保存的设置
        applySavedSettings();

        // 设置时钟更新
        updateClock();
        startClockUpdater();

        // 设置拖拽监听
        clockTextView.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 获取并判断 LayoutParams 类型
                Object lp = clockTextView.getLayoutParams();
                if (!(lp instanceof WindowManager.LayoutParams)) {
                    return false;
                }
                WindowManager.LayoutParams params = (WindowManager.LayoutParams) lp;

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        savePosition();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(clockTextView, params);
                        return true;
                }
                return false;
            }
        });



        // 添加视图到窗口
        addViewToWindow();
    }

    private void applySavedSettings() {
        // 应用保存的颜色设置
        int backgroundColor = sharedPreferences.getInt("backgroundColor", 0xFF000000);
        int textColor = sharedPreferences.getInt("textColor", 0xFFFFEE00);
        clockTextView.setBackgroundColor(backgroundColor);
        clockTextView.setTextColor(textColor);

        // 应用保存的大小设置
        int width = sharedPreferences.getInt("width", 200);
        int height = sharedPreferences.getInt("height", 70);
        clockTextView.setMinimumWidth(width);
        clockTextView.setMinimumHeight(height);

    }

    private void addViewToWindow() {
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        // 设置初始位置
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        params.x = sharedPreferences.getInt("positionX", 100);
        params.y = sharedPreferences.getInt("positionY", 100);

        windowManager.addView(clockTextView, params);
    }

    private void startClockUpdater() {
        Thread clockThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(50);
                        updateClock();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        clockThread.setDaemon(true);
        clockThread.start();
    }

    private void updateClock() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault());
        final String time = dateFormat.format(new Date());

        if (clockTextView != null) {
            clockTextView.post(new Runnable() {
                @Override
                public void run() {
                    clockTextView.setText(time);
                }
            });
        }
    }

    private void savePosition() {
        WindowManager.LayoutParams params = (WindowManager.LayoutParams) clockTextView.getLayoutParams();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("positionX", params.x);
        editor.putInt("positionY", params.y);
        editor.apply();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (clockTextView != null) {
            windowManager.removeView(clockTextView);
        }
    }
}
