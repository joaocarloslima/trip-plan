package br.com.fiap.tripplan.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Activity {

    private String title;
    private String description;
    private ActivityType type;
    private int durationInMinutes;

}
