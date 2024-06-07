package Components.Enum;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserTypeConverter implements AttributeConverter<UserType, String> {

    @Override
    public String convertToDatabaseColumn(UserType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public UserType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        for (UserType userType : UserType.values()) {
            if (userType.getValue().equalsIgnoreCase(dbData)) {
                return userType;
            }
        }

        throw new IllegalArgumentException("Unknown value: " + dbData);
    }
}