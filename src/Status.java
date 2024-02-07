
/**
 * The Status enum represents the status of a user in the system.
 * It includes two possible statuses: USER and ADMIN.
 * It also provides methods to retrieve the admin password and check if a status is admin.
 *
 * @version 1.0
 * @author Moh Marian
 * @since 2024-2-7
 */
public enum Status {
    USER, ADMIN;

    private static final int ADMIN_PASSWORD = 1111;

    public static int getAdminPassword() {
        return ADMIN_PASSWORD;
    }

    /**
     * Checks if the status is admin.
     *
     * @return True if the status is admin, otherwise false.
     */
    public boolean isAdmin() {
        return this == ADMIN;
    }
}
