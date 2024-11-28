import javax.swing.*;
import java.awt.*;

public class PatternSelectionWindow extends JFrame {
    private BingoPattern selectedPattern;

    public PatternSelectionWindow() {
        setTitle("Seleccione un Patrón de Bingo");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3, 10, 10)); // Ajustamos para incluir más patrones

        // Botones para patrones básicos y avanzados
        JButton horizontalButton = new JButton("Línea Horizontal");
        horizontalButton.addActionListener(e -> selectHorizontalPattern());

        JButton verticalButton = new JButton("Línea Vertical");
        verticalButton.addActionListener(e -> selectVerticalPattern());

        JButton diagonalButton = new JButton("Diagonal");
        diagonalButton.addActionListener(e -> selectDiagonalPattern());

        JButton fullCardButton = new JButton("Cartón Completo");
        fullCardButton.addActionListener(e -> selectPattern(BingoPattern.createFullCardPattern()));

        JButton sixPackVerticalButton = new JButton("6-Pack Vertical");
        sixPackVerticalButton.addActionListener(e -> select6PackVertical());

        JButton sixPackHorizontalButton = new JButton("6-Pack Horizontal");
        sixPackHorizontalButton.addActionListener(e -> select6PackHorizontal());

        JButton diamondButton = new JButton("Diamante");
        diamondButton.addActionListener(e -> selectPattern(BingoPattern.createDiamondPattern()));

        JButton smallCenterBoxButton = new JButton("Small Center Box");
        smallCenterBoxButton.addActionListener(e -> selectPattern(BingoPattern.createSmallCenterBoxPattern()));


        // Añadir botones al layout
        add(horizontalButton);
        add(verticalButton);
        add(diagonalButton);
        add(fullCardButton);
        add(sixPackVerticalButton);
        add(sixPackHorizontalButton);
        add(diamondButton);
        add(smallCenterBoxButton);

        setLocationRelativeTo(null); // Centrar la ventana
    }

    private void selectPattern(BingoPattern pattern) {
        selectedPattern = pattern;
        setVisible(false);
        new BingoGUI(selectedPattern).setVisible(true);
        dispose();
    }

    private void selectHorizontalPattern() {
        String rowInput = JOptionPane.showInputDialog(this, "Seleccione la fila (0-4):");
        try {
            int row = Integer.parseInt(rowInput);
            if (row >= 0 && row < 5) {
                selectPattern(BingoPattern.createHorizontalPattern(row));
            } else {
                JOptionPane.showMessageDialog(this, "Fila inválida. Debe estar entre 0 y 4.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Entrada inválida. Introduzca un número entre 0 y 4.");
        }
    }

    private void selectVerticalPattern() {
        String colInput = JOptionPane.showInputDialog(this, "Seleccione la columna (0-4):");
        try {
            int col = Integer.parseInt(colInput);
            if (col >= 0 && col < 5) {
                selectPattern(BingoPattern.createVerticalPattern(col));
            } else {
                JOptionPane.showMessageDialog(this, "Columna inválida. Debe estar entre 0 y 4.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Entrada inválida. Introduzca un número entre 0 y 4.");
        }
    }

    private void selectDiagonalPattern() {
        String[] options = {"Diagonal Izquierda a Derecha", "Diagonal Derecha a Izquierda"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Seleccione el tipo de diagonal:",
                "Seleccionar Diagonal",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            selectPattern(BingoPattern.createDiagonalPattern("leftToRight"));
        } else if (choice == 1) {
            selectPattern(BingoPattern.createDiagonalPattern("rightToLeft"));
        }
    }

    private void select6PackVertical() {
        String rowInput = JOptionPane.showInputDialog(this, "Seleccione la fila inicial (0-3):");
        String colInput = JOptionPane.showInputDialog(this, "Seleccione la columna inicial (0-2):");
        try {
            int row = Integer.parseInt(rowInput);
            int col = Integer.parseInt(colInput);
            if (row >= 0 && row < 4 && col >= 0 && col < 3) {
                selectPattern(BingoPattern.create6PackVertical(row, col));
            } else {
                JOptionPane.showMessageDialog(this, "Posición inválida. Fila: 0-3, Columna: 0-2.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Entrada inválida. Introduzca números válidos.");
        }
    }

    private void select6PackHorizontal() {
        String rowInput = JOptionPane.showInputDialog(this, "Seleccione la fila inicial (0-2):");
        String colInput = JOptionPane.showInputDialog(this, "Seleccione la columna inicial (0-3):");
        try {
            int row = Integer.parseInt(rowInput);
            int col = Integer.parseInt(colInput);
            if (row >= 0 && row < 3 && col >= 0 && col < 4) {
                selectPattern(BingoPattern.create6PackHorizontal(row, col));
            } else {
                JOptionPane.showMessageDialog(this, "Posición inválida. Fila: 0-2, Columna: 0-3.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Entrada inválida. Introduzca números válidos.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PatternSelectionWindow().setVisible(true));
    }
}