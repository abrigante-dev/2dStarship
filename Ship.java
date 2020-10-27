public abstract class Ship extends GameObject
{
   protected double hp;
   
   //demonstrating how to use "inner" definitions (if you used public inner classes)
   private Projectille.Owner o;
   
   public Ship(double  x, double y, double w, double h, double hp_in, Projectille.Owner o_in)
   {
      super(x,y,w,h,true);
      hp = hp_in;
      o = o_in;
   }
   public Ship(double  x, double y, double w, double h, double hp_in, Projectille.Owner o_in, Boolean collision)
   {
      super(x,y,w,h,collision);
      hp = hp_in;
      o = o_in;
   }
   
   public double getHp()
   {
      return hp;
   }

   public double getX()
   {
      return posX;
   }
   public double getY()
   {
      return posY;
   }
   
   //overiding the onTriggerCollide method. The idea is that I have the ships do soething if they hit a projectile. Damages the ship and removes the projectile.
   public void onTriggerCollide(GameObject other)
   {
      if(other instanceof Projectille)
      {
         Projectille p = (Projectille) other;
         if(p.getOwner() != o)
         {
            hp -= p.getDamage();
            if(hp <= 0)
            {
               markToRemove();
               
            }
            p.markToRemove();
         }
      }
   }
}