package Components.Enum;

public enum UserType {
    COMUM("comum"),
    ADMIN("admin");

    private final String value;

    UserType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static UserType fromString(String text) {
        for (UserType userType : UserType.values()) {
            if (userType.value.equalsIgnoreCase(text)) {
                return userType;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}