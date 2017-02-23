package pl.marcinkokoszka;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.marcinkokoszka.Items.*;

import java.util.ArrayList;
import java.util.Collections;

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
    public void nickel_addsOneNickel_whenInsertedCoinsEmpty(){
        vmk.nickel();
        assertEquals(1, (long)vmk.currentlyInsertedMoney().get(0.05));
    }

    @Test
    public void dime_addsOneDime_whenInsertedCoinsEmpty(){
        vmk.dime();
        assertEquals(1, (long)vmk.currentlyInsertedMoney().get(0.10));
    }

    @Test
    public void quarter_addsOneQuarter_whenInsertedCoinsEmpty(){
        vmk.quarter();
        assertEquals(1, (long)vmk.currentlyInsertedMoney().get(0.25));
    }

    @Test
    public void dollar_addsOneDollar_whenInsertedCoinsEmpty(){
        vmk.dollar();
        assertEquals(1, (long)vmk.currentlyInsertedMoney().get(1.0));
    }

    @Test
    public void coinReturn_ClearsInsertedCoins_whenOneOfEachInserted(){
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
    public void coinReturn_ReturnsCoins_whenOneDollarTwoQuartersFourDimesThreeNickelsAreInserted(){
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
    public void service_setsItemQuantities_whenItemsEmpty(){
        vmk.service(1, 2, 3, 0, 0, 0);
        assertEquals(1, vmk.availableItems().get(0).getQty());
        assertEquals(2, vmk.availableItems().get(1).getQty());
        assertEquals(3, vmk.availableItems().get(2).getQty());
    }

    @Test
    public void service_setsItemQuantities_whenItemsNotEmpty(){
        vmk.service(6, 7, 8, 9, 15, 3);
        vmk.service(1, 2, 3, 0, 0, 0);
        assertEquals(1, vmk.availableItems().get(0).getQty());
        assertEquals(2, vmk.availableItems().get(1).getQty());
        assertEquals(3, vmk.availableItems().get(2).getQty());
    }

    @Test
    public void service_setsChange_whenChangeEmpty(){
        vmk.service(0, 0, 0, 10, 20, 40);
        assertEquals(10, (int)vmk.availableChange().get(0.05));
        assertEquals(20, (int)vmk.availableChange().get(0.10));
        assertEquals(40, (int)vmk.availableChange().get(0.25));
    }

    @Test
    public void service_setsChange_whenChangeNotEmpty(){
        vmk.service(0, 0, 0, 8, 7, 15);
        vmk.service(0, 0, 0, 10, 20, 40);
        assertEquals(10, (int)vmk.availableChange().get(0.05));
        assertEquals(20, (int)vmk.availableChange().get(0.10));
        assertEquals(40, (int)vmk.availableChange().get(0.25));
    }

    @Test
    public void getA_ReturnsItemA_whenEnoughCoinsInsertedAndChangeAvailable(){
        vmk.service(1, 0, 0, 10, 10, 10);
        vmk.dollar();
        vmk.dollar();
        VMOutput out = vmk.getA();
        assertNotNull(out.item);
        assertTrue(out.item instanceof A);
    }

    @Test
    public void getA_ReturnsNull_whenNotEnoughCoinsInserted(){
        vmk.service(1, 0, 0, 10, 10, 10);
        vmk.quarter();
        VMOutput out = vmk.getA();
        assertNull(out.item);
    }

    @Test
    public void getA_ReturnsNull_whenNotEnoughChange(){
        vmk.service(1, 0, 0, 0, 0, 0);
        vmk.quarter();
        vmk.dollar();
        VMOutput out = vmk.getA();
        assertNull(out.item);
    }

    @Test
    public void getA_ReturnsNoChange_whenNotEnoughChange() {
        vmk.service(1, 0, 0, 1, 0, 0);
        vmk.dollar();
        vmk.dollar();
        VMOutput out = vmk.getA();
        assertEquals(0, out.change.size());
    }

}
