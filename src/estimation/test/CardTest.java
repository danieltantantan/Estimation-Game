//package estimation.test;
//import java.util.*;
//public class CardTest{
//
//    public static void main(String[] args) {
//        List<Card> card1 = new ArrayList<>();
//        Deck deck = new Deck();
//        for(int i = 0; i<4; i++){
//            Card c = deck.dealCard();
//            card1.add(c);
//        }
//        Collections.sort(card1, new CardComparator());
//        System.out.println(card1);
//        // above is used to test for selecting the first dealer.
//
//        List<Card> card2 = new ArrayList<>();
//        Card b = new Card(Suit.SPADES, Rank.THREE);
//        Card b1 = new Card(Suit.CLUBS, Rank.FIVE);
//        card2.add(b1);
//        Card e = new Card(Suit.DIAMONDS, Rank.JACK);
//        card2.add(e);
//        Card e1 = new Card(Suit.DIAMONDS, Rank.ACE);
//        card2.add(e1);
//        Card e2 = new Card(Suit.DIAMONDS, Rank.FIVE);
//        card2.add(e2);
//        // for(int i = 0; i<10; i++){
//        //     Card c = deck.dealCard();
//        //     card2.add(c);
//        // }
//        Collections.sort(card2, new CardComparator(b,e));
//        System.out.println(card2);
//        // above is used to test for having lead card, as well as trump card in the conditions.
//        // with Spades as the lead suit and DIAMONDS as the trump suit,
//        // we will get back a ordering of club, hearts, spades, and diamonds.
//        ArrayList<Card> testingForTrumpOnly = new ArrayList<>();
//        Card trumpCard = new Card(Suit.DIAMONDS, Rank.JACK);
//        for(int i = 0; i<4; i++){
//            Card c = deck.dealCard();
//            testingForTrumpOnly.add(c);
//        }
//        Collections.sort(testingForTrumpOnly, new CardComparator(trumpCard));
//        System.out.println(testingForTrumpOnly);
//
//
//        //testing the logic for 2 cards, with trump suit in play and lead card
//
//        Card playedCard = new Card(Suit.SPADES, Rank.TWO);
//        Card trump = new Card(Suit.SPADES, Rank.ACE);
//        Card currentLargestCard = new Card(Suit.DIAMONDS, Rank.JACK);
//        Card leadSuit = new Card(Suit.DIAMONDS, Rank.FIVE);
//        System.out.println(playedCard);
//        int abc = playedCard.compareTo(currentLargestCard,trump,leadSuit);
//        System.out.println(abc);
//        System.out.println(playedCard.getSuit().equals(trump.getSuit()));
//
//
//        ArrayList<Integer> possibleBids = new ArrayList<>(Arrays.asList(0,2,3,4));
//        Player p = new Player(false);
//        for(int i = 0; i<4; i++){
//            Card c = testingForTrumpOnly.get(i);
//            p.addToHand(c);
//        }
//        int bidPlace = p.computerPlaceBid(trump, possibleBids);
//        System.out.println(bidPlace);
//
//    }
//}