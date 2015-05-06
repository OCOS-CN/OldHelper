package com.qingluan.darkh.oldhelper.Activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.qingluan.darkh.oldhelper.R;
import com.qingluan.darkh.oldhelper.Services.BroadcastNotifer;
import com.qingluan.darkh.oldhelper.Services.RecivedBroadcastReceiver;
import com.qingluan.darkh.oldhelper.Services.TalkService;
import com.qingluan.darkh.oldhelper.arguments.ARGUMENTS;
import com.qingluan.darkh.oldhelper.components.MoudleView;
import com.qingluan.darkh.oldhelper.util.Kits;
import com.qingluan.darkh.oldhelper.util.ProgressDialogTool;
import com.qingluan.darkh.oldhelper.util.ToastShow;
import com.qingluan.darkh.oldhelper.widgets.ContactListViewAdapter;
import com.qingluan.darkh.oldhelper.widgets.PersonInfoListViewAdapter;

public class MainActivity extends Activity {
    private MoudleView  family_ibt ;
    private MoudleView  health_ibt;
    private MoudleView synthesize_iv;
    private MoudleView oldRestaurant_ibt;
    private MoudleView locate_iv;
    private MoudleView health_way_iv;
    public ListView peroson_info_ll;
    public ListView contact_lv;

    ToastShow ts;
    private Context context;
    private Button urgency_call_menue_bt;

    BroadcastNotifer notifer;
    RecivedBroadcastReceiver reciver;
    IntentFilter intentFilter;
    public int Call_if = 0;
    public  int Call_on = 1;
    private int Call_off = 0;

    public Intent register_talking_service;
    private String name_id;
    ContactListViewAdapter contactListViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().getExtras().containsKey("name_id")) {
            name_id = getIntent().getExtras().getString("name_id");
        }
        context = MainActivity.this;

        init_UI();
        BR_init();
        init_broadcast_and_services();
    }

    private void BR_init(){
        reciver = new RecivedBroadcastReceiver();
        intentFilter = new IntentFilter();
        notifer = new BroadcastNotifer(context);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void init_UI(){
        ts = new ToastShow(context);
        family_ibt = (MoudleView)findViewById(R.id.fmaily_info_ibt);
        health_ibt = (MoudleView)findViewById(R.id.health_ibt);
        oldRestaurant_ibt = (MoudleView) findViewById(R.id.old_restaurant_turn_tv);
        synthesize_iv = (MoudleView)findViewById(R.id.synthesize_iv);
        urgency_call_menue_bt = (Button) findViewById(R.id.call_menu_bt);
        locate_iv = (MoudleView)findViewById(R.id.locate_iv);
        health_way_iv = (MoudleView)findViewById(R.id.health_way_iv);


        health_way_iv.setOnEventListener(new MoudleView.onEventListener() {
            @Override
            public void onClick(View v) {
                Kits.simpleTurnActivity(context, HealthWayActivity.class, ARGUMENTS.NAME_ID, name_id);
            }
        });


        locate_iv.setOnEventListener(new MoudleView.onEventListener() {
            @Override
            public void onClick(View v) {
                Kits.simpleTurnActivity(context,MapActivity.class,ARGUMENTS.NAME_ID,name_id);
            }
        });

        oldRestaurant_ibt.setOnEventListener(new MoudleView.onEventListener() {
            @Override
            public void onClick(View v) {
                Kits.simpleTurnActivity(context,OldRestaurantTabActivity.class,ARGUMENTS.NAME_ID,name_id);
            }
        });

        synthesize_iv.setOnEventListener(new MoudleView.onEventListener() {
            @Override
            public void onClick(View v) {
                Kits.simpleTurnActivity(context,PropertityActivity.class,ARGUMENTS.NAME_ID,name_id);
            }
        });

        family_ibt.setOnEventListener(new MoudleView.onEventListener() {
            @Override
            public void onClick(View v) {
//                AlertDialog dialog = new AlertDialog.Builder(context).setPositiveButton("信息",new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Kits.simpleTurnActivity(context,ItemListActivity.class,ARGUMENTS.NAME_ID,name_id);
//                    }
//                }).setNegativeButton("定位",new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Kits.simpleTurnActivity(context,MapActivity.class,ARGUMENTS.NAME_ID,name_id);
//                    }
//                }).show();
                Kits.simpleTurnActivity(context,ItemListActivity.class,ARGUMENTS.NAME_ID,name_id);
            }
        });

        health_ibt.setOnEventListener(new MoudleView.onEventListener() {
            @Override
            public void onClick(View v) {
                Kits.simpleTurnActivity(context,HistoryInfo.class,ARGUMENTS.NAME_ID,name_id);

            }
        });

        urgency_call_menue_bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final LinearLayout baseSecne = (LinearLayout)findViewById(R.id.contetn_call_ll);
//                ViewGroup rootGroup = (ViewGroup) getLayoutInflater().inflate(R.layout.widget_urgency_call_menu_layout,baseSecne,false);


                Animation anim_alp = AnimationUtils.loadAnimation(context ,R.anim.anim_alp);
                Animation anim_tr = AnimationUtils.loadAnimation(context, R.anim.appearence_anim);
//                Scene new_one = Scene.getSceneForLayout(rootGroup,R.)
//                TransitionManager.beginDelayedTransition(baseSecne,anim_tr);
//                TransitionManager.go(new_one,anim_tr);
                if (Call_if == Call_off){
                    urgency_call_menue_bt.startAnimation(anim_tr);
//                    baseSecne.startAnimation(anim_alp);
//                    anim_alp.setAnimationListener(new Animation.AnimationListener() {
//                        @Override
//                        public void onAnimationStart(Animation animation) {
//
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Animation animation) {
//                            ViewGroup.LayoutParams params = baseSecne.getLayoutParams();
//                            params.width = 600;
//                            baseSecne.setLayoutParams(params);
//
//                        }
//
//                        @Override
//                        public void onAnimationRepeat(Animation animation) {
//
//                        }
//                    });

                    ViewGroup.LayoutParams params = baseSecne.getLayoutParams();
                    params.width = 1000;
                    baseSecne.setLayoutParams(params);
                    ToastShow toastShow = new ToastShow(context);
                    toastShow.show("change ok");

                    Call_if = Call_on;

                }else{

                    ViewGroup.LayoutParams params = baseSecne.getLayoutParams();
                    params.width = 10;
                    baseSecne.setLayoutParams(params);

                    Call_if = Call_off;

                }



            }
        });

        peroson_info_ll = (ListView)findViewById(R.id.peroson_info_ll);
        PersonInfoListViewAdapter adapter = new PersonInfoListViewAdapter(MainActivity.this);
        peroson_info_ll.setAdapter(adapter);

        contact_lv = (ListView)findViewById(R.id.urgency_contact_lv);

        contactListViewAdapter = new ContactListViewAdapter(context);
        contact_lv.setAdapter(contactListViewAdapter);
    }

    private void  init_broadcast_and_services(){
        register_talking_service  = new Intent(getApplicationContext(), TalkService.class);

        startService(register_talking_service);

    }

//    public void toLocateEvent(View v){
//        Kits.simpleTurnActivity(context,MapActivity.class,ARGUMENTS.NAME_ID,name_id);
//    }


}
