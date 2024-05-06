package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Inonderzoek.
 */
@Entity
@Table(name = "inonderzoek")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Inonderzoek implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aanduidinggegevensinonderzoek")
    private String aanduidinggegevensinonderzoek;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Inonderzoek id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAanduidinggegevensinonderzoek() {
        return this.aanduidinggegevensinonderzoek;
    }

    public Inonderzoek aanduidinggegevensinonderzoek(String aanduidinggegevensinonderzoek) {
        this.setAanduidinggegevensinonderzoek(aanduidinggegevensinonderzoek);
        return this;
    }

    public void setAanduidinggegevensinonderzoek(String aanduidinggegevensinonderzoek) {
        this.aanduidinggegevensinonderzoek = aanduidinggegevensinonderzoek;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inonderzoek)) {
            return false;
        }
        return getId() != null && getId().equals(((Inonderzoek) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inonderzoek{" +
            "id=" + getId() +
            ", aanduidinggegevensinonderzoek='" + getAanduidinggegevensinonderzoek() + "'" +
            "}";
    }
}
