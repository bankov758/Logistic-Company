package com.nbu.logisticcompany.helpers;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.Tariff;

public class TariffMockData {

    public static Tariff createTariff() {
        Tariff tariff = new Tariff();
        Company company = new Company(1, "Test Company");
        tariff.setCompany(company);
        return tariff;
    }

}
