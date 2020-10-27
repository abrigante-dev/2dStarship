//abstract parent of all projectiles.
public abstract class Projectille extends GameObject
{
   //demonstrating the use of the enum type.
   public enum Owner {PLAYER,ENEMY};

   private Owner theOwner;

   protected float damage;

   public Projectille(double x,double y,double w,double h, Owner o)
   {
      super(x,y,w,h,true);
      theOwner = o;
   }

   public float getDamage()
   {
      return damage;
   }

   public Owner getOwner()
   {
      return theOwner;
   }
}