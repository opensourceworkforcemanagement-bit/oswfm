package org.oswfm.authservice.model.auth;

import static org.junit.jupiter.api.Assertions.*;

import org.oswfm.commons.model.user.User;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void testUserBuilder_WithAllFields() {

        // Given
        String firstName = "John";
        String lastName = "Doe";
        String userName = "johndoe1";

        // When
        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .userName(userName)
                .build();

        // Then
        assertNotNull(user);
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(userName, user.getUserName());

    }

    @Test
    void testUserBuilder_DefaultValues() {

        // When
        User user = User.builder().build();

        // Then
        assertNotNull(user);
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
        assertNull(user.getUserName());

    }

    @Test
    void testUserSettersAndGetters() {

        // Given
        User user = new User();

        // When
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserName("johndoe1");
        user.setUserId(1);

        // Then
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("johndoe1", user.getUserName());
        assertEquals(1, user.getUserId());

    }

    @Test
    void testUserEquality() {

        // Given
        User user1 = User.builder()
                .firstName("John")
                .lastName("Doe")
                .userName("johndoe1")
                .build();

        User user2 = User.builder()
                .firstName("Jane")
                .lastName("Smith")
                .userName("janesmith2")
                .build();

        // When & Then
        assertNotEquals(user1, user2);

    }

}
