public class BingoPattern implements BingoPatternInterface {
    private boolean[][] patternMatrix;

    public BingoPattern(boolean[][] pattern) {
        this.patternMatrix = pattern;
    }

    @Override
    public boolean[][] getPatternMatrix() {
        return patternMatrix;
    }

    @Override
    public boolean checkPattern(BingoCard card) {
        boolean[][] markedNumbers = card.getMarkedNumbers();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (patternMatrix[i][j] && !markedNumbers[i][j]) {
                    return false; // Si alguna casilla del patrón no está marcada, no cumple
                }
            }
        }
        return true;
    }

    // Métodos para crear patrones específicos
    public static BingoPattern create6PackVertical(int startRow, int startCol) {
        boolean[][] pattern = new boolean[5][5];
        for (int i = startRow; i < startRow + 2; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (i < 5 && j < 5) {
                    pattern[i][j] = true;
                }
            }
        }
        return new BingoPattern(pattern);
    }

    public static BingoPattern create6PackHorizontal(int startRow, int startCol) {
        boolean[][] pattern = new boolean[5][5];
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 2; j++) {
                if (i < 5 && j < 5) {
                    pattern[j][i] = true;
                }
            }
        }
        return new BingoPattern(pattern);
    }

    public static BingoPattern createDiamondPattern() {
        boolean[][] pattern = new boolean[5][5];
        pattern[0][2] = true;
        pattern[1][1] = true;
        pattern[1][3] = true;
        pattern[2][0] = true;
        pattern[2][4] = true;
        pattern[3][1] = true;
        pattern[3][3] = true;
        pattern[4][2] = true;
        return new BingoPattern(pattern);
    }

    public static BingoPattern createSmallCenterBoxPattern() {
        boolean[][] pattern = new boolean[5][5];
        pattern[1][1] = true;
        pattern[1][2] = true;
        pattern[1][3] = true;
        pattern[2][1] = true;
        pattern[2][3] = true;
        pattern[3][1] = true;
        pattern[3][2] = true;
        pattern[3][3] = true;
        return new BingoPattern(pattern);
    }

    public static BingoPattern createHorizontalPattern(int row) {
        boolean[][] pattern = new boolean[5][5];
        for (int i = 0; i < 5; i++) {
            pattern[row][i] = true;
        }
        return new BingoPattern(pattern);
    }

    public static BingoPattern createVerticalPattern(int col) {
        boolean[][] pattern = new boolean[5][5];
        for (int i = 0; i < 5; i++) {
            pattern[i][col] = true;
        }
        return new BingoPattern(pattern);
    }

    public static BingoPattern createDiagonalPattern(String direction) {
        boolean[][] pattern = new boolean[5][5];
        if ("leftToRight".equals(direction)) {
            for (int i = 0; i < 5; i++) {
                pattern[i][i] = true;
            }
        } else if ("rightToLeft".equals(direction)) {
            for (int i = 0; i < 5; i++) {
                pattern[i][4 - i] = true;
            }
        }
        return new BingoPattern(pattern);
    }

    public static BingoPattern createFullCardPattern() {
        boolean[][] pattern = new boolean[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                pattern[i][j] = true;
            }
        }
        return new BingoPattern(pattern);
    }
}


