package be.c4j.trajectcontrole.dto;

import java.io.Serializable;

/**
 * User: jeroen
 * Date: 6/22/12
 * Time: 9:24 PM
 */
public class Total implements Serializable {
    private Long total;

    public Total() {
    }

    public Total(Long total) {
        this.total = total;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
