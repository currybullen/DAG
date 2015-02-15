/**
 * An interface to be implemented by all classes intended to be used as weights
 * in the graph. Ensures the weights provide a function to be added together.
 * @param <T> the type of the weight.
 */
public interface Weight<T> {
    public T sum(T weight);
 }
