package by.tms.tmsc35p3.service;


import by.tms.tmsc35p3.entity.Channel;
import by.tms.tmsc35p3.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelService {

    @Autowired
    private ChannelRepository channelRepository;

    public void create(Channel channel){

    }
}
