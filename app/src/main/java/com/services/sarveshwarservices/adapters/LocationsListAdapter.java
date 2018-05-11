package com.services.sarveshwarservices.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.services.sarveshwarservices.R;
import com.services.sarveshwarservices.model.LocationModel;

import java.util.ArrayList;


/**
 * Created by Sarveshwar Reddy on 30/04/2018.
 */
public class LocationsListAdapter extends RecyclerView.Adapter<LocationsListAdapter.ViewHolder> {

    Context context;
    ArrayList<LocationModel> home_list;

    public LocationsListAdapter(Context context, ArrayList<LocationModel> home_list) {

        this.context = context;
        this.home_list = home_list;

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Context context;
        TextView addresstextview, latitudeTextview, longitudeTextview;

        public ViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            itemView.setOnClickListener(this);

            addresstextview = (TextView) itemView.findViewById(R.id.addresstextview);
            latitudeTextview = (TextView) itemView.findViewById(R.id.latitudeTextview);
            longitudeTextview = (TextView) itemView.findViewById(R.id.longitudeTextview);


        }

        @Override
        public void onClick(View view) {

        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        LocationModel locationModel = home_list.get(position);
        holder.addresstextview.setText(locationModel.getLocation());
        holder.latitudeTextview.setText(locationModel.getLatitude());
        holder.longitudeTextview.setText(locationModel.getLongitude());


    }

    @Override
    public int getItemCount() {
        return home_list.size();
    }

}

