package com.flix.core.services.admin;

import java.io.IOException;

import com.flix.core.models.dtos.ChannelDto;
import com.flix.core.models.entities.VideoSync;

public interface ProcessVideoSyncAdminService {
    VideoSync generateSingleVideo(ChannelDto channelDto, String group, String group1) throws IOException;
}
