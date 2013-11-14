import model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * (c) Author LostSoul
 */
public class BaseActionHelper {
    protected static boolean canAttack(Trooper self, Trooper enemy, World world) {

        if (world.isVisible(self.getVisionRange(), self.getX(), self.getY(), self.getStance(), enemy.getX(), enemy.getY(), enemy.getStance())
                && self.getDistanceTo(enemy) < self.getShootingRange()) {
            return true;
        }

        return false;

    }
    protected static boolean isNeedEat(Game game, World world, Trooper self, Move move) {
        if (self.isHoldingFieldRation() && self.getActionPoints() > game.getFieldRationEatCost()) {
            if (self.getActionPoints() < self.getInitialActionPoints()) {
                move.setAction(ActionType.EAT_FIELD_RATION);
                move.setDirection(Direction.CURRENT_POINT);
                return true;
            }


        }
        return false;
    }
    protected static void updateExtremePath(int destinationX, int destinationY) {
        SquardController.setFightGoalLocation(destinationX, destinationY);
    }

    protected static boolean isChangingStanceProper(Game game, World world, Trooper self, Move move, Trooper enemy, TrooperStance stance) {


        if (world.isVisible(self.getVisionRange(), self.getX(), self.getY(), stance, enemy.getX(), enemy.getY(), enemy.getStance())) {
            return true;
        }


        return true;
    }
    protected static List<Trooper> getNearestEnemies(World world) {
        List<Trooper> enemyList = new ArrayList<>();
        for (Trooper trooper : world.getTroopers()) {
            if (!trooper.isTeammate()) {
                enemyList.add(trooper);
            }
        }
        return enemyList;
    }

}