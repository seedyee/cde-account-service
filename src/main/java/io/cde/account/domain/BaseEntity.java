package io.cde.account.domain;

import org.springframework.data.annotation.Id;

/**
 * @author lcl
 */
public class BaseEntity {

    /**
     * 主键id.
     */
    @Id
    private String id;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }
}
