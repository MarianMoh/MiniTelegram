import static java.lang.Thread.sleep;

/**
 * The Dream class provides methods for simulating pauses or rests in the program execution.
 * It includes methods to pause the program for a specified duration.
 */
public class Dream {
    /**
     * Simulates a short rest by pausing the program for 500 milliseconds.
     */
    public static void takeRest() {
        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Simulates a rest by pausing the program for the specified duration.
     *
     * @param time The duration of the rest in milliseconds.
     */
    public static void takeRest(int time) {
        try {
            sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
