package com.nbu.logisticcompany.helpers;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.Office;

public class OfficeMockData {

    public static Office createOffice() {
        Office office = new Office();
        office.setAddress("test Address");
        return office;
    }

    public static Office createOffice(Company company) {
        Office office = createOffice();
        office.setCompany(company);
        return office;
    }

}
