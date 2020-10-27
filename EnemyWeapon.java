import javafx.scene.canvas.GraphicsContext;

public abstract class EnemyWeapon extends Enemy
{
    Weapon wp = null;
    Enemy baseEnemy;
    Enemy next;
    int xOffset;
    int yOffset;
    public EnemyWeapon(double posX, int size, int hp, int xOffset, int yOffset, Enemy baseEnemy)
    {
        super(baseEnemy.getX(),size,hp, true);
        this.baseEnemy = baseEnemy;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    public void setNextEnemy(Enemy next)
    {
        this.next = next;
    }
    public Float getSpawnTime()
    {
        return baseEnemy.getSpawnTime();
    }
    public void run()
    {
        next.run();
        wp.run();
        posY+=.2;
    }
    public abstract void drawMe(GraphicsContext gc);

}
