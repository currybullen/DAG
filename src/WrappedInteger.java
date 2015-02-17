/**
 * A sample type to be used with the graph, with the same behaviour as you would
 * expect from an integer.
 */
public class WrappedInteger implements Comparable<WrappedInteger>,
        Weight<WrappedInteger> {
    private int value;

    /**
     * Constructs a WrappedInteger.
     * @param value the desired value for the WrappedInteger.
     */
    public WrappedInteger(int value) {
        this.value = value;
    }

    /**
     * Returns the value of this integer.
     * @return
     */
    public int getValue() {
        return value;
    }

    @Override
    public WrappedInteger sum(WrappedInteger weight) {
        return new WrappedInteger(value + weight.getValue());
    }

    @Override
    public int compareTo(WrappedInteger weight) {
        if (weight.getValue() > value)
            return -1;
        if (weight.getValue() < value)
            return 1;
        return 0;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
