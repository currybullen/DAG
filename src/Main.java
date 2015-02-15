/**
 * Created by c12mkn on 2015-02-12.
 */
public class Main {
    public static void main(String[] args) {
        new Main().go();
    }

    public void go() {
        DAG<WrappedInteger> dag = new DAG<WrappedInteger>(new SampleVertexFunction(),
                new SampleEdgeFunction());

        Vertex<WrappedInteger> a = dag.addVertex(new WrappedInteger(7));
        Vertex<WrappedInteger> b = dag.addVertex(new WrappedInteger(9));
        Vertex<WrappedInteger> c = dag.addVertex(new WrappedInteger(11));
        Vertex<WrappedInteger> d = dag.addVertex(new WrappedInteger(9));

        dag.addEdge(a, b, new WrappedInteger(13));
        dag.addEdge(a, c, new WrappedInteger(2));
        dag.addEdge(a, d, new WrappedInteger(17));
        dag.addEdge(c, b, new WrappedInteger(19));
        dag.addEdge(d, b, new WrappedInteger(11));

        if (!dag.isSorted()) {
            dag.orderVertices();
        }

        dag.printVertices();
        System.out.println();
        dag.printEdges();

        WrappedInteger test = dag.findLongestPath(a, b);

        System.out.println();
        System.out.println(test.toString());
    }
}
