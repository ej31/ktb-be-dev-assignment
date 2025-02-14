package ktb.back.assignment.controller;

import ktb.back.assignment.model.ResponseCompanyDto;
import ktb.back.assignment.service.CompanyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }

    @GetMapping
    public List<ResponseCompanyDto> getCompanyPrice(@RequestParam String companyCode, @RequestParam String startDate, @RequestParam String endDate) throws ParseException {
        return companyService.getCompanyStockHistory(companyCode,  startDate, endDate);
    }
}
