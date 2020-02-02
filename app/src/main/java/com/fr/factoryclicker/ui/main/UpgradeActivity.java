package com.fr.factoryclicker.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.fr.factoryclicker.R;
import com.fr.factoryclicker.automation.machines.MachineList;
import com.fr.factoryclicker.automation.machines.MachineType;
import com.fr.factoryclicker.automation.miner.MinersList;
import com.fr.factoryclicker.dataHolder.DataHolder;
import com.fr.factoryclicker.recipe.RecipeElement;
import com.fr.factoryclicker.resources.Resource;
import com.fr.factoryclicker.resources.ResourceType;
import com.fr.factoryclicker.upgrade.Goals;
import com.fr.factoryclicker.upgrade.LoadObj;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpgradeActivity extends AppCompatActivity {

    private Button iuUpgradeButton;
    private Button restartButton;
    private ImageButton closeButton;

    private ImageView iviu1;
    private ImageView iviu2;
    private ImageView iviu3;
    private ImageView iviu4;

    private ImageView ivRestart1;
    private ImageView ivRestart2;
    private ImageView ivRestart3;
    private ImageView ivRestart4;

    private TextView tviu1;
    private TextView tviu2;
    private TextView tviu3;
    private TextView tviu4;

    private TextView tvRestart1;
    private TextView tvRestart2;
    private TextView tvRestart3;
    private TextView tvRestart4;

    private ProgressBar progressBarObj;
    private TextView tvObj;
    private TextView tvObjResource;
    private ImageView ivObj;
    private Button btnLoad;
    private TextView iuTextInfo;

    private List<Resource> userInventory;
    private Map<ResourceType, Resource> mapUserInventory;
    private Resource currentResourceOnClick;

    private MinersList minersList;
    private MachineList smelterList;
    private MachineList constructorList;
    private MachineList assemblerList;
    private MachineList manufacturerList;

    private DataHolder dataHolder;
    private Goals goals;

    private Handler automation;
    private Runnable runnable;
    private static Long date = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);

        iuUpgradeButton = findViewById(R.id.iuButton);
        restartButton = findViewById(R.id.buttonRestart);
        closeButton = findViewById(R.id.closeButton);

        iviu1 = findViewById(R.id.iuImg1);
        iviu2 = findViewById(R.id.iuImg2);
        iviu3 = findViewById(R.id.iuImg3);
        iviu4 = findViewById(R.id.iuImg4);

        ivRestart1 = findViewById(R.id.restartImg1);
        ivRestart2 = findViewById(R.id.restartImg2);
        ivRestart3 = findViewById(R.id.restartImg3);
        ivRestart4 = findViewById(R.id.restartImg4);

        tviu1 = findViewById(R.id.iuText1);
        tviu2 = findViewById(R.id.iuText2);
        tviu3 = findViewById(R.id.iuText3);
        tviu4 = findViewById(R.id.iuText4);

        tvRestart1 = findViewById(R.id.restartText1);
        tvRestart2 = findViewById(R.id.restartText2);
        tvRestart3 = findViewById(R.id.restartText3);
        tvRestart4 = findViewById(R.id.restartText4);

        progressBarObj = findViewById(R.id.progressBarObj);
        tvObj = findViewById(R.id.tvObj);
        ivObj = findViewById(R.id.ivObj);
        btnLoad = findViewById(R.id.btnLoad);
        tvObjResource = findViewById(R.id.tvObjResource);
        iuTextInfo = findViewById(R.id.iuTextInfo);

        dataHolder = DataHolder.getInstance();
        goals = dataHolder.getGoals();

        date = null;
        final int delay = 1000; //milliseconds
        automation = new Handler();


        runnable = new Runnable() {
            public void run() {
                for (Resource r : userInventory) {
                    r.automationIteration();
                }
                refreshAll();
                automation.postDelayed(this, delay);
            }
        };

        iuUpgradeButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                if(goals.canBuyInventoryUpgrade()){
                    dataHolder.getGoals().buyInventoryUpgrade();
                    refreshAll();
                    Context context = getApplicationContext();
                    CharSequence text = "Inventory size upgraded !";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
           }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goals.canBuyPrestigeUpgrade()){
                    dataHolder.getGoals().buyPrestigeUpgrade();
                    resetAll();
                    refreshAll();
                    Context context = getApplicationContext();
                    CharSequence text = "Prestige successful !";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
                refreshAll();
            }
        });

        refreshAll();
    }

    private void resetAll(){
        userInventory = new ArrayList<>();
        mapUserInventory = new HashMap<>();

        userInventory = Resource.getAllResourceList();
        for(Resource r : userInventory){
            mapUserInventory.put(r.getType(), r);
        }

        currentResourceOnClick = mapUserInventory.get(ResourceType.IRON_ORE);

        minersList = new MinersList();

        smelterList = new MachineList(MachineType.SMELTER);

        constructorList = new MachineList(MachineType.CONSTRUCTOR);

        assemblerList = new MachineList(MachineType.ASSEMBLER);

        manufacturerList = new MachineList(MachineType.MANUFACTURER);
    }

    private void closeActivity(){
        finish();
    }

    private void refreshAll(){
        refreshInventoryUpgradeCost();
        refreshRestartCost();
        refreshObj();
    }

    private void refreshObj(){
        LoadObj lObj = goals.getCurrentLoadObj();
        progressBarObj.setMax((int)lObj.getNeeded());
        progressBarObj.setProgress((int)lObj.getLoaded());
        ivObj.setImageResource(Resource.getImage(lObj.getType()));
        double p = lObj.getLoaded()/lObj.getNeeded() * 100.0;
        tvObj.setText(getString(R.string.percentage, p)+"% ("+(int)lObj.getNeeded()+")");
        Resource r = DataHolder.getInstance().getMapUserInventory().get(lObj.getType());
        TextViewPrinter.printValue(tvObjResource, r.getNumberStored());
    }

    private void load(){
        Map<ResourceType, Resource> mapUserInventory = DataHolder.getInstance().getMapUserInventory();
        LoadObj lObj = goals.getCurrentLoadObj();
        Resource r = mapUserInventory.get(lObj.getType());
        double consumed = lObj.load(r.getNumberStored());
        r.consume(consumed);

        if(lObj.isCompleted()){
            Context context = getApplicationContext();
            CharSequence text = "";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            if(goals.isLoadObjOver()){
                text = "Congratz you've finished all loadings ! More coming soon !";
            }else{
                text = "Loading completed, here's the next one !";
                goals.switchToNextLoadObj();
            }
            toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    private void refreshInventoryUpgradeCost(){
        iuTextInfo.setText(getString(R.string.UpgradeInventory, (goals.getNbPrestiges()+1)*10)+"%");

        List<RecipeElement> recipe = goals.getNextInventoryUpgradeCost();
        Map<ResourceType, Resource> mapUserInventory = dataHolder.getMapUserInventory();
        RecipeElement re;
        Resource resource;
        double nb;

        re = recipe.get(0);
        resource = mapUserInventory.get(re.getIngredient());
        nb = re.getNumber();
        TextViewPrinter.printInventory(tviu1, resource.getNumberStored(), nb);
        iviu1.setImageResource(Resource.getImage(re.getIngredient()));
        if(mapUserInventory.get(re.getIngredient()).getNumberStored() >= re.getNumber()){
            tviu1.setTextColor(ContextCompat.getColor(this, R.color.colorGREEN));
        }else{
            tviu1.setTextColor(ContextCompat.getColor(this, R.color.colorRed));
        }

        re = recipe.get(1);
        resource = mapUserInventory.get(re.getIngredient());
        nb = re.getNumber();
        TextViewPrinter.printInventory(tviu2, resource.getNumberStored(), nb);
        iviu2.setImageResource(Resource.getImage(re.getIngredient()));
        if(mapUserInventory.get(re.getIngredient()).getNumberStored() >= re.getNumber()){
            tviu2.setTextColor(ContextCompat.getColor(this, R.color.colorGREEN));
        }else{
            tviu2.setTextColor(ContextCompat.getColor(this, R.color.colorRed));
        }

        re = recipe.get(2);
        resource = mapUserInventory.get(re.getIngredient());
        nb = re.getNumber();
        TextViewPrinter.printInventory(tviu3, resource.getNumberStored(), nb);
        iviu3.setImageResource(Resource.getImage(re.getIngredient()));
        if(mapUserInventory.get(re.getIngredient()).getNumberStored() >= re.getNumber()){
            tviu3.setTextColor(ContextCompat.getColor(this, R.color.colorGREEN));
        }else{
            tviu3.setTextColor(ContextCompat.getColor(this, R.color.colorRed));
        }

        re = recipe.get(3);
        resource = mapUserInventory.get(re.getIngredient());
        nb = re.getNumber();
        TextViewPrinter.printInventory(tviu4, resource.getNumberStored(), nb);
        iviu4.setImageResource(Resource.getImage(re.getIngredient()));
        if(mapUserInventory.get(re.getIngredient()).getNumberStored() >= re.getNumber()){
            tviu4.setTextColor(ContextCompat.getColor(this, R.color.colorGREEN));
        }else{
            tviu4.setTextColor(ContextCompat.getColor(this, R.color.colorRed));
        }
    }

    private void refreshRestartCost(){
        List<RecipeElement> recipe = goals.getNextPrestigeCost();
        Map<ResourceType, Resource> mapUserInventory = dataHolder.getMapUserInventory();
        RecipeElement re;
        Resource resource;
        double nb;

        re = recipe.get(0);
        resource = mapUserInventory.get(re.getIngredient());
        nb = re.getNumber();
        TextViewPrinter.printInventory(tvRestart1, resource.getNumberStored(), nb);
        ivRestart1.setImageResource(Resource.getImage(re.getIngredient()));
        if(mapUserInventory.get(re.getIngredient()).getNumberStored() >= re.getNumber()){
            tvRestart1.setTextColor(ContextCompat.getColor(this, R.color.colorGREEN));
        }else{
            tvRestart1.setTextColor(ContextCompat.getColor(this, R.color.colorRed));
        }

        re = recipe.get(1);
        resource = mapUserInventory.get(re.getIngredient());
        nb = re.getNumber();
        TextViewPrinter.printInventory(tvRestart2, resource.getNumberStored(), nb);
        ivRestart2.setImageResource(Resource.getImage(re.getIngredient()));
        if(mapUserInventory.get(re.getIngredient()).getNumberStored() >= re.getNumber()){
            tvRestart2.setTextColor(ContextCompat.getColor(this, R.color.colorGREEN));
        }else{
            tvRestart2.setTextColor(ContextCompat.getColor(this, R.color.colorRed));
        }

        re = recipe.get(2);
        resource = mapUserInventory.get(re.getIngredient());
        nb = re.getNumber();
        TextViewPrinter.printInventory(tvRestart3, resource.getNumberStored(), nb);
        ivRestart3.setImageResource(Resource.getImage(re.getIngredient()));
        if(mapUserInventory.get(re.getIngredient()).getNumberStored() >= re.getNumber()){
            tvRestart3.setTextColor(ContextCompat.getColor(this, R.color.colorGREEN));
        }else{
            tvRestart3.setTextColor(ContextCompat.getColor(this, R.color.colorRed));
        }

        re = recipe.get(3);
        resource = mapUserInventory.get(re.getIngredient());
        nb = re.getNumber();
        TextViewPrinter.printInventory(tvRestart4, resource.getNumberStored(), nb);
        ivRestart4.setImageResource(Resource.getImage(re.getIngredient()));
        if(mapUserInventory.get(re.getIngredient()).getNumberStored() >= re.getNumber()){
            tvRestart4.setTextColor(ContextCompat.getColor(this, R.color.colorGREEN));
        }else{
            tvRestart4.setTextColor(ContextCompat.getColor(this, R.color.colorRed));
        }
    }

    public void saveGameState(){
        Context context = this;
        SharedPreferences pref =
                this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences sharedPref =
                context.getSharedPreferences(
                        getString(R.string.shared_pref_name),
                        Context.MODE_PRIVATE);


        SharedPreferences.Editor editor = sharedPref.edit();
        final Gson gson = new Gson();

        Date date = new Date();
        Long time = date.getTime();

        editor.remove("time");
        editor.putString("time", gson.toJson(time));

        editor.remove("userInventory");
        editor.putString("userInventory", gson.toJson(userInventory));
        editor.remove("currentResourceOnClick");
        editor.putString("currentResourceOnClick", gson.toJson(currentResourceOnClick));
        editor.remove("minersList");
        editor.putString("minersList", gson.toJson(minersList));
        editor.remove("smelterList");
        editor.putString("smelterList", gson.toJson(smelterList));
        editor.remove("constructorList");
        editor.putString("constructorList", gson.toJson(constructorList));
        editor.remove("assemblerList");
        editor.putString("assemblerList", gson.toJson(assemblerList));
        editor.remove("manufacturerList");
        editor.putString("manufacturerList", gson.toJson(manufacturerList));
        editor.remove("goals");
        editor.putString("goals", gson.toJson(goals));

        editor.apply();
    }

    public void loadGameState(){
        Context context = this;
        SharedPreferences sharedPref =
                context.getSharedPreferences(
                        getString(R.string.shared_pref_name),
                        Context.MODE_PRIVATE);

        final Gson gson = new Gson();

        String json = sharedPref.getString("userInventory", null);
        if(json != null) {
            Type listType = new TypeToken<ArrayList<Resource>>(){}.getType();
            this.userInventory = gson.fromJson(json, listType);
            mapUserInventory = new HashMap<>();
            for(Resource r : userInventory){
                mapUserInventory.put(r.getType(), r);
            }
        }

        json = sharedPref.getString("currentResourceOnClick", null);
        if(json != null) {
            Resource tmp = gson.fromJson(json, Resource.class);
            this.currentResourceOnClick = mapUserInventory.get(tmp.getType());
        }
        json = sharedPref.getString("minersList", null);
        if(json != null) {
            this.minersList = gson.fromJson(json, MinersList.class);
        }
        json = sharedPref.getString("smelterList", null);
        if(json != null) {
            this.smelterList = gson.fromJson(json, MachineList.class);
        }
        json = sharedPref.getString("constructorList", null);
        if(json != null) {
            this.constructorList = gson.fromJson(json, MachineList.class);
        }
        json = sharedPref.getString("assemblerList", null);
        if(json != null) {
            this.assemblerList = gson.fromJson(json, MachineList.class);
        }
        json = sharedPref.getString("manufacturerList", null);
        if(json != null) {
            this.manufacturerList = gson.fromJson(json, MachineList.class);
        }
        json = sharedPref.getString("goals", null);
        if(json != null) {
            this.goals = gson.fromJson(json, Goals.class);
        }

        json = sharedPref.getString("time", null);
        if(json != null) {
            Date date = new Date();
            Long tmp = date.getTime();
            Long time = gson.fromJson(json, Long.class);
            Long seconds = (tmp - time)/1000;
            for(long i = 0; i < seconds; i++) {
                for (Resource r : userInventory) {
                    r.automationIteration();
                }
            }

            Log.i("INFO", seconds+" seconds idle production");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.loadGameState();

        dataHolder.setMapUserInventory(mapUserInventory);
        dataHolder.setUserInventory(userInventory);
        dataHolder.setMinersList(minersList);
        dataHolder.setSmelterList(smelterList);
        dataHolder.setConstructorList(constructorList);
        dataHolder.setAssemblerList(assemblerList);
        dataHolder.setManufacturerList(manufacturerList);
        dataHolder.setGoals(goals);

        Date tmpDate = new Date();
        if(date != null){
            long seconds = (tmpDate.getTime() - date) / 1000;
            if(seconds > 28800)//max 8 hours of idle production
                seconds = 28800;
            Log.i("INFO", "nb sec to auto: "+seconds);
            for(long i = 0; i < seconds; i++){
                for (Resource r : userInventory) {
                    r.automationIteration();
                }
            }
            date = null;
        }

        automation.removeCallbacks(runnable);
        final int delay = 1000;
        automation.postDelayed(runnable, delay);
    }

    @Override
    protected void onPause() {
        super.onPause();

        automation.removeCallbacks(runnable);
        Date tmpDate = new Date();
        date = tmpDate.getTime();

        this.saveGameState();
    }
}
