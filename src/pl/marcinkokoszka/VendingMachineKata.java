package pl.marcinkokoszka;

import pl.marcinkokoszka.Items.*;

import java.util.*;

/**
 * Created by kokoseq on 15.02.2017.
 */
public class VendingMachineKata {
    private VendingMachine vm;

    public VendingMachineKata(){
        vm = new VendingMachine(new A(0.65, 0), new B(1.00, 0), new C(1.50, 0));
    }

    public void nickel(){
        vm.insertNickel();
    }

    public void dime(){
        vm.insertDime();
    }

    public void quarter(){
        vm.insertQuarter();
    }

    public void dollar(){
        vm.insertDollar();
    }

    public ArrayList<Double> coinReturn(){
        return vm.returnChange();
    }

    public VMOutput getA(){
        return vm.getItem(0);
    }

    public VMOutput getB(){
        return vm.getItem(1);
    }

    public VMOutput getC(){
        return vm.getItem(2);
    }

    public void service(int qtyA, int qtyB, int qtyC, int nickelQty, int dimeQty, int quarterQty){
        vm.setItem(0, qtyA);
        vm.setItem(1, qtyB);
        vm.setItem(2, qtyC);

        vm.setNickels(nickelQty);
        vm.setDimes(dimeQty);
        vm.setQuarters(quarterQty);
    }

    public List<VMItem> availableItems(){
        return vm.getItems();
    }

    public Map<Double, Integer> availableChange(){
        return vm.getChange();
    }

    public Map<Double, Integer> currentlyInsertedMoney(){
        return vm.getInsertedChange();
    }

}
