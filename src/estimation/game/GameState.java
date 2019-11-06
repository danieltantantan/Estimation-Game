/**
 * GameState
 */
package estimation.game;
import estimation.player.*;
import estimation.card.*;
import estimation.bids.*;
import java.util.*;
public class GameState {
    // Player id is the key, Player is the value
    private Map<String, Player> currentState;
    private int rounds;
    private static int maxRounds = 12;
    private String lastDealer; // the ID of the dealer of the previous round
    // we assume playerSequence will always be Player --> Computer_1 --> Computer_2 --> Computer_3
    // the sequence of dealer/starter will be decided in the methods getNextDealer and getNextStarter
    private static ArrayList<String> playerSequence = new ArrayList<>(List.of("Player", "Computer_1", "Computer_2", "Computer_3")); 
    private Card trumpCard;
    private ArrayList<Integer> roundBidsPlaced = new ArrayList<>();
    private ArrayList<Card> pile;
    // number of cards to distirubte pertaining to each round. This is set in stone
    private static ArrayList<Integer> numCardsToDistribute = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 5, 4, 3, 2, 1));

    public GameState() {
        this.rounds = 1;
        this.currentState = generateState();
    }

    public void addToRoundBids(int bid) {
        this.roundBidsPlaced.add(bid);
    }

    public int getDistributeCount() {
        return numCardsToDistribute.get(rounds - 1);
    }


    public ArrayList<Integer> getPossibleBids(boolean isDealer) {
        // if is Dealer, take into account previous bids due to rule restrictions that
        // the sum of bids cannot be equals to the number of cards on hand
        Bids possibleBidsPerRound;
        if (isDealer) {
            possibleBidsPerRound = new Bids(getDistributeCount(), this.roundBidsPlaced);
        } else {
            possibleBidsPerRound = new Bids(getDistributeCount());
        }

        return possibleBidsPerRound.getPossibleBids();        
    }

    /**
     * @return the currentState
     */
    public Map<String, Player> getCurrentState() {
        return currentState;
    }

    /**
     * @return the rounds
     */
    public int getRounds() {
        return rounds;
    }

    public void addRound() {
        // add test case for when it exceeds max rounds
        if (this.rounds + 1 >= maxRounds) {
            throw new MaxRoundsException("Max Rounds Reached!");
        }

        this.rounds++;
    }

    // TODO: Limit number of cards distributed per round 
    public Deck distributeCards(Deck deck) {
        for (String playerId : currentState.keySet()) {
            for (int i = 0; i < getDistributeCount(); i++) {
                Card c = deck.dealCard();
                currentState.get(playerId).addToHand(c);
            }
        }
        return deck;
    }

    public ArrayList<String> getMaxScore() {
        boolean firstLoop = true;
        int maxScore = 0;
        Player maxScorePlayer = null;
        for (String playerId: currentState.keySet()) {
            Player p = currentState.get(playerId);

            if (firstLoop) {
                maxScore = p.getPoints();
                firstLoop = false;
                maxScorePlayer = p;
            } else if (p.getPoints() > maxScore) {
                maxScore = p.getPoints();
                maxScorePlayer = p;
            }
        }

        var maxDetails = new ArrayList<String>();
        maxDetails.add(maxScorePlayer.getId());
        maxDetails.add(Integer.toString(maxScore));

        return maxDetails;

    }

    public Map<String, Player> generateState() {
        Player player;
        Map<String, Player> gameState = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            player = new Player(false, i);

            if (i == 0) {
                // true in constructor means that this is the human player, not computer
                player = new Player(true);
            }
            gameState.put(player.getId(), player);
        }

        return gameState;
    }

    public void resetHand() {
        /*
        This function loops through all players and resets their hands
        */
        for (String playerId: currentState.keySet()) {
            currentState.get(playerId).resetHand();
        }
    }

    // Overloaded function for each Round's first Trick starter which is the guy beside the dealer
    public String setFirstRoundTrickStarter(String dealerId) {
        // player id of the guy who starts the first trick at the very first turn of each round
        String firstTrickPlayerId = getNextPlayer(dealerId);
        currentState.get(firstTrickPlayerId).makeStarter();
        return firstTrickPlayerId;
    }

    // between tricks
    public void setNextTrickStarter(String prevTrickStarter, String trickWinnerId) {
        /*
        This function takes in the winner and sets him to be the starter of the next trick
        */
        
        //reset prev trickstarter
        currentState.get(prevTrickStarter).resetStarter();
        currentState.get(trickWinnerId).makeStarter();
    }

    // get next player on the right
    public String getNextPlayer(String lastPlayerId) {
        /*
        This function sets the next Dealer
        */
        int nextIndex = -1;
        for (int i = 0; i < playerSequence.size(); i++) {
            String playerId = playerSequence.get(i);
            // reset the isDealer variable for all players
            if (playerId.equals(lastPlayerId)) {
                nextIndex = (i + 1) % 4;
            }
        }
        // set the next Dealer for player
        String nextPlayerId = playerSequence.get(nextIndex);
        return nextPlayerId;
    }


    public void setNextDealer(String dealerId) {
        /*
        This function sets the next Dealer
        */
        String nextDealerId = getNextPlayer(dealerId);
        String currentDealerId = findDealerId();
        // reset the dealer
        currentState.get(currentDealerId).resetDealer();
        // make next player dealer
        currentState.get(nextDealerId).makeDealer();
    }


    // function returns the current round's dealer
    public String findDealerId() {
        for (String pid : currentState.keySet()) {
            Player p = currentState.get(pid);
            // System.out.println(p.getHand());
            if (p.isDealer()) {
                return pid;
            }
        }
        return "not found"; // this should never happen
    }


    public void setFirstRoundDealer() {
        Deck deck = new Deck();
        var currentPlayedCards = new HashMap<String, Card>();
        for (String id: currentState.keySet()) {
            currentPlayedCards.put(id, deck.dealCard());
        }

        CurrentPlayedCards cards = new CurrentPlayedCards(currentPlayedCards);
        lastDealer = cards.returnFirstRoundDealer();
        
        // set the isDealer variable to true for this playerId
        currentState.get(lastDealer).makeDealer();
    }

    public void setTrumpCard(Card trumpCard) {
        this.trumpCard = trumpCard;
    }

    public Card getTrumpCard() {
        return this.trumpCard;
    }


    public void resetRoundBids() {
        roundBidsPlaced.clear();
    }


    public void setRoundBids(Card trumpCard, String dealerId) {
        resetRoundBids();
        int numPlayers = 4;
        String prevPlayerId = "";

        for (int i = 0; i < numPlayers; i ++) {
            Player currPlayer;
            if (i == 0) {
                // for first turn, the next player is the one beside the current dealerId
                currPlayer = currentState.get(getNextPlayer(dealerId));
                prevPlayerId = currPlayer.getId();
            } else {
                currPlayer = currentState.get(getNextPlayer(prevPlayerId));
                prevPlayerId = currPlayer.getId();
            }

            ArrayList<Integer> possibleBidsPerRound = this.getPossibleBids(currPlayer.isDealer());

            if (currPlayer.getPlayerType()) { // TODO rename this function, getPlayerType() returns true is player is player
                addToRoundBids(this.playerBid(currPlayer, possibleBidsPerRound));
            } else {
                addToRoundBids(this.computerBid(currPlayer, possibleBidsPerRound));
            }
        }
        
    }

    public ArrayList<Integer> getRoundBids() {
        return this.roundBidsPlaced;
    }

    public int playerBid(Player p, ArrayList<Integer> possibleBids) {
        Scanner reader = new Scanner(System.in);
        int bidChosen = -1;
        boolean validBid = possibleBids.contains(bidChosen);

        while(!validBid){
            try{
                System.out.printf("Your current hand is %s%n", p.getHand());
                System.out.printf("Possible Bids are : %s%n", possibleBids);
                System.out.print("Please choose your bid number within the possible bids: ");
                bidChosen = Integer.parseInt(reader.nextLine());
                validBid = possibleBids.contains(bidChosen);
                if(!validBid){
                    System.out.println("Invalid bid!");
                }
            }catch(NumberFormatException e){ 
                System.out.println("Invalid bid!");
            }catch(InputMismatchException e){
                System.out.println("Invalid bid!");
            }
        }

        p.setBids(bidChosen);
        return bidChosen;
    }

    public int computerBid(Player c, ArrayList<Integer> possibleBids) {
        int bidChosen =  c.computerPlaceBid(this.getTrumpCard(), possibleBids);
        System.out.printf("%s chose %d%n", c.getId(), bidChosen);
        c.setBids(bidChosen);
        System.out.printf("%s current hand: %s%n", c.getId(), c.getHand());
        return bidChosen;
    }

    public void resetTrickPoints() {
        for (String playerId : currentState.keySet()) {
            Player p = currentState.get(playerId);
            p.resetTrickWins();
        }
    }
    
    // returns the Id of the Winner of the Trick
    public String playTrick(String trickStarter) {
        System.out.println("<><><><><><><><><><><><><><>START OF TRICK<><><><><><><><><><><><><><><><><><><>");
        pile = new ArrayList<>();
        HashMap<String, Card> currentPlayedCards = new HashMap<>();
        int numPlayers = 4;
        String lastPlayerId = "";

        for (int tricksToPlay = 0; tricksToPlay < numPlayers; tricksToPlay++) {
            Player currPlayer = tricksToPlay == 0 ? currentState.get(trickStarter) : currentState.get(getNextPlayer(lastPlayerId));
            System.out.printf("Current Pile is : %s%n", pile);
            Card chosenCard;
            if (currPlayer.getPlayerType()) {
                // if player provide with option
                chosenCard = currPlayer.playerChooseCard();
                System.out.printf("Player chose %s%n", chosenCard);
            } else {
                chosenCard = currPlayer.computerChooseCard(trumpCard, pile);
                System.out.printf("%s chose %s%n", currPlayer.getId(), chosenCard);
            }
            pile.add(chosenCard);
            currentPlayedCards.put(currPlayer.getId(), chosenCard);
            lastPlayerId = currPlayer.getId();
        }
        
        CurrentPlayedCards c = new CurrentPlayedCards(currentPlayedCards);
        System.out.println("FINAL PILE FOR THE TRICK ROUND!!!!!!!!!!!!!!!!!!!!!!  " + pile);
        String winnerId = c.returnWinnerID(trumpCard, pile);
        
        System.out.println("WINNER WINNER CHICKEN DINNER " + winnerId + " WINNING CARD :" + currentPlayedCards.get(winnerId));
        
        currentState.get(winnerId).addTrickWins();
        
        System.out.printf("%s trick_wins: %d%n %strick_wins: %d%n %strick_wins: %d%n %strickwins: %d%n",
                currentState.get("Player").getId(), currentState.get("Player").getTrickWins(),
                currentState.get("Computer_1").getId(), currentState.get("Computer_1").getTrickWins(),
                currentState.get("Computer_2").getId(), currentState.get("Computer_2").getTrickWins(),
                currentState.get("Computer_3").getId(), currentState.get("Computer_3").getTrickWins());

        System.out.println("<><><><><><><><><><><><><><>END OF TRICK<><><><><><><><><><><><><><><><><><><>");
        return winnerId;
    }
}