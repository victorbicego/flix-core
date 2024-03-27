package com.flix.core.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import com.flix.core.models.enums.Category;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoSyncDto {

    private String id;
    private String title;
    private String link;
    private LocalDate date;
    private String description;
    private String channelId;
    private Category category;

}
