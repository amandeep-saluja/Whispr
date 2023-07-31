package com.chat.whispr.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "USER_TBL")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String name;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_CHAT_TABLE",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "chat_id", referencedColumnName = "id")
            })
//    @JsonManagedReference
    @JsonIgnoreProperties("users")
    private List<Chat> chats;
}

