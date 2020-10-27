package raptor.states;

public class RightState extends State
{
    public double getXSpeed(double x)
    {
        return ++x;
    }
    public double getYSpeed(double y)
    {
        return y;
    }
}
