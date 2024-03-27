package com.flix.core.services.general;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.ChannelDto;

public interface ChannelService {

    ChannelDto findById(String channelId) throws NotFoundException;
}
