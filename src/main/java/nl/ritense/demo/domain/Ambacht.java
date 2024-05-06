package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Ambacht.
 */
@Entity
@Table(name = "ambacht")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ambacht implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ambachtsoort")
    private String ambachtsoort;

    @Column(name = "jaarambachttot")
    private String jaarambachttot;

    @Column(name = "jaarambachtvanaf")
    private String jaarambachtvanaf;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ambacht id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAmbachtsoort() {
        return this.ambachtsoort;
    }

    public Ambacht ambachtsoort(String ambachtsoort) {
        this.setAmbachtsoort(ambachtsoort);
        return this;
    }

    public void setAmbachtsoort(String ambachtsoort) {
        this.ambachtsoort = ambachtsoort;
    }

    public String getJaarambachttot() {
        return this.jaarambachttot;
    }

    public Ambacht jaarambachttot(String jaarambachttot) {
        this.setJaarambachttot(jaarambachttot);
        return this;
    }

    public void setJaarambachttot(String jaarambachttot) {
        this.jaarambachttot = jaarambachttot;
    }

    public String getJaarambachtvanaf() {
        return this.jaarambachtvanaf;
    }

    public Ambacht jaarambachtvanaf(String jaarambachtvanaf) {
        this.setJaarambachtvanaf(jaarambachtvanaf);
        return this;
    }

    public void setJaarambachtvanaf(String jaarambachtvanaf) {
        this.jaarambachtvanaf = jaarambachtvanaf;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ambacht)) {
            return false;
        }
        return getId() != null && getId().equals(((Ambacht) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ambacht{" +
            "id=" + getId() +
            ", ambachtsoort='" + getAmbachtsoort() + "'" +
            ", jaarambachttot='" + getJaarambachttot() + "'" +
            ", jaarambachtvanaf='" + getJaarambachtvanaf() + "'" +
            "}";
    }
}
