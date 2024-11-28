import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class BingoGUI extends JFrame {
    private BingoGame game; // Lógica del juego
    private JLabel winnerLabel; // Etiqueta para mostrar el ganador
    private JButton drawButton; // Botón para sacar una bola
    private JButton viewCardButton; // Botón para ver la carta
    private Map<Integer, JLabel> bingoBoardLabels; // Map para almacenar los JLabels del tablero de Bingo
    private JFrame cardWindow; // Ventana de la carta del jugador
    private JPanel cardPanel; // Panel de la carta del jugador

    public BingoGUI(BingoPatternInterface selectedPattern) {
        game = new BingoGame(selectedPattern); // Inicializar el juego con el patrón seleccionado
        bingoBoardLabels = new HashMap<>();
        setupFrame();
    }

    private void setupFrame() {
        setTitle("Juego de Bingo");
        setSize(1000, 600); // Ajustamos el tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear panel para los botones
        JPanel topPanel = new JPanel(new FlowLayout());

        // Botón "Sacar Bola"
        drawButton = new JButton("Sacar Bola");
        drawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDrawBall();
            }
        });

        // Botón "Ver Carta"
        viewCardButton = new JButton("Ver Carta");
        viewCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPlayerCardWindow();
            }
        });

        // Etiqueta para mostrar mensaje de ganador
        winnerLabel = new JLabel("", SwingConstants.CENTER);
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        winnerLabel.setForeground(Color.RED);

        topPanel.add(drawButton);
        topPanel.add(viewCardButton);
        topPanel.add(winnerLabel);

        JPanel tombolaPanel = createTombola(); // Cambiar a diseño horizontal

        add(topPanel, BorderLayout.NORTH);
        add(tombolaPanel, BorderLayout.CENTER);
    }

    private JPanel createTombola() {
        JPanel tombolaPanel = new JPanel();
        tombolaPanel.setLayout(new BorderLayout()); // Usamos un diseño principal con subdivisiones

        // Panel para las letras BINGO (vertical)
        JPanel lettersPanel = new JPanel(new GridLayout(5, 1, 0, 10)); // Una letra por fila
        String[] letters = {"B", "I", "N", "G", "O"};
        Color[] colors = {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, new Color(128, 0, 128)}; // Colores por letra

        for (int i = 0; i < letters.length; i++) {
            JLabel letterLabel = new JLabel(letters[i], SwingConstants.CENTER);
            letterLabel.setFont(new Font("Arial", Font.BOLD, 24));
            letterLabel.setOpaque(true);
            letterLabel.setBackground(colors[i]); // Color correspondiente
            letterLabel.setForeground(Color.WHITE); // Texto en blanco para contraste
            letterLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            lettersPanel.add(letterLabel);
        }

        // Panel para los números (5 columnas y 15 filas por columna)
        JPanel numbersPanel = new JPanel(new GridLayout(5, 15, 2, 2)); // Columnas y filas
        for (int i = 0; i < letters.length; i++) {
            int start = 1 + 15 * i; // Rango de números para cada letra
            int end = start + 14;
            for (int number = start; number <= end; number++) {
                JLabel numberLabel = new JLabel(String.valueOf(number), SwingConstants.CENTER);
                numberLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                numberLabel.setOpaque(true);
                numberLabel.setBackground(Color.DARK_GRAY);
                numberLabel.setForeground(Color.LIGHT_GRAY); // Números gris claro
                numberLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                bingoBoardLabels.put(number, numberLabel); // Registrar en el Map
                numbersPanel.add(numberLabel);
            }
        }

        tombolaPanel.add(lettersPanel, BorderLayout.WEST); // Letras a la izquierda
        tombolaPanel.add(numbersPanel, BorderLayout.CENTER); // Números a la derecha
        tombolaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen

        return tombolaPanel;
    }

    private void handleDrawBall() {
        int newBall = game.getBallCage().drawBall();
        if (bingoBoardLabels.containsKey(newBall)) {
            JLabel label = bingoBoardLabels.get(newBall);
            label.setBackground(Color.YELLOW); // Marcar número en amarillo
        }

        game.getPlayerCard().markNumber(newBall);

        if (game.checkWinner()) {
            showWinnerMessage();
        }

        // Actualizar la carta del jugador si la ventana está abierta
        if (cardWindow != null && cardWindow.isVisible()) {
            updateCardPanel(); // Esta es la llamada correcta
        }
    }

    private void showWinnerMessage() {
        winnerLabel.setText("¡Ganaste!");
        drawButton.setEnabled(false);
    }

    private void showPlayerCardWindow() {
        // Solo crear la ventana si no está abierta
        if (cardWindow == null || !cardWindow.isVisible()) {
            cardWindow = new JFrame("Carta del Jugador");
            cardWindow.setSize(400, 400);
            cardWindow.setLayout(new BorderLayout());
            cardWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel headerPanel = new JPanel(new GridLayout(1, 5));
            String[] bingoHeader = {"B", "I", "N", "G", "O"};
            for (String letter : bingoHeader) {
                JLabel headerLabel = new JLabel(letter, SwingConstants.CENTER);
                headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
                headerLabel.setOpaque(true);
                headerLabel.setBackground(Color.CYAN);
                headerLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                headerPanel.add(headerLabel);
            }

            cardPanel = new JPanel(new GridLayout(5, 5));
            updateCardPanel(); // Inicializa la carta

            cardWindow.add(headerPanel, BorderLayout.NORTH);
            cardWindow.add(cardPanel, BorderLayout.CENTER);
            cardWindow.setVisible(true);
        }
    }

    private void updateCardPanel() {
        // Actualiza la carta con los números marcados
        cardPanel.removeAll(); // Limpiar el panel

        int[][] cardNumbers = game.getPlayerCard().getCardNumbers();
        boolean[][] markedNumbers = game.getPlayerCard().getMarkedNumbers();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                String text = (i == 2 && j == 2) ? "Free" : String.valueOf(cardNumbers[i][j]);
                JLabel numberLabel = new JLabel(text, SwingConstants.CENTER);
                numberLabel.setOpaque(true);
                numberLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                numberLabel.setFont(new Font("Arial", Font.BOLD, 14));
                numberLabel.setPreferredSize(new Dimension(50, 50));

                if (markedNumbers[i][j]) {
                    numberLabel.setBackground(Color.GREEN);
                } else {
                    numberLabel.setBackground(Color.LIGHT_GRAY);
                }
                cardPanel.add(numberLabel);
            }
        }

        cardPanel.revalidate(); // Asegura que se repinte el panel
        cardPanel.repaint();
    }
}


