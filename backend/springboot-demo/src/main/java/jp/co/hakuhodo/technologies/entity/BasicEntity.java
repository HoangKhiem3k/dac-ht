package jp.co.hakuhodo.technologies.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BasicEntity implements Serializable {

    private static final long serialVersionUID = 6769376333919990385L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="insert_datetime", updatable = false)
    private LocalDateTime insertDatetime;

    @Column(name="update_datetime")
    private LocalDateTime updateDatetime;

    @Column(name="valid_flag")
    private Boolean validFlag;

    @PrePersist
    public void prePersist() {
        this.insertDatetime = LocalDateTime.now();
        this.updateDatetime = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updateDatetime = LocalDateTime.now();
    }
}
