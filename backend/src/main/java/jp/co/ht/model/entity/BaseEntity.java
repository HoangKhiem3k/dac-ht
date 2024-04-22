package jp.co.ht.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import jp.co.ht.common.constant.CommonConst;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 6769376333919990385L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="insert_datetime", updatable = false)
    private LocalDateTime insertDatetime;

    @Column(name="update_datetime")
    private LocalDateTime updateDatetime;

    @Column(name="valid_flag")
    private Short validFlag;

    @PrePersist
    public void prePersist() {
        this.insertDatetime = LocalDateTime.now();
        this.updateDatetime = LocalDateTime.now();
        this.setValidFlag(CommonConst.VALID_DATA_FLAG);
    }

    @PreUpdate
    public void preUpdate() {
        this.updateDatetime = LocalDateTime.now();
    }
}
