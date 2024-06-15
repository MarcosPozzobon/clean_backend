package com.marcos.desenvolvimento.repository;


import com.marcos.desenvolvimento.gateways.entities.UserEntity;
import com.marcos.desenvolvimento.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    private UserEntity user;

    @BeforeEach
    public void setup() {
       user = new UserEntity();
       user.setLogin("test_breeze");
       user.setPassword("test_password");
    }

    @Test
    @DisplayName("When calling save method it must return the saved user.")
    void testGivenPersonObject_whenSave_thenReturnSavedPerson() {

        UserEntity savedUser = repository.save(user);

        assertTrue(savedUser.getId() > 0);
        assertNotNull(savedUser.getLogin());
        assertNotNull(savedUser.getPassword());

        assertEquals(savedUser.getLogin(), user.getLogin());
        assertEquals(savedUser.getPassword(), user.getPassword());

    }

    @Test
    @DisplayName("When calling the findAll method it must return a list containing both saved users.")
    void testGivenTwoSavedUsersItMustReturnAListContainingBothSavedUsers(){

        UserEntity anotherSavedUser = new UserEntity();
        anotherSavedUser.setLogin("test_breeze2");
        anotherSavedUser.setPassword("test_password2");


        repository.save(user);
        repository.save(anotherSavedUser);

        List<UserEntity> allUsers = repository.findAll();

        UserEntity firstUser = allUsers.get(1);
        UserEntity secondUser = allUsers.get(0);

        assertEquals("test_breeze2", firstUser.getLogin());
        assertEquals("test_password2", firstUser.getPassword());

        assertEquals("test_breeze", secondUser.getLogin());
        assertEquals("test_password", secondUser.getPassword());

        assertNotNull(allUsers);
        assertEquals(allUsers.size(), 2, () -> "Deve retornar uma lista contendo 2 usuários.");

    }

    @Test
    @DisplayName("When calling the findById method it must return the user containing that id.")
    void testGivenAUserItMustItsUserBasedOnTheirId() {
        var savedUser = repository.save(user);

        var foundUser = repository.findById(savedUser.getId()).orElse(null);

        assertNotNull(foundUser);
        assertEquals(savedUser.getId(), foundUser.getId());
        assertEquals(savedUser.getLogin(), foundUser.getLogin());
        assertEquals(savedUser.getPassword(), foundUser.getPassword());
    }


    @Test
    @DisplayName("When calling the method to update an user it must return the updated values of user.")
    void testGivenAnUpdatedUserObjectItMustReturnItsUpdatedValue(){

        user.setLogin("test_breeze3");
        repository.save(user);

        assertNotNull(user);
        assertEquals("test_breeze3", user.getLogin());
    }

    @Test
    @DisplayName("When calling the deleteById method it must delete the following user.")
    void testGivenAUserItMustDeleteTheUserBasedOnTheirId(){

        var userToBeDeleted = new UserEntity();
        userToBeDeleted.setLogin("toDelete");
        userToBeDeleted.setPassword("delete");

        var savedUser = repository.save(userToBeDeleted);

        repository.deleteById(savedUser.getId());

        var deletedUser = repository.findById(savedUser.getId());

        assertTrue(deletedUser.isEmpty(), "O usuario deve ser deletado e nao mantido no repositorio.");
    }

}
