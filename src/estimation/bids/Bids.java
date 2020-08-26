package estimation.bids;
import java.util.ArrayList;

/**
 * This class handles the bid logic for the application
 */

public class Bids {
    private ArrayList<Integer> possibleBids = new ArrayList<Integer>();

    /**
     *
     * @param currentCardCount the distributed card counts. For e.g, if everyone has 2 cards, the currentCardcount is 2
     */
    public Bids(int currentCardCount) {
        constructPossibleBids(currentCardCount);
    }

    /**
     *
     * @param currentCardCount the distributed card counts. For e.g, if everyone has 2 cards, the currentCardcount is 2
     * @param roundBidsPlaced the list of the bids placed per round. E.g For an ArrayList of [0, 1, 2], someone bidded 0, someone bidded 1, someone bidded 2
     */
    public Bids(int currentCardCount, ArrayList<Integer> roundBidsPlaced) {
        constructPossibleBids(currentCardCount, roundBidsPlaced);
    }

    /**
     * Get possible bids.
     * @return an ArrayList of the the possibleBids one can place
     */
    public ArrayList<Integer> getPossibleBids() {
        return this.possibleBids;
    }

    /**
     * This is an overloaded method to get the possible bids. It will get called when player is not the dealer.
     * @param currentCardCount the distributed card counts. For e.g, if everyone has 2 cards, the currentCardcount is 2
     */
    private void constructPossibleBids(int currentCardCount) {
        for (int i = 0; i < currentCardCount + 1; i++ ) {
            this.possibleBids.add(i);
        }
    }

    /**
     *  Gets the current possible bids for each player. If he is a dealer, he cannot place certain bids.
     * @param currentCardCount the distributed card counts. For e.g, if everyone has 2 cards, the currentCardcount is 2
     * @param roundBidsPlaced the list of the bids placed per round. E.g For an ArrayList of [0, 1, 2], someone bidded 0, someone bidded 1, someone bidded 2
     */
    private void constructPossibleBids(int currentCardCount, ArrayList<Integer> roundBidsPlaced) {
        int sum = 0;
        for (int bid : roundBidsPlaced) {
            sum += bid;
        }

        // bid that player cannot place
        int restrictedBid = currentCardCount - sum;
        constructPossibleBids(currentCardCount);

        // if the restrictedBid does not exist in possible bids, do not remove it 
        if (possibleBids.indexOf(restrictedBid) != -1) {
            this.possibleBids.remove(possibleBids.indexOf(restrictedBid));
        }
    }

}