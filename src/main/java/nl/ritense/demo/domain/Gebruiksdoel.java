package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Gebruiksdoel.
 */
@Entity
@Table(name = "gebruiksdoel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Gebruiksdoel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "gebruiksdoelgebouwdobject")
    private String gebruiksdoelgebouwdobject;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Gebruiksdoel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGebruiksdoelgebouwdobject() {
        return this.gebruiksdoelgebouwdobject;
    }

    public Gebruiksdoel gebruiksdoelgebouwdobject(String gebruiksdoelgebouwdobject) {
        this.setGebruiksdoelgebouwdobject(gebruiksdoelgebouwdobject);
        return this;
    }

    public void setGebruiksdoelgebouwdobject(String gebruiksdoelgebouwdobject) {
        this.gebruiksdoelgebouwdobject = gebruiksdoelgebouwdobject;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gebruiksdoel)) {
            return false;
        }
        return getId() != null && getId().equals(((Gebruiksdoel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Gebruiksdoel{" +
            "id=" + getId() +
            ", gebruiksdoelgebouwdobject='" + getGebruiksdoelgebouwdobject() + "'" +
            "}";
    }
}
