package com.example.backend_sem2.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

public enum MovieLabelEnum {
    P(null), C12(12L), C16(16L), C18(18L);
    private final Long minAge;

    MovieLabelEnum(Long minAge) {
        this.minAge = minAge;
    }

    public Long getMinAge() {
        return minAge;
    }
}
