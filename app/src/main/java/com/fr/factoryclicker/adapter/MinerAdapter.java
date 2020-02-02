package com.fr.factoryclicker.adapter;

import android.content.Context;
import android.text.LoginFilter;
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

import com.fr.factoryclicker.R;
import com.fr.factoryclicker.automation.miner.MinerData;
import com.fr.factoryclicker.automation.miner.MinersList;
import com.fr.factoryclicker.dataHolder.DataHolder;
import com.fr.factoryclicker.resources.Resource;
import com.fr.factoryclicker.resources.ResourceType;
import com.fr.factoryclicker.ui.main.MinerFragment;
import com.fr.factoryclicker.ui.main.MyMainActivity;
import com.fr.factoryclicker.ui.main.TextViewPrinter;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

public class MinerAdapter extends ArrayAdapter<MinerData> {

    private final List<MinerData> objects;
    private final int layoutResource;
    private MinersList minersList;
    private TextView tvMinerUsed;

    public MinerAdapter(Context context, int resource, List<MinerData> objects,
                        Map<ResourceType, Resource> mapUserInventory, MinersList minersList, TextView tvMinerUsed){
        super(context, resource, objects);
        this.layoutResource = resource;
        this.objects = objects;
        this.minersList = minersList;
        this.tvMinerUsed = tvMinerUsed;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        DataHolder dataHolder = DataHolder.getInstance();
        minersList = dataHolder.getMinersList();

        final MinerData tmp = getItem(position);
        final MinerData minerData = minersList.getMinerData(tmp.getResourceMined());

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.miner_layout, parent, false);
            }
        Map<ResourceType, Resource> mapUserInventory = DataHolder.getInstance().getMapUserInventory();
        ImageView ivMinerType = convertView.findViewById(R.id.resourceImageMiner);
        ivMinerType.setImageResource(Resource.getImage(minerData.getResourceMined()));
        final TextView tvNbMiner = convertView.findViewById(R.id.nbMiners);
        final TextView tvMinerProdPerMinute = convertView.findViewById(R.id.minerProdPerMinute);

        TextViewPrinter.printNbMachines(tvNbMiner, minersList.getMinerData(minerData.getResourceMined()).getNbMiners());
        double ppm = mapUserInventory.get(minerData.getResourceMined()).getProdPerMinute();
        TextViewPrinter.printPPM(tvMinerProdPerMinute, ppm);

        ImageButton add = convertView.findViewById(R.id.btnMinerAdd);
        ImageButton del = convertView.findViewById(R.id.btnMinerDelete);

        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DataHolder dataHolder = DataHolder.getInstance();
                minersList = dataHolder.getMinersList();
                MinerData md = minersList.getMinerData(minerData.getResourceMined());
                if(addMiner(md)){
                    TextViewPrinter.printNbMachines(tvNbMiner, md.getNbMiners());
                    Map<ResourceType, Resource> mapUserInventory = DataHolder.getInstance().getMapUserInventory();
                    double ppm = mapUserInventory.get(md.getResourceMined()).getProdPerMinute();
                    TextViewPrinter.printPPM(tvMinerProdPerMinute, ppm);
                }
            }
        });

        del.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DataHolder dataHolder = DataHolder.getInstance();
                minersList = dataHolder.getMinersList();
                MinerData md = minersList.getMinerData(minerData.getResourceMined());
                if(delMiner(md)){
                    TextViewPrinter.printNbMachines(tvNbMiner, md.getNbMiners());
                    Map<ResourceType, Resource> mapUserInventory = DataHolder.getInstance().getMapUserInventory();
                    double ppm = mapUserInventory.get(md.getResourceMined()).getProdPerMinute();
                    TextViewPrinter.printPPM(tvMinerProdPerMinute, ppm);
                }
            }
        });

        return convertView;
    }

    public boolean addMiner(MinerData md) {
        ResourceType type = md.getResourceMined();
        Map<ResourceType, Resource> mapUserInventory = DataHolder.getInstance().getMapUserInventory();
        if(minersList.addMiner(md)){
            Log.i("INFO", "MINER ADD");
            Resource resource = mapUserInventory.get(type);
            resource.setProdPerMinute((double) md.getTotalProductionPerMinute());//don't forget to change the prod per minute !!
            this.tvMinerUsed.setText(getContext().getString(R.string.nbMinerUsed, minersList.getNbMinersUsed(), minersList.getNbMiners()));
            Log.i("PROD", md.getResourceMined() +" "+resource.getProdPerMinute()+" / min");
            return true;
        }else{
            Context context = MyMainActivity.getMyMain();
            CharSequence text = "Not enough miners";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            Log.i("INFO", "MINER NOT ADDED");
            return false;
        }
    }

    public boolean delMiner(MinerData md) {
        ResourceType type = md.getResourceMined();
        Map<ResourceType, Resource> mapUserInventory = DataHolder.getInstance().getMapUserInventory();
        double totalProd = md.getTotalProductionPerMinute();
        double prodByMiner = md.getProductionPerMinuteByMiner();

        Resource resource = mapUserInventory.get(type);

        int cons = (int) resource.getConsPerMinute();

        if((totalProd - prodByMiner) >= cons){
            if(minersList.delMiner(md)){
                Log.i("INFO", "MINER DELETED");
                resource.setProdPerMinute((double) md.getTotalProductionPerMinute());//don't forget to change the prod per minute !!
                this.tvMinerUsed.setText(getContext().getString(R.string.nbMinerUsed, minersList.getNbMinersUsed(), minersList.getNbMiners()));
                Log.i("PROD", md.getResourceMined() +" "+resource.getProdPerMinute()+" / min");
                return true;
            }else{
                Context context = MyMainActivity.getMyMain();
                CharSequence text = "No miner to delete.";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Log.i("INFO", "MINER NOT DELETED");
                return false;
            }
        }else{
            Context context = MyMainActivity.getMyMain();
            CharSequence text;
            if(totalProd != 0.0)
                text = "Can't delete, reduce item consumption.";
            else
                text = "No miner to delete.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return false;
        }
    }
}
