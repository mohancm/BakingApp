package com.mohan.bakingapp.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.mohan.bakingapp.R;
import com.mohan.bakingapp.adapters.IngredientsListAdapter;
import com.mohan.bakingapp.model.Ingredient;
import com.mohan.bakingapp.model.IngredientList;
import com.mohan.bakingapp.ui.RecipeItemDetailActivity;
import com.mohan.bakingapp.ui.RecipeItemListActivity;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A fragment Representing a single RecipeItem detail Screen.
 * This fragment is either contained in a {@link RecipeItemListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeItemDetailActivity}
 * on handsets.
 */
public class IngrendientDetailFragment extends Fragment {

    public static final String INGREDIENT_ARG = "ingredients";
    private ArrayList<Ingredient> ingredients;
    LinearLayoutManager llm;
    RecyclerView ing_list;
    StringBuilder ingredientList;

    public IngrendientDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(INGREDIENT_ARG)) {

            ingredients = getArguments().getParcelableArrayList(INGREDIENT_ARG);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.ingredient_detail, container, false);

        ButterKnife.bind(this, rootView);

        if(ingredients != null) {

            for (Ingredient ingredient: ingredients) {
                List<IngredientList> ing_adapter = new ArrayList<>();
                ingredientList = new StringBuilder();
                ing_list = rootView.findViewById(R.id.ingredient_detail);
                for(int i=0; i<ingredients.size(); i++) {
                    String quantity = String.valueOf(ingredients.get(i).getQuantity());
                    String measure = ingredients.get(i).getMeasure();
                    String name = ingredients.get(i).getIngredient();

                    String ingredients = "- " + quantity + " " +  measure + " " + name;
                    IngredientsListAdapter adapter = new IngredientsListAdapter(ing_adapter);
                    IngredientList in_holder = new IngredientList(ingredients);
                    ing_adapter.add(in_holder);
                    ing_list.setAdapter(adapter);
                    llm = new LinearLayoutManager(getContext());
                    ing_list.setLayoutManager(llm);
                    ingredientList.append(ingredient);

                }
            }

        }

        return rootView;
    }
}
