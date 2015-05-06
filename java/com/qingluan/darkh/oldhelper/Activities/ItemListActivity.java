package com.qingluan.darkh.oldhelper.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import com.qingluan.darkh.oldhelper.R;
import com.qingluan.darkh.oldhelper.Services.BroadReciver;
import com.qingluan.darkh.oldhelper.Services.BroadcastNotifer;
import com.qingluan.darkh.oldhelper.Services.Commands;
import com.qingluan.darkh.oldhelper.Services.RecivedBroadcastReceiver;
import com.qingluan.darkh.oldhelper.arguments.ARGUMENTS;
import com.qingluan.darkh.oldhelper.network.NetworkInteract;
import com.qingluan.darkh.oldhelper.util.JsonTools;
import com.qingluan.darkh.oldhelper.util.Kits;
import com.qingluan.darkh.oldhelper.util.LogTools;
import com.qingluan.darkh.oldhelper.util.ProgressDialogTool;
import com.qingluan.darkh.oldhelper.util.ToastShow;
import com.qingluan.darkh.oldhelper.widgets.FamilyChooseListViewAdapter;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ItemListFragment} and the item details
 * (if present) is a {@link ItemDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link ItemListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ItemListActivity extends Activity
        implements ItemListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private ListView item_lv;
    private  ListView base_info_lv;
    ArrayAdapter base_info_adapter;
    ToastShow toastShow;
    FamilyChooseListViewAdapter adapter ;

    BroadcastNotifer notifer;
    RecivedBroadcastReceiver reciver;
    IntentFilter intentFilter;


    Context context ;
    String name_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        toastShow = new ToastShow(ItemListActivity.this);
        context = ItemListActivity.this;

        if (getIntent().getExtras().containsKey("name_id")) {
            name_id = getIntent().getExtras().getString("name_id");
            toastShow.show(name_id);
        }
        /**
         *  init some arguments
         */
        //
//        BR_init();
//        init_Data();

        /**      **
         *   end **
         */



        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
            item_lv = (ListView)findViewById(R.id.item_list);
            base_info_lv = (ListView)findViewById(R.id.base_info_lv);
            base_info_adapter = new ArrayAdapter(context,android.R.layout.simple_list_item_1,new String[]{
                    "李刘章",
                    "汉",
                    "67岁",
                    "慧家小区",
                    "xxx街道xx",
                    "血压：100kps",
                    "血糖：0.02 g/ml",
                    "无病历史",
                    "爱好：羽毛球"
            });
            base_info_lv.setAdapter(base_info_adapter);

            adapter = new FamilyChooseListViewAdapter(ItemListActivity.this);
            item_lv.setAdapter(adapter);
            item_lv.setOnItemSelectedListener(listener);
            item_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    item_lv.setSelection(position);
//                    item_lv.setActivated(true);
                    toastShow.show(String.valueOf(position));
                    toastShow.show("choose ");
                    Bundle arguments = new Bundle();
                    arguments.putString(ItemDetailFragment.ARG_ITEM_ID, adapter.getKey(position));
                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();


                }
            });

//            item_lv.setActivated(true);
            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
//            ((ItemListFragment) getFragmentManager()
//                    .findFragmentById(R.id.item_list))
//                    .setActivateOnItemClick(true);


        }

        // TODO: If exposing deep links into your app, handle intents here.
    }


    private void BR_init(){
        reciver = new RecivedBroadcastReceiver();
        intentFilter = new IntentFilter();
        notifer = new BroadcastNotifer(context);
        intentFilter.addAction(ARGUMENTS.AFTER_GET_FAMILY_INFO_ACTION);
    }


    @Override
    protected void onDestroy() {
//        unregisterReceiver(reciver);
        super.onDestroy();
    }

    private void init_Data (){

        registerReceiver(reciver,intentFilter);

        reciver.setReceivedListener(new RecivedBroadcastReceiver.ReceivedListener() {
            @Override
            public void recieved(String info) {
                JsonTools jsonTools = new JsonTools(info);
                JSONArray array = jsonTools.getDatas("content");

                toastShow.show("get data");
                Log.d("HttpData",array.toString());
                ProgressDialogTool.dis();
            }
        });



        notifer.sendIntentService(context, Commands.get_family_command(name_id));

        ProgressDialogTool.show(context,"wait ","data donwloading ....");

    }


    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, id);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, ItemDetailActivity.class);
            detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }


    /**
     * Callback method from {@link ItemListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    ListView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            toastShow.show("choose "+id);
            if (mTwoPane) {
                // In two-pane mode, show the detail view in this activity by
                // adding or replacing the detail fragment using a
                // fragment transaction.

                toastShow.show("choose ");
                Bundle arguments = new Bundle();
                arguments.putString(ItemDetailFragment.ARG_ITEM_ID, adapter.getKey(position));
                ItemDetailFragment fragment = new ItemDetailFragment();
                fragment.setArguments(arguments);
                getFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit();


            } else {
                // In single-pane mode, simply start the detail activity
                // for the selected item ID.
                Intent detailIntent = new Intent(ItemListActivity.this,ItemDetailActivity.class);
                detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, id);
                startActivity(detailIntent);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


}
