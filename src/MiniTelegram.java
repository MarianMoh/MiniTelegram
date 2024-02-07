import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The MiniTelegram class represents a simplified version of a messaging application.
 * It includes various menu methods and methods for managing users and chats.
 *
 * @version 1.0
 * @author Moh Marian
 * @since 2024-2-7
 */

public class MiniTelegram extends Messenger {
    private User user;
    private ArrayList<User> users;
    private Chat chat;

    /**
     * Input saves a number from user.
     */
    private transient int input;

    /**
     * Represents the timer for updating the chat messages.
     */
    private transient Chat.Timer timer;

    /**
     * MiniTelegram() initializes objects and starts Thread Timer
     */
    public MiniTelegram() {
        users = new ArrayList<>();
        users = getAllUsers();
        user = new User();
        chat = new Chat("Global chat");

        timer = new Chat.Timer();
        Thread thread = new Thread(timer);
        thread.start();
        authorize();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    /**
     * Displays the main menu for navigating to other menus or exiting the application.
     */
    @Override
    void mainMenu() {
        while (true) {
            System.out.println("\n-- Main menu --");
            System.out.println(" User menu - 1");
            System.out.println(" Global chat - 2");
            System.out.println(" Database - 3");
            System.out.println(" Exit - 4");

            input = UserInput.getNumberFromUser(1, 4);

            switch (input) {
                case 1 -> userMenu();
                case 2 -> chatMenu();
                case 3 -> databaseMenu();
                case 4 -> {
                    System.out.println("\n-- Bye! --");
                    System.exit(0);
                }
            }
        }
    }

    /**
     * Displays the user-specific menu for managing user settings and actions.
     */
    @Override
    void userMenu() {
        while (true) {
            System.out.println("\n-- User menu --");
            System.out.println(" Info - 1");
            System.out.println(" Change acc. - 2");
            System.out.println(" Rename - 3");
            System.out.println(" Delete - 4");
            System.out.println(" Back - 5");

            input = UserInput.getNumberFromUser(1, 5);

            switch (input) {
                case 1 -> userInfo();
                case 2 -> authorize();
                case 3 -> renameUser();
                case 4 -> deleteUser();
                default -> {
                    return;
                }
            }
        }
    }

    /**
     * Displays the chat menu for interacting with chat rooms and messages.
     */
    @Override
    void chatMenu() {
        while (true) {
            System.out.println("\n-- Welcome to " + chat.getName() + " --");
            String text = Chat.readAllMessages();

            if (text.isEmpty()) {
                System.out.println("This chat is empty...");
            } else {
                System.out.print(text);
            }

            System.out.println("\n Write message - 1");
            System.out.println(" Back - 2");

            input = UserInput.getNumberFromUser(1, 2);

            if (input == 1) {
                chat.writeMessage(user);
            } else {
                break;
            }
        }
    }

    /**
     * Displays the database menu for managing user data.
     */
    @Override
    public void databaseMenu() {
        boolean isAdmin = user.getStatus().isAdmin() || checkIfAdmin();
        System.out.print("\n-- Users database --");

        if (isAdmin) {
            System.out.println(User.readInfoFromFile(true));

            System.out.println("Back - 1");
            System.out.println("Delete - 2");

            input = UserInput.getNumberFromUser(1, 2);

            if (input == 2) {
                User.deleteInfoFromFile();
                System.err.println("\n- Database was deleted...");
                Dream.takeRest();
                authorize();
            }
        }
    }

    /**
     * Authorizes the user before entering the main menu.
     */
    @Override
    void authorize() {
        users = getAllUsers();
        user.setLastTimeOnline(LocalDate.now());

        System.out.println("\n--Welcome to MiniTelegram--");
        String name = UserInput.getNameFromUser();
        Dream.takeRest();

        if (isUserInList(name)) {
            System.out.println("\n--Welocome " + name + "--");
            Dream.takeRest();
            user = getUserByName(name);
        } else {
            System.out.println("\nYou haven't been here before");
            Dream.takeRest();

            int password = UserInput.getPasswordFromUser(3);
            user = new User(name, password);
            user.setStatus(Status.USER);
            user.writeUserToFile();
            users.add(user);
        }
        mainMenu();
    }

    /**
     * Displays user information.
     */
    public void userInfo() {
        System.out.print("\n-- User Info --");
        System.out.println(user);
        UserInput.getUserAction();
    }

    /**
     * Renames the user.
     */
    public void renameUser() {
        deleteUserFromFile();
        user.setName(UserInput.getNameFromUser());
        user.writeUserToFile();
    }

    /**
     * Deletes the user.
     */
    public void deleteUser() {
        deleteUserFromFile();
        authorize();
    }

    /**
     * Deletes the user from file.
     */
    public void deleteUserFromFile() {
        users.remove(user);
        User.deleteInfoFromFile();
        users.forEach(User::writeUserToFile);
    }

    /**
     * Retrieves the user by name.
     *
     * @param name The name of the user to retrieve.
     * @return The user with the specified name, or null if not found.
     */
    public User getUserByName(String name) {
        return users.stream()
                .filter(u -> u.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Checks if the user is an admin.
     *
     * @return True if the user is an admin, otherwise false.
     */
    public boolean checkIfAdmin() {
        boolean isAdmin = false;
        System.out.print("\n\nEnter admin password (or 0 to exit)");
        input = UserInput.getNumberFromUser(0, 9999);

        if (input == Status.getAdminPassword()) {
            deleteUserFromFile();
            user.setStatus(Status.ADMIN);
            user.writeUserToFile();
            isAdmin = true;
        } else if (input != 0) {
            System.err.println("\n- Error! Password isn't correct");
            Dream.takeRest();
        }
        return isAdmin;
    }

    /**
     * Checks if the user is in the list of users.
     *
     * @param name The name of the user to check.
     * @return True if the user is in the list, otherwise false.
     */
    public boolean isUserInList(String name) {
        return users.stream().anyMatch(u -> u.getName().equals(name));
    }

    /**
     * Retrieves all users from the file.
     *
     * @return The list of users.
     */
    public ArrayList<User> getAllUsers() {
        String info = User.readInfoFromFile(false);
        String[] lines = info.split("\n");

        users.clear();
        User tempUser = new User();
        int i = 1;

        for (String line : lines) {
            switch (i) {
                case 2 -> tempUser.setName(line);
                case 3 -> tempUser.setPassword(Integer.parseInt(line));
                case 4 -> tempUser.setStatus(Status.valueOf(line));
                case 5 -> {
                    tempUser.setLastTimeOnline(LocalDate.parse(line));
                    users.add(tempUser);
                    tempUser = new User();
                }
            }
            i = (i % 5) + 1;
        }
        return users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MiniTelegram that = (MiniTelegram) o;
        return Objects.equals(user, that.user)
                && Objects.equals(users, that.users)
                && Objects.equals(chat, that.chat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, users, chat);
    }

    @Override
    public String toString() {
        return "\nUser: " + user
                + "\nUsers: " + users
                + "\nChat: " + chat;
    }
}