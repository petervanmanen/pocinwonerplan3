package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Ligplaats.
 */
@Entity
@Table(name = "ligplaats")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ligplaats implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheid")
    private LocalDate datumbegingeldigheid;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumeindegeldigheid")
    private LocalDate datumeindegeldigheid;

    @Column(name = "datumingang")
    private LocalDate datumingang;

    @Column(name = "documentdatum")
    private LocalDate documentdatum;

    @Column(name = "documentnummer")
    private String documentnummer;

    @Column(name = "geconstateerd")
    private String geconstateerd;

    @Column(name = "geometrie")
    private String geometrie;

    @Column(name = "identificatie")
    private String identificatie;

    @Column(name = "inonderzoek")
    private Boolean inonderzoek;

    @Column(name = "status")
    private String status;

    @Column(name = "versie")
    private String versie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ligplaats id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbegingeldigheid() {
        return this.datumbegingeldigheid;
    }

    public Ligplaats datumbegingeldigheid(LocalDate datumbegingeldigheid) {
        this.setDatumbegingeldigheid(datumbegingeldigheid);
        return this;
    }

    public void setDatumbegingeldigheid(LocalDate datumbegingeldigheid) {
        this.datumbegingeldigheid = datumbegingeldigheid;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Ligplaats datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumeindegeldigheid() {
        return this.datumeindegeldigheid;
    }

    public Ligplaats datumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.setDatumeindegeldigheid(datumeindegeldigheid);
        return this;
    }

    public void setDatumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.datumeindegeldigheid = datumeindegeldigheid;
    }

    public LocalDate getDatumingang() {
        return this.datumingang;
    }

    public Ligplaats datumingang(LocalDate datumingang) {
        this.setDatumingang(datumingang);
        return this;
    }

    public void setDatumingang(LocalDate datumingang) {
        this.datumingang = datumingang;
    }

    public LocalDate getDocumentdatum() {
        return this.documentdatum;
    }

    public Ligplaats documentdatum(LocalDate documentdatum) {
        this.setDocumentdatum(documentdatum);
        return this;
    }

    public void setDocumentdatum(LocalDate documentdatum) {
        this.documentdatum = documentdatum;
    }

    public String getDocumentnummer() {
        return this.documentnummer;
    }

    public Ligplaats documentnummer(String documentnummer) {
        this.setDocumentnummer(documentnummer);
        return this;
    }

    public void setDocumentnummer(String documentnummer) {
        this.documentnummer = documentnummer;
    }

    public String getGeconstateerd() {
        return this.geconstateerd;
    }

    public Ligplaats geconstateerd(String geconstateerd) {
        this.setGeconstateerd(geconstateerd);
        return this;
    }

    public void setGeconstateerd(String geconstateerd) {
        this.geconstateerd = geconstateerd;
    }

    public String getGeometrie() {
        return this.geometrie;
    }

    public Ligplaats geometrie(String geometrie) {
        this.setGeometrie(geometrie);
        return this;
    }

    public void setGeometrie(String geometrie) {
        this.geometrie = geometrie;
    }

    public String getIdentificatie() {
        return this.identificatie;
    }

    public Ligplaats identificatie(String identificatie) {
        this.setIdentificatie(identificatie);
        return this;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public Boolean getInonderzoek() {
        return this.inonderzoek;
    }

    public Ligplaats inonderzoek(Boolean inonderzoek) {
        this.setInonderzoek(inonderzoek);
        return this;
    }

    public void setInonderzoek(Boolean inonderzoek) {
        this.inonderzoek = inonderzoek;
    }

    public String getStatus() {
        return this.status;
    }

    public Ligplaats status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersie() {
        return this.versie;
    }

    public Ligplaats versie(String versie) {
        this.setVersie(versie);
        return this;
    }

    public void setVersie(String versie) {
        this.versie = versie;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ligplaats)) {
            return false;
        }
        return getId() != null && getId().equals(((Ligplaats) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ligplaats{" +
            "id=" + getId() +
            ", datumbegingeldigheid='" + getDatumbegingeldigheid() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumeindegeldigheid='" + getDatumeindegeldigheid() + "'" +
            ", datumingang='" + getDatumingang() + "'" +
            ", documentdatum='" + getDocumentdatum() + "'" +
            ", documentnummer='" + getDocumentnummer() + "'" +
            ", geconstateerd='" + getGeconstateerd() + "'" +
            ", geometrie='" + getGeometrie() + "'" +
            ", identificatie='" + getIdentificatie() + "'" +
            ", inonderzoek='" + getInonderzoek() + "'" +
            ", status='" + getStatus() + "'" +
            ", versie='" + getVersie() + "'" +
            "}";
    }
}
