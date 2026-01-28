package iuh.core;

/**
 * @author Nguyen Tan Nghi
 * @version 1.0
 * @created 1/28/2026 1:42 PM
 */

public interface Observer<T> {
    void update(T data);
}