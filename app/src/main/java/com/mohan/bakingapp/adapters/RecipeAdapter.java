package com.mohan.bakingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.mohan.bakingapp.R;
import com.mohan.bakingapp.model.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mohancm on 04/05/18.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{

    private RecipeClickListener listener;
    private List<Recipe> recipes;

    public RecipeAdapter(List<Recipe> recipes, RecipeClickListener listener) {
        this.listener = listener;
        this.recipes = recipes;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipeViewHolder(View.inflate(parent.getContext(), R.layout.recipe_item, null), listener);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {

        holder.bind(recipes.get(position));

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_name) TextView recipeName;
        @BindView(R.id.servings) TextView servings;
        @BindView(R.id.image) ImageView image;

        private RecipeClickListener listener;

        public RecipeViewHolder(View itemView, final RecipeClickListener listener) {
            super(itemView);

            this.listener = listener;
            ButterKnife.bind(this, itemView);

        }

        public void bind(final Recipe recipe) {

            recipeName.setText(recipe.getName());
            servings.setText(String.format(itemView.getContext().getString(R.string.number_servings), recipe.getServings()));

            switch (recipe.getName()) {
                case "Nutella Pie":
                    image.setImageResource(R.drawable.nutellapie);

                    break;
                case "Brownies":
                    image.setImageResource(R.drawable.brownies);
                    break;
                case "Yellow Cake":
                    image.setImageResource(R.drawable.yellowcake);
                    break;
                case "Cheesecake":
                    image.setImageResource(R.drawable.cheesecake);
                    break;
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    listener.onRecipeClicked(recipe);

                }
            });

        }

    }

    public interface RecipeClickListener {
        void onRecipeClicked(Recipe recipe);
    }

}