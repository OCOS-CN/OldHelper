package com.qingluan.darkh.oldhelper.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.media.Image;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qingluan.darkh.oldhelper.R;

import java.util.zip.Inflater;

/**
 * Created by darkh on 5/3/15.
 */
public class MoudleView extends LinearLayout {
    private TextView moudle_tv;
    private ImageView moudle_iv;
    private LayoutInflater inflater;
    private int resourceId = 0;
    private String text ;
    private View rootView;
    private LinearLayout root_layout;
    private Context context;
    TypedArray attr_array;
    private OnClickListener listener;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP) {
            if(listener != null) listener.onClick(this);
        }
        return super.dispatchTouchEvent(event);
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_UP && (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER || event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            if(listener != null) listener.onClick(this);
        }
        return super.dispatchKeyEvent(event);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public MoudleView(Context context, AttributeSet attrs) {
        super(context,attrs);
        attr_array =  context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.MoudleView, 0, 0);






        try {
            //get the text and colors specified using the names in attrs.xml
            resourceId = attr_array.getResourceId(R.styleable.MoudleView_iconSrc, 0);
            text = attr_array.getString(R.styleable.MoudleView_text);
        } finally {
            attr_array.recycle();
        }

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        root_layout = (LinearLayout)inflater.inflate(R.layout.widget_moudle,this);
        moudle_iv = (ImageView)root_layout.findViewById(R.id.moudle_iv);
        moudle_tv = (TextView)root_layout.findViewById(R.id.moudle_tv);

        if (resourceId > 0){
            moudle_iv.setImageResource(resourceId);

        }
        if(text != null){
            moudle_tv.setText(text);
        }


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



    }

    public void setOnEventListener(final onEventListener onClickListener){
        moudle_iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(v);
            }
        });
    }

    public interface  onEventListener{
        public void onClick(View v);
    }
}
