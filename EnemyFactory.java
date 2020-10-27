import java.util.ArrayList;
import java.util.Map;

public class EnemyFactory
{
    public EnemyFactory()
    {}

    static ArrayList<Enemy> enemyArray;
    Enemy tempEnemy;
    EnemyWeapon tempEW;
    EnemyWeapon tempEW2;
    public Enemy createEnemy(Map<String, Enemy> enemyMap, String type, int xOffset, Float spawnTime)
    {
        tempEnemy = new ConcreteEnemy(enemyMap.get(type), xOffset, spawnTime);
        if(tempEnemy.getNumOfWeps() == 1)
        {
            if(tempEnemy.getWepType() == Enemy.WeaponType.SPECK)
            {
                tempEW = new EnemyWeaponSpeck(tempEnemy.getxOffset1(), tempEnemy.getyOffset1(), tempEnemy);
                tempEW.setNextEnemy(tempEnemy);
                return tempEW;
            } else if(tempEnemy.getWepType() == Enemy.WeaponType.SIDE)
            {
                tempEW = new EnemyWeaponSides(tempEnemy.getxOffset1(), tempEnemy.getyOffset1(), tempEnemy);
                tempEW.setNextEnemy(tempEnemy);
                return tempEW;
            }
        } else if(tempEnemy.getNumOfWeps() == 2)
        {
            if(tempEnemy.getWepType() == Enemy.WeaponType.SPECK)
            {
                tempEW = new EnemyWeaponSpeck(tempEnemy.getxOffset1(), tempEnemy.getyOffset1(), tempEnemy);
                tempEW.setNextEnemy(tempEnemy);
                tempEW2 = new EnemyWeaponSpeck(tempEnemy.getxOffset2(), tempEnemy.getyOffset2(), tempEnemy);
                tempEW2.setNextEnemy(tempEW);
                return tempEW2;
            }
        } else {
            return tempEnemy;
        }
        return tempEnemy;
    }
}
