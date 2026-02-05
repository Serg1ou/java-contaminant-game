package h01;

import fopbot.Direction;
import fopbot.Robot;
import fopbot.RobotFamily;
import h01.template.Contaminant;
import h01.template.TickBased;
import h01.template.Utils;
import org.tudalgo.algoutils.student.Student;

/**
 * A {@link Contaminant}-{@link Robot} that moves in a predefined way
 * and contaminates the floor.
 */
public class Contaminant2 extends Robot implements Contaminant, TickBased {

    /**
     * Creates a new {@link Contaminant2}.
     *
     * @param x             the initial x coordinate of the robot
     * @param y             the initial y coordinate of the robot
     * @param direction     the initial direction of the robot
     * @param numberOfCoins the initial number of coins of the robot
     */
    public Contaminant2(
        final int x,
        final int y,
        final Direction direction,
        final int numberOfCoins
    ) {
        super(x, y, direction, numberOfCoins, RobotFamily.SQUARE_AQUA);
    }

    @Override
    public int getUpdateDelay() {
        return 8;
    }

    @Override
    public void doMove() {

        boolean[] isDirectionClear = new boolean[4];

        // Turn off if no coins are left
        if (!hasAnyCoins()) {
            turnOff();
            return;
        }

        // Place coins until there are at least 2 on the current field
        while (Utils.getCoinAmount(getX(), getY()) < 2 && hasAnyCoins()) {
            putCoin();
        }

        // Check all four directions
        for (int i = 0; i < 4; i++) {
            turnLeft();
            isDirectionClear[i] = isFrontClear();
        }

        // Prefer left direction if possible
        if (isDirectionClear[0]) {
            turnLeft();
        } else {
            // Otherwise check the remaining directions
            for (int i = 3; i > 0; i--) {
                if (isDirectionClear[i]) {
                    for (int j = 0; j < i + 1; j++) {
                        turnLeft();
                    }
                    break;
                }
            }
        }

        move();
    }
}








