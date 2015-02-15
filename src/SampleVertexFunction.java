/**
 * Created by c12mkn on 2015-02-12.
 */
public class SampleVertexFunction implements VertexFunction<WrappedInteger> {
    public WrappedInteger calculateWeight(WrappedInteger term) {
        return new WrappedInteger(term.getValue()*1);
    }
}
