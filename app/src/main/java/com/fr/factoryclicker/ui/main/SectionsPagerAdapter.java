package com.fr.factoryclicker.ui.main;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.fr.factoryclicker.R;
import com.fr.factoryclicker.automation.machines.MachineType;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.empty, R.string.empty, R.string.empty, R.string.empty, R.string.empty, R.string.empty};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        switch (position){
            case 0: return InventoryFragment.newInstance(position + 1);
            case 1: return MinerFragment.newInstance(position + 1);
            case 2: return MachineFragment.newInstance(position + 1, MachineType.SMELTER);
            case 3: return MachineFragment.newInstance(position + 1, MachineType.CONSTRUCTOR);
            case 4: return MachineFragment.newInstance(position + 1, MachineType.ASSEMBLER);
            case 5: return MachineFragment.newInstance(position + 1, MachineType.MANUFACTURER);
        }
        Log.i("BUG", "SectionPagerAdapter.getItem returning null, position >= getCount");
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 6 total pages.
        return 6;
    }
}