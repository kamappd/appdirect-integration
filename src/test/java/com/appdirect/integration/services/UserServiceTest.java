package com.appdirect.integration.services;

import com.appdirect.integration.models.Company;
import com.appdirect.integration.models.User;
import com.appdirect.integration.repositories.UserRepository;
import com.appdirect.integration.utils.IdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.appdirect.integration.models.EditionCode.BASIC;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private IdGenerator idGenerator;
    @Mock
    private UserRepository userRepository;

    @Test
    public void when_receiving_new_order__add_subscription() throws Exception {

        // Given
        when(idGenerator.generateId()).thenReturn("1234");
        User user = aBasicUser("1234");

        // Execute
        userService.save(user);

        // Verify
        verify(userRepository).save(user);
    }

    private Company aBasicCompany() {
        Company company = new Company();
        company.setId("1234");
        company.setName("aCompany");
        company.setEditionCode(BASIC);
        return company;
    }

    private User aBasicUser(String id) {
        User user = new User();
        user.setId(id);
        user.setCompany(aBasicCompany());
        user.setEmail("john.doe@somewhere.net");
        user.setFirstName("john");
        user.setLastName("doe");
        user.setOpenId("123456789");
        return user;
    }
}