package pl.marcinkokoszka;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.marcinkokoszka.Items.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by kokoseq on 08.02.2017.
 */
public class VendingMachineTest {
    VendingMachine vm;

    @Before
    public void setUp() {
        vm = new VendingMachine(new A(0.65, 0), new B(1.00, 0), new C(1.50, 0));
    }

    @After
    public void tearDown(){
        vm = null;
    }

    @Test
    public void setNickelsCorrectly(){
        vm.setNickels(15);
        assertEquals(15, (long) vm.getChange().get(0.05));
    }

    @Test
    public void setDimesCorrectly(){
        vm.setDimes(2);
        assertEquals(2, (long) vm.getChange().get(0.10));
    }

    @Test
    public void setQuartersCorrectly(){
        vm.setQuarters(34);
        assertEquals(34, (long) vm.getChange().get(0.25));
    }

    @Test
    public void SetItem_OnPosition0Correctly(){
        vm.setItem(0, 5);
        assertEquals(5, (long) vm.getItems().get(0).getQty());
    }

    @Test
    public void SetItem_OnPosition1Correctly(){
        vm.setItem(1, 7);
        assertEquals(7, (long) vm.getItems().get(1).getQty());
    }

    @Test
    public void vendingMachine_HasCorrectStateAfterInstantiation(){
        assertEquals(0, (long) vm.getChange().get(0.05));
        assertEquals(0, (long) vm.getChange().get(0.10));
        assertEquals(0, (long) vm.getChange().get(0.25));
        assertEquals(0, (long) vm.getItems().get(0).getQty());
        assertEquals(0, (long) vm.getItems().get(1).getQty());
        assertEquals(0, (long) vm.getItems().get(2).getQty());
    }

    @Test
    public void returnChange_ClearsInsertedChangeCorrectly(){
        vm.returnChange();
        assertEquals(0, (long) vm.getInsertedChange().get(0.05));
        assertEquals(0, (long) vm.getInsertedChange().get(0.10));
        assertEquals(0, (long) vm.getInsertedChange().get(0.25));
        assertEquals(0, (long) vm.getInsertedChange().get(0.25));
    }

    @Test
    public void insertNickelWorks(){
        vm.returnChange();
        vm.insertNickel();
        assertEquals(1, (long) vm.getInsertedChange().get(0.05));
    }

    @Test
    public void insertDimeWorks(){
        vm.returnChange();
        vm.insertDime();
        assertEquals(1, (long) vm.getInsertedChange().get(0.10));
    }

    @Test
    public void insertQuarterWorks(){
        vm.returnChange();
        vm.insertQuarter();
        assertEquals(1, (long) vm.getInsertedChange().get(0.25));
    }

    @Test
    public void insertDollarWorks(){
        vm.returnChange();
        vm.insertDollar();
        assertEquals(1, (long) vm.getInsertedChange().get(1.0));
    }




}
