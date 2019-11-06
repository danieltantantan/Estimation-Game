package estimation.player;
import java.util.*;
import estimation.card.*;
public class Player {
    private int points;
    private ArrayList<Card> hand;
    private int trickWins;  //Determine how many trick you win
    private int bids; //Determine how much you bid
    private boolean isDealer; // is he the dealer
    private boolean isPlayer; // is he a player of computer
    private String id; // id constraints are "Player", "Computer_1", "Computer_2", "Computer_3"
    private boolean isStarter;

    public Player(boolean isPlayer) {
      this(isPlayer, 0);
    }

    public Player(boolean isPlayer, int addCount){
      this.isDealer = false;
      this.isPlayer = isPlayer; 
      this.setPlayerId(isPlayer, addCount); // if true, set id to "Player", else set to "Computer_<n> where n is the count of computer"
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
     * @return the id
     */
    public String getId() { return id; }

    public int getBids() { return bids; }

    public boolean getPlayerType() {
      return this.isPlayer;
    }

    public boolean isDealer() {
      return this.isDealer;
    }

    public boolean isStarter() {
      return this.isStarter;
    }

    public void resetStarter() {
      this.isStarter = false;
    }

    public void resetDealer(){
        this.isDealer = false;
    }

    public void makeDealer() {
      this.isDealer = true;
    }

    public void makeStarter() {
      this.isStarter = true;
    }


    public void setBids(int bids) {
        this.bids = bids;
    }

    public int getTrickWins() {
        return trickWins;
    }
    
    public void resetTrickWins() {
        this.trickWins = 0;
    }

    public void addTrickWins() {
        this.trickWins++;
    }

    public int getPoints() {
        return points;
      }
    
    public void setPoints(int points) {
        this.points = points;
      }

    public void addPoints(int value){
        this.points += value;
    }

    public void minusPoints(int value){
        this.points -= value;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
    
    public void resetHand() {
      this.hand.clear();
    }


    public void addToHand(Card card) {
      this.hand.add(card);
    }

    public boolean isWin(){
        if(trickWins==points){
            return true;
        } else {
            return false;
        }
    }
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

    public Card playerChooseCard() {
      /*
      This function tells the player to choose a card
      After a card is chosen, we will minus the card from his current hand of playing cards
      If the card chosen is invalid, show him his current hand of cards and tell him to choose a valid card
      If it is valid, minus the card from his current hand and return the chosen card.
      */

      // if hand size is only 1, just choose 1, dont need to prompt user
      if (hand.size() == 1) {
        Card chosenCard = hand.get(0);
        System.out.printf("Since your hand size is only 1, %s is chosen card automatically%n", chosenCard);
        hand.remove(0);
        return chosenCard;
      }


      Scanner reader = new Scanner(System.in);
      int chosenIndex = -1;

      boolean validIndex = (chosenIndex < (hand.size())) && (chosenIndex >= 0);
      while(!validIndex){
        try{
          System.out.printf("Your hand is %s%n : ", hand);
          System.out.printf("Please choose the index from [0-%d]: ", hand.size()-1);
          chosenIndex = Integer.parseInt(reader.nextLine());
          validIndex = (chosenIndex < (hand.size())) && (chosenIndex >= 0);
          if(!validIndex){
            System.out.println("Invalid index chosen!");
          }
        }catch(NumberFormatException e){ 
          System.out.println("Invalid index chosen!");
        }catch(InputMismatchException e){
          System.out.println("Invalid index chosen!");
        }
      }

      Card chosenCard = hand.get(chosenIndex);
      hand.remove(chosenIndex);
      return chosenCard;
    }


    public Card computerChooseCard(Card trumpCard, ArrayList<Card> pile) {
      /*
      This function will help the computer choose the card.
      It makes decisions based on the current trump card, lead suit and the current pile of cards.
      To settle certain logic
      */
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
        Card leadCard = pileCopy.get(0);
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