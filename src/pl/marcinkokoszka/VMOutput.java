package pl.marcinkokoszka;

import pl.marcinkokoszka.Items.VMItem;

import java.util.ArrayList;

/**
 * Created by kokoseq on 08.02.2017.
 */
class VMOutput {
    VMItem item;
    ArrayList<Double> change;

    public VMOutput(VMItem item){
        this.item = item;
        change = new ArrayList<Double>();
    }

    public VMOutput(){
        this.item = null;
        change = new ArrayList<Double>();
    }

    public void setChange(ArrayList<Double> change){
        this.change = change;
    }

    public String toString(){
        if (change.size() > 0) return item.toString() + ", coins: " + change;
        if (item == null) return "coins: " + change;
        return item.toString();
    }
}