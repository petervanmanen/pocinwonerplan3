package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Leerjaar.
 */
@Entity
@Table(name = "leerjaar")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Leerjaar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "jaareinde")
    private String jaareinde;

    @Column(name = "jaarstart")
    private String jaarstart;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Leerjaar id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJaareinde() {
        return this.jaareinde;
    }

    public Leerjaar jaareinde(String jaareinde) {
        this.setJaareinde(jaareinde);
        return this;
    }

    public void setJaareinde(String jaareinde) {
        this.jaareinde = jaareinde;
    }

    public String getJaarstart() {
        return this.jaarstart;
    }

    public Leerjaar jaarstart(String jaarstart) {
        this.setJaarstart(jaarstart);
        return this;
    }

    public void setJaarstart(String jaarstart) {
        this.jaarstart = jaarstart;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Leerjaar)) {
            return false;
        }
        return getId() != null && getId().equals(((Leerjaar) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Leerjaar{" +
            "id=" + getId() +
            ", jaareinde='" + getJaareinde() + "'" +
            ", jaarstart='" + getJaarstart() + "'" +
            "}";
    }
}
