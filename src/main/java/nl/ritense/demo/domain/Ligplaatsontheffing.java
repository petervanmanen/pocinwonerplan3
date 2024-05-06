package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Ligplaatsontheffing.
 */
@Entity
@Table(name = "ligplaatsontheffing")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ligplaatsontheffing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "stickernummer")
    private String stickernummer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ligplaatsontheffing id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStickernummer() {
        return this.stickernummer;
    }

    public Ligplaatsontheffing stickernummer(String stickernummer) {
        this.setStickernummer(stickernummer);
        return this;
    }

    public void setStickernummer(String stickernummer) {
        this.stickernummer = stickernummer;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ligplaatsontheffing)) {
            return false;
        }
        return getId() != null && getId().equals(((Ligplaatsontheffing) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ligplaatsontheffing{" +
            "id=" + getId() +
            ", stickernummer='" + getStickernummer() + "'" +
            "}";
    }
}
