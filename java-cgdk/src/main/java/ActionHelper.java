import model.*;

import java.util.*;

/**
 * (c) Author LostSoul
 */
public class ActionHelper {
    static public boolean isNeedHeal(Game game, World world, Trooper self, Move move) {

        Trooper weakestTroop = null;
        if (self.getActionPoints() >= game.getFieldMedicHealCost()) {
            for (Trooper trooper : world.getTroopers()) {
                if (trooper.isTeammate() && (trooper.getMaximalHitpoints() - trooper.getHitpoints() > game.getFieldMedicHealBonusHitpoints())) {       //TODO if current HP is not full
                    if (weakestTroop == null) {
                        weakestTroop = trooper;
                    }
                    if (self.getDistanceTo(trooper) < self.getDistanceTo(weakestTroop)) {
                        weakestTroop = trooper;
                    }                                 // TODO need to check path instead distance
                }
            }

        }
        if(weakestTroop == null){
            return false;
        }

        if (self.getDistanceTo(weakestTroop) > 1) {        //TODO go to the trooper
            move.setAction(ActionType.MOVE);
            move.setX(weakestTroop.getX());
            move.setY(weakestTroop.getY());
            return true;
        }

        else {
            move.setAction(ActionType.HEAL);
            move.setX(weakestTroop.getX());
            move.setY(weakestTroop.getY());
            return true;
        }


    }

    static public boolean isNeedAttack(Game game, World world, Trooper self, Move move) {

        if (isShotWillDeny(game, world, self, move)) {
            return true;
        }

        Trooper weakestEnemy = null;
        for (Trooper enemy : getNearestEnemies(world)) {

            if (canAttack(self, enemy, world)) {
                if (weakestEnemy == null) {
                    weakestEnemy = enemy;
                }
                if (weakestEnemy.getHitpoints() > enemy.getHitpoints()) {
                    weakestEnemy = enemy;
                }
            }
        }

        if (weakestEnemy != null) {
            move.setAction(ActionType.SHOOT);
            move.setX(weakestEnemy.getX());
            move.setY(weakestEnemy.getY());
            return true;
        }

        return false;
    }


    static public boolean isShotWillDeny(Game game, World world, Trooper self, Move move) {
        if (self.getActionPoints() > self.getShootCost())
            for (Trooper enemy : getNearestEnemies(world)) {
                if (enemy.getHitpoints() < self.getDamage() && canAttack(self, enemy, world)) {
                    move.setAction(ActionType.SHOOT);
                    move.setX(enemy.getX());
                    move.setY(enemy.getY());
                    return true;
                }
            }

        return false;
    }


    public static List<Trooper> getNearestEnemies(World world) {
        List<Trooper> enemyList = new ArrayList<>();
        for (Trooper trooper : world.getTroopers()) {
            if (!trooper.isTeammate()) {
                enemyList.add(trooper);
            }
        }
        return enemyList;
    }

    private static boolean canAttack(Trooper self, Trooper enemy, World world) {

        if (world.isVisible(self.getVisionRange(), self.getX(), self.getY(), self.getStance(), enemy.getX(), enemy.getY(), enemy.getStance())
                &&self.getDistanceTo(enemy)< self.getShootingRange()) {
            return true;
        }

        return false;

    }
}
