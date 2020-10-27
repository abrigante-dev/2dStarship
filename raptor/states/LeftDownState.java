package raptor.states;

public class LeftDownState extends State
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
        return ++y;
    }
}
