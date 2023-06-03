package at.ac.fhcampuswien.fhmdb.subscription;

public interface Observable {

    void subscribe(EventType eventType, Observer listener);
    void notify(EventType eventType);
}
