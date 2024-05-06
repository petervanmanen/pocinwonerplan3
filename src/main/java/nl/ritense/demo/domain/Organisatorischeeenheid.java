package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Organisatorischeeenheid.
 */
@Entity
@Table(name = "organisatorischeeenheid")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Organisatorischeeenheid implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumontstaan")
    private String datumontstaan;

    @Column(name = "datumopheffing")
    private String datumopheffing;

    @Column(name = "emailadres")
    private String emailadres;

    @Size(max = 20)
    @Column(name = "faxnummer", length = 20)
    private String faxnummer;

    @Column(name = "formatie")
    private String formatie;

    @Column(name = "naam")
    private String naam;

    @Column(name = "naamverkort")
    private String naamverkort;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "organisatieidentificatie")
    private String organisatieidentificatie;

    @Size(max = 20)
    @Column(name = "telefoonnummer", length = 20)
    private String telefoonnummer;

    @Column(name = "toelichting")
    private String toelichting;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Organisatorischeeenheid id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatumontstaan() {
        return this.datumontstaan;
    }

    public Organisatorischeeenheid datumontstaan(String datumontstaan) {
        this.setDatumontstaan(datumontstaan);
        return this;
    }

    public void setDatumontstaan(String datumontstaan) {
        this.datumontstaan = datumontstaan;
    }

    public String getDatumopheffing() {
        return this.datumopheffing;
    }

    public Organisatorischeeenheid datumopheffing(String datumopheffing) {
        this.setDatumopheffing(datumopheffing);
        return this;
    }

    public void setDatumopheffing(String datumopheffing) {
        this.datumopheffing = datumopheffing;
    }

    public String getEmailadres() {
        return this.emailadres;
    }

    public Organisatorischeeenheid emailadres(String emailadres) {
        this.setEmailadres(emailadres);
        return this;
    }

    public void setEmailadres(String emailadres) {
        this.emailadres = emailadres;
    }

    public String getFaxnummer() {
        return this.faxnummer;
    }

    public Organisatorischeeenheid faxnummer(String faxnummer) {
        this.setFaxnummer(faxnummer);
        return this;
    }

    public void setFaxnummer(String faxnummer) {
        this.faxnummer = faxnummer;
    }

    public String getFormatie() {
        return this.formatie;
    }

    public Organisatorischeeenheid formatie(String formatie) {
        this.setFormatie(formatie);
        return this;
    }

    public void setFormatie(String formatie) {
        this.formatie = formatie;
    }

    public String getNaam() {
        return this.naam;
    }

    public Organisatorischeeenheid naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getNaamverkort() {
        return this.naamverkort;
    }

    public Organisatorischeeenheid naamverkort(String naamverkort) {
        this.setNaamverkort(naamverkort);
        return this;
    }

    public void setNaamverkort(String naamverkort) {
        this.naamverkort = naamverkort;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Organisatorischeeenheid omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getOrganisatieidentificatie() {
        return this.organisatieidentificatie;
    }

    public Organisatorischeeenheid organisatieidentificatie(String organisatieidentificatie) {
        this.setOrganisatieidentificatie(organisatieidentificatie);
        return this;
    }

    public void setOrganisatieidentificatie(String organisatieidentificatie) {
        this.organisatieidentificatie = organisatieidentificatie;
    }

    public String getTelefoonnummer() {
        return this.telefoonnummer;
    }

    public Organisatorischeeenheid telefoonnummer(String telefoonnummer) {
        this.setTelefoonnummer(telefoonnummer);
        return this;
    }

    public void setTelefoonnummer(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public String getToelichting() {
        return this.toelichting;
    }

    public Organisatorischeeenheid toelichting(String toelichting) {
        this.setToelichting(toelichting);
        return this;
    }

    public void setToelichting(String toelichting) {
        this.toelichting = toelichting;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organisatorischeeenheid)) {
            return false;
        }
        return getId() != null && getId().equals(((Organisatorischeeenheid) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Organisatorischeeenheid{" +
            "id=" + getId() +
            ", datumontstaan='" + getDatumontstaan() + "'" +
            ", datumopheffing='" + getDatumopheffing() + "'" +
            ", emailadres='" + getEmailadres() + "'" +
            ", faxnummer='" + getFaxnummer() + "'" +
            ", formatie='" + getFormatie() + "'" +
            ", naam='" + getNaam() + "'" +
            ", naamverkort='" + getNaamverkort() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", organisatieidentificatie='" + getOrganisatieidentificatie() + "'" +
            ", telefoonnummer='" + getTelefoonnummer() + "'" +
            ", toelichting='" + getToelichting() + "'" +
            "}";
    }
}
