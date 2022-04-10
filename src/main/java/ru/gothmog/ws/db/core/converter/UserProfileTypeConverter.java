package ru.gothmog.ws.db.core.converter;

import ru.gothmog.ws.db.core.model.profile.UserProfileTypeName;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserProfileTypeConverter implements AttributeConverter<UserProfileTypeName, String> {
    @Override
    public String convertToDatabaseColumn(UserProfileTypeName attribute) {
        return attribute.getUserProfileTypeName();
    }

    @Override
    public UserProfileTypeName convertToEntityAttribute(String dbData) {
        return UserProfileTypeName.fromUserProfileTypeName(dbData);
    }
}
