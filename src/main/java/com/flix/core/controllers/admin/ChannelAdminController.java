package com.flix.core.controllers.admin;

import com.flix.core.exceptions.InvalidOperationException;
import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.ChannelDto;
import com.flix.core.services.admin.ChannelAdminService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/channel")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChannelAdminController {

  private final ChannelAdminService channelAdminService;

  @GetMapping("/get")
  public List<ChannelDto> getAll(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    return channelAdminService.getAll(page, size);
  }

  @GetMapping("/get/{id}")
  public ChannelDto getById(@PathVariable String id) throws NotFoundException {
    return channelAdminService.getById(id);
  }

  @PostMapping("/save")
  public ChannelDto save(@RequestBody ChannelDto channelDto) {
    return channelAdminService.save(channelDto);
  }

  @PutMapping("/update/{id}")
  public ChannelDto update(@PathVariable String id, @RequestBody ChannelDto channelDto)
      throws NotFoundException {
    return channelAdminService.update(id, channelDto);
  }

  @DeleteMapping("/delete/{id}")
  public void deleteById(@PathVariable String id)
      throws NotFoundException, InvalidOperationException {
    channelAdminService.deleteById(id);
  }
}
