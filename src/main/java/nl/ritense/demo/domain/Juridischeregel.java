package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Juridischeregel.
 */
@Entity
@Table(name = "juridischeregel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Juridischeregel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbekend")
    private LocalDate datumbekend;

    @Column(name = "datumeindegeldigheid")
    private LocalDate datumeindegeldigheid;

    @Column(name = "datuminwerking")
    private LocalDate datuminwerking;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "regeltekst")
    private String regeltekst;

    @Column(name = "thema")
    private String thema;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Juridischeregel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbekend() {
        return this.datumbekend;
    }

    public Juridischeregel datumbekend(LocalDate datumbekend) {
        this.setDatumbekend(datumbekend);
        return this;
    }

    public void setDatumbekend(LocalDate datumbekend) {
        this.datumbekend = datumbekend;
    }

    public LocalDate getDatumeindegeldigheid() {
        return this.datumeindegeldigheid;
    }

    public Juridischeregel datumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.setDatumeindegeldigheid(datumeindegeldigheid);
        return this;
    }

    public void setDatumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.datumeindegeldigheid = datumeindegeldigheid;
    }

    public LocalDate getDatuminwerking() {
        return this.datuminwerking;
    }

    public Juridischeregel datuminwerking(LocalDate datuminwerking) {
        this.setDatuminwerking(datuminwerking);
        return this;
    }

    public void setDatuminwerking(LocalDate datuminwerking) {
        this.datuminwerking = datuminwerking;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Juridischeregel datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Juridischeregel omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getRegeltekst() {
        return this.regeltekst;
    }

    public Juridischeregel regeltekst(String regeltekst) {
        this.setRegeltekst(regeltekst);
        return this;
    }

    public void setRegeltekst(String regeltekst) {
        this.regeltekst = regeltekst;
    }

    public String getThema() {
        return this.thema;
    }

    public Juridischeregel thema(String thema) {
        this.setThema(thema);
        return this;
    }

    public void setThema(String thema) {
        this.thema = thema;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Juridischeregel)) {
            return false;
        }
        return getId() != null && getId().equals(((Juridischeregel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Juridischeregel{" +
            "id=" + getId() +
            ", datumbekend='" + getDatumbekend() + "'" +
            ", datumeindegeldigheid='" + getDatumeindegeldigheid() + "'" +
            ", datuminwerking='" + getDatuminwerking() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", regeltekst='" + getRegeltekst() + "'" +
            ", thema='" + getThema() + "'" +
            "}";
    }
}
