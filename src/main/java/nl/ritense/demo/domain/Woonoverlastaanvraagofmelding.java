package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Woonoverlastaanvraagofmelding.
 */
@Entity
@Table(name = "woonoverlastaanvraagofmelding")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Woonoverlastaanvraagofmelding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "locatie")
    private String locatie;

    @Column(name = "locatieomschrijving")
    private String locatieomschrijving;

    @Column(name = "meldingomschrijving")
    private String meldingomschrijving;

    @Column(name = "meldingtekst")
    private String meldingtekst;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Woonoverlastaanvraagofmelding id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocatie() {
        return this.locatie;
    }

    public Woonoverlastaanvraagofmelding locatie(String locatie) {
        this.setLocatie(locatie);
        return this;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getLocatieomschrijving() {
        return this.locatieomschrijving;
    }

    public Woonoverlastaanvraagofmelding locatieomschrijving(String locatieomschrijving) {
        this.setLocatieomschrijving(locatieomschrijving);
        return this;
    }

    public void setLocatieomschrijving(String locatieomschrijving) {
        this.locatieomschrijving = locatieomschrijving;
    }

    public String getMeldingomschrijving() {
        return this.meldingomschrijving;
    }

    public Woonoverlastaanvraagofmelding meldingomschrijving(String meldingomschrijving) {
        this.setMeldingomschrijving(meldingomschrijving);
        return this;
    }

    public void setMeldingomschrijving(String meldingomschrijving) {
        this.meldingomschrijving = meldingomschrijving;
    }

    public String getMeldingtekst() {
        return this.meldingtekst;
    }

    public Woonoverlastaanvraagofmelding meldingtekst(String meldingtekst) {
        this.setMeldingtekst(meldingtekst);
        return this;
    }

    public void setMeldingtekst(String meldingtekst) {
        this.meldingtekst = meldingtekst;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Woonoverlastaanvraagofmelding)) {
            return false;
        }
        return getId() != null && getId().equals(((Woonoverlastaanvraagofmelding) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Woonoverlastaanvraagofmelding{" +
            "id=" + getId() +
            ", locatie='" + getLocatie() + "'" +
            ", locatieomschrijving='" + getLocatieomschrijving() + "'" +
            ", meldingomschrijving='" + getMeldingomschrijving() + "'" +
            ", meldingtekst='" + getMeldingtekst() + "'" +
            "}";
    }
}
