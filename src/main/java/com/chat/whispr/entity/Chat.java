package com.chat.whispr.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Chat_TBL")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "group_name")
    private String groupName;

    @ManyToMany(mappedBy = "chats", fetch = FetchType.LAZY)
//    @JsonBackReference
    @JsonIgnoreProperties("chats")
    private List<User> users;
}
