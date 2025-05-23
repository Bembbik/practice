package com.example.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String username;
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    @JsonIgnore
    private Set<User> friends = new HashSet<>();

    @ManyToMany(mappedBy = "friends")
    @JsonIgnore
    private Set<User> friendOf = new HashSet<>();

    // Добавление друга с двусторонней связью
    public void addFriend(User friend) {
        if (friends.add(friend)) {
            friend.getFriends().add(this);
        }
    }

    // Удаление друга с двусторонней связью
    public void removeFriend(User friend) {
        if (friends.remove(friend)) {
            friend.getFriends().remove(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return id != null && id.equals(user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
