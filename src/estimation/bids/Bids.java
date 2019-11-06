package estimation.bids;
import java.util.ArrayList;
/**
 * Bids
 */
public class Bids {
    private ArrayList<Integer> possibleBids = new ArrayList<Integer>();

    public Bids(int currentCardCount) {
        constructPossibleBids(currentCardCount);
    }

    // If its the last person, you need to take into account the current bids placed
    public Bids(int currentCardCount, ArrayList<Integer> roundBidsPlaced) {
        constructPossibleBids(currentCardCount, roundBidsPlaced);
    }

    /**
     * @return the possibleBids
     */
    public ArrayList<Integer> getPossibleBids() {
        return this.possibleBids;
    }

    private void constructPossibleBids(int currentCardCount) {
        for (int i = 0; i < currentCardCount + 1; i++ ) {
            this.possibleBids.add(i);
        }
    }

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