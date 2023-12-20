package com.example.backend_sem2.Enum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

public enum MovieLabelEnum {
    P(0L), C12(12L), C16(16L), C18(18L);
    private final Long minAge;

    MovieLabelEnum(Long minAge) {
        this.minAge = minAge;
    }

    public Long getMinAge() {
        return minAge;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
