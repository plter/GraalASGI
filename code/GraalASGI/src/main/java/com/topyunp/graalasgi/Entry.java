package com.topyunp.graalasgi;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class Entry {

    public static void main(String[] args) {
        var port = 8001;

        var boos = new NioEventLoopGroup();
        var task = new NioEventLoopGroup();
        new ServerBootstrap()
                .group(boos, task)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline()
                                .addLast(new HttpServerCodec())
                                .addLast(new ASGIHandler());
                    }
                }).bind(port).addListener((ChannelFutureListener) future -> {
                    System.out.println("Server started at port " + port);
                });
    }
}
