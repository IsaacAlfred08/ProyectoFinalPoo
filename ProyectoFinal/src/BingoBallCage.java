import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BingoBallCage {
    private List<Integer> drawnBalls = new ArrayList<>();
    private Random random = new Random();
    private final int totalBalls = 75;

    public int drawBall() {
        int ball;
        do {
            ball = random.nextInt(totalBalls) + 1;
        } while (drawnBalls.contains(ball));
        drawnBalls.add(ball);
        return ball;
    }

    public List<Integer> getDrawnBalls() {
        return drawnBalls;
    }
}


