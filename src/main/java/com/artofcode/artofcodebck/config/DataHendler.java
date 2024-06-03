package com.artofcode.artofcodebck.Config;


import com.artofcode.artofcodebck.Entities.Message;
import com.artofcode.artofcodebck.Services.IMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Slf4j
public class DataHendler  extends TextWebSocketHandler {

    @Autowired
    IMessage iMessage;

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        log.info("Message:{} ", message.getPayload());

        //  JsonFactory factory = new JsonFactory();

        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsontest = (String) message.getPayload();

            Message message1 = mapper.readValue((String) message.getPayload(), Message.class);
            //mapper.writeValue(new File("Message"),Message );

            jsontest = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(message1);
            iMessage.addMessage(message1);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
