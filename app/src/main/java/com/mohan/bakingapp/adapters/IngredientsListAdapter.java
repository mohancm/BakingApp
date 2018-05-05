package com.mohan.bakingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mohan.bakingapp.R;
import com.mohan.bakingapp.model.IngredientList;

import java.util.List;

/**
 * Created by mohancm on 03/05/18.
 */

public class IngredientsListAdapter  extends RecyclerView.Adapter<IngredientsListAdapter.ViewHolder> {

    private List<IngredientList> mLists;

    public IngredientsListAdapter(List<IngredientList> inglist) {
        mLists = inglist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_raw, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        IngredientList inglist = mLists.get(position);
        holder.in.setText(inglist.getIng());
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView in;

        public ViewHolder(View view) {
            super(view);
            in = view.findViewById(R.id.ingredient_rw);
        }
    }
}
