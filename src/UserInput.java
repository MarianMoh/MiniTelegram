import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The UserInput class provides methods for getting user input from the console.
 * It includes methods for getting user's name, message, password, and numerical input.
 *
 * @version 1.0
 * @author Moh Marian
 * @since 2024-2-7
 */
public class UserInput {
    private static final Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    /**
     * Gets the user's name from the console input.
     * The name must consist of alphabetic characters, digits
     * or underscores and have a length between 3 and 12 characters.
     *
     * @return The user's name input.
     */
    public static String getNameFromUser() {
        String name;
        while (true) {
            System.out.print("\nEnter your name: ");
            name = scanner.nextLine();

            Pattern p = Pattern.compile("\\w{2,12}");
            Matcher m = p.matcher(name);

            if (m.matches())
                break;
            else {
                System.err.println("- Error! Name must have a-zA-Z_0-9 from 3 to 12 times...");
                Dream.takeRest();
            }
        }
        return name;
    }

    /**
     * Gets the user's message from the console input.
     * The message must not be empty and should not exceed the specified maximum length.
     *
     * @param maxMessageLength The maximum length allowed for the message.
     * @return The user's message input.
     */
    public static String getMessageFromUser(int maxMessageLength) {
        String text;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("\nEnter your message: ");
            text = scanner.nextLine();

            if (text.isEmpty()) {
                System.err.println("- Error! Your message is empty...");
            } else if (text.length() > maxMessageLength) {
                System.err.println("- Error! You can use only 120 letters...");
            } else {
                break;
            }
            Dream.takeRest();
        }
        return text;
    }

    /**
     * Gets the user's password from the console input.
     * The password must consist of at least the specified minimum length of digits.
     *
     * @param length The minimum length required for the password.
     * @return The user's password input.
     */
    public static int getPasswordFromUser(int length) {
        int password;
        while (true) {
            password = getUserInput("\nEnter your password: ");

            if (password == -1)
                continue;

            if (String.valueOf(password).length() >= length)
                break;

            System.err.println("- Error! Password must have at least 3 numbers...");
            Dream.takeRest();
        }
        return password;
    }

    /**
     * Gets numerical input from the user within the specified range.
     *
     *
     * @param from The lower bound of the input range.
     * @param to   The upper bound of the input range.
     * @return The user's numerical input within the specified range.
     */
    public static int getNumberFromUser(int from, int to) {
        int input;
        while (true) {
            input = getUserInput("\nInput: ");

            if (input == -1)
                continue;

            if ((from <= input) && (input <= to))
                break;

            System.err.println("- Error! Invalid input (from " + from + " to " + to + ")");
            Dream.takeRest();
        }
        return input;
    }

    /**
     * Helper method to get user input from the console and handle exceptions.
     *
     * @param text The prompt message for the user input.
     * @return The user's input as an integer.
     */
    private static int getUserInput(String text) {
        int input = -1;
        System.out.print(text);

        try {
            input = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.err.println("- Error! Wrong data type...");
            Dream.takeRest();
        }
        return input;
    }

    /**
     * Prompts the user to perform an action by typing any key.
     */
    public static void getUserAction() {
        System.out.print("\nType any key to exit");
        scanner.nextLine();
    }
}
