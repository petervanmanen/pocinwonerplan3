package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Vastgoedcontractregel.
 */
@Entity
@Table(name = "vastgoedcontractregel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vastgoedcontractregel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bedrag")
    private String bedrag;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "frequentie")
    private String frequentie;

    @Column(name = "identificatie")
    private String identificatie;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "status")
    private String status;

    @Column(name = "type")
    private String type;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vastgoedcontractregel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBedrag() {
        return this.bedrag;
    }

    public Vastgoedcontractregel bedrag(String bedrag) {
        this.setBedrag(bedrag);
        return this;
    }

    public void setBedrag(String bedrag) {
        this.bedrag = bedrag;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Vastgoedcontractregel datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Vastgoedcontractregel datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public String getFrequentie() {
        return this.frequentie;
    }

    public Vastgoedcontractregel frequentie(String frequentie) {
        this.setFrequentie(frequentie);
        return this;
    }

    public void setFrequentie(String frequentie) {
        this.frequentie = frequentie;
    }

    public String getIdentificatie() {
        return this.identificatie;
    }

    public Vastgoedcontractregel identificatie(String identificatie) {
        this.setIdentificatie(identificatie);
        return this;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Vastgoedcontractregel omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getStatus() {
        return this.status;
    }

    public Vastgoedcontractregel status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return this.type;
    }

    public Vastgoedcontractregel type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vastgoedcontractregel)) {
            return false;
        }
        return getId() != null && getId().equals(((Vastgoedcontractregel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vastgoedcontractregel{" +
            "id=" + getId() +
            ", bedrag='" + getBedrag() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", frequentie='" + getFrequentie() + "'" +
            ", identificatie='" + getIdentificatie() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", status='" + getStatus() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
