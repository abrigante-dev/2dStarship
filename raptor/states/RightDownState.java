package raptor.states;

public class RightDownState extends State
{
    public double getXSpeed(double x)
    {
        return ++x;
    }
    public double getYSpeed(double y)
    {
        return ++y;
    }
}
