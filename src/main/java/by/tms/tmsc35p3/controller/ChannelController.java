package by.tms.tmsc35p3.controller;

import by.tms.tmsc35p3.repository.ChannelRepository;
import by.tms.tmsc35p3.service.ChannelService;
import by.tms.tmsc35p3.dto.ChannelDto;
import by.tms.tmsc35p3.entity.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/channel")
public class ChannelController {

    @Autowired
    private ChannelService channelService;
    @Autowired
    private ChannelRepository channelRepository;

    @PostMapping
    public ResponseEntity<Channel> createChannel(@RequestBody ChannelDto dto){
    Channel channel =  new Channel();
    channel.setTitle(dto.getTitle());
    channel.setDescription(dto.getDescription());
    channel.setAuthorId(dto.getAuthorId());

    Channel save = channelRepository.save(channel);
    return ResponseEntity.ok(save);
}
}
