/**
 * An interface to be implemented by the class that will represent the function
 * used to evaluate weights of edges in the graph. Ensures that there will
 * exist a functionality to calculate the weight correctly.
 * @param <T> the type of the weight.
 */
public interface EdgeFunction<T> {

    /**
     * A function that should return the evaluated weight of a vertex.
     * @param weight the weight of the vertex.
     * @return the evaluated weight of the vertex.
     */
    public T calculateWeight(T weight);
}
