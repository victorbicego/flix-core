package com.flix.core.models.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelDto {

    private String id;
    @NotNull
    private String name;
    @NotNull
    private String mainLink;
    @NotNull
    private String logoLink;
    @NotNull
    private String backgroundLink;
    @NotNull
    private String tag;
}
