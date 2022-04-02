package com.topyunp.graalasgi.test;

import org.graalvm.polyglot.Context;
import org.junit.jupiter.api.Test;

public class TestRunPyAsyncFunc {

    @Test
    public void doTest() {
        var context = Context.newBuilder().allowAllAccess(true).build();
        context.eval("python", "import asyncio\nasync def hello():\n    await asyncio.sleep(3000)\n    print('OK')\nasyncio.run(hello())");
//        var hello = context.eval("python", "hello");
//        System.out.println(hello);
//        hello.execute();
//        context.eval("python", "asyncio.run(hello())");
    }
}
