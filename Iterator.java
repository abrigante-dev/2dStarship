public interface Iterator {

     // indicates whether there are more elements to
     // iterate over
     boolean hasNext();
     // returns the next element
     Enemy next();
     Enemy getCurrent();

}
