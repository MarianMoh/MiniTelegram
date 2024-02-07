import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInput {
    private static final Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

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

    public static void getUserAction() {
        System.out.print("\nType any key to exit");
        scanner.nextLine();
    }
}
