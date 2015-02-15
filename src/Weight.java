/**
 * An interface to be implemented by all classes intended to be used as weights
 * in the graph. Ensures the weights provide a function to be added together.
 * @param <T> the type of the weight.
 */
public interface Weight<T> {

    /**
     * A function that should return the sum of another weight and this one.
     * @param weight the summed weight of this weight and another one.
     * @return the summed weight of this and another one.
     */
    public T sum(T weight);
 }
