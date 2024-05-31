package org.jooby.internal;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.jooby.Err;
import org.jooby.MediaType;
import org.jooby.Renderer;
import org.jooby.Results;
import org.jooby.View;
import org.jooby.test.MockUnit;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class AbstractRendererContextTest {

  @Test(expected = Err.class)
  public void norenderer() throws Throwable {
    List<Renderer> renderers = new ArrayList<>();
    List<MediaType> produces = ImmutableList.of(MediaType.json);
    View value = Results.html("view");
    new MockUnit()
        .run(unit -> {
          new AbstractRendererContext(renderers, produces, StandardCharsets.UTF_8, Locale.US,
              Collections.emptyMap()) {

            @Override
            protected void _send(final byte[] bytes) throws Exception {
            }

            @Override
            protected void _send(final ByteBuffer buffer) throws Exception {
            }

            @Override
            protected void _send(final FileChannel file) throws Exception {
            }

            @Override
            protected void _send(final InputStream stream) throws Exception {
            }

          }.render(value);
        });
  }

}
