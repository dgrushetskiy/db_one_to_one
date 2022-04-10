package ru.gothmog.ws.db.core.model.profile;

public enum UserProfileTypeName {
    INDIVIDUAL("физическое лицо"),
    INDIVIDUAL_IP("физическое лицо (ИП)"),
    FARM_ECONOMY("крестьянское (фермерское) хозяйство"),
    LEGAL_ENTITY("юридическое лицо");

    private final String userProfileTypeName;

    UserProfileTypeName(String userProfileTypeName) {
        this.userProfileTypeName = userProfileTypeName;
    }

    public static UserProfileTypeName fromUserProfileTypeName(String userProfileTypeName) {
        switch (userProfileTypeName){
            case "физическое лицо":
                return UserProfileTypeName.INDIVIDUAL;
            case "физическое лицо (ИП)":
                return UserProfileTypeName.INDIVIDUAL_IP;
            case "крестьянское (фермерское) хозяйство":
                return UserProfileTypeName.FARM_ECONOMY;
            case "юридическое лицо":
                return UserProfileTypeName.LEGAL_ENTITY;

            default:
                throw new IllegalArgumentException("userProfileTypeName [ " + userProfileTypeName + " ] not found");
        }
    }

    public String getUserProfileTypeName() {
        return userProfileTypeName;
    }
}
