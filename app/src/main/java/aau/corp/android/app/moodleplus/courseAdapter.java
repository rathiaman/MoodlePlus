package aau.corp.android.app.moodleplus;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Aman Rathi on 20-02-2016.
 */
public class courseAdapter extends BaseExpandableListAdapter {

    private Context ctx;
    private HashMap<String, List<String>> courses_reg;
    private List<String> courses_list_reg;

    public courseAdapter(Context ctx, HashMap<String, List<String>> courses_reg, List<String> courses_list_reg) {
        this.ctx = ctx;
        this.courses_reg = courses_reg;
        this.courses_list_reg = courses_list_reg;
    }

    @Override
    public int getGroupCount() {
        return courses_list_reg.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return courses_reg.get(courses_list_reg.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return courses_list_reg.get(groupPosition);
    }

    @Override
    public Object getChild(int parent, int child) {
        return courses_reg.get(courses_list_reg.get(parent)).get(child);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int parent, int child) {
        return child;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int parent, boolean isExpanded, View convertView, ViewGroup parentView) {
        String group_title = (String) getGroup(parent);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.parent_layout, parentView, false);
            }
        TextView parent_textview = (TextView) convertView.findViewById(R.id.parent_txt);
        parent_textview.setTypeface(null, Typeface.BOLD);
        parent_textview.setText(group_title);
        return convertView;

    }



    @Override
    public View getChildView(int parent, int child, boolean LastChild, View convertView, ViewGroup parentview) {

        String child_title = (String) getChild(parent,child);
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_layout, parentview, false);
        }
        TextView child_textview = (TextView) convertView.findViewById(R.id.child_txt);
        child_textview.setText(child_title);
        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
