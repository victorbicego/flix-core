package com.flix.core.controllers.general;

import com.flix.core.models.dtos.ChannelDto;
import com.flix.core.services.general.ChannelService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/channel")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChannelController {

  private final ChannelService channelService;

  @GetMapping("/find")
  public List<ChannelDto> findByWord(
      @RequestParam(defaultValue = "") String word,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    return channelService.findByWord(word, page, size);
  }
}
