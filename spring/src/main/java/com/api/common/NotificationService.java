package com.api.common;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class NotificationService {
    private final WebClient webClient;
    private final String notificationApiUri;

    public NotificationService(WebClient webClient, @Value("${api.notifications.uri}") String notificationApiUri) {
        this.webClient = webClient;
        this.notificationApiUri = notificationApiUri;
    }

    /**
     * Send a email
     * 
     * @param email recipient email 
     * @param message message to sender
     */
    public void sendEmail(String email, String message) {
        try {
            ClientResponse response = this.webClient.post()
                .uri(notificationApiUri + "/notify")
                .bodyValue(Map.of(
                    "email", email,
                    "message", message
                ))
                .exchangeToMono(Mono::just)
                .block();

            HttpStatusCode status = (response != null) ? response.statusCode() : null;

            if (!HttpStatus.NO_CONTENT.equals(status)) {
                System.out.println("Falha ao enviar notificação. Status: " + status);
            } else {
                System.out.println("Notificação enviada com sucesso!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao enviar notificação: " + e.getMessage());
        }
    }
}
