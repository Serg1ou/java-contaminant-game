package h01;

import fopbot.Robot;
import fopbot.World;
import h01.template.GameControllerBase;
import h01.template.Utils;
import org.tudalgo.algoutils.student.Student;

/**
 * A {@link GameController} controls the game loop and the {@link Robot}s and checks the win condition.
 */
public class GameController extends GameControllerBase {

    /**
     * Creates a new {@link GameControllerBase}.
     */
    public GameController() {
        setup();
    }

    @Override
    public void checkWinCondition() {
        boolean cleanerWins =
            Utils.getCoinAmount(0, World.getHeight() - 1) >= 200
                || (getContaminant1().isTurnedOff() && getContaminant2().isTurnedOff());

        int width = World.getWidth();
        int height = World.getHeight();
        int covered = 0;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (Utils.getCoinAmount(x, y) > 0) {
                    covered++;
                }
            }
        }

        boolean contaminantsWin =
            ((double) covered / (width * height)) >= 0.5;

        if (cleanerWins) {
            getContaminant1().turnOff();
            getContaminant2().turnOff();
            System.out.println("Cleaning robot won!");
            stopGame();
            return;
        }

        if (contaminantsWin) {
            getCleaningRobot().turnOff();
            System.out.println("Contaminants won!");
            stopGame();
        }
    }
}

