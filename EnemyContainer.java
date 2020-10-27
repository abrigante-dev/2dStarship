import java.util.ArrayList;

public class EnemyContainer {

    public void addEnemy( Enemy e_in){
        enemies.add(e_in);
    }

    public void removeEnemy(Enemy e_in){
        enemies.remove(e_in);
    }

    public Iterator getIterator(){
        return new EnemyIterator();
    }

    //public Iterator setIterator () { }

    public int getSize(){
        return enemies.size();
    }

    private ArrayList<Enemy> enemies = new ArrayList<>();


    private class EnemyIterator implements Iterator{

        int index = 0;
        @Override
        public boolean hasNext() {
            if(index < enemies.size()){
                return true;
            }
            else{
                return false;
            }
        }

        @Override
        public Enemy next() {
           if(this.hasNext()){
               return enemies.get(index++);
           }
           else{
               return null;
           }
        }

        @Override
        public Enemy getCurrent() {
            Enemy toReturn = enemies.get(index);
            return toReturn;
        }
    }
}
