package se.farm;

/**
 * Created by BuiAnhVu on 5/26/2017.
 */

public class Transaction {
    private int Transaction_ID;
    private String Type;
    private String Action;
    private Double Money;
    private String Trans_Date;
    public Transaction(int id, String type, String action, Double money, String trans_Date){
        this.setAction(action);
        this.setTransaction_ID(id);
        this.setType(type);
        this.setMoney(money);
        this.setTrans_Date(trans_Date);
    }

    public int getTransaction_ID() {
        return Transaction_ID;
    }

    public void setTransaction_ID(int transaction_ID) {
        Transaction_ID = transaction_ID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public Double getMoney() {
        return Money;
    }

    public void setMoney(Double money) {
        Money = money;
    }

    public String getTrans_Date() {
        return Trans_Date;
    }

    public void setTrans_Date(String trans_Date) {
        Trans_Date = trans_Date;
    }


}
