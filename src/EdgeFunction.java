/**
 * An interface to be implemented by the class that will represent the function
 * used to evaluate weights of edges in the graph. Ensures that there will
 * exist a functionality to calculate the weight correctly.
 * @param <T> the type of the weight.
 */
public interface EdgeFunction<T> {
    public T calculateWeight(T weight);
}
