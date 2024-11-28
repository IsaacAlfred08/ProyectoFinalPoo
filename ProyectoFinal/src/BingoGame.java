public class BingoGame {
    private BingoBallCage ballCage;
    private BingoCard playerCard;
    private BingoPatternInterface pattern; // Usar interfaz para flexibilidad


    public BingoGame(BingoPatternInterface selectedPattern) {
        this.ballCage = new BingoBallCage();
        this.pattern = selectedPattern;
        this.playerCard = new BingoCard();
    }

    public BingoBallCage getBallCage() {
        return ballCage;
    }

    public BingoCard getPlayerCard() {
        return playerCard;
    }

    public boolean checkWinner() {
        return pattern.checkPattern(playerCard); // Delegar lógica a la interfaz
    }
}

