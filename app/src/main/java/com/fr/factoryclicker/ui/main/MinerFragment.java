package com.fr.factoryclicker.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.fr.factoryclicker.R;
import com.fr.factoryclicker.adapter.MinerAdapter;
import com.fr.factoryclicker.automation.miner.MinersList;
import com.fr.factoryclicker.dataHolder.DataHolder;
import com.fr.factoryclicker.recipe.RecipeElement;
import com.fr.factoryclicker.resources.Resource;
import com.fr.factoryclicker.resources.ResourceType;

import java.util.List;
import java.util.Map;

public class MinerFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    private ImageView iv1, iv2, iv3, iv4, iv5;
    private TextView tv1, tv2, tv3, tv4, tv5;

    private MinersList minersList;
    private Handler automation;
    private Runnable runnable;

    public static MinerFragment newInstance(int index) {
        MinerFragment fragment = new MinerFragment();
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
        View root = inflater.inflate(R.layout.fragment_miner, container, false);

        tv1 = root.findViewById(R.id.item1Nb);
        tv2 = root.findViewById(R.id.item2Nb);
        tv3 = root.findViewById(R.id.item3Nb);
        tv4 = root.findViewById(R.id.item4Nb);
        tv5 = root.findViewById(R.id.item5Nb);

        iv1 = root.findViewById(R.id.item1Img);
        iv2 = root.findViewById(R.id.item2Img);
        iv3 = root.findViewById(R.id.item3Img);
        iv4 = root.findViewById(R.id.item4Img);
        iv5 = root.findViewById(R.id.item5Img);

        ListView minersListView = root.findViewById(R.id.minersListView);
        Button btnBuyMiner = root.findViewById(R.id.btnBuyMiner);
        final TextView tvMinerUsed = root.findViewById(R.id.tvMinerUsed);
        MyMainActivity main = MyMainActivity.getMyMain();
        DataHolder dataHolder = DataHolder.getInstance();
        minersList = dataHolder.getMinersList();
        Map<ResourceType, Resource> mapUserInventory = dataHolder.getMapUserInventory();

        refreshMinerCost();

        tvMinerUsed.setText(getString(R.string.nbMinerUsed, minersList.getNbMinersUsed(), minersList.getNbMiners()));
        MinerAdapter adapter = new MinerAdapter(main, R.layout.miner_layout, minersList.getMiners(),
                mapUserInventory, minersList, tvMinerUsed);

        minersListView.setAdapter(adapter);

        btnBuyMiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minersList = DataHolder.getInstance().getMinersList();
                if(minersList.buyMiner()){
                    Log.i("INFO", "MINER BOUGHT");
                    tvMinerUsed.setText(getString(R.string.nbMinerUsed, minersList.getNbMinersUsed(), minersList.getNbMiners()));
                    refreshMinerCost();
                }else{
                    Log.i("INFO", "MINER NOT BOUGHT");
                }
            }
        });

        final int delay = 1000; //milliseconds
        automation = new Handler();

        runnable = new Runnable() {
            public void run() {
                    refreshMinerCost();
                automation.postDelayed(this, delay);
            }
        };

        automation.postDelayed(runnable, delay);

        return root;
    }

    private void refreshMinerCost(){
        MinersList minersList = DataHolder.getInstance().getMinersList();
        Map<ResourceType, Resource> mapUserInventory = DataHolder.getInstance().getMapUserInventory();
        int nextMachineNumber = minersList.getNbMiners() + 1;
        List<RecipeElement> recipe = minersList.getNewMinerRecipe(nextMachineNumber);

        RecipeElement re = recipe.get(0);
        Resource resource = mapUserInventory.get(re.getIngredient());
        double nb = re.getNumber();
        iv1.setImageResource(resource.getImage());
        TextViewPrinter.printInventory(tv1, resource.getNumberStored(), nb);
        if(resource.getNumberStored() >= nb){
            tv1.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorGREEN));
        }else{
            tv1.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorRed));
        }

        re = recipe.get(1);
        resource = mapUserInventory.get(re.getIngredient());
        nb = re.getNumber();
        iv2.setImageResource(resource.getImage());
        TextViewPrinter.printInventory(tv2, resource.getNumberStored(), nb);
        if(resource.getNumberStored() >= nb){
            tv2.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorGREEN));
        }else{
            tv2.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorRed));
        }

        re = recipe.get(2);
        resource = mapUserInventory.get(re.getIngredient());
        nb = re.getNumber();
        iv3.setImageResource(resource.getImage());
        TextViewPrinter.printInventory(tv3, resource.getNumberStored(), nb);
        if(resource.getNumberStored() >= nb){
            tv3.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorGREEN));
        }else{
            tv3.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorRed));
        }

        re = recipe.get(3);
        resource = mapUserInventory.get(re.getIngredient());
        nb = re.getNumber();
        iv4.setImageResource(resource.getImage());
        TextViewPrinter.printInventory(tv4, resource.getNumberStored(), nb);
        if(resource.getNumberStored() >= nb){
            tv4.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorGREEN));
        }else{
            tv4.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorRed));
        }

        re = recipe.get(4);
        resource = mapUserInventory.get(re.getIngredient());
        nb = re.getNumber();
        iv5.setImageResource(resource.getImage());
        TextViewPrinter.printInventory(tv5, resource.getNumberStored(), nb);
        if(resource.getNumberStored() >= nb){
            tv5.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorGREEN));
        }else{
            tv5.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorRed));
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        automation.removeCallbacks(runnable);
    }

    @Override
    public void onStart() {
        super.onStart();
        automation.removeCallbacks(runnable);
        final int delay = 1000;
        automation.postDelayed(runnable, delay);
    }

}
