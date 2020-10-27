package raptor.states;

public abstract class State
{
    public enum AIDirection {UP, DOWN, LEFT, RIGHT, LEFTUP, LEFTDOWN, RIGHTUP, RIGHTDOWN}

    public abstract double getXSpeed(double x);
    public abstract double getYSpeed(double y);

}
