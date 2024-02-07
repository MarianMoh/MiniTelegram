public enum Status {
    USER, ADMIN;

    private static final int ADMIN_PASSWORD = 1111;

    public static int getAdminPassword() {
        return ADMIN_PASSWORD;
    }

    public boolean isAdmin() {
        return this == ADMIN;
    }
}
