public interface Numeric<E extends Numeric> {
    public E add(E num);
    public E subtract(E num);
    public E multiply(E num);
    public E divide(E num);
    public String toString();
}
