package estimation.game;
import estimation.card.*;
import estimation.player.*;
public class GameManager {

    public static void main(String[] args) {
        // gameState stores the Key as each Player and the Integer as the Game Points
        GameState gameState = new GameState();

        var maxScoreOfRound = gameState.getMaxScore();
        int maxScore = 0; // max score out of all players
        gameState.setFirstRoundDealer();


        while (gameState.getRounds() != 12 && maxScore != 100) {
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<start of round " + gameState.getRounds() + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            // reset hand in the moment a new round starts
            gameState.resetHand();
            // for each round, get a new Deck, then give each player a hand.
            Deck deck = new Deck();
            // distribute cards for each players according to the number of rounds
            // number rounds logic is encapsulated within the gameState class
            // get trump card for the round
            Card trumpCard = deck.dealCard();
            System.out.println(trumpCard);
            gameState.setTrumpCard(trumpCard);
            gameState.distributeCards(deck);

            // get the current State
            var currentHand = gameState.getCurrentState();

            // start VERY first trick within round
            Player firstDealer = currentHand.get(gameState.findDealerId());
            String firstTrickStarterId = gameState.setFirstRoundTrickStarter(firstDealer.getId());

            // place bids
            // System.out.println(firstDealer.getId());
            System.out.println("--------------------------------PLACING BIDS--------------------------------------");
            gameState.setRoundBids(trumpCard, firstDealer.getId());
            System.out.println("--------------------------------BIDS PLACED--------------------------------------");
            // System.out.println(gameState.getRoundBids());

            // start trick , assume tricks is rounds
            int numberOfTricksPerRound = gameState.getRounds();

            String trickStarter = "";
            for (int i = 0; i < numberOfTricksPerRound; i++ ) {

                if (i == 0) {
                    trickStarter = firstTrickStarterId;
                }

                String trickWinnerId = gameState.playTrick(trickStarter);
                gameState.setNextTrickStarter(trickStarter, trickWinnerId);
                trickStarter = trickWinnerId;
            }

            /*
            for each player, we compare his trick wins with his bids and add or minus points accordingly
            */
            updatePoints(gameState);

            gameState.addRound();
            gameState.setNextDealer(firstDealer.getId());
            // reset all trick points at the end of the round
            gameState.resetTrickPoints();

            // get the maxScore
            maxScoreOfRound = gameState.getMaxScore();

            // 1 is the maxPoints, 0 is the maxPlayerId
            maxScore = Integer.parseInt(maxScoreOfRound.get(1));
            System.out.println("====================end of round =========================");
        }

        String overallWinnerId = maxScoreOfRound.get(0);
        System.out.println("WINNER: " + overallWinnerId);
    }


    public static void updatePoints(GameState state) {
        var currentState = state.getCurrentState();
        for (String playerId : currentState.keySet()) {
            Player currPlayer = currentState.get(playerId);
            int bids = currPlayer.getBids();
            int tricksWon = currPlayer.getTrickWins();

            if (bids == tricksWon) {
                currPlayer.addPoints(bids + 10);
            } else {
                currPlayer.minusPoints(bids + 10);
            }
            System.out.printf("%s Points : %d%n" , currPlayer.getId(), currPlayer.getPoints());
        }
    }
}