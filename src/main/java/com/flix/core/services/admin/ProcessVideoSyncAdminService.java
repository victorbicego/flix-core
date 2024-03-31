package com.flix.core.services.admin;

import com.flix.core.models.dtos.ChannelDto;
import com.flix.core.models.entities.VideoSync;
import java.io.IOException;

public interface ProcessVideoSyncAdminService {
  VideoSync generateSingleVideo(ChannelDto channelDto, String group, String group1)
      throws IOException;
}
