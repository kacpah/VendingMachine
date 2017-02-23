package pl.marcinkokoszka.Items;

/**
 * Created by kokoseq on 28.01.2017.
 */
public interface VMItem {
    double getPrice();
    int getQty();
    void setQty(int qty);
    VMItem newSingleInstance();
}