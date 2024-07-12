package com.debugarenaevents.model.dto;

import com.debugarenaevents.model.enums.VideoPlatformEnum;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AddEventDTO {

    @NotBlank(message = "{blank_field_message}")
    @Size(min = 6, message = "{title.length.message}")
    private String title;

    @NotBlank(message = "{blank_field_message}")
    @Size(min = 20, message = "{description.length.message}")
    private String description;

    @NotBlank(message = "{blank_field_message}")
    @Size(min = 6, message = "{authorName.length.message}")
    private String authorName;

    @NotBlank(message = "{blank_field_message}")
    @Email
    private String authorEmail;

    @NotNull(message = "{date.not.null.message}")
    @Future(message = "{valid.date.message}")
    private LocalDateTime date;

    @NotNull(message = "{platform.not.null.message}")
    private VideoPlatformEnum platform;
}
