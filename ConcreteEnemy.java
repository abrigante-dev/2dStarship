import javafx.scene.canvas.GraphicsContext;
import raptor.states.*;

import java.util.ArrayList;

public class ConcreteEnemy extends Enemy
{
    int size;
    State currentState;

    ArrayList<ArrayList<Object>> aiList = new ArrayList<>();
    public ConcreteEnemy(Enemy e, double posX, Float spawnTime)
    {
        super(posX,e.getSize(), e.getHP(), true);
        color = e.getColor();
        money = e.getMoney();
        this.spawnTime = spawnTime;
        this.size = e.getSize();
        setWeaponType(e.getWepType(),e.getNumOfWeps());
        aiList = ((ConcreteEnemy)e).getAIList();
        if(e.getNumOfWeps() == 1)
        {
            setWeapon1(e.getxOffset1(), e.getyOffset1());
        } else if (e.getNumOfWeps() ==2)
        {
            setWeapon1(e.getxOffset1(), e.getyOffset1());
            setWeapon2(e.getxOffset2(), e.getyOffset2());
        }
        setCurrentState();

    }
    public ConcreteEnemy(int size, String colorString, int hp,  int money)
    {
        super(0,size,hp, true);
        color = getColor(colorString);
        this.colorString = colorString;
        this.money = money;
        this.size = size;
    }

    public int getmoney()
    {
        return money;
    }

    public void setAI(ArrayList<ArrayList<Object>> aiArray)
    {
        aiList = aiArray;
    }

    public ArrayList<ArrayList<Object>> getAIList()
    {
        return aiList;
    }
    int frames = 0;
    String direction;
    int currentStateIndex = 0;
    int prevStateIndex = 0;
    private void setCurrentState()
    {

        if(aiList.size() > 1)
        {
            direction = ((String)aiList.get(currentStateIndex).get(0));
        } else {
             direction = ((String)aiList.get(0).get(0));
        }
        if(direction.equals("left"))
        {
            currentState = new LeftState();
        } else if(direction.equals("leftup"))
        {
            currentState = new LeftUpState();
        } else if(direction.equals("leftdown"))
        {
            currentState = new LeftDownState();
        } else if(direction.equals("right"))
        {
            currentState = new RightState();
        } else if(direction.equals("rightup"))
        {
            currentState = new RightUpState();
        } else if(direction.equals("rightdown"))
        {
            currentState = new RightDownState();
        } else if(direction.equals("up"))
        {
            currentState = new UpState();
        } else {
            currentState = new DownState();
        }
    }
    public void run()
    {
        if(currentStateIndex != prevStateIndex)
        {
            if(prevStateIndex == aiList.size()-1)
            {
                prevStateIndex = -1;
            }
            prevStateIndex++;
            setCurrentState();
        }
        if(frames >= ((int)aiList.get(currentStateIndex).get(1)))
        {
            if(currentStateIndex == aiList.size()-1)
            {
                currentStateIndex = 0;
            } else {
                currentStateIndex++;
            }
            frames = 0;
        }
        posX = currentState.getXSpeed(posX)*.9 + .1*currentState.getXSpeed(posX);
        //posX = currentState.getXSpeed(posX);
        posY = currentState.getYSpeed(posY)*.9 + .1*currentState.getYSpeed(posY);
        //posY = currentState.getYSpeed(posY);
        frames++;
    }
    public void drawMe(GraphicsContext gc)
    {
        gc.setFill(color);
        gc.fillRect(posX,posY,size,size);
    }

    @Override
    public void onTriggerCollide(GameObject other)
    {
    if(other instanceof Projectille)
    {
        Projectille p = (Projectille) other;
        if(p.getOwner() != Projectille.Owner.ENEMY)
        {
            hp -= p.getDamage();
            if(hp < 1)
            {
                Player.getInstance().setMoney(Player.getInstance().getMoney() + money);
                markToRemove();

            }
        p.markToRemove();
        }
    }
    }
}
