import java.io.*;
import java.time.LocalDate;
import java.util.Objects;

/**
 * The User class represents a user in the system.
 * It includes methods for user information management,
 * such as writing to and reading from a file.
 *
 * @version 1.0
 * @author Moh Marian
 * @since 2024-2-7
 */
public class User {
    private static final String FILE_NAME = "users.txt";
    private String name;
    private int password;
    private Status status;
    private LocalDate lastTimeOnline;

    public User() {
    }

    public User(String name, int password) {
        this.name = name;
        this.password = password;
        this.lastTimeOnline = LocalDate.now();
    }

    public String getFileName() {
        return FILE_NAME;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getLastTimeOnline() {
        return lastTimeOnline;
    }

    public void setLastTimeOnline(LocalDate lastTimeOnline) {
        this.lastTimeOnline = lastTimeOnline;
    }

    /**
     * Writes the user information to a file.
     */
    public void writeUserToFile() {
        try {
            FileWriter fw = new FileWriter(FILE_NAME, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("\n" + name
                    + "\n" + password
                    + "\n" + status
                    + "\n" + lastTimeOnline
                    + "\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads user information from a file.
     *
     * @param full Indicates whether to return the full information of the user.
     * @return The user information read from the file.
     */
    public static String readInfoFromFile(boolean full) {
        StringBuilder text = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            int i = 1;
            while ((line = br.readLine()) != null) {
                if (full) {
                    switch (i) {
                        case 2 -> text.append("Name: ");
                        case 3 -> text.append("Password: ");
                        case 4 -> text.append("Status: ");
                        case 5 -> text.append("Last time online: ");
                    }
                }
                text.append(line).append("\n");
                i = (i % 5) + 1;
            }
        } catch (FileNotFoundException e) {
            System.err.println("- Error! File not found...");
            Dream.takeRest();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }

    /**
     * Deletes user information from the file.
     */
    public static void deleteInfoFromFile() {
        try {
            FileWriter fw = new FileWriter(FILE_NAME, false);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return password == user.password
                && Objects.equals(name, user.name)
                && Objects.equals(status, user.status)
                && Objects.equals(lastTimeOnline, user.lastTimeOnline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password, status, lastTimeOnline);
    }

    @Override
    public String toString() {
        return "\nName: " + name
                + "\nPassword: " + password
                + "\nStatus: " + status
                + "\nLast time online: " + lastTimeOnline;
    }
}
