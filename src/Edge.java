/**
 * A class representing an edge of the graph.
 * @param <T> the type to be used for the weight of the edge.
 */
public class Edge<T extends Comparable & Weight> {
    private Vertex<T> origin;
    private Vertex<T> destination;
    private T weight;

    /**
     * Constructs an edge between an origin and a destination with the desired
     * weight.
     * @param origin the origin vertex.
     * @param destination the destination vertex.
     * @param weight the weight of the vertex.
     */
    public Edge(Vertex<T> origin, Vertex<T> destination, T weight) {
        this.origin = origin;
        this.destination = destination;
        this.weight = weight;
    }

    /**
     * Returns the origin vertex.
     * @return the origin vertex.
     */
    public Vertex<T> getOrigin() {
        return origin;
    }

    /**
     * Returns the destination vertex.
     * @return the destination vertex.
     */
    public Vertex<T> getDestination() {
        return destination;
    }

    /**
     * Returns the weight of the vertex.
     * @return the weight of the vertex.
     */
    public T getWeight() {
        return weight;
    }
}
