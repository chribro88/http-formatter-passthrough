package io.axual.connect.plugins.http.formatter;

/*-
 * ========================LICENSE_START=================================
 * HTTP Sink Connector for Kafka Connect
 * %%
 * Copyright (C) 2020 Axual B.V.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import io.axual.connect.plugins.http.exceptions.MessageFormattingException;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.kafka.connect.sink.SinkRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>This message formatter will use the contents of the SinkRecord and transform it into a
 * JSON message.
 * </p>
 *
 * <p>To use the JSON Envelope Message Formatter the setting {@link io.axual.connect.plugins.http.HttpSinkConnectorConfig#MESSAGE_FORMATTER_CLASS_CONFIG}
 * must be set to this class. <br>
 * The SON Envelope Message Formatter doesn't take any arguments</p>
 */
public class PassthroughJsonValueFormatter extends JsonEnvelopeMessageFormatter {
    private static final Logger LOG = LoggerFactory.getLogger(JsonEnvelopeMessageFormatter.class);

    // private final ObjectMapper objectMapper;

    public PassthroughJsonValueFormatter(ObjectMapper objectMapper, JsonNodeFactory jsonNodeFactory) {
        super(objectMapper, jsonNodeFactory);
        // this.objectMapper = objectMapper;
    }

    public PassthroughJsonValueFormatter() {
        this(new ObjectMapper(), JsonNodeFactory.instance);
    }
  
    @Override
    public StringEntity createJsonMessage(SinkRecord record, ContentType contentType) {
        LOG.debug("Creating JSON Message");
        try {
          // String message = objectMapper.writeValueAsString(createNode(record.valueSchema(), record.value()));
          return new StringEntity(record.value().toString(), contentType);
        } catch (DataException e) {
          throw new MessageFormattingException("Could not serialize message", e);
        }
      }
}
}
