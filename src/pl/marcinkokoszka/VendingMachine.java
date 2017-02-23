package pl.marcinkokoszka;
import pl.marcinkokoszka.Items.*;

import java.util.*;

public class VendingMachine
{
    private ArrayList<VMItem> items;
    private HashMap<Double, Integer> change;
    private HashMap<Double, Integer> insertedChange;


    public VendingMachine(VMItem... itemsToPutIntoVM){
        items = new ArrayList<>();
        for (VMItem i: itemsToPutIntoVM) items.add(i);

        change = new HashMap<>();
        change.put(0.05, 0);
        change.put(0.10, 0);
        change.put(0.25, 0);

        insertedChange = new HashMap<>();
        insertedChange.put(0.05, 0);
        insertedChange.put(0.10, 0);
        insertedChange.put(0.25, 0);
        insertedChange.put(1.00, 0);
    }

    public Map<Double, Integer> getChange(){
        return Collections.unmodifiableMap(change);
    }

    public Map<Double, Integer> getInsertedChange(){
        return Collections.unmodifiableMap(insertedChange);
    }

    public Map<VMItem, Integer> getItemsWithQuantities(){
        Map<VMItem, Integer> itemsMap = new HashMap<>();
        for (VMItem i: items) itemsMap.put(i, i.getQty());
        return Collections.unmodifiableMap(itemsMap);
    }

    public List<VMItem> getItems(){
        return Collections.unmodifiableList(items);
    }

    public void insertNickel(){
        insertedChange.put(0.05, insertedChange.get(0.05) + 1);
    }

    public void insertDime(){
        insertedChange.put(0.10, insertedChange.get(0.10) + 1);
    }

    public void insertQuarter(){
        insertedChange.put(0.25, insertedChange.get(0.25) + 1);
    }

    public void insertDollar(){
        insertedChange.put(1.0, insertedChange.get(1.0) + 1);
    }

    public ArrayList<Double> returnChange(){
        ArrayList<Double> coinsToReturn = new ArrayList<>();
        for (int i = 0; i < insertedChange.get(0.05); i++) coinsToReturn.add(0.05);
        for (int i = 0; i < insertedChange.get(0.10); i++) coinsToReturn.add(0.10);
        for (int i = 0; i < insertedChange.get(0.25); i++) coinsToReturn.add(0.25);
        for (int i = 0; i < insertedChange.get(1.0); i++) coinsToReturn.add(1.0);
        insertedChangeRemoveAll();
        return coinsToReturn;
    }

    private void insertedChangeRemoveAll(){
        insertedChange = new HashMap<>();
        insertedChange.put(0.05, 0);
        insertedChange.put(0.10, 0);
        insertedChange.put(0.25, 0);
        insertedChange.put(1.00, 0);
    }

    public void setNickels(int nickelQty){
        change.put(0.05, nickelQty);
    }

    public void setDimes(int dimeQty){
        change.put(0.10, dimeQty);
    }

    public void setQuarters(int quarterQty){
        change.put(0.25, quarterQty);
    }

    public void setItem(int position, int qty){
        items.get(position).setQty(qty);
    }

    private void decreaseItemQty(int position){
        items.get(position).setQty(items.get(position).getQty() - 1);
    }

    public VMOutput getItem(int position){
        if (isItemAvailable(position) && isEnoughInserted(position)) {
            ArrayList<Double> change = giveChange(position);
            if (change == null) return new VMOutput();
            VMOutput vmo = new VMOutput(items.get(position).newSingleInstance());
            vmo.setChange(change);
            decreaseItemQty(position);
            return vmo;
        }
        return new VMOutput();
    }

    private ArrayList<Double> giveChange(int position){
        ArrayList<Double> coinsToReturn;
        coinsToReturn = new ArrayList<>();
        double totalSumToReturn = insertedChangeSum() - items.get(position).getPrice();
        double sumToReturn = totalSumToReturn;

        while(0.25 <= sumToReturn && isCoinAvailable(0.25)){
            decreaseChangeQty(0.25);
            coinsToReturn.add(0.25);
            sumToReturn -= 0.25;
            sumToReturn = Math.floor(sumToReturn * 1000.0 + 0.5) / 1000.0;
        }

        while(0.1 <= sumToReturn && isCoinAvailable(0.1)){
            decreaseChangeQty(0.1);
            coinsToReturn.add(0.1);
            sumToReturn -= 0.1;
            sumToReturn = Math.floor(sumToReturn * 1000.0 + 0.5) / 1000.0;
        }

        while(0.05 <= sumToReturn && isCoinAvailable(0.05)){
            decreaseChangeQty(0.05);
            coinsToReturn.add(0.05);
            sumToReturn -= 0.05;
            sumToReturn = Math.floor(sumToReturn * 1000.0 + 0.5) / 1000.0;
        }
        if (Math.abs(doubleArrayListSum(coinsToReturn) - totalSumToReturn) >= 0.01) return null;
        moveInsertedCoinsToChange();
        return coinsToReturn;
    }

    private void moveInsertedCoinsToChange(){
        change.put(0.05, change.get(0.05) + insertedChange.get(0.05));
        change.put(0.10, change.get(0.10) + insertedChange.get(0.10));
        change.put(0.25, change.get(0.25) + insertedChange.get(0.25));
        insertedChangeRemoveAll();
    }

    private void decreaseChangeQty(double value){
        change.put(value, change.get(value) - 1);
    }

    private boolean isCoinAvailable(double value){
        return change.get(value) > 0;
    }

    private boolean isItemAvailable(int position){
        return items.get(position).getQty() >= 1;
    }

    private boolean isEnoughInserted(int position){
        return insertedChangeSum() >= items.get(position).getPrice();
    }

    private double insertedChangeSum() {
        Iterator it = insertedChange.entrySet().iterator();
        double sum = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            sum += (double) pair.getKey() * (Integer) pair.getValue();
            sum = Math.floor(sum * 1000.0 + 0.5) / 1000.0;
            //it.remove(); // avoids a ConcurrentModificationException
        }
        return sum;
    }

    private double doubleArrayListSum(ArrayList<Double> al){
        double sum = 0;
        for(Double d : al)
            sum += d;
        return sum;
    }
}