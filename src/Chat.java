import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

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

    public void writeMessage(User user) {
        String message = UserInput.getMessageFromUser(120);
        try {
            FileWriter fw = new FileWriter(FILE_NAME, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("\n" + user.getName() + ": " + message);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public static class Timer implements Runnable {
        private static final int SECONDS_IN_MINUTE = 60;
        private LocalTime localTime;

        public Timer() {
            this.localTime = LocalTime.now();
        }

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
