/**
 * Created by c12mkn on 2015-02-12.
 */
public class WrappedInteger implements Comparable<WrappedInteger>,
        Weight<WrappedInteger> {
    private int value;

    public WrappedInteger(int value) {
        this.value = value;
    }

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
