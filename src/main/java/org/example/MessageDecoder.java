package org.example;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MessageDecoder extends LengthFieldBasedFrameDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageDecoder.class);

    public MessageDecoder() {
        super(Short.MAX_VALUE, 0, 4, 0, 4);
    }


    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf byteBuf = (ByteBuf) super.decode(ctx, in);
        if (byteBuf == null) {
            return null;
        }
        int value = byteBuf.readInt();
        MessageType type = MessageType.fromValue(value);
        return switch (type) {
            case Login -> new LoginMessage();
            case Move -> MoveInput.create(byteBuf.readInt(), byteBuf.readInt(), byteBuf.readInt());
            default -> null;
        };
    }
}
