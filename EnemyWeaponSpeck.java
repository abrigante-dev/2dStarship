import javafx.scene.canvas.GraphicsContext;

public class EnemyWeaponSpeck extends EnemyWeapon
{
    public EnemyWeaponSpeck(int xOffset, int yOffset, Enemy e)
    {
        super(e.getX(),e.getSize(),e.getHP(), xOffset, yOffset, e);
        wp = new WeaponSpeck((size/2) + xOffset,(size/2) + yOffset,e, false);
    }

    public void drawMe(GraphicsContext gc)
    {
        next.drawMe(gc);
        gc.setFill(color);
        gc.fillRect(baseEnemy.getX() + (size/2) + xOffset-1,baseEnemy.getY() + (size/2) + yOffset-1,2,2);
    }
}
