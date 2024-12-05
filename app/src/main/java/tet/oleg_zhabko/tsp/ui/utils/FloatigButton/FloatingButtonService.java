package tet.oleg_zhabko.tsp.ui.utils.FloatigButton;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.Image;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.ui.route.OnRouteMainActivity;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class FloatingButtonService extends Service {
    private WindowManager windowManager;
    private View floatingView;
    private String pseudo_tag = FloatingButtonService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        TetDebugUtil.e(pseudo_tag, "Sevice onCreate");
        // Create window
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        floatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_button, null);

        int layoutFlag = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                : WindowManager.LayoutParams.TYPE_PHONE;
        TetDebugUtil.e(pseudo_tag, "Check Build.VERSION_CODES=[" + Build.VERSION.SDK_INT + "]");
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 0;
        params.y = 10;

        TetDebugUtil.e(pseudo_tag, "add FAB");
        windowManager.addView(floatingView, params);

        ImageView fab = (ImageView) floatingView.findViewById(R.id.fab);
        fab.bringToFront();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TetDebugUtil.e(pseudo_tag, "Click fab.setOnClickListener ");
                Intent intent = new Intent(FloatingButtonService.this, OnRouteMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);



            }
        });
        // Move logic
        fab.setOnTouchListener(new View.OnTouchListener() {
            private int initialX, initialY;
            private float initialTouchX, initialTouchY;
            private boolean isMoving = false; // Флаг для отслеживания перемещения

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        isMoving = false; // Сбрасываем флаг
                        return true; // Обрабатываем нажатие

                    case MotionEvent.ACTION_MOVE:
                        isMoving = true; // Устанавливаем флаг перемещения
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(floatingView, params);
                        return true; // Обрабатываем перемещение

                    case MotionEvent.ACTION_UP:
                        if (!isMoving) {
                            // Если не было перемещения, обрабатываем клик
                            fab.performClick(); // Вызываем метод клика
                            return true; // Обрабатываем отпускание
                        }
                        return false; // Если было перемещение, возвращаем false

                    default:
                        return false; // Для других событий
                }
            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        TetDebugUtil.e(pseudo_tag, "onDestroy();");
        if (floatingView != null) windowManager.removeView(floatingView);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}