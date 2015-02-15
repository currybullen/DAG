import java.util.ArrayList;

/**
 * A class representing a vertex.
 * @param <T> the type of weight to be used in the vertex.
 */
public class Vertex<T extends Comparable & Weight> {
    private int identifier;
    private T weight;
    private ArrayList<Edge<T>> incomingEdges;
    private ArrayList<Edge<T>> outgoingEdges;
    private static int highestIdentifier = 0;

    /**
     * Constructs a vertex with the specified weight.
     * @param weight
     */
    public Vertex(T weight) {
        this.weight = weight;
        identifier = highestIdentifier++;
        incomingEdges = new ArrayList<Edge<T>>();
        outgoingEdges = new ArrayList<Edge<T>>();
    }

    /**
     * Adds an edge as an incoming edge to this vertex.
     * @param edge an edge to be added as incoming.
     */
    public void addIncomingEdge(Edge<T> edge) {
        incomingEdges.add(edge);
    }

    /**
     * Adds an edge as an outgoing edge from this vertex.
     * @param edge an edge to be added as outgoing.
     */
    public void addOutgoingEdge(Edge<T> edge) {
        outgoingEdges.add(edge);
    }

    /**
     * Returns the unique identifier of this vertex.
     * @return the unique identifier of this vertex.
     */
    public int getIdentifier() {
        return identifier;
    }

    /**
     * Returns the weight of this vertex.
     * @return the weight of this vertex.
     */
    public T getWeight() {
        return weight;
    }

    /**
     * Returns an ArrayList containing all of the incoming edges to this
     * vertex.
     * @return an ArrayList containing all of the incoming edges to this
     * vertex.
     */
    public ArrayList<Edge<T>> getIncomingEdges() {
        return incomingEdges;
    }

    /**
     * Returns an ArrayList containing all of the outgoing edges from this
     * vertex.
     * @return an ArrayList containing all of the outgoing edges from this
     * vertex.
     */
    public ArrayList<Edge<T>> getOutgoingEdges() {
        return outgoingEdges;
    }
}
