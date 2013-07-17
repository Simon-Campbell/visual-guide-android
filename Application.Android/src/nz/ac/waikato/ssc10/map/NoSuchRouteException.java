package nz.ac.waikato.ssc10.map;

/**
 * An exception that describes when a route does not exist
 */
public class NoSuchRouteException extends Exception {
    public NoSuchRouteException(String message) {
        super(message);
    }

    public NoSuchRouteException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
