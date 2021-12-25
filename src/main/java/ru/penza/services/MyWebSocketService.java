package ru.penza.services;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Service
@EnableScheduling
public class MyWebSocketService {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private WebSocketMessageBrokerStats webSocketMessageBrokerStats;

    @Autowired
    private EngineService engineService;

    @Autowired
    private ToolService toolService;

    @Autowired
    private ValueService valueService;


    @Scheduled(fixedRate = 1000, initialDelay = 5000)
    public void publishMessage() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("critical_values", toolService.getAllCriticalsMap());
        jsonObject.put("values", valueService.getAllValuesMap());
        jsonObject.put("engines", engineService.getAllEnginesNames());
        jsonObject.put("server_time", System.currentTimeMillis());
        messagingTemplate.convertAndSend("/topic/public", jsonObject.toString());
//        System.out.println("sent to topic");
    }


    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        System.out.println();
        System.out.println("[Disconnect Header]  " + headerAccessor);
        System.out.println("[Disconnect Stats] " + webSocketMessageBrokerStats.toString());
        System.out.println();
//        messagingTemplate.convertAndSend("/topic/public", "user disconnected");
    }


}



