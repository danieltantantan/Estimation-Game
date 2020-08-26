package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import estimation.card.Card;
import estimation.card.Deck;
import estimation.game.*;
import estimation.player.Player;

/**
 * This class is the view of the application
 */

public class AppMain {
    private JButton StartButton;
    private JPanel CardGame;
    private JLabel TrumpCard;
    private JLabel Player1;
    private JLabel Player2;
    private JLabel Player3;
    private JLabel Player4;
    private JLabel P1C1;
    private JLabel P1C2;
    private JLabel P1C3;
    private JLabel P1C4;
    private JLabel P1C5;
    private JLabel P1C6;
    private JLabel P2C1;
    private JLabel P2C2;
    private JLabel P2C3;
    private JLabel P2C4;
    private JLabel P2C5;
    private JLabel P2C6;
    private JLabel P3C1;
    private JLabel P3C2;
    private JLabel P3C3;
    private JLabel P3C4;
    private JLabel P3C5;
    private JLabel P3C6;
    private JLabel P4C1;
    private JLabel P4C2;
    private JLabel P4C3;
    private JLabel P4C4;
    private JLabel P4C5;
    private JLabel P4C6;
    public JLabel TableC1;
    private JLabel TableC2;
    private JLabel TableC3;
    private JLabel TableC4;
    private JLabel PlayerHand;
    private JLabel DealerP4;
    private JLabel DealerP3;
    private JLabel DealerP1;
    private JLabel DealerP2;
    private JLabel Player1Pts;
    private JLabel Player1Bids;
    private JLabel Player1Tricks;
    private JLabel Computer2Pts;
    private JLabel Computer2Bids;
    private JLabel Computer2Tricks;
    private JLabel Computer1Pts;
    private JLabel Computer1Bids;
    private JLabel Computer1Tricks;
    private JLabel Computer3Pts;
    private JLabel Computer3Bids;
    private JLabel Computer3Tricks;
    private JLabel TrickWinnerP4;
    private JLabel TrickWinnerP1;
    private JLabel TrickWinnerP3;
    private JLabel TrickWinnerP2;
    private JLabel C2LeadSuit;
    private JLabel PlayerLeadSuit;
    private JLabel C1LeadSuit;
    private JLabel C3LeadSuit;
    private JLabel[] dealerLabels = new JLabel[]{DealerP1, DealerP2, DealerP3, DealerP4};
    private JLabel[] trickWinnerLabels = new JLabel[]{TrickWinnerP1, TrickWinnerP2, TrickWinnerP3, TrickWinnerP4};
    private JLabel[] leadSuitLabels = new JLabel[] {PlayerLeadSuit, C1LeadSuit, C2LeadSuit, C3LeadSuit};
    private JLabel[] cardLabels = new JLabel[]{P1C1, P1C2, P1C3, P1C4, P1C5, P1C6, P2C1, P2C2, P2C3, P2C4, P2C5, P2C6, P3C1, P3C2, P3C3, P3C4, P3C5, P3C6, P4C1, P4C2, P4C3, P4C4, P4C5, P4C6};
    private JLabel[] tableLabels = new JLabel[]{TableC1, TableC2, TableC3, TableC4};
    private final GameState GAMESTATE = GameState.getInstance();
    private HashMap<Player, ArrayList<JLabel>> playerLabelMap = new HashMap<>();


    public AppMain() {
        StartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartButton.setVisible(false);
                startGame();
            }
        });
    }

    /**
     * This method starts the game
     */

    public void startGame () {

        var maxScoreOfRound = GAMESTATE.getMaxScore();
        int maxScore = 0; // max score out of all players

        Deck deck = new Deck();
        var currentPlayedCards = new HashMap<String, Card>();
        for (String id: GAMESTATE.getCurrentState().keySet()) {
            currentPlayedCards.put(id, deck.dealCard());
        }

        populateTable(currentPlayedCards);
        GAMESTATE.setFirstRoundDealer(currentPlayedCards);
        String message = "First dealer of the game is " + GAMESTATE.findDealerId();
        JOptionPane.showMessageDialog(CardGame, message);
        clearTableDisplay();
        // set mapping of player ids to the respective positions of the Jlabels
        setLabels();

        while (GAMESTATE.getRounds() < 12 && maxScore < 100) {
            populateNumbers();
            startRound();
            // get max score of the round
            maxScoreOfRound = GAMESTATE.getMaxScore();
            maxScore = Integer.parseInt(maxScoreOfRound.get(1));
//            System.out.println("Max score of this round is >> " + maxScore);
            populateNumbers();
        }

        String overallWinnerId = maxScoreOfRound.get(0);
        displayWinnerChip(overallWinnerId);
        message = "Winner of the game is "+ overallWinnerId;
        JOptionPane.showMessageDialog(CardGame, message);
    }

    /**
     * This method initiates each round within the game
     */
    public void startRound () {
        GAMESTATE.resetHand();
        Deck deck = new Deck();
        Card trumpCard = deck.dealCard();
        GAMESTATE.setTrumpCard(trumpCard);
        // display trumpcard image on the board
        displayCardImage(trumpCard, TrumpCard);
        // dealHand to each player
        GAMESTATE.dealHand(deck);
        // display hand in the gamestate
        displayHand();
        // get the current State
        var currentHand = GAMESTATE.getCurrentState();

        // start VERY first trick within round
        Player firstDealer = currentHand.get(GAMESTATE.findDealerId());
        displayDealerChip(firstDealer.getId());
        String firstTrickStarterId = GAMESTATE.setFirstRoundTrickStarter(firstDealer.getId());
        populateNumbers();
        GAMESTATE.setRoundBids(trumpCard, firstDealer.getId());

        int numberOfTricksPerRound = GAMESTATE.getCurrentState().get("Player").getHand().size();
        String trickStarter = "";

        for (int i = 0; i < numberOfTricksPerRound; i++) {

            if (i == 0) {
                trickStarter = firstTrickStarterId;
            }

            String trickWinnerId = playTricks(trickStarter, false);
            GAMESTATE.getCurrentState().get(trickWinnerId).addTrickWins();
            GAMESTATE.clearPile();
            populateNumbers();
            String message = "Winner of the trick is "+ trickWinnerId;
            JOptionPane.showMessageDialog(CardGame, message);

            // clear icons for player and table for each trick
            clearPlayerLabels();
            resetTrickWinnerChip();
            clearTableDisplay();

            GAMESTATE.setNextTrickStarter(trickStarter, trickWinnerId);
            trickStarter = trickWinnerId;

        }

        Map<String, Integer> pointsAddedAndDeducted = GAMESTATE.updatePoints();
        String message = "Points Deducted/Added for Round " + GAMESTATE.getRounds() +"\nPlayer: " + pointsAddedAndDeducted.get("Player")
                +"\nComputer 1: " + pointsAddedAndDeducted.get("Computer_1") + " \nComputer 2:" + pointsAddedAndDeducted.get("Computer_2")
                + "\nComputer 3:" + pointsAddedAndDeducted.get("Computer_3");
        JOptionPane.showMessageDialog(CardGame, message);
        GAMESTATE.addRound();
        GAMESTATE.setNextDealer(firstDealer.getId());
        GAMESTATE.resetTrickPoints();
    }

    /**
     * This method initiates tricks to be played per round
     * @param trickStarter trick starter for the round
     * @return String, the winner of each trick
     */
    private String playTricks (String trickStarter, boolean isTest){
        String currentPlayer = trickStarter;
        if (!isTest) {
            displayTrickWinnerChip(trickStarter);
            populateNumbers();
            if (currentPlayer.equals("Player")) { playerBlankHand(); }
        }

        Map<String, Card> currentPile = GAMESTATE.playTrick(currentPlayer, PlayerHand);
        if(!isTest) {
            populateTable(currentPile);
            populateNumbers();
        }

        for (int trickRound = 0; trickRound < 3; trickRound++) {

            currentPlayer = GAMESTATE.getNextPlayer(currentPlayer);
            if (currentPlayer.equals("Player")) { playerBlankHand(); }
            currentPile = GAMESTATE.playTrick(currentPlayer, PlayerHand);
            if(!isTest) {
                populateTable(currentPile);
                populateNumbers();
            }
        }
        Card leadCard = currentPile.get(trickStarter);
        CurrentPlayedCards currentPlayedCards = new CurrentPlayedCards(currentPile);
        String trickWinnerId = currentPlayedCards.returnWinnerID(GAMESTATE.getTrumpCard(), leadCard);
        return trickWinnerId;
    }

    /**
     * This method displays the card image for each label
     * @param c card
     * @param labelToAdd label that card should go to
     */
    private void displayCardImage (Card c, JLabel labelToAdd){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(c.getUrl()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(img);

        labelToAdd.setIcon(icon);
    }

    /**
     * This method displays the OVERALL winner chip
     * @param winnerId OVERALL winner
     */
    private void displayWinnerChip(String winnerId){
        for (JLabel j : dealerLabels) {
            j.setVisible(false);
        }

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("./images/WinnerChip.gif"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(img);
        JLabel labelToAdd = null;

        if (winnerId.equals("Player")) {
            labelToAdd = dealerLabels[0];
        } else if (winnerId.equals("Computer_1")) {
            labelToAdd = dealerLabels[1];
        } else if (winnerId.equals("Computer_2")) {
            labelToAdd = dealerLabels[2];
        } else if (winnerId.equals("Computer_3")) {
            labelToAdd = dealerLabels[3];
        }
        labelToAdd.setIcon(icon);
        labelToAdd.setVisible(true);
    }

    /**
     * This method displays the trick winner chip
     * @param trickWinnerId current trick winner
     */
    private void displayTrickWinnerChip(String trickWinnerId){
        for (JLabel j : trickWinnerLabels) {
            j.setVisible(false);
        }

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("./images/TrickWinnerChip.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(img);
        JLabel labelToAdd = null;
        JLabel leadSuitToAdd = null;
        if (trickWinnerId.equals("Player")) {
            labelToAdd = trickWinnerLabels[0];
            leadSuitToAdd = leadSuitLabels[0];
        } else if (trickWinnerId.equals("Computer_1")) {
            labelToAdd = trickWinnerLabels[1];
            leadSuitToAdd = leadSuitLabels[1];
        } else if (trickWinnerId.equals("Computer_2")) {
            labelToAdd = trickWinnerLabels[2];
            leadSuitToAdd = leadSuitLabels[2];
        } else if (trickWinnerId.equals("Computer_3")) {
            labelToAdd = trickWinnerLabels[3];
            leadSuitToAdd = leadSuitLabels[3];
        }
        labelToAdd.setIcon(icon);
        labelToAdd.setVisible(true);
        leadSuitToAdd.setText("Lead Suit");
        leadSuitToAdd.setForeground(Color.yellow);
        leadSuitToAdd.setVisible(true);
    }
    private void resetTrickWinnerChip(){
        for (JLabel j : trickWinnerLabels) {
            j.setVisible(false);
        }

        for (JLabel j : leadSuitLabels) {
            j.setVisible(false);
        }

    }

    /**
     * This method displays the dealer chip
     * @param dealerId current round's dealer
     */
    private void displayDealerChip(String dealerId){
        for (JLabel j : dealerLabels) {
            j.setVisible(false);
        }

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("./images/DealerChip.gif"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(img);
        JLabel labelToAdd = null;
        if (dealerId.equals("Player")) {
            labelToAdd = dealerLabels[0];
        } else if (dealerId.equals("Computer_1")) {
            labelToAdd = dealerLabels[1];
        } else if (dealerId.equals("Computer_2")) {
            labelToAdd = dealerLabels[2];
        } else if (dealerId.equals("Computer_3")) {
            labelToAdd = dealerLabels[3];
        }
        labelToAdd.setIcon(icon);
        labelToAdd.setVisible(true);
    }

    /**
     * This method sets the card image for each player
     * @param url card image url
     * @param playerLabel label of the player to set the card images to
     */
    private void displayCardImage (String url, JLabel playerLabel){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(url));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(img);

        playerLabel.setIcon(icon);
    }

    /**
     * This method sets the player's hand to be blank green cards
     */
    private void playerBlankHand(){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("./images/blank-background.gif"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(img);
        P1C1.setIcon(icon);
        P1C2.setIcon(icon);
        P1C3.setIcon(icon);
        P1C4.setIcon(icon);
        P1C5.setIcon(icon);
        P1C6.setIcon(icon);
    }

    /**
     * This method maps each player id to a specific label
     */
    private void setLabels () {
        var playerMap = GAMESTATE.getCurrentState();
        int i = 0;
        for (String pid : playerMap.keySet()) {
            Player p = playerMap.get(pid);
            ArrayList<JLabel> playerLabel = new ArrayList<>();
            for (int j = 0; j < 6; j++) {
                if (playerLabelMap.containsKey(p)) {
                    playerLabelMap.get(p).add(cardLabels[i]);
                } else {
                    playerLabel.add(cardLabels[i]);
                    playerLabelMap.put(p, playerLabel);
                }
                i++;
            }
        }
    }

    /**
     * This method populates the statistics (Bids, Trick wins, Points won/deducted) after each trick/round.
     */
    public void populateNumbers() {
        var currentState = GAMESTATE.getCurrentState();
        for (String pid : currentState.keySet()) {
            Player p = currentState.get(pid);
            String trickWins = "" + p.getTrickWins();
            String bids = "" + p.getBids();
            String points = "" + p.getPoints();
            if (pid.equals("Player")) {
                Player1Pts.setText(points);
                Player1Bids.setText(bids);
                Player1Tricks.setText(trickWins);
            } else if (pid.equals("Computer_1")) {
                Computer1Pts.setText(points);
                Computer1Bids.setText(bids);
                Computer1Tricks.setText(trickWins);
            } else if (pid.equals("Computer_2")) {
                Computer2Pts.setText(points);
                Computer2Bids.setText(bids);
                Computer2Tricks.setText(trickWins);
            } else if (pid.equals("Computer_3")) {
                Computer3Pts.setText(points);
                Computer3Bids.setText(bids);
                Computer3Tricks.setText(trickWins);
            }
        }
    }

    /**
     * This method clears the players old cards
     */
    private void clearPlayerLabels () {
        //Removing played card from GUI
        for (Player p : playerLabelMap.keySet()) {
            for (int j = 0; j < playerLabelMap.get(p).size(); j++) {
                playerLabelMap.get(p).get(j).setIcon(null);
            }
            setLabels();
            displayHand();
        }
    }

    /**
     * Clear the cards displayed on the table
     */
    private void clearTableDisplay () {
        for (JLabel tLabel : tableLabels) {
            tLabel.setIcon(null);
        }
    }

    /**
     * This method populates the tables with the cards that each player has selected
     * @param pile the current pile of cards at the state
     */
    private void populateTable (Map < String, Card > pile){
        // take in the gamestate's current pile and populate the table accordingly
        JLabel label;
        for (String playerId : pile.keySet()) {
            if (playerId.equals("Player")) {
                label = tableLabels[0];
                displayCardImage(pile.get(playerId), label);
            } else if (playerId.equals("Computer_1")) {
                label = tableLabels[1];
                displayCardImage(pile.get(playerId), label);
            } else if (playerId.equals("Computer_2")) {
                label = tableLabels[2];
                displayCardImage(pile.get(playerId), label);
            } else if (playerId.equals("Computer_3")) {
                label = tableLabels[3];
                displayCardImage(pile.get(playerId), label);
            }
        }
    }

    /**
     * This method displays the hand of each player on the table
     */
    private void displayHand() {
        var currentState = GAMESTATE.getCurrentState();
        for (String playerId : currentState.keySet()) {
            Player p = currentState.get(playerId);
            var hand = p.getHand();

            if (!playerId.equals("Player")) {
                // for computers, display facedown cards
                for (int i = 0; i < hand.size(); i++) {
                    displayCardImage("./images/facedown.gif", playerLabelMap.get(p).get(i));
                }
            } else {
                for (int i = 0; i < hand.size(); i++) {
                    displayCardImage(hand.get(i), playerLabelMap.get(p).get(i));
                }
            }
        }
    }
    public static void main (String[]args) throws Exception {
        JFrame frame = new JFrame("BorderLayout Test");
        frame.setLayout(new FlowLayout());
        // get the screen size as a java dimension
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // get 2/3 of the height, and 2/3 of the width
        int height = screenSize.height ;
        int width = screenSize.width;

        // set the frame height and width
        frame.setSize(new Dimension(width, height));
        frame.setContentPane(new AppMain().CardGame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void createUIComponents () {
        // TODO: place custom component creation code here
    }
}
