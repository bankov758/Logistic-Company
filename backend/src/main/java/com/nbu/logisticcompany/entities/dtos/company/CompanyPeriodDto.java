package com.nbu.logisticcompany.entities.dtos.company;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

public class CompanyPeriodDto {

    @NotNull(message = "Please enter start of period")
    private LocalDateTime periodStart;

    @NotNull(message = "Please enter end of period")
    private LocalDateTime periodEnd;

    @Positive(message = "Company ID has to be a positive number")
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
