package pl.marcinkokoszka;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.marcinkokoszka.Items.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by kokoseq on 15.02.2017.
 */
public class VendingMachineKataTest {
    private VendingMachineKata vmk;

    @Before
    public void setUp() {
        vmk = new VendingMachineKata();
    }

    @After
    public void tearDown(){
        vmk = null;
    }

    @Test
    public void nickel_InsertedCorrectly(){
        vmk.nickel();
        assertEquals(1, (long)vmk.currentlyInsertedMoney().get(0.05));
    }

    @Test
    public void dime_InsertedCorrectly(){
        vmk.dime();
        assertEquals(1, (long)vmk.currentlyInsertedMoney().get(0.10));
    }

    @Test
    public void quarter_InsertedCorrectly(){
        vmk.quarter();
        assertEquals(1, (long)vmk.currentlyInsertedMoney().get(0.25));
    }

    @Test
    public void dollar_InsertedCorrectly(){
        vmk.dollar();
        assertEquals(1, (long)vmk.currentlyInsertedMoney().get(1.0));
    }

    @Test
    public void coinReturn_ClearsInsertedCoins_WhenOneOfEachInserted(){
        vmk.dollar();
        vmk.quarter();
        vmk.dime();
        vmk.nickel();

        vmk.coinReturn();
        assertEquals(0, (long)vmk.currentlyInsertedMoney().get(1.0));
        assertEquals(0, (long)vmk.currentlyInsertedMoney().get(0.25));
        assertEquals(0, (long)vmk.currentlyInsertedMoney().get(0.1));
        assertEquals(0, (long)vmk.currentlyInsertedMoney().get(0.05));
    }

    @Test
    public void coinReturn_ReturnsCoinsCorrectly_WhenOneDollarTwoQuartersFourDimesThreeNickelsAreInserted(){
        vmk.dollar();
        vmk.quarter();
        vmk.quarter();
        vmk.dime();
        vmk.dime();
        vmk.dime();
        vmk.dime();
        vmk.nickel();
        vmk.nickel();
        vmk.nickel();

        ArrayList<Double> returnedCoins = vmk.coinReturn();
        assertEquals(3, Collections.frequency(returnedCoins, 0.05));
        assertEquals(4, Collections.frequency(returnedCoins, 0.10));
        assertEquals(2, Collections.frequency(returnedCoins, 0.25));
        assertEquals(1, Collections.frequency(returnedCoins, 1.0));
    }

    @Test
    public void service_setsItemQuantitiesCorrectly(){
        vmk.service(1, 2, 3, 0, 0, 0);
        assertEquals(1, vmk.availableItems().get(0).getQty());
        assertEquals(2, vmk.availableItems().get(1).getQty());
        assertEquals(3, vmk.availableItems().get(2).getQty());
    }

    @Test
    public void service_setsChangeCorrectly(){
        vmk.service(0, 0, 0, 10, 20, 40);
        assertEquals(10, (int)vmk.availableChange().get(0.05));
        assertEquals(20, (int)vmk.availableChange().get(0.10));
        assertEquals(40, (int)vmk.availableChange().get(0.25));
    }

    @Test
    public void getA_ReturnsItemA_WhenEnoughCoinsInsertedAndChangeAvailable(){
        vmk.service(1, 0, 0, 10, 10, 10);
        vmk.dollar();
        vmk.dollar();
        VMOutput out = vmk.getA();
        assertNotNull(out.item);
        assertTrue(out.item instanceof A);
    }

    @Test
    public void getA_ReturnsNull_WhenNotEnoughCoinsInserted(){
        vmk.service(1, 0, 0, 10, 10, 10);
        vmk.quarter();
        VMOutput out = vmk.getA();
        assertNull(out.item);
    }

    @Test
    public void getA_ReturnsNull_WhenNotEnoughChange(){
        vmk.service(1, 0, 0, 0, 0, 0);
        vmk.quarter();
        vmk.dollar();
        VMOutput out = vmk.getA();
        assertNull(out.item);
    }

    @Test
    public void getA_ReturnsNoChange_WhenNotEnoughChange() {
        vmk.service(1, 0, 0, 1, 1, 1);
        vmk.dollar();
        vmk.dollar();
        VMOutput out = vmk.getA();
        assertEquals(0, out.change.size());
    }

}
