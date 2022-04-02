package com.topyunp.graalasgi;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class ASGIHandler extends ChannelInboundHandlerAdapter {

    private static final Context pyContext = Context.create();
    private static Value app = null;

    static {
        var file = new File("python/web.py");
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(file);
            var fileContent = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            pyContext.eval("python", fileContent);
            app = pyContext.eval("python", "app");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        var scope = new HashMap<String, String>();
        scope.put("type", "a");
        System.out.println(pyContext.asValue(scope));

        app.execute(pyContext.asValue(scope), null, null);
    }
}
