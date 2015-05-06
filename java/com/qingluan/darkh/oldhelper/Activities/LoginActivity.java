package com.qingluan.darkh.oldhelper.Activities;

import com.baidu.location.BDLocation;
import com.qingluan.darkh.oldhelper.R;
import com.qingluan.darkh.oldhelper.Services.BroadReciver;
import com.qingluan.darkh.oldhelper.Services.Infor;
import com.qingluan.darkh.oldhelper.Services.LocateService;
import com.qingluan.darkh.oldhelper.Services.RecivedBroadcastReceiver;
import com.qingluan.darkh.oldhelper.arguments.ARGUMENTS;
import com.qingluan.darkh.oldhelper.database.LoginDataBase;
import com.qingluan.darkh.oldhelper.network.JsonStructurer;
import com.qingluan.darkh.oldhelper.network.NetworkHandler;
import com.qingluan.darkh.oldhelper.util.JsonTools;
import com.qingluan.darkh.oldhelper.util.Kits;
import com.qingluan.darkh.oldhelper.util.SystemUiHider;
import com.qingluan.darkh.oldhelper.util.ToastShow;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.crypto.Cipher;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class LoginActivity extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    private Button login_bt;
    private Button regist_bt;

    ToastShow toastShow ;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);
        toastShow = new ToastShow(context);
        context = LoginActivity.this;
        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        login_bt = (Button) findViewById(R.id.login_bt);
        regist_bt = (Button)findViewById(R.id.register_bt);


        login_bt.setOnTouchListener(mDelayHideTouchListener);
//        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
        login_bt.setOnClickListener(loginBtEvent);
        regist_bt.setOnClickListener(registerBtEvent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    View.OnClickListener registerBtEvent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View resister_view = inflater.inflate(R.layout.widget_register,null);
            final EditText account_identity = (EditText)resister_view.findViewById(R.id.account_identity_re_et);
            final EditText account_user = (EditText)resister_view.findViewById(R.id.account_name_re_et);
            final EditText account_passwd = (EditText)resister_view.findViewById(R.id.account_passwd_re_et);
            final EditText account_community = (EditText)resister_view.findViewById(R.id.account_community_re_et);
            final EditText account_address = (EditText)resister_view.findViewById(R.id.account_address_re_et);
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setView(resister_view)
                    .setPositiveButton("注册", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ContentValues newUser = new ContentValues();
                            String name = account_user.getText().toString().trim();
                            String name_id = account_identity.getText().toString().trim();
                            String passwd = account_passwd.getText().toString().trim();
                            String community = account_community.getText().toString().trim();
                            String address = account_address.getText().toString().trim();
                            ToastShow toastShow = new ToastShow(LoginActivity.this);
                            if (name.equals("")  || name.length() < 2) {
                                toastShow.show("name is empty");
                                dialog.dismiss();
                            }

                            if (name_id.equals("")  || name_id.length() < 2) {
                                toastShow.show("identity is empty");
                                dialog.dismiss();
                            }

                            if (passwd.equals("")  || passwd.length() < 2) {
                                toastShow.show("password is empty");
                                dialog.dismiss();
                            }
                            if (community.equals("")  || community.length() < 2 ) {
                                toastShow.show("name_id is empty");
                                dialog.dismiss();
                            }

                            if (address.equals("") || address.length() < 2 ) {
                                toastShow.show("address is empty");
                                dialog.dismiss();
                            }

//                            toastShow.show(name+name.length() + "|" + name_id + name_id.length()+"|" + passwd +passwd.length()+ "|" + community+community.length());

                            newUser.put("name", name);
                            newUser.put("password", passwd);
                            newUser.put("community", community);
                            newUser.put("name_id", name_id);
                            newUser.put("address", address);
                            newUser.put("version_id", "1");
                            newUser.put("auto_login","0");
                            LoginDataBase l_db = new LoginDataBase(context);
                            l_db.insert(newUser);
                            l_db.close();

                            Kits.simpleTurnActivity(context,MainActivity.class,ARGUMENTS.NAME_ID,name_id);

                        }
                    })
                    .show();

        }
    };

    View.OnClickListener loginBtEvent = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            final LoginDataBase l_db = new LoginDataBase(context);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout_view= inflater.inflate(R.layout.login_box_widget,null);
            final CheckBox checkBox = (CheckBox)layout_view.findViewById(R.id.account_keep_user_cb);
            String name_id  =  l_db.search("1","auto_login","name_id");
            if (name_id != null){
                Kits.simpleTurnActivity(context, MainActivity.class, ARGUMENTS.NAME_ID, name_id);
            }else {
                AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this).setView(layout_view)
                        .setTitle("Login")
                        .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String user = ((TextView) layout_view.findViewById(R.id.account_user_et))
                                        .getText()
                                        .toString();
                                String passwd = ((TextView) layout_view.findViewById(R.id.account_passwd_et))
                                        .getText()
                                        .toString();
                                String name_id = null;

                                if (user.length() < 5 || passwd.length() < 3) {
                                    dialog.dismiss();
                                }
                                String password = l_db.search(user, "name", "password");
                                if (password == null || passwd == null) {
                                    dialog.dismiss();
                                }
                                try {
                                    if (password.equals(passwd)) {
                                        name_id = l_db.search(user, "name", "name_id");
                                        if (checkBox.isChecked()) {
                                            ContentValues values = new ContentValues();
                                            values.put("auto_login", "1");
                                            l_db.update(name_id, values);
                                        }
                                        String autto = l_db.search(user,"name","auto_login");
                                        Log.d("DB","auto login : " +autto);
                                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                        Bundle arg = new Bundle();
                                        arg.putString(ARGUMENTS.NAME_ID, name_id);
                                        i.putExtras(arg);
                                        startActivity(i);

                                    } else {
                                        dialog.dismiss();
                                    }
                                } catch (NullPointerException e) {
                                    dialog.dismiss();
                                }
                            }
                        }).show();
//                .setButton(dialog.BUTTON_POSITIVE,"Login",new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//
            }


        }
    };

    public void  locateEvent(View view){
//        toastShow.show("test start :");
        Toast.makeText(context,"start ",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(context, LocateService.class);
        startService(i);

        Infor infor = new Infor(context,ARGUMENTS.AFTER_GET_LOCATE_INFO_ACTION,new RecivedBroadcastReceiver.ReceivedListener() {


            @Override
            public void recieved(String info) {
                Log.d("some",info);
                JsonTools jsonTools = new JsonTools(info);
                Log.d("some",String.valueOf((Double)jsonTools.getData("lat")));
//                if (info != null) {
//                    JsonTools jsonTools = new JsonTools(info);
                    Double lat = (Double) jsonTools.getData("lat");
                    Double lng = (Double) jsonTools.getData("lng");
//
                    Toast.makeText(context, String.valueOf( lat)+ " | " + String.valueOf(lng), Toast.LENGTH_SHORT).show();
//                }
            }
        });



    }

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
