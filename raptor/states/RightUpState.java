package raptor.states;

public class RightUpState extends State
{
    public double getXSpeed(double x)
    {
        return ++x;
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
