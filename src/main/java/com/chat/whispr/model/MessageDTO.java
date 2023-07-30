package com.chat.whispr.model;

import com.chat.whispr.entity.Message;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MessageDTO {
    private String id;

    private String body;

    private boolean isRead;

    private boolean isReceived;

    private LocalDateTime creationDateTime;

    private String chatId;

    private String userId;

    public static MessageDTO getMessageDTO(Message message) {
        MessageDTO dto = new MessageDTO();
        dto.setId(message.getId());
        dto.setChatId(message.getChatId());
        dto.setBody(message.getBody());
        dto.setRead(message.isRead());
        dto.setReceived(message.isReceived());
        dto.setUserId(message.getUserId());
        dto.setCreationDateTime(message.getCreationDateTime());
        return dto;
    }

    public static List<MessageDTO> getMessageDTOList(List<Message> messages) {
        if(null!=messages) {
            return messages.stream().map(MessageDTO::getMessageDTO).collect(Collectors.toList());
        }
        return null;
    }
}
