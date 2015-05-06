package com.qingluan.darkh.oldhelper.Activities;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qingluan.darkh.oldhelper.R;

import com.qingluan.darkh.oldhelper.Activities.dummy.DummyContent;
import com.qingluan.darkh.oldhelper.widgets.OtherConteactListViewAdapter;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    /**
     * widgets init
     */
    private ListView other_contact__lv;
    private ImageView fam_imag;
    private String item_name;
    public OtherConteactListViewAdapter otherConatctListViewAdapter = null;
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            item_name = getArguments().getString(ARG_ITEM_ID);
            otherConatctListViewAdapter = new OtherConteactListViewAdapter(this);
            otherConatctListViewAdapter.setBaseContext(this.getActivity());

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (item_name != null) {
            ((TextView) rootView.findViewById(R.id.family_name)).setText(item_name);
            other_contact__lv = (ListView)rootView.findViewById(R.id.other_contact_lv);
            other_contact__lv.setAdapter(otherConatctListViewAdapter);

            fam_imag = (ImageView) rootView.findViewById(R.id.family_iv);
            fam_imag.setImageResource(R.drawable.oldwoman);
        }

        return rootView;
    }
}
