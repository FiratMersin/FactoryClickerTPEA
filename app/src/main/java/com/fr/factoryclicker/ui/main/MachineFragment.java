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
import com.fr.factoryclicker.adapter.MachineAdapter;
import com.fr.factoryclicker.automation.machines.MachineList;
import com.fr.factoryclicker.automation.machines.MachineType;
import com.fr.factoryclicker.dataHolder.DataHolder;
import com.fr.factoryclicker.recipe.RecipeElement;
import com.fr.factoryclicker.resources.Resource;
import com.fr.factoryclicker.resources.ResourceType;

import java.util.List;
import java.util.Map;

public class MachineFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    MachineType type;

    private DataHolder dataHolder;

    private ListView machineListView;
    private MachineList machineList;
    private TextView tvMachineUsed;
    private Button btnBuyMachine, btnPrice;

    //Imageview and TextView for recipe/cost display
    private ImageView iv1, iv2, iv3, iv4;
    private TextView tv1, tv2, tv3, tv4;

    private Handler automation;
    private Runnable runnable;

    private boolean printRecipeMachine = true;

    public MachineFragment(MachineType type){
        this.type = type;
    }

    public static MachineFragment newInstance(int index, MachineType type) {
        MachineFragment fragment = new MachineFragment(type);
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

        View root = inflater.inflate(R.layout.fragment_machine, container, false);

        machineListView = root.findViewById(R.id.machineListView);

        dataHolder = DataHolder.getInstance();

        iv1 = root.findViewById(R.id.item1Img);
        tv1 = root.findViewById(R.id.item1Nb);

        iv2 = root.findViewById(R.id.item2Img);
        tv2 = root.findViewById(R.id.item2Nb);

        iv3 = root.findViewById(R.id.item3Img);
        tv3 = root.findViewById(R.id.item3Nb);

        iv4 = root.findViewById(R.id.item4Img);
        tv4 = root.findViewById(R.id.item4Nb);

        btnBuyMachine = root.findViewById(R.id.btnBuyMachine);
        btnPrice = root.findViewById(R.id.btnPrice);
        tvMachineUsed = root.findViewById(R.id.tvMachineUsed);
        MyMainActivity main = MyMainActivity.getMyMain();

        switch (this.type){
            case SMELTER: machineList = dataHolder.getSmelterList(); btnBuyMachine.setText(R.string.buy_smelter); break;
            case CONSTRUCTOR: machineList = dataHolder.getConstructorList(); btnBuyMachine.setText(R.string.buy_constructor); break;
            case ASSEMBLER: machineList = dataHolder.getAssemblerList(); btnBuyMachine.setText(R.string.buy_assembler); break;
            case MANUFACTURER: machineList = dataHolder.getManufacturerList(); btnBuyMachine.setText(R.string.buy_manufacturer); break;
            default: Log.i("BUG", "UNKNOWN MACHINE TYPE IN MACHINE FRAGMENT");
                     machineList = null; break;
        }

        final Map<ResourceType, Resource> mapUserInventory = dataHolder.getMapUserInventory();

        tvMachineUsed.setText(getString(R.string.nbMachineUsed, machineList.getNbMachinesUsed(), machineList.getNbMachines()));

        final MachineAdapter adapter = new MachineAdapter(main, R.layout.miner_layout, machineList.getMachines(),
                machineList, tvMachineUsed, iv1, iv2, iv3, iv4, tv1, tv2, tv3, tv4, this);

        machineListView.setAdapter(adapter);

        btnBuyMachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type){
                    case SMELTER: machineList = dataHolder.getSmelterList(); break;
                    case CONSTRUCTOR: machineList = dataHolder.getConstructorList(); break;
                    case ASSEMBLER: machineList = dataHolder.getAssemblerList(); break;
                    case MANUFACTURER: machineList = dataHolder.getManufacturerList(); break;
                    default: Log.i("BUG", "UNKNOWN MACHINE TYPE IN MACHINE FRAGMENT");
                        machineList = null; break;
                }
                if(machineList.buyMachine()){
                    Log.i("INFO", "MACHINE BOUGHT");
                    tvMachineUsed.setText(getString(R.string.nbMachineUsed, machineList.getNbMachinesUsed(), machineList.getNbMachines()));
                    recipeMachine();
                }else{
                    Log.i("INFO", "MACHINE NOT BOUGHT");
                }
            }
        });

        btnPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipeMachine();
                printRecipeMachine = true;
            }
        });

        final int delay = 1000; //milliseconds
        automation = new Handler();

        runnable = new Runnable() {
            public void run() {
                if(printRecipeMachine){
                    recipeMachine();
                }
                automation.postDelayed(this, delay);
            }
        };

        automation.postDelayed(runnable, delay);

        recipeMachine();

        return root;
    }

    public void recipeMachine(){
        int nextMachineNumber = machineList.getNbMachines() + 1;
        List<RecipeElement> recipe = machineList.getNewMachineRecipe(nextMachineNumber);

        switch (recipe.size()){
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

        refreshRecipePrint(recipe);
    }

    public void refreshRecipePrint(List<RecipeElement> recipe){

        Map<ResourceType, Resource> mapUserInventory = dataHolder.getMapUserInventory();

        if(recipe.size() == 0) return;
        else{
            RecipeElement re;
            Resource resource;
            double nb;
            switch(recipe.size()){
                case 4 : re = recipe.get(3);
                    resource = mapUserInventory.get(re.getIngredient());
                    nb = re.getNumber();
                    TextViewPrinter.printInventory(tv4, resource.getNumberStored(), nb);
                    if(mapUserInventory.get(re.getIngredient()).getNumberStored() >= re.getNumber()){
                        tv4.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorGREEN));
                    }else{
                        tv4.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorRed));
                    }

                case 3 :re = recipe.get(2);
                    resource = mapUserInventory.get(re.getIngredient());
                    nb = re.getNumber();
                    TextViewPrinter.printInventory(tv3, resource.getNumberStored(), nb);
                    if(mapUserInventory.get(re.getIngredient()).getNumberStored() >= re.getNumber()){
                        tv3.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorGREEN));
                    }else{
                        tv3.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorRed));
                    }

                case 2 :re = recipe.get(1);
                    resource = mapUserInventory.get(re.getIngredient());
                    nb = re.getNumber();
                    TextViewPrinter.printInventory(tv2, resource.getNumberStored(), nb);
                    if(mapUserInventory.get(re.getIngredient()).getNumberStored() >= re.getNumber()){
                        tv2.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorGREEN));
                    }else{
                        tv2.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorRed));
                    }

                case 1 :re = recipe.get(0);
                    resource = mapUserInventory.get(re.getIngredient());
                    nb = re.getNumber();
                    TextViewPrinter.printInventory(tv1, resource.getNumberStored(), nb);
                    if(mapUserInventory.get(re.getIngredient()).getNumberStored() >= re.getNumber()){
                        tv1.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorGREEN));
                    }else{
                        tv1.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorRed));
                    }
            }
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

    public void setPrintRecipeMachine(boolean printRecipeMachine) {
        this.printRecipeMachine = printRecipeMachine;
    }

    public MachineType getType() {
        return type;
    }
}
