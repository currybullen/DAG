import java.util.ArrayList;

/**
 * A class representing a directed acyclic graph.
 */
public class DAG<T extends Comparable<T> & Weight<T>> {
    private VertexFunction<T> vertexFunction;
    private EdgeFunction<T> edgeFunction;
    private ArrayList<Vertex<T>> vertices;
    private ArrayList<Edge<T>> edges;
    private boolean isSorted;

    /**
     * Constructs a directed acyclic graph.
     * @param vertexFunction a class used to evaluate the weight of the vertices
     *                       in the graph.
     * @param edgeFunction a class used to evaluate the weight of the edges in
     *                     the graph.
     */
    public DAG(VertexFunction<T> vertexFunction, EdgeFunction<T> edgeFunction) {
        this.vertexFunction = vertexFunction;
        this.edgeFunction = edgeFunction;
        vertices = new ArrayList<Vertex<T>>();
        edges = new ArrayList<Edge<T>>();
        isSorted = true;
    }

    /**
     * Checks if the ArrayList containing the vertices is currently sorted
     * topologically.
     * @return True if the ArrayList is sorted, false if not.
     */
    public boolean isSorted() {
        return isSorted;
    }

    /**
     * Adds a vertex with the specified weight to the graph.
     * @param weight the weight of the vertex.
     * @return the added vertex.
     */
    public Vertex<T> addVertex(T weight) {
        Vertex<T> vertex = new Vertex<T>(weight);
        vertices.add(vertex);
        return vertex;
    }


    /**
     * Adds an edge to the graph, provided that the edge will not generate a
     * cycle. If a cycle would be generated, the edge is not added and null is
     * returned.
     * @param origin the origin vertex for the edge.
     * @param destination the destination vertex for the edge.
     * @param weight the weight of the edge.
     * @return the added edge.
     */
    public Edge<T> addEdge(Vertex<T> origin, Vertex<T> destination, T weight) {

        /*If the edge would generate a cycle, do not add it and return null.*/
        if (hasPath(destination, origin)) {
            System.err.println("Edge from " + origin.toString() + " to " +
                    destination.toString() + " was not added, would cause " +
                            "cyclical graph.");
            return null;
        } else {
            Edge edge = new Edge(origin, destination, weight);
            edges.add(edge);
            edge.getOrigin().addOutgoingEdge(edge);
            edge.getDestination().addIncomingEdge(edge);
            isSorted = false;
            return edge;
        }
    }

    /**
     * Internal function used to check if there is a path between two vertices.
     * @param start the starting vertex.
     * @param goal the goal vertex.
     * @return true if there exists a path, if not false.
     */
    private boolean hasPath(Vertex<T> start, Vertex<T> goal) {
        for (Edge<T> edge : start.getOutgoingEdges()) {
            if (edge.getDestination().equals(goal))
                return true;
            if (hasPath(edge.getDestination(), goal))
                return true;
        }

        return false;
    }

    /**
     * Orders the ArrayList containing the vertices of the graph in a
     * topological order.
     */
    public void orderVertices() {

        /*Retrieve all the vertices of the graph which do not have any incoming
        * edges.*/
        ArrayList<Vertex<T>> unorderedList = getOriginVertices();

        /*Create a new, ordered list of vertices to replace the old one.*/
        ArrayList<Vertex<T>> orderedList = new ArrayList<Vertex<T>>();

        /*While the list of vertices which do not have any incoming edges is
        * not empty, do the following: */
        while (!unorderedList.isEmpty()) {

            /*Remove a vertex from that list.*/
            Vertex<T> vertex = unorderedList.remove(0);

            /*Add it to the tail of the new, ordered list.*/
            orderedList.add(vertex);

            /*Remove all the outgoing edges from that vertex, if it would lead
            * to vertices without any incoming edges left, add those to the
            * unordered list.*/
            for (Edge<T> edge : vertex.getOutgoingEdges()) {
                Vertex<T> destination = edge.getDestination();
                destination.getIncomingEdges().remove(edge);
                if (destination.getIncomingEdges().isEmpty()) {
                    unorderedList.add(destination);
                }
            }
        }

        /*Since all of the incoming edges were previously removed in the
        * algorithm above, simply add them again.*/
        for (Edge<T> edge : edges) {
            edge.getDestination().addIncomingEdge(edge);
        }

        /*Set the ArrayList of vertices in this graph to the new, sorted one.*/
        vertices = orderedList;

        /*The vertices are now topologically sorted.*/
        isSorted = true;
    }

    /**
     * Returns an ArrayList containing all vertices that have no incoming edges
     * in the graph.
     * @return an ArrayList containing all vertices that have no incoming edges
     * in the graph.
     */
    private ArrayList<Vertex<T>> getOriginVertices() {
        ArrayList<Vertex<T>> originVertices = new ArrayList<Vertex<T>>();

        for (Vertex<T> vertex : vertices) {
            if (vertex.getIncomingEdges().isEmpty()) {
                originVertices.add(vertex);
            }
        }

        return originVertices;
    }

    //TODO Fix the algorithm.
    public T findLongestPath(Vertex<T> start, Vertex<T> finish) {
        T returnWeight = null;

        if (start == finish) {
            returnWeight = finish.getWeight();
        } else {
            T highestWeight = null;

            for (Edge<T> edge : start.getOutgoingEdges()) {
                Vertex<T> destination = edge.getDestination();
                if (vertices.indexOf(destination) > vertices.indexOf(finish))
                    continue;
                T weight = findLongestPath(destination, finish);
                if (weight == null)
                    continue;
                if (highestWeight == null) {
                    highestWeight = weight;
                    returnWeight = weight.sum(edgeFunction.calculateWeight(
                            edge.getWeight())).sum(vertexFunction.
                            calculateWeight(start.getWeight()));
                } else if (highestWeight.compareTo(weight) > 0) {
                    highestWeight = weight;
                    returnWeight = weight.sum(edgeFunction.calculateWeight(
                            edge.getWeight())).sum(vertexFunction.
                            calculateWeight(start.getWeight()));
                }
            }
        }

        return returnWeight;
    }

    /**
     * Test function used to print all the vertices of the graph.
     */
    public void printVertices() {
        System.out.println("Vertices:\n");

        for (Vertex vertex : vertices) {
            System.out.println("Identifier: " + vertex.getIdentifier() +
                    " Weight: " + vertex.getWeight());
        }
    }

    /**
     * Test function used to print all the edges of the graph.
     */
    public void printEdges() {
        System.out.println("Edges:\n");

        for (Edge edge : edges) {
            System.out.println("Origin: " +  edge.getOrigin().getIdentifier() +
                    " Destination: " + edge.getDestination().getIdentifier() +
                    " Weight: " + edge.getWeight().toString());
        }
    }
}
