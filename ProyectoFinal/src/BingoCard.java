import java.util.Random;

public class BingoCard {
    private int[][] cardNumbers;  // Números en la carta
    private boolean[][] markedNumbers;  // Estado de marcado de cada posición

    public BingoCard() {
        this.cardNumbers = generateCardNumbers();
        this.markedNumbers = new boolean[5][5]; // Inicializar sin marcas
        this.markedNumbers[2][2] = true; // Marcar la casilla central como Free
    }

    private int[][] generateCardNumbers() {
        Random rand = new Random();
        int[][] numbers = new int[5][5];

        for (int col = 0; col < 5; col++) {
            int min = col * 15 + 1;
            int max = min + 15;

            for (int row = 0; row < 5; row++) {
                if (col == 2 && row == 2) {
                    numbers[row][col] = 0; // Casilla central es Free
                } else {
                    numbers[row][col] = rand.nextInt(max - min) + min;

                    // Evitar duplicados dentro de una columna
                    for (int prev = 0; prev < row; prev++) {
                        if (numbers[prev][col] == numbers[row][col]) {
                            row--; // Reintentar
                            break;
                        }
                    }
                }
            }
        }

        return numbers;
    }

    public void markNumber(int number) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (cardNumbers[i][j] == number) {
                    markedNumbers[i][j] = true;
                }
            }
        }
    }

    public boolean hasPattern(BingoPattern pattern) {
        boolean[][] patternMatrix = pattern.getPatternMatrix();

        // Verificar si el patrón se cumple
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (patternMatrix[i][j] && !markedNumbers[i][j]) {
                    return false; // Si una posición del patrón no está marcada, el patrón no se cumple
                }
            }
        }
        return true; // Todas las posiciones requeridas están marcadas
    }

    public int[][] getCardNumbers() {
        return cardNumbers;
    }

    public boolean[][] getMarkedNumbers() {
        return markedNumbers;
    }
}