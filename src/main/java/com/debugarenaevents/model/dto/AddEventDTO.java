package com.debugarenaevents.model.dto;

import com.debugarenaevents.model.enums.VideoPlatformEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddEventDTO {

    private Long id;

    private String authorName;

    private String title;

    private String description;

    private VideoPlatformEnum platform;
}
