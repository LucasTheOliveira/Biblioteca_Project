package Components.Enum;

public class CurrentUser {
    private static CurrentUser instance;
    private String username;
    @SuppressWarnings("unused")
    private boolean isAdmin;

    public static CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}