package nl.hu.s3.project.chips.presentation.dto;

import jakarta.validation.constraints.Positive;

public class Withdrawal {
    @Positive
    public Long amount;
}
