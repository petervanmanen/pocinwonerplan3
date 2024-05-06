package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Geboorteingeschrevenpersoon.
 */
@Entity
@Table(name = "geboorteingeschrevenpersoon")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Geboorteingeschrevenpersoon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumgeboorte")
    private String datumgeboorte;

    @Column(name = "geboorteland")
    private String geboorteland;

    @Column(name = "geboorteplaats")
    private String geboorteplaats;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Geboorteingeschrevenpersoon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatumgeboorte() {
        return this.datumgeboorte;
    }

    public Geboorteingeschrevenpersoon datumgeboorte(String datumgeboorte) {
        this.setDatumgeboorte(datumgeboorte);
        return this;
    }

    public void setDatumgeboorte(String datumgeboorte) {
        this.datumgeboorte = datumgeboorte;
    }

    public String getGeboorteland() {
        return this.geboorteland;
    }

    public Geboorteingeschrevenpersoon geboorteland(String geboorteland) {
        this.setGeboorteland(geboorteland);
        return this;
    }

    public void setGeboorteland(String geboorteland) {
        this.geboorteland = geboorteland;
    }

    public String getGeboorteplaats() {
        return this.geboorteplaats;
    }

    public Geboorteingeschrevenpersoon geboorteplaats(String geboorteplaats) {
        this.setGeboorteplaats(geboorteplaats);
        return this;
    }

    public void setGeboorteplaats(String geboorteplaats) {
        this.geboorteplaats = geboorteplaats;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Geboorteingeschrevenpersoon)) {
            return false;
        }
        return getId() != null && getId().equals(((Geboorteingeschrevenpersoon) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Geboorteingeschrevenpersoon{" +
            "id=" + getId() +
            ", datumgeboorte='" + getDatumgeboorte() + "'" +
            ", geboorteland='" + getGeboorteland() + "'" +
            ", geboorteplaats='" + getGeboorteplaats() + "'" +
            "}";
    }
}
