package estimation.player;
import java.awt.*;
import java.util.*;
import java.util.List;

import estimation.card.*;

import javax.swing.*;

/**
 * This class focus on all Player logic
 */

public class Player {
    private int points;
    private ArrayList<Card> hand;
    //Determine how many trick you win
    private int trickWins;
    //Determine how much you bid
    private int bids;
    // is he the dealer
    private boolean isDealer;
    // is he a player of computer
    private boolean isPlayer;
    // id constraints are "Player", "Computer_1", "Computer_2", "Computer_3"
    private String id;
    private boolean isStarter;

    public Player(boolean isPlayer) {
        this(isPlayer, 0);
    }

    public Player(boolean isPlayer, int addCount){
        this.isDealer = false;
        this.isPlayer = isPlayer;
        // if true, set id to "Player", else set to "Computer_<n> where n is the count of computer"
        this.setPlayerId(isPlayer, addCount);
        this.hand = new ArrayList<Card>();
        this.trickWins = 0;
    }

    public void setPlayerId(boolean isPlayer, int addCount) {
        if (isPlayer) {
            this.id = "Player";
        } else {
            this.id = String.format("Computer_%d", addCount);
        }
    }

    /**
     *
     * @return String, id of this player
     */
    public String getId() { return id; }

    /**
     *
     * @return int, bids this palyer made
     */
    public int getBids() { return bids; }

    /**
     * Check if the player is computer or not
     * @return boolean, if isPlayer, return true, if is Computer, return false
     */
    public boolean isPlayer() {
        return this.isPlayer;
    }

    /**
     * Check if player is dealer or not
     * @return boolean, true if dealer, false otherwise.
     */
    public boolean isDealer() {
        return this.isDealer;
    }

    /**
     * Check if player is starter or not
     * @return boolean, true if starter, false otherwise.
     */
    public boolean isStarter() {
        return this.isStarter;
    }

    /**
     * resets starter
     */
    public void resetStarter() {
        this.isStarter = false;
    }

    /**
     * resets dealer
     */
    public void resetDealer(){
        this.isDealer = false;
    }

    /**
     * Make this Player dealer
     */
    public void makeDealer() {
        this.isDealer = true;
    }

    /**
     * Make this Player the Starter for the trick
     */
    public void makeStarter() {
        this.isStarter = true;
    }

    /**
     * Update bids made per round for this player
     * @param bids Bids
     */
    public void setBids(int bids) {
        this.bids = bids;
    }

    /**
     * Get the total trick wins for this player
     * @return int, the trick wins for this player
     */
    public int getTrickWins() {
        return trickWins;
    }

    /**
     * Reset the trick wins for this player in preperation for the next round
     */
    public void resetTrickWins() {
        this.trickWins = 0;
    }

    /**
     * Add trick wins to this player once a trick is won
     */
    public void addTrickWins() {
        this.trickWins++;
    }

    /**
     * Get the points this player has won
     * @return int, points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Add points to this player according to how many points won
     * @param pointsWon points won
     */
    public void addPoints(int pointsWon){
        this.points += pointsWon;
    }

    /**
     * Add points to this player according to how many points won
     * @param pointsDeducted points lost
     */
    public void minusPoints(int pointsDeducted){
        this.points -= pointsDeducted;
    }

    /**
     * Gets the current hand of the player
     * @return the hand of the player
     */
    public ArrayList<Card> getHand() {
        return hand;
    }

    /**
     * Rests the player's hand each round
     */
    public void resetHand() {
        this.hand.clear();
    }

    /**
     * Add the card to the player's current hand
     * @param card card to add to the hand
     */
    public void addToHand(Card card) {
        this.hand.add(card);
    }

    /**
     * This method contains all logic needed for a computer to place a bid
     * @param trumpCard the trump card for this round
     * @param possibleBids the possible bids for this computer. Affected if the computer is the dealer
     * @return an int representing the bid the computer selected based on the logic provided
     */
    public int computerPlaceBid(Card trumpCard, ArrayList<Integer> possibleBids){

        //count the number of TRUMP and Higher rank cards
        List<Rank> highRanks = Rank.HIGH_VALUES;
        int count = 0;
        double percentHigh = 0;
        int sizeOfbids = possibleBids.size();
        int indexOfMedian = 0;
        for(int i = 0; i < hand.size(); i++){
            Card currentCard = hand.get(i);

            // counting how many trumps and high cards are present
            if(currentCard.getSuit().equals(trumpCard.getSuit()) || highRanks.contains(currentCard.getRank()) ){
                count++;
            }
        }
        percentHigh = 100 * count / hand.size();
        if(sizeOfbids % 2 == 0){
            indexOfMedian = sizeOfbids/2 -1;
        }else{
            indexOfMedian = (sizeOfbids-1)/2;
        }
        if(percentHigh <= 25){
            indexOfMedian-=2;
        }else if(percentHigh <= 50){
            indexOfMedian-=1;
        }else if(percentHigh <= 75){
            indexOfMedian+=1;
        }else{
            indexOfMedian+=2;
        }
        if(indexOfMedian<0){
            indexOfMedian = 0;
        }
        else if(indexOfMedian >= sizeOfbids){
            indexOfMedian = sizeOfbids - 1;
        }

        return possibleBids.get(indexOfMedian);
    }

    /**
     * This method prompts a player to choose a card from his hand
     * @param hand his hand of cards
     * @param PlayerHand The JLabel to show the positioning of the prompt
     * @return int representing the index of the cards chosen
     */
    public static int promptPlayerCardInput(List<Card> hand, JLabel PlayerHand) {
        ImageIcon[] options = new ImageIcon[hand.size()];
        for (int i = 0; i < hand.size(); i++) {
            options[i] = new ImageIcon(hand.get(i).getUrl());
        }
//        System.out.println("input prompted");
        int cardIndex = JOptionPane.showOptionDialog(PlayerHand, null, "Select a Card", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,  null, options, options[0]);

        return cardIndex;
    }

    /**
     * This function tells the player to choose a card
     * After a card is chosen, we will minus the card from his current hand of playing cards
     * If the card chosen is invalid, show him his current hand of cards and tell him to choose a valid card
     * If it is valid, minus the card from his current hand and return the chosen card.
     * @param PlayerHand the jlabel to show the prompt option box for player input
     * @return Card, the chosen card the player selected.
     */
    public Card playerChooseCard(JLabel PlayerHand) {
        // if hand size is only 1, just choose 1, dont need to prompt user
        int chosenIndex = -1;
        if (PlayerHand != null) {
            chosenIndex = promptPlayerCardInput(hand, PlayerHand);
        } else {
            Scanner reader = new Scanner(System.in);
            System.out.print("Please choose a card index: ");
            chosenIndex = Integer.parseInt(reader.nextLine());
        }

        Card chosenCard = hand.get(chosenIndex);
//        System.out.println(chosenCard);
        hand.remove(chosenIndex);
        return chosenCard;
    }

    /**
     * This function will help the computer choose the card.
     * It makes decisions based on the current trump card, lead suit and the current pile of cards.
     * To settle certain logic
     * @param trumpCard trump card at round
     * @param pile current pile of cards on the table
     * @param leadCard lead card for trick
     * @return Card, the chosen card the computer selected.
     *
     */
    public Card computerChooseCard(Card trumpCard, ArrayList<Card> pile, Card leadCard) {

        Card chosenCard;
        ArrayList<Card> currHand = (ArrayList<Card>)hand.clone();
        ArrayList<Card> pileCopy = (ArrayList<Card>)pile.clone();

        if (this.isStarter) {
            if (this.getBids() > 0) {
                // sort the current hand taking into account the trump card
                Collections.sort(currHand, new CardComparator(trumpCard));
                for (int i = currHand.size() - 1; i >= 0; i--) {
                    // Compare chosen card's suit with the trump card's suit, if it is equals, go to the next card
                    chosenCard = currHand.get(i);
                    if (!chosenCard.getSuit().equals(trumpCard.getSuit())) {
                        hand.remove(chosenCard);
                        return chosenCard;
                    }
                }
                chosenCard = currHand.get(currHand.size() - 1);
                hand.remove(chosenCard);
                return chosenCard;

            } else {
                // return the card with the lowest rank
                // sort the current hand WITHOUT taking into account the trump card
                Collections.sort(currHand, new CardComparator());
                chosenCard = currHand.get(0);
                hand.remove(chosenCard);
                return chosenCard;
            }
        } else {
            // sort the pile according to the trump card and lead card.
            Collections.sort(pileCopy, new CardComparator(trumpCard,leadCard));
            //get back the largest lead suit card from the pile.
            Card largestLeadCard = null;
            for(int i = pileCopy.size() - 1; i>=0; i--){
                Card currentCard = pileCopy.get(i);

                if(currentCard.getSuit().equals(leadCard.getSuit())){
                    largestLeadCard = currentCard;
                    break;
                }
            }
            Card largestTrumpCard = pileCopy.get(pileCopy.size() - 1);
            // sort the hand according to the trump card and lead card.
            Collections.sort(currHand, new CardComparator(trumpCard,leadCard));

            if (this.getBids() > 0) {

                // loop from the lowest ranked card to the highest rank card
                // return the card if its lead suit and larger than the largest lead suit card
                for (int i = 0; i < currHand.size(); i++) {
                    chosenCard = currHand.get(i);

                    if (chosenCard.compareTo(largestLeadCard, trumpCard, leadCard) > 0 && chosenCard.getSuit().equals(leadCard.getSuit())) {
                        // if the chosen card is bigger than the largestLeadCard
                        // taking into account trumpcard, return the chosenCard
                        hand.remove(chosenCard);
                        return chosenCard;
                    }
                }
                // picks a card of the trump suit whose rank is higher than that is played.
                // comes into this for-loop only when the computer has no LEAD SUIT card that is higher than the biggest lead suit card that is played
                for (int i = 0; i < currHand.size(); i++) {
                    chosenCard = currHand.get(i);

                    if (chosenCard.compareTo(largestTrumpCard, trumpCard, leadCard) > 0 && chosenCard.getSuit().equals(trumpCard.getSuit())) {
                        // if the chosen card is bigger than the largestTrumpCard
                        // taking into account trumpcard, return the chosenCard
                        hand.remove(chosenCard);
                        return chosenCard;
                    }
                }


                // if there is no card that beats the leadcard, pick the lowest card from the lead suit
                // if there is no lowest card from lead suit, pick from the trump suit
                //currHand must be sorted in this manner --- other suit, lead suit, trump suit
                for (int i = 0; i < currHand.size(); i++) {
                    chosenCard = currHand.get(i);

                    if (chosenCard.getSuit().equals(leadCard.getSuit())) {
                        hand.remove(chosenCard);
                        return chosenCard;
                    }
                    if (chosenCard.getSuit().equals(trumpCard.getSuit())) {
                        hand.remove(chosenCard);
                        return chosenCard;
                    }
                }
                hand.remove(currHand.get(0));
                return currHand.get(0);
            }else{
                //Pick a card from the lead suit that is smaller than the highest card in the pile but it is the highest rank card on hand

                // lead card is the first card in the pile
                // sort the pile according to the leadCard
                for(int i = currHand.size() - 1; i >= 0; i-- ){
                    chosenCard = currHand.get(i);
                    //checking if the chosen card is smaller than the largest lead suit card played.
                    if (chosenCard.compareTo(largestLeadCard, trumpCard, leadCard) < 0 && chosenCard.getSuit().equals(leadCard.getSuit())) {
                        hand.remove(chosenCard);
                        return chosenCard;
                    }
                }

                //if it is, checking through the player's hand to see if the player has a TRUMP suit card, that is smaller than the largest card played (card with trump suit)
                for(int i = currHand.size() - 1; i >= 0; i-- ){
                    chosenCard = currHand.get(i);
                    //checking if the chosen card is smaller than the largest trump suit card played.
                    if (chosenCard.compareTo(largestTrumpCard, trumpCard, leadCard) < 0 && chosenCard.getSuit().equals(trumpCard.getSuit())) {
                        hand.remove(chosenCard);
                        return chosenCard;
                    }
                }

                //remove the biggest card that the player has.
                hand.remove(currHand.get(currHand.size() - 1));
                return currHand.get(currHand.size() - 1);

            }
        }
    }
}