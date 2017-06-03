package se.farm;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by BuiAnhVu on 4/20/2017.
 */

public class MedicalListAdapter extends BaseAdapter{
    Context context;
    ArrayList<Animal> arr;
    public static JSONArray jsonArray = new JSONArray();
    public static Array array_id;
    //    public static Array array;
    private View rowView;
    public MedicalListAdapter(Context context, ArrayList<Animal> arr){
        this.context = context;
        this.arr = arr;
    }
    @Override
    public int getCount() {
        if(arr != null)
            return arr.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return arr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        rowView = inflater.inflate(R.layout.item_pig2,parent, false);
        ImageView imagePet = (ImageView) rowView.findViewById(R.id.imageView);
        TextView pig_weight = (TextView) rowView.findViewById(R.id.row_animal_weight);
        TextView pig_id = (TextView) rowView.findViewById(R.id.row_tv_animalid);
        TextView pig_stt = (TextView) rowView.findViewById(R.id.row_tv_animalstatus);
        TextView pig_heathindex = (TextView) rowView.findViewById(R.id.row_animal_health_index);
        TextView pig_sex = (TextView) rowView.findViewById(R.id.row_animal_sex);
        TextView pig_source = (TextView) rowView.findViewById(R.id.row_animal_source);
//        RelativeLayout pig_item = (RelativeLayout) rowView.findViewById(R.id.item_pig);
        Animal animal_item = arr.get(position);
        if (animal_item.getAnimal_id() == 1)
            imagePet.setImageResource(R.drawable.pig_icon);
        else if (animal_item.getAnimal_id() == 2)
            imagePet.setImageResource(R.drawable.buffalo_icon);
        else if (animal_item.getAnimal_id() == 3)
            imagePet.setImageResource(R.drawable.cow_icon);
        else
            imagePet.setImageResource(R.drawable.chicken_icon);
        if(animal_item.getHeath_index() < 40) {
            pig_stt.setText("Status: Weak");
        }
        else if(animal_item.getHeath_index() < 80)
            pig_stt.setText("Status: Normal");
        else
            pig_stt.setText("Status: Good");

        pig_id.setText("ID_No: "+animal_item.getId());
        if(animal_item.getSex() == 1){
            pig_sex.setText("Sex: Male");
        }else{
            pig_sex.setText("Sex: Female");
        }
//        pig_cost.setText("Cost: 400 $");
        pig_heathindex.setText("Health: " + animal_item.getHeath_index());
        pig_weight.setText("Weight: " + animal_item.getWeight());
        pig_source.setText("Source: " + animal_item.getSource());
        return rowView;
    }
}
