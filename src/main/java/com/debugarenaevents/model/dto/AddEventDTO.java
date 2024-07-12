package com.debugarenaevents.model.dto;

import com.debugarenaevents.model.enums.VideoPlatformEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AddEventDTO {

    private String title;

    private String description;

    private String authorName;

    private String authorEmail;

    private LocalDateTime date;

    private VideoPlatformEnum platform;
}
