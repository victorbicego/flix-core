package com.flix.core.controllers.general;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.ChannelDto;
import com.flix.core.services.general.ChannelService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/channel")
@RequiredArgsConstructor
public class ChannelController {

  private final ChannelService channelService;

  @GetMapping("/{id}")
  public ChannelDto getById(@PathVariable String id) throws NotFoundException {
    return channelService.findById(id);
  }

  @GetMapping("/get")
  public List<ChannelDto> getAll() throws NotFoundException {
    return channelService.getAll();
  }
}
