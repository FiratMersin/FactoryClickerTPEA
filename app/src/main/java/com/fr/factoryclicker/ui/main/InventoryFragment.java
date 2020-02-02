package com.fr.factoryclicker.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.fr.factoryclicker.R;
import com.fr.factoryclicker.adapter.ResourceAdapter;
import com.fr.factoryclicker.dataHolder.DataHolder;
import com.fr.factoryclicker.recipe.RecipeElement;
import com.fr.factoryclicker.resources.Resource;
import com.fr.factoryclicker.resources.ResourceType;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class InventoryFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    private Handler automation;
    private Runnable runnable;

    private DataHolder dataHolder;

    private ImageView item1Img;
    private TextView item1Nb;

    private ImageView item2Img;
    private TextView item2Nb;

    private ImageView item3Img;
    private TextView item3Nb;

    private ImageView item4Img;
    private TextView item4Nb;

    private ProgressBar pgBarClick;
    private ImageButton btnClicker;

    private ListView listPersonalInventory;
    private ResourceAdapter resourceAdapter;

    private static Long date = null;

    public static InventoryFragment newInstance(int index) {
        InventoryFragment fragment = new InventoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_inventory, container, false);

        dataHolder = DataHolder.getInstance();

        btnClicker = root.findViewById(R.id.btnClicker);
        listPersonalInventory = root.findViewById(R.id.listPersonalInventory);
        pgBarClick = root.findViewById(R.id.pgBarClick);

        item1Img = root.findViewById(R.id.item1Img);//recipe item 1 image
        item1Nb= root.findViewById(R.id.item1Nb);//recipe item 1 cost/nbOwned

        item2Img = root.findViewById(R.id.item2Img);
        item2Nb= root.findViewById(R.id.item2Nb);

        item3Img = root.findViewById(R.id.item3Img);
        item3Nb= root.findViewById(R.id.item3Nb);

        item4Img = root.findViewById(R.id.item4Img);
        item4Nb= root.findViewById(R.id.item4Nb);

        final Map<ResourceType, Resource> mapUserInventory = dataHolder.getMapUserInventory();
        final List<Resource> userInventory = dataHolder.getUserInventory();

        btnClicker.setImageResource(dataHolder.getCurrentResourceOnClick().getImage());
        pgBarClick.setMax(dataHolder.getCurrentResourceOnClick().getNumberOfClickToProd());
        recipePrint(item1Img, item2Img, item3Img, item4Img, item1Nb,
                item2Nb, item3Nb, item4Nb, dataHolder.getCurrentResourceOnClick(),
                mapUserInventory);

        MyMainActivity main = MyMainActivity.getMyMain();

        resourceAdapter = new ResourceAdapter
                (main, R.layout.inventory_layout, R.id.lblNumberOfResource, userInventory, mapUserInventory);

        listPersonalInventory.setAdapter(resourceAdapter);

        listPersonalInventory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Resource chosen = dataHolder.getMapUserInventory().
                        get(resourceAdapter.getItem(position).getType());
                dataHolder.setCurrentResourceOnClick(chosen);
                pgBarClick.setProgress(0);
                pgBarClick.setMax(dataHolder.getCurrentResourceOnClick().getNumberOfClickToProd());
                btnClicker.setImageResource(dataHolder.getCurrentResourceOnClick().getImage());
                recipePrint(item1Img, item2Img, item3Img, item4Img, item1Nb,
                        item2Nb, item3Nb, item4Nb, dataHolder.getCurrentResourceOnClick(),
                        mapUserInventory);
            }
        });

        btnClicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resource currentResourceOnClick = dataHolder.getCurrentResourceOnClick();
                pgBarClick.incrementProgressBy(1);
                if(pgBarClick.getProgress() == pgBarClick.getMax()) {
                    pgBarClick.setProgress(0);
                    if (currentResourceOnClick == null) {
                        Log.d("BUG", "Click whit current Ressource null");
                    } else {
                        ResourceType type = currentResourceOnClick.getType();
                        int nbIngr = currentResourceOnClick.getNumberOfIngredients();
                        double recipeProdN = currentResourceOnClick.getRecipeProductionNumber();
                        if (nbIngr > 0) {
                            double max = currentResourceOnClick.getMax();
                            if (currentResourceOnClick.getNumberStored() == max ||
                                    (currentResourceOnClick.getNumberStored() + recipeProdN) > max) return;//can't produce more

                            List<RecipeElement> ingredients = currentResourceOnClick.getIngredients();
                            for (int i = 0; i < nbIngr; i++) {//consume all ingredients
                                RecipeElement re = ingredients.get(i);
                                Log.i("INGREDIENT", re.getIngredient() + "");
                                if (! mapUserInventory.get(re.getIngredient()).consume(re.getNumber())) {//if not enough ingredients
                                    for (int j = i - 1; j >= 0; j--) {//restore all previously consumed ingredients
                                        RecipeElement re2 = ingredients.get(j);
                                        mapUserInventory.get(re2.getIngredient()).restore(re2.getNumber());
                                    }
                                    Log.i("NOTPRODUCED", type + "");
                                    return;
                                }
                            }
                            if (currentResourceOnClick.incrementNumberStored(recipeProdN)) {
                                notifyDataSetChange(item1Nb, item2Nb, item3Nb, item4Nb, dataHolder.getCurrentResourceOnClick(),
                                        mapUserInventory);
                                Log.i("PRODUCED", type + " : " +
                                        currentResourceOnClick.getNumberStored());
                            }
                        } else if (currentResourceOnClick.incrementNumberStored(recipeProdN)) {
                            notifyDataSetChange(item1Nb, item2Nb, item3Nb, item4Nb, dataHolder.getCurrentResourceOnClick(),
                                    mapUserInventory);
                            Log.i("PRODUCED", type + " : " + currentResourceOnClick.getNumberStored());
                        }
                    }

                }

            }
        });

        final int delay = 1000; //milliseconds
        automation = new Handler();

        runnable = new Runnable() {
            public void run() {
                notifyDataSetChange(item1Nb, item2Nb, item3Nb, item4Nb, dataHolder.getCurrentResourceOnClick(),
                        mapUserInventory);
                automation.postDelayed(this, delay);
            }
        };

        automation.postDelayed(runnable, delay);

        return root;
    }


    public void recipePrint(ImageView iv1, ImageView iv2, ImageView iv3, ImageView iv4,
                                   TextView tv1, TextView tv2, TextView tv3, TextView tv4,
                                   Resource resource, Map<ResourceType, Resource> mapUserInventory){

        List<RecipeElement> recipe = resource.getIngredients();
        switch (resource.getNumberOfIngredients()){
            case 0: iv1.setVisibility(View.INVISIBLE);
                tv1.setText("");
                iv2.setVisibility(View.INVISIBLE);
                tv2.setText("");
                iv3.setVisibility(View.INVISIBLE);
                tv3.setText("");
                iv4.setVisibility(View.INVISIBLE);
                tv4.setText("");
                break;

            case 1:iv1.setVisibility(View.VISIBLE);
                iv2.setVisibility(View.INVISIBLE);
                tv2.setText("");
                iv3.setVisibility(View.INVISIBLE);
                tv3.setText("");
                iv4.setVisibility(View.INVISIBLE);
                tv4.setText("");
                iv1.setImageResource(Resource.getImage(recipe.get(0).getIngredient()));
                break;

            case 2:iv1.setVisibility(View.VISIBLE);
                iv2.setVisibility(View.VISIBLE);
                iv3.setVisibility(View.INVISIBLE);
                tv3.setText("");
                iv4.setVisibility(View.INVISIBLE);
                tv4.setText("");
                iv1.setImageResource(Resource.getImage(recipe.get(0).getIngredient()));
                iv2.setImageResource(Resource.getImage(recipe.get(1).getIngredient()));
                break;

            case 3:iv1.setVisibility(View.VISIBLE);
                iv2.setVisibility(View.VISIBLE);
                iv3.setVisibility(View.VISIBLE);
                iv4.setVisibility(View.INVISIBLE);
                tv4.setText("");
                iv1.setImageResource(Resource.getImage(recipe.get(0).getIngredient()));
                iv2.setImageResource(Resource.getImage(recipe.get(1).getIngredient()));
                iv3.setImageResource(Resource.getImage(recipe.get(2).getIngredient()));
                break;

            case 4:iv1.setVisibility(View.VISIBLE);
                iv2.setVisibility(View.VISIBLE);
                iv3.setVisibility(View.VISIBLE);
                iv4.setVisibility(View.VISIBLE);
                iv1.setImageResource(Resource.getImage(recipe.get(0).getIngredient()));
                iv2.setImageResource(Resource.getImage(recipe.get(1).getIngredient()));
                iv3.setImageResource(Resource.getImage(recipe.get(2).getIngredient()));
                iv4.setImageResource(Resource.getImage(recipe.get(3).getIngredient()));
                break;
        }

        refreshRecipePrint(tv1, tv2, tv3, tv4, resource, mapUserInventory);
    }

    public void refreshRecipePrint(TextView tv1, TextView tv2, TextView tv3, TextView tv4,
                                   Resource resource, Map<ResourceType, Resource> mapUserInventory){
        List<RecipeElement> recipe = resource.getIngredients();
        RecipeElement re;
        switch(resource.getNumberOfIngredients()){
            case 4 : re = recipe.get(3);
                    tv4.setText(getString(R.string.strUserInventory, mapUserInventory.get(re.getIngredient()).getNumberStored(),
                    re.getNumber()));

            case 3 :re = recipe.get(2);
                tv3.setText(getString(R.string.strUserInventory, mapUserInventory.get(re.getIngredient()).getNumberStored(),
                        re.getNumber()));

            case 2 :re = recipe.get(1);
                tv2.setText(getString(R.string.strUserInventory, mapUserInventory.get(re.getIngredient()).getNumberStored(),
                        re.getNumber()));

            case 1 :re = recipe.get(0);
                tv1.setText(getString(R.string.strUserInventory, mapUserInventory.get(re.getIngredient()).getNumberStored(),
                        re.getNumber()));
            default: break;
        }

    }

    public void notifyDataSetChange(TextView tv1, TextView tv2, TextView tv3, TextView tv4,
                                    Resource resource, Map<ResourceType, Resource> mapUserInventory){
        resourceAdapter.notifyDataSetChanged();
        refreshRecipePrint(tv1, tv2, tv3, tv4, resource,
                mapUserInventory);
    }

    @Override
    public void onStop() {
        super.onStop();
        automation.removeCallbacks(runnable);
        Date tmpDate = new Date();
        date = tmpDate.getTime();
    }

    @Override
    public void onStart() {Log.i("FRAGONSTART", "INV "+dataHolder.getCurrentResourceOnClick().getType());
        super.onStart();

        final Map<ResourceType, Resource> mapUserInventory = dataHolder.getMapUserInventory();
        final List<Resource> userInventory = dataHolder.getUserInventory();
        MyMainActivity main = MyMainActivity.getMyMain();
        resourceAdapter = new ResourceAdapter
                (main, R.layout.inventory_layout, R.id.lblNumberOfResource, userInventory, mapUserInventory);
        listPersonalInventory.setAdapter(resourceAdapter);

        pgBarClick.setProgress(0);
        pgBarClick.setMax(dataHolder.getCurrentResourceOnClick().getNumberOfClickToProd());
        btnClicker.setImageResource(dataHolder.getCurrentResourceOnClick().getImage());
        recipePrint(item1Img, item2Img, item3Img, item4Img, item1Nb,
                item2Nb, item3Nb, item4Nb, dataHolder.getCurrentResourceOnClick(),
                mapUserInventory);


        automation.removeCallbacks(runnable);
        final int delay = 1000;
        automation.postDelayed(runnable, delay);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        date = null;
    }
}
