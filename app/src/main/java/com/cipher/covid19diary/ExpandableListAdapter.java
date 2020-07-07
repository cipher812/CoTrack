package com.cipher.covid19diary;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends ArrayAdapter<String>
{
    private Activity context;
    private String[] Date;
    private String[] Location;
    private String[] People,vehicle;

    public ExpandableListAdapter(Activity context, String[] Date,String[] Location,String[] People,String[] vehicle)
    {
        super(context, R.layout.list_item,Date);

        this.context=context;
        this.Date=Date;
        this.Location=Location;
        this.People=People;
        this.vehicle=vehicle;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View r=convertView;
        ViewHolder viewHolder=null;

        if(r==null)
        {
            LayoutInflater inflater=context.getLayoutInflater();
            r=inflater.inflate(R.layout.list_item,null,true);
            viewHolder=new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder) r.getTag();
        }

        viewHolder.tdate.setText(Date[position]);
        viewHolder.tloc.setText(Location[position]);
        viewHolder.tveh.setText(People[position]);
        viewHolder.tppl.setText(vehicle[position]);

        return r;
    }

    class ViewHolder
    {
        TextView tdate,tloc,tppl,tveh;
        ViewHolder(View v)
        {
            tdate=v.findViewById(R.id.day);
            tloc=v.findViewById(R.id.location);
            tppl=v.findViewById(R.id.people);
            tveh=v.findViewById(R.id.veh);
        }
    }
}
