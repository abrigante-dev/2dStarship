package raptor.states;

public class LeftUpState extends State
{
    public double getXSpeed(double x)
    {
        if(x > 1)
        {
            return --x;
        } else {
            return 0.0;
        }

    }
    public double getYSpeed(double y)
    {
        if(y > 1)
        {
            return --y;
        } else {
            return 0.0;
        }
    }
}
