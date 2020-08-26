package estimation.test;

import estimation.bids.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * This test covers the bid class
 */

public class BidsTest {
    @Test
    /*
     * Test if the possible bid count with dealer is correct
     */
    public void possibleBidsWithDealerTest1() {
        ArrayList<Integer> roundBidsPlaced = new ArrayList<>(List.of(0, 0, 0));
        Bids bids = new Bids(1, roundBidsPlaced);
        assertEquals(bids.getPossibleBids(), new ArrayList<>(List.of(0)));
    }

    @Test
    /*
     * Test if the possible bid count with dealer is correct
     */
    public void possibleBidsWithDealerTest2() {
        ArrayList<Integer> roundBidsPlaced = new ArrayList<>(List.of(0, 1, 0));
        Bids bids = new Bids(2, roundBidsPlaced);
        assertEquals(bids.getPossibleBids(), new ArrayList<>(List.of(0, 2)));
    }

    @Test
    /*
     * Test if the possible bid count with dealer is correct
     */
    public void possibleBidsWithDealerTest3() {
        ArrayList<Integer> roundBidsPlaced = new ArrayList<>(List.of(0, 2, 0));
        Bids bids = new Bids(4, roundBidsPlaced);
        assertEquals(bids.getPossibleBids(), new ArrayList<>(List.of(0, 1, 3, 4)));
    }

    @Test
    /*
     * Test if the possible bid count with dealer is correct
     */
    public void possibleBidsWithDealerTest4() {
        ArrayList<Integer> roundBidsPlaced = new ArrayList<>(List.of(1, 2, 3));
        Bids bids = new Bids(6, roundBidsPlaced);
        assertEquals(bids.getPossibleBids(), new ArrayList<>(List.of(1, 2, 3, 4, 5, 6)));
    }

    @Test
    /*
     * Test if the possible bid count without dealer is correct
     */
    public void possibleBidsWithoutDealerTest1() {
        Bids bids = new Bids(6);
        assertEquals(bids.getPossibleBids(), new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6)));
    }

    @Test
    /*
     * Test if the possible bid count without dealer is correct
     */
    public void possibleBidsWithoutDealerTest2() {
        Bids bids = new Bids(6);
        assertEquals(bids.getPossibleBids(), new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6)));
    }

    @Test
    /*
     * Test if the possible bid count without dealer is correct
     */
    public void possibleBidsWithoutDealerTest3() {
        Bids bids = new Bids(3);
        assertEquals(bids.getPossibleBids(), new ArrayList<>(List.of(0, 1, 2, 3)));
    }

    @Test
    /*
     * Test if the possible bid count without dealer is correct
     */
    public void possibleBidsWithoutDealerTest4() {
        Bids bids = new Bids(1);
        assertEquals(bids.getPossibleBids(), new ArrayList<>(List.of(0, 1)));
    }
}