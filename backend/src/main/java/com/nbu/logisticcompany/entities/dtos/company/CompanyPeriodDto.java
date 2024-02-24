package com.nbu.logisticcompany.entities.dtos.company;

import java.time.LocalDateTime;

public class CompanyPeriodDto {

    private LocalDateTime periodStart;
    private LocalDateTime periodEnd;
    int companyId;

    public CompanyPeriodDto() {
    }

    public CompanyPeriodDto(LocalDateTime periodStart, LocalDateTime periodEnd, int companyId) {
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.companyId = companyId;
    }

    public LocalDateTime getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(LocalDateTime periodStart) {
        this.periodStart = periodStart;
    }

    public LocalDateTime getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(LocalDateTime periodEnd) {
        this.periodEnd = periodEnd;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

}
