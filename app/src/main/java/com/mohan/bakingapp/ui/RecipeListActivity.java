package com.mohan.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import com.mohan.bakingapp.helper.SimpleIdlingResource;
import com.mohan.bakingapp.R;
import com.mohan.bakingapp.adapters.RecipeAdapter;
import com.mohan.bakingapp.model.Recipe;
import com.mohan.bakingapp.data.RecipeDbHelper;
import com.mohan.bakingapp.helper.DbHelper;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.swipe) SwipeRefreshLayout refresh;
    @BindView(R.id.recycler) RecyclerView recycler;

    int images[] = {
            R.drawable.nutellapie,
            R.drawable.brownies,
            R.drawable.yellowcake,
            R.drawable.cheesecake
    };

    private GridLayoutManager layoutManager;

    @Nullable
    private SimpleIdlingResource mSimpleIdlingResource;

    @VisibleForTesting
    @NonNull
    public android.support.test.espresso.IdlingResource getIdlingResource() {
        if (mSimpleIdlingResource == null) {
            mSimpleIdlingResource = new SimpleIdlingResource();
        }
        return mSimpleIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_list);

        ButterKnife.bind(this);

        getIdlingResource();

        setSupportActionBar(toolbar);

        layoutManager =  new GridLayoutManager(this, getResources().getInteger(R.integer.recipe_collums));
        recycler.setLayoutManager(layoutManager);

        refresh.post(new Runnable() {
            @Override
            public void run() {

                refresh.setRefreshing(true);

            }
        });

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getRecipes();

            }
        });

        getRecipes();

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mSimpleIdlingResource != null) {
            mSimpleIdlingResource.setIdleState(false);
        }

    }

    private void getRecipes() {

        RecipeDbHelper.getInstance().getRecipes(new DbHelper() {

            @Override
            public void onSuccess(Object object) {

                refresh.setRefreshing(false);

                List<Recipe> list = (List<Recipe>) object;
                RecipeAdapter adapter = new RecipeAdapter(list, new RecipeAdapter.RecipeClickListener() {
                    @Override
                    public void onRecipeClicked(Recipe recipe) {

                        Intent i = new Intent(RecipeListActivity.this, RecipeItemListActivity.class);
                        i.putExtra("recipe", recipe);
                        startActivity(i);

                    }
                });
                recycler.setAdapter(adapter);


                if (mSimpleIdlingResource != null) {
                    mSimpleIdlingResource.setIdleState(true);
                }

            }

            @Override
            public void onError(String error) {

                refresh.setRefreshing(false);

            }

        });

    }

}
