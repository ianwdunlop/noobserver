/*
 * Copyright 2024 Ian Dunlop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.thetravellingbard.noob;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NoobServerTest {

    @Test
    void testRequest() {
        NoobRoute noobRequest = new NoobRoute("/a/page") {
            @Override
            public String getHTML() {
                return "";
            }
        };
        assertEquals(noobRequest.getRoute(), "/a/page");
    }

    @Test
    void testRouteHTML() {
        NoobRoute noobRequest = new NoobRoute("/another/page") {
            @Override
            public String getHTML() {
                return "<html><head></head><body><p>A paragraph</p></body></html>";
            }
        };
        assertEquals(noobRequest.getHTML(), "<html><head></head><body><p>A paragraph</p></body></html>");
    }
}
