
/**
 * The Messenger abstract class represents a messaging application.
 * It defines abstract methods for managing the main menu, user menu,
 * chat menu, database menu, and authorization process.
 *
 * @version 1.0
 * @author Moh Marian
 * @since 2024-2-7
 */
public abstract class Messenger {
    /**
     * Displays the main menu of the messaging application.
     */
    abstract void mainMenu();

    /**
     * Displays the user-specific menu for managing user settings and actions.
     */
    abstract void userMenu();

    /**
     * Displays the chat menu for interacting with chat rooms and messages.
     */
    abstract void chatMenu();

    /**
     * Displays the database menu for managing user data.
     */
    abstract void databaseMenu();

    /**
     * Authorizes the user before entering the main menu.
     */
    abstract void authorize();
}
