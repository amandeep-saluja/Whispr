package com.chat.whispr.collections;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "chat")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Chat {

    @Id
    private String id;
    private Boolean isGroup;
    private Boolean deleted;
    private String name;
    private List<ChatUserDetails> userDetails;
}
