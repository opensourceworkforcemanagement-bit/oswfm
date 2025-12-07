package org.oswfm.commons.model.user.enums;

/**
 * Represents the status of a user.
 * This enum defines the different states a user can have in the system.
 */
public enum UserStatus {
    ACTIVE(1),
    INACTIVE(0),
    LOCKED(2),
    SUSPENDED(3);

    private final int value;

    UserStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static UserStatus fromValue(int value) {
        for (UserStatus status : UserStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid UserStatus value: " + value);
    }
}
