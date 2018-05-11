package com.services.sarveshwarservices.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.services.sarveshwarservices.R;
import com.services.sarveshwarservices.model.UsersResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    Context context;
 //   JSONArray usersArray;
    ArrayList<UsersResponse> usersResponseArrayList = new ArrayList<UsersResponse>();

    public UsersAdapter(Context context, ArrayList<UsersResponse> usersResponseArrayList) {

        this.context = context;
        this.usersResponseArrayList = usersResponseArrayList;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Context context;
        TextView adminTextView, userTextView;

        public ViewHolder(View view) {
            super(view);
            context = itemView.getContext();

            adminTextView = (TextView) itemView.findViewById(R.id.adminTextView);
            userTextView = (TextView) itemView.findViewById(R.id.userTextView);

        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.users_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        try {

          /*   JSONObject object = usersArray.getJSONObject(position);
            String adminName = object.getString("admin_name");
            holder.adminTextView.setText(adminName.substring(0, 1).toUpperCase() + adminName.substring(1, adminName.length()).toLowerCase());
            //   holder.adminTextView.setText(object.getString("admin_name"));
            holder.userTextView.setText(object.getString("pin"));*/

            String adminName = usersResponseArrayList.get(position).getAdmin_name();
            holder.adminTextView.setText(adminName.substring(0, 1).toUpperCase() + adminName.substring(1, adminName.length()).toLowerCase());
            //   holder.adminTextView.setText(object.getString("admin_name"));
            holder.userTextView.setText(usersResponseArrayList.get(position).getPin());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return usersResponseArrayList.size();
    }
}

