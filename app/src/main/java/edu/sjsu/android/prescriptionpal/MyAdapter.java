package edu.sjsu.android.prescriptionpal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


import java.util.ArrayList;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
    {
        private List<String> values;
        public String btnTxt;
        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder
        {
            public Button btn;
            public TextView txtFooter;

            public View layout;

            public ViewHolder(View v)
            {
                super(v);
                layout=v;
                btn = (Button)v.findViewById(R.id.firstLine);
                txtFooter=(TextView)v.findViewById(R.id.secondLine);
            }
        }
        public void add(int position,String item)
        {
            values.add(position,item);
            notifyItemInserted(position);
        }

        public void remove(int position)
        {
            values.remove(position);
            notifyItemRemoved(position);
        }
        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(List<String>myDataset, String s)
        {
            values=myDataset;
            btnTxt = s;
        }
        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            // create a new view
            LayoutInflater inflater=LayoutInflater.from(parent.getContext());
            View v= inflater.inflate(R.layout.row_layout,parent,false);
            // set the view's size, margins, paddings and layout parameters
            ViewHolder vh=new ViewHolder(v);
            return vh;
        }
        // Replace the contents of a view (invoked by the layout manager)
        @Override public void onBindViewHolder(ViewHolder holder,final int position)
        {
            // -get element from your dataset at this position
            // -replace the contents of the view with that element
            final String name=values.get(position);
            holder.txtFooter.setText(name);
            holder.btn.setText(btnTxt);
        }
        // Return the size of your dataset (invoked by the layout manager)
        @Override public int getItemCount(){return values.size();
        }
    }


