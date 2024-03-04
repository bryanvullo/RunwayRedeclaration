package uk.ac.soton.comp2211.dataStructure;

/**
 * A Tuple data structure which can hold any 2 objects
 * @param <F> The type of the first Object
 * @param <S> The type of the second Object
 */
public class Pair<F, S> {
    
    private F first;
    private S second;
    
    /**
     * Constructor to create a new Triplet with 3 objects
     * @param object0 the first object
     * @param object1 the second object
     */
    public Pair(F object0, S object1) {
        this.first = object0;
        this.second = object1;
    }
    
    /**
     * Returns the first object
     * @return the first object
     */
    public F getFirst() {
        return first;
    }
    
    /**
     * Returns the second object
     * @return the second object
     */
    public S getSecond() {
        return second;
    }
    
}
