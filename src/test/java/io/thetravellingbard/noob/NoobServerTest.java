package io.thetravellingbard.noob;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NoobServerTest {

    @Test
    void testRequest() {
        NoobRoute noobRequest = new NoobRoute("/home/a/page");
        assertEquals(noobRequest.getRoute(), "/home/a/page");
    }
}
