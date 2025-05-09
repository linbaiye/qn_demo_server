package org.example;

import io.netty.channel.Channel;

public record ConnectionClosedMessage(Channel channel){
}
