package com.example.cms.model.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "preferred_characters")
public class PreferredCharacter extends Preferred<Character> {
    @ManyToOne
    @JoinColumn(name="characterId")
    private Character character;

    public static final String PATH = "/preferredcharacters";

    public PreferredCharacter(User user, Character character) {
        setUser(user);
        this.character = character;
    }

    @Override
    public Character getPreference() {
        return character;
    }

    @Override
    public void setPreference(Character character) {
        this.character = character;
    }
}
