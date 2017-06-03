package se.farm;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by BuiAnhVu on 4/26/2017.
 */

public class Animal {
    private int sex;
    private int id;
    private int animal_id;
    private int heath_index;
    private String source;
    private String date;
    private String account;
    private int weight;
    private boolean selected = false;
    boolean getchecked(){
        return selected;
    }
    public void reverse_checked(){
        this.selected = !this.selected;
    }
    public Animal (int id, int sex, int animal_id, int heath_index, int weight, String source, String date, String account ){
        this.setId(id).setSex(sex).setAnimal_id(animal_id).setHeath_index(heath_index).setWeight(weight).setSource(source).setDate(date).setAccount(account);
    }

    public String getAccount() {
        return account;
    }

    public Animal setAccount(String account) {
        this.account = account;
        return this;
    }


    public int getSex() {
        return sex;
    }

    public Animal setSex(int sex) {
        this.sex = sex;
        return this;
    }


    public int getId() {
        return id;
    }

    public Animal setId(int id) {
        this.id = id; return this;
    }
    public int getAnimal_id() {
        return animal_id;
    }

    public Animal setAnimal_id(int animal_id) {
        this.animal_id = animal_id; return this;
    }
    public int getHeath_index() {
        return heath_index;
    }

    public Animal setHeath_index(int heath_index) {
        this.heath_index = heath_index; return this;
    }

    public String getSource() {
        return source;
    }

    public Animal setSource(String source) {
        this.source = source; return this;
    }

    public String getDate() {
        return date;
    }

    public Animal setDate(String date_time) {
        this.date = date_time; return this;
    }

    public int getWeight() {
        return weight;
    }

    public Animal setWeight(int weight) {
        this.weight = weight; return this;
    }

    public String toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Var.KEY_SEX, getSex());
            jsonObject.put(Var.KEY_HEALTH_INDEX, getHeath_index());
            jsonObject.put(Var.KEY_SOURCE, getSource());
            jsonObject.put(Var.KEY_WEIGHT, getWeight());
            jsonObject.put(Var.KEY_ANIMAL_ID, getAnimal_id());
            jsonObject.put(Var.KEY_ACCOUNT, getAccount());
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
