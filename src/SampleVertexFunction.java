/**
 * A sample vertex function to be used with the WrappedInteger type, the
 * defined behaviour is to double the weight.
 */
public class SampleVertexFunction implements VertexFunction<WrappedInteger> {
    public WrappedInteger calculateWeight(WrappedInteger term) {
        return new WrappedInteger(term.getValue()*2);
    }
}
