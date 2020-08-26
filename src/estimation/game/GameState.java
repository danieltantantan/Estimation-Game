package estimation.game;
import estimation.player.*;
import estimation.card.*;
import estimation.bids.*;

import javax.swing.*;
import java.util.*;

/**
 * This class handles all the main game logic
 */

public class GameState {
    // Player id is the key, Player is the value
    private Map<String, Player> currentState;
    private int rounds;
    private static int maxRounds = 13;
    private String lastDealer; // the ID of the dealer of the previous round
    // we assume playerSequence will always be Player --> Computer_1 --> Computer_2 --> Computer_3
    // the sequence of dealer/starter will be decided in the methods getNextDealer and getNextStarter
    private static ArrayList<String> playerSequence = new ArrayList<>(List.of("Player", "Computer_3","Computer_2", "Computer_1"));
    private Card trumpCard;
    private ArrayList<Integer> roundBidsPlaced = new ArrayList<>();
    private Map<String, Card> pile = new HashMap<>();
    // number of cards to distirubte pertaining to each round. This is set in stone
    private static ArrayList<Integer> numCardsToDistribute = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 5, 4, 3, 2, 1));
    private static final GameState INSTANCE = new GameState();


    private GameState() {
        this.rounds = 1;
        this.currentState = generateState();
    }

    public static GameState getInstance() {
        return INSTANCE;
    }

    /**
     *
     * @param bid the bid placed
     */
    public void addToRoundBids(int bid) {
        this.roundBidsPlaced.add(bid);
    }

    /**
     * This method determines how many cards to distribute per person per round.
     * @return an int from 1-6, the number of cards to distribute per round
     */
    public int getDistributeCount() {
        return numCardsToDistribute.get(rounds - 1);
    }

    /**
     * This method returns an Array of the possible bids that a player can make at each instance,
     * if the player is a dealer, there are restrictions to the bids the player can amek.
     * @param isDealer boolean is Dealer
     * @return ArrayList, the possible bids for each player.
     */
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
     * Returns the current state of the game
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

    /**
     * This method deals the cards to each player
     * @param deck the deck of cards at the current state
     * @return Deck, the remaining deck of cards after dealing out cards.
     */
    public Deck dealHand(Deck deck) {
        for (String playerId : currentState.keySet()) {
            for (int i = 0; i < getDistributeCount(); i++) {
                Card c = deck.dealCard();
                currentState.get(playerId).addToHand(c);
            }
        }
        return deck;
    }

    /**
     * This method gets the max score of the players at that instance as well as the player with the max score
     * @return List, where index 0 is the player id and 1 is the score
     */
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

    /**
     * This method generates the gameState upon game start
     * @return Map, gameState
     */
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

    /**
     * This method resets the hand for all players
     */
    public void resetHand() {
        /*
        This method loops through all players and resets their hands
        */
        for (String playerId: currentState.keySet()) {
            currentState.get(playerId).resetHand();
        }
    }

    /**
     * This method sets the trick starter for the very first round
     * This method is an Overloaded method for each Round's first Trick starter which is the guy beside the dealer
     * @param dealerId the id of the dealer
     * @return String, id of the first trick player
     */
    public String setFirstRoundTrickStarter(String dealerId) {
        // player id of the guy who starts the first trick at the very first turn of each round
        String firstTrickPlayerId = getNextPlayer(dealerId);
        currentState.get(firstTrickPlayerId).makeStarter();
        return firstTrickPlayerId;
    }

    /**
     * This method resets the previous trick starter variable and makes the current trick winner the next trick starter.
     * @param prevTrickStarter the id of the previous person who started the trick
     * @param trickWinnerId the id of the person who won the previous trick
     */
    public void setNextTrickStarter(String prevTrickStarter, String trickWinnerId) {
        /*
        This method takes in the winner and sets him to be the starter of the next trick
        */

        //reset prev trickstarter
        currentState.get(prevTrickStarter).resetStarter();
        currentState.get(trickWinnerId).makeStarter();
    }

    /**
     * This method gets the player on the right and returns his/her id.
     * @param lastPlayerId the current player
     * @return String, the next player id
     */
    public String getNextPlayer(String lastPlayerId) {
        /*
        This method sets the next Dealer
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

    /**
     * This method sets the next dealer (person of the right of the current dealer)
     * according to the current dealer's id
     * @param dealerId the previous round's dealers id
     */
    public void setNextDealer(String dealerId) {
        /*
        This method sets the next Dealer
        */
        String nextDealerId = getNextPlayer(dealerId);
        String currentDealerId = findDealerId();
        // reset the dealer
        currentState.get(currentDealerId).resetDealer();
        // make next player dealer
        currentState.get(nextDealerId).makeDealer();
    }


    /**
     * This method finds the current round's dealer
     * @return String, id of the current dealer
     */
    public String findDealerId() {
        for (String pid : currentState.keySet()) {
            Player p = currentState.get(pid);
            // System.out.println(p.getHand());
            if (p.isDealer()) {
                return pid;
            }
        }
        return null; // this should never happen
    }

    /**
     * This method sets the dealer for the very first round
     * @param currentPlayedCards the cards dealt to decide who the dealer is
     */
    public void setFirstRoundDealer(Map<String, Card> currentPlayedCards) {
        CurrentPlayedCards cards = new CurrentPlayedCards(currentPlayedCards);
        lastDealer = cards.returnFirstRoundDealer();

        // set the isDealer variable to true for this playerId
        currentState.get(lastDealer).makeDealer();
    }

    /**
     *
     * @param trumpCard the trump card for the round
     */
    public void setTrumpCard(Card trumpCard) {
        this.trumpCard = trumpCard;
    }

    /**
     *
     * @return trumpCard, the trump card
     */
    public Card getTrumpCard() {
        return this.trumpCard;
    }

    /**
     * Reset all bids to 0 in preperation for the next round
     */
    public void resetRoundBids() {
        roundBidsPlaced.clear();
    }

    /**
     * This method sets the round bids for all players. If it is the Player, prompt him for input, else just use
     * computer logic
     * @param trumpCard trumpcard for the round
     * @param dealerId current dealer
     */
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

            if (currPlayer.isPlayer()) {
                addToRoundBids(this.playerBid(currPlayer, possibleBidsPerRound));
            } else {
                addToRoundBids(this.computerBid(currPlayer, possibleBidsPerRound));
            }
        }

    }

    public ArrayList<Integer> getRoundBids() {
        return this.roundBidsPlaced;
    }

    /**
     * This method prompts the player to make a bid
     * @param possibleBids list of possible bids for the player at that position
     * @return int; the bid the player chose
     */
    public static int promptPlayerBids(ArrayList<Integer> possibleBids){
        Integer[] options = new Integer[possibleBids.size()];
        for (int i = 0; i < possibleBids.size(); i++) {
            options[i] = possibleBids.get(i);
        }

        int bidIndex = JOptionPane.showOptionDialog(null, "Please choose your bids", "Place Bids", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        return options[bidIndex];
    }

    /**
     * This method sets the bids for the player
     * @param p player
     * @param possibleBids possible bids
     * @return int, the bid chosen
     */
    public int playerBid(Player p, ArrayList<Integer> possibleBids) {
        int bidChosen = -1;
        bidChosen = promptPlayerBids(possibleBids);
        p.setBids(bidChosen);
        return bidChosen;
    }

    /**
     * This method is for when the computer decides what to bid each round
     * @param c computer
     * @param possibleBids possible bids
     * @return int, the bid chosen
     */
    public int computerBid(Player c, ArrayList<Integer> possibleBids) {
        int bidChosen =  c.computerPlaceBid(this.getTrumpCard(), possibleBids);
//        System.out.printf("%s chose %d%n", c.getId(), bidChosen);
        c.setBids(bidChosen);
//        System.out.printf("%s current hand: %s%n", c.getId(), c.getHand());
        return bidChosen;
    }

    /**
     * This method resets trick points for all players
     */
    public void resetTrickPoints() {
        for (String playerId : currentState.keySet()) {
            Player p = currentState.get(playerId);
            p.resetTrickWins();
        }
    }

    /**
     * Play the trick within each round
     * @param playerId playerId of the person wo is playing
     * @param PlayerHand the hand of the player
     * @return the current pile of cards on the table
     */
    public Map<String, Card> playTrick(String playerId, JLabel PlayerHand) {
        Player currPlayer = currentState.get(playerId);
        Card chosenCard;
        if (currPlayer.isPlayer()) {
            // if player provide with option
            chosenCard = currPlayer.playerChooseCard(PlayerHand);

        } else {
            Card leadCard = null;
            for(String s : pile.keySet()){
                Player p = currentState.get(s);
                if(p.isStarter()){
                     leadCard = pile.get(s);
                }
            }
            chosenCard = currPlayer.computerChooseCard(trumpCard, new ArrayList<>(pile.values()), leadCard);
        }
        pile.put(playerId, chosenCard);
//        System.out.println("Current pile is " + pile);
        return pile;
    }

    /**
     * This method clears the pile of cards on the table, meant for use after each trick has finished
     */
    public void clearPile() {
        this.pile.clear();
    }

    /**
     * Update the points for each player in the game state
     * @return Map, the points added/deducted per player so that it can be displayed in the AppMain view.
     */
    public Map<String, Integer> updatePoints() {
        var playerPoints = new HashMap<String, Integer>();
        for (String playerId : currentState.keySet()) {
            Player currPlayer = currentState.get(playerId);
            int bids = currPlayer.getBids();
            int tricksWon = currPlayer.getTrickWins();
            int points = bids + 10;
            if (bids == tricksWon) {
                currPlayer.addPoints(bids + 10);
            } else {
                currPlayer.minusPoints(bids + 10);
                points = -points;
            }
            playerPoints.put(playerId, points);
        }
        return playerPoints;
    }
}