package com.flix.core.models.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoWithChannelDto {

    @NotNull VideoDto video;
    @NotNull
    private ChannelDto channel;
}
