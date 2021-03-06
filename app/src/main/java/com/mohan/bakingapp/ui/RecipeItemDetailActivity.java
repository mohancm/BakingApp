package com.mohan.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.mohan.bakingapp.R;
import com.mohan.bakingapp.model.Recipe;
import com.mohan.bakingapp.model.Step;
import com.mohan.bakingapp.ui.fragments.IngrendientDetailFragment;
import com.mohan.bakingapp.ui.fragments.StepDetailFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a single RecipeItem detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeItemListActivity}.
 */
public class RecipeItemDetailActivity extends AppCompatActivity {

    @BindView(R.id.next) Button next;
    @BindView(R.id.previous) Button previous;

    private int position = 0;
    private Recipe recipe;
    public static final String RECIPE_ARG = "recipe";
    public static final String STEP_POSITION = "position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipeitem_detail);

        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if(savedInstanceState != null) {

            position = savedInstanceState.getInt(STEP_POSITION, 0);
            recipe = savedInstanceState.getParcelable(RECIPE_ARG);

        } else {

            Log.i("state","null");

            Bundle arguments = new Bundle();

            if(getIntent().hasExtra(RECIPE_ARG)) {

                next.setText(R.string.next_step);

                position = getIntent().getIntExtra(STEP_POSITION, 0);
                recipe = getIntent().getParcelableExtra(RECIPE_ARG);

                Log.i("position", ""+position);

                if(position == 0) {

                    previous.setVisibility(View.GONE);

                    arguments.putParcelableArrayList(IngrendientDetailFragment.INGREDIENT_ARG,
                            recipe.getIngredients());

                    IngrendientDetailFragment fragment = new IngrendientDetailFragment();
                    fragment.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.recipeitem_detail_container, fragment, "ingredient")
                            .commit();

                    getSupportActionBar().setTitle(R.string.ingredients);

                } else {

                    previous.setVisibility(View.VISIBLE);

                    if(position == recipe.getSteps().size()) {
                        next.setVisibility(View.GONE);
                    }

                    Step recipeStep = recipe.getSteps().get(position-1);

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.recipeitem_detail_container,
                                    StepDetailFragment.newInstance(
                                            recipeStep), "steps")
                            .commit();

                    getSupportActionBar().setTitle(recipeStep.getShortDescription());

                }



            }

        }

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

                Intent intent = new Intent(RecipeItemDetailActivity.this, RecipeItemDetailActivity.class);
                intent.putExtra(RecipeItemDetailActivity.RECIPE_ARG, recipe);
                intent.putExtra(RecipeItemDetailActivity.STEP_POSITION, position-1);

                startActivity(intent);


            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

                Intent intent = new Intent(RecipeItemDetailActivity.this, RecipeItemDetailActivity.class);
                intent.putExtra(RecipeItemDetailActivity.RECIPE_ARG, recipe);
                intent.putExtra(RecipeItemDetailActivity.STEP_POSITION, position+1);

                startActivity(intent);

            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(position != 0) {
            outState.putInt(STEP_POSITION, position);
        }

        if(recipe != null) {
            outState.putParcelable(RECIPE_ARG, recipe);
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();

        return true;
    }

}
