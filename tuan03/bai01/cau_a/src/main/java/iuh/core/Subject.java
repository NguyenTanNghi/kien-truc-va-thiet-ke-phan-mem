package iuh.core;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:42 PM
 */
public interface Subject<T> {
    void attach(Observer<T> observer);
    void detach(Observer<T> observer);
    void notifyObservers(T data);
}