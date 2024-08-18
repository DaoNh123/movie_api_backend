package com.example.backend_sem2.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GenderEnum {
    M ("Male"), F("Female"), O ("Other");
    private final String genderName;
}
