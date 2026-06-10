package com.wsdungeon.dungeon.controller;

import com.wsdungeon.dungeon.model.GameResponse;
import com.wsdungeon.dungeon.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Map;

@Controller
public class GameWebSocketController {

    @Autowired
    private GameService gameService;

    @MessageMapping("/game/{sessionId}/command") // ta ka PostMapping bet websocketiem
    @SendTo("/topic/game/{sessionId}") // atgriezto returno visiem kas ir subscribojusi sitam endpointam
    public GameResponse handleCommand(@DestinationVariable String sessionId, @Payload Map<String, String> payload) throws IOException { // payload ir ta ka RequestBody
        return gameService.handleCommand(sessionId, payload.get("userId"), payload.get("command"));
    }
}
