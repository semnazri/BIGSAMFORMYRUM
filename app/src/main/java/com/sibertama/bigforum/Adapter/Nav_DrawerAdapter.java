package com.sibertama.bigforum.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sibertama.bigforum.Model.POJO.NavDrawerItem;
import com.sibertama.bigforum.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 4/26/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class Nav_DrawerAdapter extends BaseExpandableListAdapter
//        RecyclerView.Adapter<Nav_DrawerAdapter.MyViewHolder>
{
    private Context _context;
    private List<NavDrawerItem> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    public Nav_DrawerAdapter(Context context, List<NavDrawerItem> listDataHeader,
                             HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return (this._listDataChild.get(this._listDataHeader.get(groupPosition).getTitle())
                .get(childPosititon) == null ? "" : this._listDataChild.get(this._listDataHeader.get(groupPosition).getTitle())
                .get(childPosititon));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.nav_drawer_child_layout, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.nav_child_title);

        txtListChild.setText(childText);
        txtListChild.setTypeface(Typeface.createFromAsset(_context.getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return (this._listDataChild.get(this._listDataHeader.get(groupPosition).getTitle()) == null ? 0 : this._listDataChild.get(this._listDataHeader.get(groupPosition).getTitle()).size());
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = _listDataHeader.get(groupPosition).getTitle();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.nav_drawer_parent_layout, null);

        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.nav_parent_title);

        ImageView imageHeader = (ImageView) convertView.findViewById(R.id.img_lbl);

        imageHeader.setImageDrawable(_listDataHeader.get(groupPosition).getIcon());
        lblListHeader.setTypeface(Typeface.createFromAsset(_context.getAssets(), "fonts/Open_Sans_regular.ttf"), Typeface.NORMAL);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
