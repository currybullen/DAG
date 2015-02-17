/**
 * A sample vertex function to be used with the WrappedInteger type, the
 * defined behaviour is to triple the weight.
 */
public class SampleEdgeFunction implements EdgeFunction<WrappedInteger> {
    public WrappedInteger calculateWeight(WrappedInteger term) {
        return new WrappedInteger(term.getValue()*3);
    }
}
