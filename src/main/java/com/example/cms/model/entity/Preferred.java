package com.example.cms.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public abstract class Preferred<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    public abstract T getPreference();
    public abstract void setPreference(T preference);
}
