package com.fr.factoryclicker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.fr.factoryclicker.R;
import com.fr.factoryclicker.dataHolder.DataHolder;
import com.fr.factoryclicker.resources.Resource;
import com.fr.factoryclicker.resources.ResourceType;

import java.util.List;
import java.util.Map;

import com.fr.factoryclicker.recipe.RecipeElement;
import com.fr.factoryclicker.ui.main.TextViewPrinter;

public class ResourceAdapter extends ArrayAdapter<Resource> {

    private final List<Resource> objects;
    private final int layoutResource;
    private Map<ResourceType, Resource> mapUserInventory;

    public ResourceAdapter(Context context, int resource, int layout, List<Resource> objects, Map<ResourceType, Resource> mapUserInventory){
        super(context, resource, layout, objects);
        this.layoutResource = resource;
        this.objects = objects;
        this.mapUserInventory = mapUserInventory;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Resource resource = DataHolder.getInstance().getMapUserInventory().get(getItem(position).getType());


        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.inventory_layout, parent, false);
        }

        ImageView iv = convertView.findViewById(R.id.resourceImage);
        iv.setImageResource(resource.getImage());

        int nbIngredients = resource.getNumberOfIngredients();
        ImageView ig;
        TextView tv;
        List<RecipeElement> ingredients = resource.getIngredients();

        if(nbIngredients == 0) {
            ig = convertView.findViewById(R.id.ingredient1);
            ig.setImageResource(R.color.colorOrangeDark);
            ig.setVisibility(View.INVISIBLE);
            tv = convertView.findViewById(R.id.nbIngr1);
            tv.setText("");

            ig = convertView.findViewById(R.id.ingredient2);
            ig.setImageResource(R.color.colorOrangeDark);
            ig.setVisibility(View.INVISIBLE);
            tv = convertView.findViewById(R.id.nbIngr2);
            tv.setText("");

            ig = convertView.findViewById(R.id.ingredient3);
            ig.setImageResource(R.color.colorOrangeDark);
            ig.setVisibility(View.INVISIBLE);
            tv = convertView.findViewById(R.id.nbIngr3);
            tv.setText("");

            ig = convertView.findViewById(R.id.ingredient4);
            ig.setImageResource(R.color.colorOrangeDark);
            ig.setVisibility(View.INVISIBLE);
            tv = convertView.findViewById(R.id.nbIngr4);
            tv.setText("");

        }else if(nbIngredients == 1){

            RecipeElement re = ingredients.get(0);
            ig = convertView.findViewById(R.id.ingredient1);
            ig.setImageResource(Resource.getImage(re.getIngredient()));
            ig.setVisibility(View.VISIBLE);
            tv = convertView.findViewById(R.id.nbIngr1);
            tv.setText(this.getContext().getString(R.string.strNbIngredientNeed, re.getNumber()));
            if(mapUserInventory.get(re.getIngredient()).getNumberStored() >= re.getNumber()){
                tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorGREEN));
            }else{
                tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorRed));
            }

            ig = convertView.findViewById(R.id.ingredient2);
            ig.setImageResource(R.color.colorOrangeDark);
            ig.setVisibility(View.INVISIBLE);
            tv = convertView.findViewById(R.id.nbIngr2);
            tv.setText("");

            ig = convertView.findViewById(R.id.ingredient3);
            ig.setImageResource(R.color.colorOrangeDark);
            ig.setVisibility(View.INVISIBLE);
            tv = convertView.findViewById(R.id.nbIngr3);
            tv.setText("");

            ig = convertView.findViewById(R.id.ingredient4);
            ig.setImageResource(R.color.colorOrangeDark);
            ig.setVisibility(View.INVISIBLE);
            tv = convertView.findViewById(R.id.nbIngr4);
            tv.setText("");


        }else if(nbIngredients == 2){

            RecipeElement re = ingredients.get(0);
            ig = convertView.findViewById(R.id.ingredient1);
            ig.setImageResource(Resource.getImage(re.getIngredient()));
            ig.setVisibility(View.VISIBLE);
            tv = convertView.findViewById(R.id.nbIngr1);
            tv.setText(this.getContext().getString(R.string.strNbIngredientNeed, re.getNumber()));
            if(mapUserInventory.get(re.getIngredient()).getNumberStored() >= re.getNumber()){
                tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorGREEN));
            }else{
                tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorRed));
            }

            re = ingredients.get(1);
            ig = convertView.findViewById(R.id.ingredient2);
            ig.setImageResource(Resource.getImage(re.getIngredient()));
            ig.setVisibility(View.VISIBLE);
            tv = convertView.findViewById(R.id.nbIngr2);
            tv.setText(this.getContext().getString(R.string.strNbIngredientNeed, re.getNumber()));
            if(mapUserInventory.get(re.getIngredient()).getNumberStored() >= re.getNumber()){
                tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorGREEN));
            }else{
                tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorRed));
            }

            ig = convertView.findViewById(R.id.ingredient3);
            ig.setImageResource(R.color.colorOrangeDark);
            ig.setVisibility(View.INVISIBLE);
            tv = convertView.findViewById(R.id.nbIngr3);
            tv.setText("");

            ig = convertView.findViewById(R.id.ingredient4);
            ig.setImageResource(R.color.colorOrangeDark);
            ig.setVisibility(View.INVISIBLE);
            tv = convertView.findViewById(R.id.nbIngr4);
            tv.setText("");

        }else if(nbIngredients == 3){

            RecipeElement re = ingredients.get(0);
            ig = convertView.findViewById(R.id.ingredient1);
            ig.setImageResource(Resource.getImage(re.getIngredient()));
            ig.setVisibility(View.VISIBLE);
            tv = convertView.findViewById(R.id.nbIngr1);
            tv.setText(this.getContext().getString(R.string.strNbIngredientNeed, re.getNumber()));
            if(mapUserInventory.get(re.getIngredient()).getNumberStored() >= re.getNumber()){
                tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorGREEN));
            }else{
                tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorRed));
            }

            re = ingredients.get(1);
            ig = convertView.findViewById(R.id.ingredient2);
            ig.setImageResource(Resource.getImage(re.getIngredient()));
            ig.setVisibility(View.VISIBLE);
            tv = convertView.findViewById(R.id.nbIngr2);
            tv.setText(this.getContext().getString(R.string.strNbIngredientNeed, re.getNumber()));
            if(mapUserInventory.get(re.getIngredient()).getNumberStored() >= re.getNumber()){
                tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorGREEN));
            }else{
                tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorRed));
            }

            re = ingredients.get(2);
            ig = convertView.findViewById(R.id.ingredient3);
            ig.setImageResource(Resource.getImage(re.getIngredient()));
            ig.setVisibility(View.VISIBLE);
            tv = convertView.findViewById(R.id.nbIngr3);
            tv.setText(this.getContext().getString(R.string.strNbIngredientNeed, re.getNumber()));
            if(mapUserInventory.get(re.getIngredient()).getNumberStored() >= re.getNumber()){
                tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorGREEN));
            }else{
                tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorRed));
            }

            ig = convertView.findViewById(R.id.ingredient4);
            ig.setImageResource(R.color.colorOrangeDark);
            ig.setVisibility(View.INVISIBLE);
            tv = convertView.findViewById(R.id.nbIngr4);
            tv.setText("");

        }else if(nbIngredients == 4){

            RecipeElement re = ingredients.get(0);
            ig = convertView.findViewById(R.id.ingredient1);
            ig.setImageResource(Resource.getImage(re.getIngredient()));
            ig.setVisibility(View.VISIBLE);
            tv = convertView.findViewById(R.id.nbIngr1);
            tv.setText(this.getContext().getString(R.string.strNbIngredientNeed, re.getNumber()));
            if(mapUserInventory.get(re.getIngredient()).getNumberStored() >= re.getNumber()){
                tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorGREEN));
            }else{
                tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorRed));
            }

            re = ingredients.get(1);
            ig = convertView.findViewById(R.id.ingredient2);
            ig.setImageResource(Resource.getImage(re.getIngredient()));
            ig.setVisibility(View.VISIBLE);
            tv = convertView.findViewById(R.id.nbIngr2);
            tv.setText(this.getContext().getString(R.string.strNbIngredientNeed, re.getNumber()));
            if(mapUserInventory.get(re.getIngredient()).getNumberStored() >= re.getNumber()){
                tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorGREEN));
            }else{
                tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorRed));
            }

            re = ingredients.get(2);
            ig = convertView.findViewById(R.id.ingredient3);
            ig.setImageResource(Resource.getImage(re.getIngredient()));
            ig.setVisibility(View.VISIBLE);
            tv = convertView.findViewById(R.id.nbIngr3);
            tv.setText(this.getContext().getString(R.string.strNbIngredientNeed, re.getNumber()));
            if(mapUserInventory.get(re.getIngredient()).getNumberStored() >= re.getNumber()){
                tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorGREEN));
            }else{
                tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorRed));
            }

            re = ingredients.get(3);
            ig = convertView.findViewById(R.id.ingredient4);
            ig.setImageResource(Resource.getImage(re.getIngredient()));
            ig.setVisibility(View.VISIBLE);
            tv = convertView.findViewById(R.id.nbIngr4);
            tv.setText(this.getContext().getString(R.string.strNbIngredientNeed, re.getNumber()));
            if(mapUserInventory.get(re.getIngredient()).getNumberStored() >= re.getNumber()){
                tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorGREEN));
            }else{
                tv.setTextColor(ContextCompat.getColor(this.getContext(), R.color.colorRed));
            }

        }

        TextView tvl =  convertView.findViewById(R.id.lblNumberOfResource);
        TextViewPrinter.printInventory(tvl, resource.getNumberStored(), resource.getMax());

        return convertView;

    }
}
