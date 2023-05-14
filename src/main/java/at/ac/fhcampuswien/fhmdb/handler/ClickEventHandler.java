package at.ac.fhcampuswien.fhmdb.handler;

@FunctionalInterface
public interface ClickEventHandler<T> {
    void onClick(T item);
}
