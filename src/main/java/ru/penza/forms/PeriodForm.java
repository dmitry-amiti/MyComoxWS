package ru.penza.forms;

import lombok.Data;

@Data
public class PeriodForm {
    private Long time_now;
    private Long time_then;
    private String engine;
    private String tool;
}
