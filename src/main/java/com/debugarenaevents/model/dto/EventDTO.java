package com.debugarenaevents.model.dto;

import com.debugarenaevents.model.enums.VideoPlatformEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDTO {

    private Long id;

    private String authorName;

    private String title;

    private String description;

    private VideoPlatformEnum platform;
}
