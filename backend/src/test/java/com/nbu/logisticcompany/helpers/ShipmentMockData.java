package com.nbu.logisticcompany.helpers;

import com.nbu.logisticcompany.entities.Company;
import com.nbu.logisticcompany.entities.Shipment;

public class ShipmentMockData {

    public static Shipment createShipment() {
        Shipment shipment = new Shipment();
        shipment.setId(1);
        shipment.setCompany(new Company(1, "Test Company"));
        return shipment;
    }

}
