
import org.junit.*;
import java.io.*;

public class UserTest {
    private static User user;

    @BeforeClass
    public static void setUpBeforeClass() {
        user = new User("Marin", 1234);
        user.setStatus(Status.ADMIN);
    }

    @Test
    public void testWriteUserToFile() {
        user.writeUserToFile();

        StringBuilder text = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(user.getFileName()))) {
            String line;
            int i = 1;
            while ((line = br.readLine()) != null) {
                if(i == 1) {
                    text = new StringBuilder();
                }
                text.append(line).append("\n");
                i = (i % 5) + 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String expectedText = "\n" + user.getName()
                + "\n" + user.getPassword()
                + "\n" + user.getStatus()
                + "\n" + user.getLastTimeOnline()
                + "\n";
        Assert.assertEquals(expectedText, text.toString());
    }

    @Test
    public void testReadInfoFromFile() {
        try (FileWriter fw = new FileWriter(user.getFileName(), false)) {
        } catch (IOException e) {
            e.printStackTrace();
        }

        user.writeUserToFile();
        String text = User.readInfoFromFile(false);

        String expectedText = "\n" + user.getName()
                + "\n" + user.getPassword()
                + "\n" + user.getStatus()
                + "\n" + user.getLastTimeOnline()
                + "\n";
        Assert.assertEquals(expectedText, text);
    }

    @Test
    public void testReadFullInfoFromFile() {
        try (FileWriter fw = new FileWriter(user.getFileName(), false)) {
        } catch (IOException e) {
            e.printStackTrace();
        }

        user.writeUserToFile();
        String text = User.readInfoFromFile(true);

        String expectedText = "\nName: " + user.getName()
                + "\nPassword: " + user.getPassword()
                + "\nStatus: " + user.getStatus()
                + "\nLast time online: " + user.getLastTimeOnline()
                + "\n";
        Assert.assertEquals(expectedText, text);
    }

    @Test
    public void testDeleteInfoFromFile() {
        User.deleteInfoFromFile();
        StringBuilder text = new StringBuilder();

        try(BufferedReader br = new BufferedReader(new FileReader(user.getFileName()))) {
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String expectedText = "";
        Assert.assertEquals(expectedText, text.toString());
    }
}
