/**
 * An interface to be implemented by the class that will represent the function
 * used to evaluate weights of vertices in the graph. Ensures that there will
 * exist a functionality to calculate the weight correctly.
 * @param <T> the type of the weight.
 */
public interface VertexFunction<T> {

    /**
     * A function that should return the evaluated weight of an edge.
     * @param weight the weight of the edge.
     * @return the evaluated weight of the edge.
     */
    public T calculateWeight(T weight);
}
