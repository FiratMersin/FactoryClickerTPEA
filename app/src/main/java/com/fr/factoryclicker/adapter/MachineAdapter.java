package com.fr.factoryclicker.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.fr.factoryclicker.R;
import com.fr.factoryclicker.automation.machines.MachineList;
import com.fr.factoryclicker.automation.machines.ProductionData;
import com.fr.factoryclicker.dataHolder.DataHolder;
import com.fr.factoryclicker.recipe.RecipeElement;
import com.fr.factoryclicker.resources.Resource;
import com.fr.factoryclicker.resources.ResourceType;
import com.fr.factoryclicker.ui.main.MachineFragment;
import com.fr.factoryclicker.ui.main.MyMainActivity;
import com.fr.factoryclicker.ui.main.TextViewPrinter;

import java.util.List;
import java.util.Map;

public class MachineAdapter extends ArrayAdapter<ProductionData> {

    private MachineList machineList;
    private TextView tvMachineUsed;
    private TextView tv1,tv2,tv3,tv4;
    private ImageView iv1,iv2,iv3,iv4;
    private MachineFragment fragment;

    public MachineAdapter(Context context, int resource, List<ProductionData> objects,
                            MachineList machineList, TextView tvMachineUsed,
                            ImageView iv1, ImageView iv2, ImageView iv3, ImageView iv4,
                            TextView tv1, TextView tv2, TextView tv3, TextView tv4, MachineFragment fragment){
        super(context, resource, objects);
        this.machineList = machineList;
        this.tvMachineUsed = tvMachineUsed;

        //following Textview and Imageview are used for displaying recipes

        this.tv1 = tv1;
        this.tv2 = tv2;
        this.tv3 = tv3;
        this.tv4 = tv4;
        this.iv1 = iv1;
        this.iv2 = iv2;
        this.iv3 = iv3;
        this.iv4 = iv4;

        //

        this.fragment = fragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        DataHolder dataHolder = DataHolder.getInstance();

        switch (fragment.getType()){
            case SMELTER: machineList = dataHolder.getSmelterList(); break;
            case CONSTRUCTOR: machineList = dataHolder.getConstructorList(); break;
            case ASSEMBLER: machineList = dataHolder.getAssemblerList(); break;
            case MANUFACTURER: machineList = dataHolder.getManufacturerList(); break;
        }

        final ProductionData tmp = getItem(position);
        final ProductionData prodData = machineList.getProductionData(tmp.getResourceProducted());

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.miner_layout, parent, false);
        }

        final Map<ResourceType, Resource> mapUserInventory = DataHolder.getInstance().getMapUserInventory();
        ImageView ivMachineType = convertView.findViewById(R.id.resourceImageMiner);
        ivMachineType.setImageResource(Resource.getImage(prodData.getResourceProducted()));
        final TextView tvNbMachine = convertView.findViewById(R.id.nbMiners);
        final TextView tvMachineProdPerMinute = convertView.findViewById(R.id.minerProdPerMinute);

        TextViewPrinter.printNbMachines(tvNbMachine, prodData.getNbMachines());
        double ppm = mapUserInventory.get(prodData.getResourceProducted()).getProdPerMinute();
        TextViewPrinter.printPPM(tvMachineProdPerMinute,ppm);

        ImageButton add = convertView.findViewById(R.id.btnMinerAdd);
        ImageButton del = convertView.findViewById(R.id.btnMinerDelete);

        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                fragment.setPrintRecipeMachine(false);
                MachineList ml = null;

                DataHolder dataHolder = DataHolder.getInstance();

                switch (fragment.getType()){
                    case SMELTER: ml = dataHolder.getSmelterList(); break;
                    case CONSTRUCTOR: ml = dataHolder.getConstructorList(); break;
                    case ASSEMBLER: ml = dataHolder.getAssemblerList(); break;
                    case MANUFACTURER: ml = dataHolder.getManufacturerList(); break;
                }
                ProductionData pd = ml.getProductionData(prodData.getResourceProducted());
                if(addMachine(pd)){
                    TextViewPrinter.printNbMachines(tvNbMachine, pd.getNbMachines());
                    double ppm = mapUserInventory.get(pd.getResourceProducted()).getProdPerMinute();
                    TextViewPrinter.printPPM(tvMachineProdPerMinute,ppm);
                }

                recipePrint(pd.getRecipe(), pd);
            }
        });

        del.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                fragment.setPrintRecipeMachine(false);
                MachineList ml = null;

                DataHolder dataHolder = DataHolder.getInstance();

                switch (fragment.getType()){
                    case SMELTER: ml = dataHolder.getSmelterList(); break;
                    case CONSTRUCTOR: ml = dataHolder.getConstructorList(); break;
                    case ASSEMBLER: ml = dataHolder.getAssemblerList(); break;
                    case MANUFACTURER: ml = dataHolder.getManufacturerList(); break;
                }
                ProductionData pd = ml.getProductionData(prodData.getResourceProducted());
                if(delMachine(pd)){
                    TextViewPrinter.printNbMachines(tvNbMachine, pd.getNbMachines());
                    double ppm = mapUserInventory.get(pd.getResourceProducted()).getProdPerMinute();
                    TextViewPrinter.printPPM(tvMachineProdPerMinute,ppm);
                }

                recipePrint(pd.getRecipe(), pd);
            }
        });

        return convertView;
    }


    public boolean addMachine(ProductionData md) {
        Map<ResourceType, Resource> mapUserInventory = DataHolder.getInstance().getMapUserInventory();

        ResourceType type = md.getResourceProducted();

        //can add a machine if enough production of the item's recipe
        if(machineList.canAddMachine(md)) {
            if (machineList.addMachine(md)) {
                Log.i("INFO", "MACHINE ADD");
                Resource resource = mapUserInventory.get(type);
                resource.setProdPerMinute(md.getTotalProductionPerMinute());//don't forget to change the prod per minute !!

                //and change the cons per minute of each element in the recipe !!
                List<RecipeElement> recipe = md.getRecipe();
                double ppbm = md.getProductionPerMinuteByMachine();
                double rpn = resource.getRecipeProductionNumber();
                for (RecipeElement re : recipe) {
                    Resource reResource = mapUserInventory.get(re.getIngredient());
                    double nbPerRecipe = re.getNumber();
                    double new_cons = reResource.getConsPerMinute() + (ppbm / rpn * nbPerRecipe);//
                    reResource.setConsPerMinute(new_cons);
                }

                this.tvMachineUsed.setText(getContext().getString(R.string.nbMachineUsed, machineList.getNbMachinesUsed(), machineList.getNbMachines()));
                Log.i("PROD", md.getResourceProducted() + " " + resource.getProdPerMinute() + " / min");
                return true;
            } else {
                Context context = MyMainActivity.getMyMain();
                CharSequence text = "Not enough machines";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Log.i("INFO", "MACHINE NOT ADDED");
                return false;
            }
        }else{
            Context context = MyMainActivity.getMyMain();
            CharSequence text = "Not enough recipe production";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return false;
        }
    }


    public boolean delMachine(ProductionData md) {
        Map<ResourceType, Resource> mapUserInventory = DataHolder.getInstance().getMapUserInventory();

        ResourceType type = md.getResourceProducted();

        double totalProd = md.getTotalProductionPerMinute();
        double prodByMachine = md.getProductionPerMinuteByMachine();

        Resource resource = mapUserInventory.get(type);

        double cons = resource.getConsPerMinute();

        if((totalProd - prodByMachine) >= cons){
            if(machineList.canDelMachine(md) && machineList.delMachine(md)){
                Log.i("INFO", "MACHINE DELETED");
                resource.setProdPerMinute(md.getTotalProductionPerMinute());//don't forget to change the prod per minute !!

                //and change the cons per minute of each element in the recipe !!
                List<RecipeElement> recipe = md.getRecipe();
                double ppbm = md.getProductionPerMinuteByMachine();
                double rpn = resource.getRecipeProductionNumber();
                for(RecipeElement re : recipe){
                    Resource reResource = mapUserInventory.get(re.getIngredient());
                    double nbPerRecipe = re.getNumber();
                    double new_cons = reResource.getConsPerMinute() - (ppbm / rpn * nbPerRecipe);
                    reResource.setConsPerMinute(new_cons);
                }

                this.tvMachineUsed.setText(getContext().getString(R.string.nbMachineUsed, machineList.getNbMachinesUsed(), machineList.getNbMachines()));
                Log.i("PROD", md.getResourceProducted() +" "+resource.getProdPerMinute()+" / min");
                return true;
            }else{
                Context context = MyMainActivity.getMyMain();
                CharSequence text = "No machine to delete.";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Log.i("INFO", "MACHINE NOT DELETED");
                return false;
            }
        }else{
            Context context = MyMainActivity.getMyMain();
            CharSequence text;
            if(totalProd != 0.0)
                text = "Can't delete, reduce item consumption.";
            else
                text = "No machine to delete.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return false;
        }
    }

    public void recipePrint(List<RecipeElement> recipe, ProductionData prodData){

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

        refreshRecipePrint(recipe, prodData);
    }

    public void refreshRecipePrint(List<RecipeElement> recipe, ProductionData prodData){

        Map<ResourceType, Resource> mapUserInventory = DataHolder.getInstance().getMapUserInventory();
        double consPerMach = prodData.getProductionPerMinuteByMachine();
        Resource resource = mapUserInventory.get(prodData.getResourceProducted());
        double rpn = resource.getRecipeProductionNumber();

        RecipeElement re;
        switch(recipe.size()){
            case 4 : re = recipe.get(3);
                Resource r = mapUserInventory.get(re.getIngredient());
                double ppm = r.getProdPerMinute();
                double cpm = r.getConsPerMinute();
                double newcons = cpm + (consPerMach / rpn * re.getNumber());
                TextViewPrinter.printConsProd(tv4, ppm, newcons);
                if(newcons <= ppm){
                    tv4.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorGREEN));
                }else{
                    tv4.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorRed));
                }

            case 3 :re = recipe.get(2);
                r = mapUserInventory.get(re.getIngredient());
                ppm = r.getProdPerMinute();
                cpm = r.getConsPerMinute();
                newcons = cpm + (consPerMach / rpn * re.getNumber());
                TextViewPrinter.printConsProd(tv3, ppm, newcons);
                if(newcons <= ppm){
                    tv3.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorGREEN));
                }else{
                    tv3.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorRed));
                }

            case 2 :re = recipe.get(1);
                r = mapUserInventory.get(re.getIngredient());
                ppm = r.getProdPerMinute();
                cpm = r.getConsPerMinute();
                newcons = cpm + (consPerMach / rpn * re.getNumber());
                TextViewPrinter.printConsProd(tv2, ppm, newcons);
                if(newcons <= ppm){
                    tv2.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorGREEN));
                }else{
                    tv2.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorRed));
                }

            case 1 :re = recipe.get(0);
                r = mapUserInventory.get(re.getIngredient());
                ppm = r.getProdPerMinute();
                cpm = r.getConsPerMinute();
                newcons = cpm + (consPerMach / rpn * re.getNumber());
                TextViewPrinter.printConsProd(tv1, ppm, newcons);
                if(newcons <= ppm){
                    tv1.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorGREEN));
                }else{
                    tv1.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorRed));
                }
            default: break;
        }

    }
}
