package tet.oleg_zhabko.tsp.ui.utils.FloatigButton;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import tet.oleg_zhabko.tsp.R;
import tet.oleg_zhabko.tsp.ui.route.OnRouteMainActivity;
import tet.tetlibrarymodules.tetdebugutils.debug.debug_tools.TetDebugUtil;

public class FloatingService extends Service {
    private WindowManager windowManager;
    private View floatingView;
    private String pseudo_tag = FloatingService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        TetDebugUtil.e(pseudo_tag, "Sevice onCreate");
        // Создаем окно
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        floatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_button, null);

        int layoutFlag = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                : WindowManager.LayoutParams.TYPE_PHONE;
        TetDebugUtil.e(pseudo_tag, "Check Build.VERSION_CODES=[" + Build.VERSION.SDK_INT + "]");
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                layoutFlag,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 0;
        params.y = 10;

        TetDebugUtil.e(pseudo_tag, "add FAB");
        windowManager.addView(floatingView, params);

        ImageView fab = (ImageView) floatingView.findViewById(R.id.fab);

        // Логика перемещения
        fab.setOnTouchListener(new View.OnTouchListener() {
            private int initialX, initialY;
            private float initialTouchX, initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(floatingView, params);
                        return true;
                }
                return false;
            }
        });


        fab.setOnClickListener(v -> {
            TetDebugUtil.e(pseudo_tag, "Click fab.setOnClickListener ");
            Intent intent = new Intent(FloatingService.this, OnRouteMainActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
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