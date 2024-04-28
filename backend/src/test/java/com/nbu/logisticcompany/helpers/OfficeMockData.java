package com.nbu.logisticcompany.helpers;

import com.nbu.logisticcompany.entities.Office;

public class OfficeMockData {

    public static Office createOffice() {
        Office office = new Office();
        office.setAddress("test Address");
        return office;
    }

}
