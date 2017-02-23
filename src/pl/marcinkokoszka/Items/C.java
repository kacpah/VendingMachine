package pl.marcinkokoszka.Items;

/**
 * Created by kokoseq on 29.01.2017.
 */
public class C implements VMItem {
    private double price;
    private int qty;

    public C(double price, int qty){
        this.price = price;
        this.qty = qty;
    }

    public double getPrice(){
        return price;
    }
    public int getQty(){
        return qty;
    }
    public void setQty(int qty){
        this.qty = qty;
    }
    public C newSingleInstance(){
        return new C(this.price, 1);
    }
    public String toString(){
        return "C";
    }
}