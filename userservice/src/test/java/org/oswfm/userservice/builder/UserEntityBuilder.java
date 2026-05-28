package org.oswfm.userservice.builder;

import org.oswfm.commons.model.user.entity.UserEntity;
import org.oswfm.commons.model.user.enums.UserStatus;

public class UserEntityBuilder extends BaseBuilder<UserEntity> {

    public UserEntityBuilder() {
        super(UserEntity.class);
    }

    public UserEntityBuilder withValidUserFields() {
        return this
                .withUserId(1)
                .withUserName("johndoe1")
                .withFirstName("John")
                .withLastName("Doe")
                .withUserStatus(UserStatus.ACTIVE);
    }

    public UserEntityBuilder withValidAdminFields() {
        return this
                .withUserId(2)
                .withUserName("adminuser1")
                .withFirstName("Admin")
                .withLastName("User")
                .withUserStatus(UserStatus.ACTIVE);
    }

    public UserEntityBuilder withUserId(Integer userId) {
        data.setUserId(userId);
        return this;
    }

    public UserEntityBuilder withUserName(String userName) {
        data.setUserName(userName);
        return this;
    }

    public UserEntityBuilder withFirstName(String firstName) {
        data.setFirstName(firstName);
        return this;
    }

    public UserEntityBuilder withLastName(String lastName) {
        data.setLastName(lastName);
        return this;
    }

    public UserEntityBuilder withUserStatus(UserStatus userStatus) {
        data.setUserStatus(userStatus.getValue());
        return this;
    }

}
