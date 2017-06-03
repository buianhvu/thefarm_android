package se.farm;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by BuiAnhVu on 4/20/2017.
 */

public class TransactionAdapter extends BaseAdapter implements View.OnClickListener {
    Context context;
    ArrayList<Transaction> arr;
    public static JSONArray jsonArray = new JSONArray();
    //    public static Array array;
    private View rowView;
    public TransactionAdapter(Context context, ArrayList<Transaction> arr){
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
        rowView = inflater.inflate(R.layout.transaction_row,parent, false);
        TextView tvID = (TextView) rowView.findViewById(R.id.tvID);
        TextView tvAction = (TextView) rowView.findViewById(R.id.tvAction);
        TextView tvType = (TextView) rowView.findViewById(R.id.tvType);
        TextView tvDate = (TextView) rowView.findViewById(R.id.tvDate);
        TextView tvMoney = (TextView) rowView.findViewById(R.id.tvMoney);

        Transaction transaction_item = arr.get(position);
        tvID.setText("ID: "+transaction_item.getTransaction_ID());
        tvType.setText("Type: "+transaction_item.getType());
        tvDate.setText("Date: "+transaction_item.getTrans_Date());
        tvAction.setText("Action: "+transaction_item.getAction());
        tvMoney.setText("Money:  "+transaction_item.getMoney()+"$");
        return rowView;
    }

    @Override
    public void onClick(View v) {

    }
}
