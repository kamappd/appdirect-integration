package com.appdirect.integration.services;

import com.appdirect.integration.models.Company;
import com.appdirect.integration.repositories.CompanyRepository;
import com.appdirect.integration.utils.IdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.appdirect.integration.models.EditionCode.BASIC;
import static com.appdirect.integration.models.EditionCode.PREMIUM;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceTest {

    @InjectMocks
    private CompanyService companyService;
    @Mock
    private IdGenerator idGenerator;
    @Mock
    private CompanyRepository companyRepository;

    @Test
    public void can_save_new_company() throws Exception {

        // Given
        when(idGenerator.generateId()).thenReturn("1234");
        Company company = aBasicCompany("1234");

        // Execute
        companyService.save(company);

        // Verify
        verify(companyRepository).save(company);
    }

    @Test
    public void can_update_existing_company() throws Exception {

        // Given
        Company company = givenACompanyAlreadyExists("1234");

        // Execute
        companyService.update("1234", PREMIUM);

        // Verify
        company.setEditionCode(PREMIUM);
        verify(companyRepository).save(company);
    }

    @Test
    public void can_delete_existing_company() throws Exception {

        // Given
        givenACompanyAlreadyExists("1234");

        // Execute
        companyService.delete("1234");

        // Verify
        verify(companyRepository).delete("1234");
    }

    private Company aBasicCompany(String id) {
        Company company = new Company();
        company.setId(id);
        company.setName("aCompany");
        company.setEditionCode(BASIC);
        return company;
    }

    private Company givenACompanyAlreadyExists(String id) {
        Company company = aBasicCompany(id);
        when(companyRepository.findOne(id)).thenReturn(company);
        return company;
    }
}