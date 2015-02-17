import java.util.ArrayList;

/**
 * A class representing a directed acyclic graph.
 */
public class DAG<T extends Comparable<T> & Weight<T>> {
    private ArrayList<Vertex<T>> vertices;
    private ArrayList<Edge<T>> edges;
    private boolean sorted;

    /**
     * Constructs a directed acyclic graph.
     */
    public DAG() {
        vertices = new ArrayList<Vertex<T>>();
        edges = new ArrayList<Edge<T>>();
        sorted = true;
    }

    /**
     * Checks if the ArrayList containing the vertices is currently sorted
     * topologically.
     * @return true if the ArrayList is sorted, false if not.
     */
    public boolean isSorted() {
        return sorted;
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
            sorted = false;
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
    public void orderTopological() {

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
        sorted = true;
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

    /**
     * Finds the longest path between two vertices in the graph.
     * @param start the vertex where the path begins.
     * @param goal the vertex where the path ends.
     * @param vertexFunction the function to evaluate the weight of the
     *                       vertices.
     * @param edgeFunction the function to evaluate the weight of the edges.
     * @return the weight of the longest path if it exists, if not null is
     * returned.
     */
    public T findLongestPath(Vertex<T> start, Vertex<T> goal,
                             VertexFunction<T> vertexFunction,
                             EdgeFunction<T> edgeFunction) {

        /*Initially set the return weight to null.*/
        T returnWeight = null;

        /*If we are already at the goal, return the weight of the goal.*/
        if (start == goal) {
            returnWeight = vertexFunction.calculateWeight(goal.getWeight());
        } else {

            /*Set the highest found weight between the start and the goal to
            * null.*/
            T highestWeight = null;

            /*For all the outgoing edges of the current vertex, do the
            following: */
            for (Edge<T> edge : start.getOutgoingEdges()) {

                /*Pick out the destination of the edge.*/
                Vertex<T> destination = edge.getDestination();

                /*If the index of the destination vertex is greater than the
                * index of our goal vertex, skip over it as there will be
                * no path to the goal originating from it. This assumes
                * the list of vertices is topologically sorted.*/
                if (sorted && vertices.indexOf(destination) >
                        vertices.indexOf(goal))
                    continue;

                /*Make a recursive call to this function using the new vertex
                * as the start but retaining the original goal.*/
                T weight = findLongestPath(destination, goal,
                        vertexFunction, edgeFunction);

                /*If there were no valid paths found in the next vertex,
                * dismiss it.*/
                if (weight == null)
                    continue;

                /*Add the weight of the edge leading to the next vertex to
                * the already returned value.*/
                 weight = weight.sum(edgeFunction.calculateWeight(
                         edge.getWeight()));

                /*If no weights have been recorded, set this one as the
                * highest one.*/
                if (highestWeight == null) {
                    highestWeight = weight;

                    /*Set the return weight to the weight of the returned
                    * path plus the weight of this vertex.*/
                    returnWeight = highestWeight.sum(vertexFunction.
                            calculateWeight(start.getWeight()));

                    /*If other weights have previously been recorded and this
                    * new one is the biggest one, replace the old.*/
                } else if (highestWeight.compareTo(weight) < 0) {
                    highestWeight = weight;
                    returnWeight = highestWeight.sum(vertexFunction.
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
