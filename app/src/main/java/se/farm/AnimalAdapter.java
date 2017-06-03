package se.farm;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by BuiAnhVu on 4/20/2017.
 */

public class AnimalAdapter extends BaseAdapter implements View.OnClickListener {
    Context context;
    ArrayList<Animal> arr;
    public static JSONArray jsonArray = new JSONArray();
    public static Array array_id;
//    public static Array array;
    private View rowView;
    private CheckBox checkBox;
    public AnimalAdapter(Context context, ArrayList<Animal> arr){
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
        rowView = inflater.inflate(R.layout.item_pig,parent, false);
        ImageView imagePet = (ImageView) rowView.findViewById(R.id.imageView);
        TextView pig_weight = (TextView) rowView.findViewById(R.id.row_animal_weight);
        TextView pig_id = (TextView) rowView.findViewById(R.id.row_tv_animalid);
        TextView pig_stt = (TextView) rowView.findViewById(R.id.row_tv_animalstatus);
        TextView pig_heathindex = (TextView) rowView.findViewById(R.id.row_animal_health_index);
        TextView pig_sex = (TextView) rowView.findViewById(R.id.row_animal_sex);
        checkBox = (CheckBox) rowView.findViewById(R.id.checkBox);
//        TextView pig_cost = (TextView) rowView.findViewById(R.id.row_animal_);
        TextView pig_source = (TextView) rowView.findViewById(R.id.row_animal_source);
        if (AnimalActivity.Animal_ID == Var.PIG_ID)
            imagePet.setImageResource(R.drawable.pig_icon);
        else if (AnimalActivity.Animal_ID == Var.BUFFALO_ID)
            imagePet.setImageResource(R.drawable.buffalo_icon);
        else if (AnimalActivity.Animal_ID == Var.COW_ID)
            imagePet.setImageResource(R.drawable.cow_icon);
        else if (AnimalActivity.Animal_ID == Var.CHICKEN_ID)
            imagePet.setImageResource(R.drawable.chicken_icon);
//        RelativeLayout pig_item = (RelativeLayout) rowView.findViewById(R.id.item_pig);
        Animal animal_item = arr.get(position);
        if(arr.get(position).getchecked()){
            checkBox.setChecked(true);
        }
        else
            checkBox.setChecked(false);
        checkBox.setOnClickListener(this);
        checkBox.setTag(position);

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.checkBox) {
            int position = (int) v.getTag();
            System.out.println("IDDD" + position);
            // now check turn to checked
            if (arr.get(position).getchecked() == false) {
                System.out.println("checked");
                arr.get(position).reverse_checked();
//                array.
                jsonArray.put(arr.get(position).getId());
            }
            // means unchecked
            else {
                System.out.println("unchecked");
                arr.get(position).reverse_checked();
                remove_id_from_array(arr.get(position).getId());
            }
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void remove_id_from_array(int id){

        for(int i = 0; i < jsonArray.length(); i++ ){
            try {
                if(id == jsonArray.getInt(i)){
                    jsonArray.remove(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void flush_array(){
        for(int i = 0; i<jsonArray.length(); i++){
            jsonArray.remove(i);
        }
    }
}
