import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * The Chat class represents a chat room where users can communicate by sending messages.
 * It provides methods for writing messages to the chat, reading all messages, and managing a timer.
 *
 * @version 1.0
 * @author Moh Marian
 * @since 2024-2-7
 */
public class Chat {
    private static final String FILE_NAME = "global chat.txt";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    private String name;
    private int maxMessageLength = 120;

    public Chat() {}

    public Chat(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxMessageLength() {
        return maxMessageLength;
    }

    public void setMaxMessageLength(int maxMessageLength) {
        this.maxMessageLength = maxMessageLength;
    }

    /**
     * Writes a message to the chat from a specific user.
     *
     * @param user The user who send message.
     */
    public void writeMessage(User user) {
        String message = UserInput.getMessageFromUser(120);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))){
            bw.write("\n" + user.getName() + ": " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads all messages from the chat.
     *
     * @return A string containing all messages in the chat.
     */
    public static String readAllMessages() {
        StringBuilder text = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line).append("\n");
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
     * The Timer class represents a timer that
     * periodically writes the current time to the chat.
     *
     * @version 1.0
     * @author Moh Marian
     * @since 2024-2-7
     */
    public static class Timer implements Runnable {
        private static final int SECONDS_IN_MINUTE = 60;
        private LocalTime localTime;

        public Timer() {
            this.localTime = LocalTime.now();
        }

        /**
         * Executes the timer task.
         * Writes the current time to the chat file every minute.
         */
        @Override
        public void run() {
            while(true) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
                    bw.write("\nCurrent time: " + localTime.format(formatter));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Dream.takeRest(SECONDS_IN_MINUTE * 1000);
                localTime = LocalTime.now();
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return Objects.equals(name, chat.name)
                && maxMessageLength == chat.maxMessageLength;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, maxMessageLength);
    }

    @Override
    public String toString() {
        return "\nName: " + name
                + "\nMax message length: " + maxMessageLength;
    }
}
