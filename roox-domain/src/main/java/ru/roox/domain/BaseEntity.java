package ru.roox.domain;


import javax.persistence.*;

/**
 * @author Siarhei Shchahratsou <s.siarhei@gmail.com>
 * @since 30.08.2014
 */
@MappedSuperclass
public class BaseEntity {

    /**
     * GenerationType.AUTO provides generating unique ID per DB
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Version
    protected Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }
}
