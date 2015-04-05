package com.appdirect.integration.controllers;


import com.appdirect.integration.models.Company;
import com.appdirect.integration.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class CompanyController {

    private CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RequestMapping(value = "/companies", method = GET, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Company> getCompanies() throws Exception {
        return companyService.getCompanies();
    }
}
