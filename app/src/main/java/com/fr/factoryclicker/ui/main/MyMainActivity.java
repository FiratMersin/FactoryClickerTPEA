package com.fr.factoryclicker.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.fr.factoryclicker.R;
import com.fr.factoryclicker.automation.machines.MachineList;
import com.fr.factoryclicker.automation.machines.MachineType;
import com.fr.factoryclicker.automation.miner.MinersList;
import com.fr.factoryclicker.dataHolder.DataHolder;
import com.fr.factoryclicker.resources.Resource;
import com.fr.factoryclicker.resources.ResourceType;
import com.fr.factoryclicker.upgrade.Goals;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyMainActivity extends AppCompatActivity {

    private List<Resource> userInventory;
    private Map<ResourceType, Resource> mapUserInventory;
    private Resource currentResourceOnClick;

    private MinersList minersList;
    private MachineList smelterList;
    private MachineList constructorList;
    private MachineList assemblerList;
    private MachineList manufacturerList;
    private Goals goals;

    private static MyMainActivity myMain;

    private DataHolder myDataHolder = DataHolder.getInstance();

    private Handler automation;
    private Runnable runnable;
    private static Long date = null;

    private boolean onRestart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myMain = this;

        setContentView(R.layout.activity_my_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        tabs.getTabAt(0).setIcon(R.mipmap.ic_image_craft_bench);
        tabs.getTabAt(1).setIcon(R.mipmap.ic_image_miner);
        tabs.getTabAt(2).setIcon(R.mipmap.ic_image_smelter);
        tabs.getTabAt(3).setIcon(R.mipmap.ic_image_constructor);
        tabs.getTabAt(4).setIcon(R.mipmap.ic_image_assembler);
        tabs.getTabAt(5).setIcon(R.mipmap.ic_image_manufacturer);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUpgrade();
            }
        });

        /////////////////////////////////////////
        //////////////////DATA///////////////////
        /////////////////////////////////////////

        userInventory = new ArrayList<>();
        mapUserInventory = new HashMap<>();

        userInventory = Resource.getAllResourceList();
        for(Resource r : userInventory){
            mapUserInventory.put(r.getType(), r);
        }

        currentResourceOnClick = mapUserInventory.get(ResourceType.IRON_ORE);

        myDataHolder.setMapUserInventory(mapUserInventory);
        myDataHolder.setUserInventory(userInventory);
        myDataHolder.setCurrentResourceOnClick(currentResourceOnClick);

        minersList = new MinersList();

        smelterList = new MachineList(MachineType.SMELTER);

        constructorList = new MachineList(MachineType.CONSTRUCTOR);

        assemblerList = new MachineList(MachineType.ASSEMBLER);

        manufacturerList = new MachineList(MachineType.MANUFACTURER);

        goals = new Goals();

        date = null;
        final int delay = 1000; //milliseconds
        automation = new Handler();


        runnable = new Runnable() {
            public void run() {
                for (Resource r : userInventory) {
                    r.automationIteration();
                }
                automation.postDelayed(this, delay);
            }
        };
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        this.loadGameState();

        myDataHolder.setMapUserInventory(mapUserInventory);
        myDataHolder.setUserInventory(userInventory);
        myDataHolder.setMinersList(minersList);
        myDataHolder.setSmelterList(smelterList);
        myDataHolder.setConstructorList(constructorList);
        myDataHolder.setAssemblerList(assemblerList);
        myDataHolder.setManufacturerList(manufacturerList);
        myDataHolder.setCurrentResourceOnClick(currentResourceOnClick);
        myDataHolder.setGoals(goals);
        onRestart = true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(! onRestart) {
            this.loadGameState();

            myDataHolder.setMapUserInventory(mapUserInventory);
            myDataHolder.setUserInventory(userInventory);
            myDataHolder.setMinersList(minersList);
            myDataHolder.setSmelterList(smelterList);
            myDataHolder.setConstructorList(constructorList);
            myDataHolder.setAssemblerList(assemblerList);
            myDataHolder.setManufacturerList(manufacturerList);
            myDataHolder.setCurrentResourceOnClick(currentResourceOnClick);
            myDataHolder.setGoals(goals);
            onRestart = false;
        }

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


    public void saveGameState(){
        Context context = this;
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
        editor.putString("currentResourceOnClick", gson.toJson(myDataHolder.getCurrentResourceOnClick()));
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

    public void goToUpgrade(){
        Intent intent = new Intent(this, UpgradeActivity.class);
        startActivity(intent);
    }

    public Resource getCurrentResourceOnClick() {
        return currentResourceOnClick;
    }

    public void setCurrentResourceOnClick(Resource currentResourceOnClick) {
        this.currentResourceOnClick = currentResourceOnClick;
    }

    public static MyMainActivity getMyMain() {
        return myMain;
    }
}