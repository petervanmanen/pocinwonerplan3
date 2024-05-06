package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Vomaanvraagofmelding.
 */
@Entity
@Table(name = "vomaanvraagofmelding")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vomaanvraagofmelding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "activiteiten")
    private String activiteiten;

    @Column(name = "adres")
    private String adres;

    @Column(name = "bagid")
    private String bagid;

    @Column(name = "dossiernummer")
    private String dossiernummer;

    @Column(name = "intaketype")
    private String intaketype;

    @Column(name = "internnummer")
    private String internnummer;

    @Column(name = "kadastraleaanduiding")
    private String kadastraleaanduiding;

    @Column(name = "kenmerk")
    private String kenmerk;

    @Column(name = "locatie")
    private String locatie;

    @Column(name = "locatieomschrijving")
    private String locatieomschrijving;

    @Column(name = "toelichting")
    private String toelichting;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vomaanvraagofmelding id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActiviteiten() {
        return this.activiteiten;
    }

    public Vomaanvraagofmelding activiteiten(String activiteiten) {
        this.setActiviteiten(activiteiten);
        return this;
    }

    public void setActiviteiten(String activiteiten) {
        this.activiteiten = activiteiten;
    }

    public String getAdres() {
        return this.adres;
    }

    public Vomaanvraagofmelding adres(String adres) {
        this.setAdres(adres);
        return this;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getBagid() {
        return this.bagid;
    }

    public Vomaanvraagofmelding bagid(String bagid) {
        this.setBagid(bagid);
        return this;
    }

    public void setBagid(String bagid) {
        this.bagid = bagid;
    }

    public String getDossiernummer() {
        return this.dossiernummer;
    }

    public Vomaanvraagofmelding dossiernummer(String dossiernummer) {
        this.setDossiernummer(dossiernummer);
        return this;
    }

    public void setDossiernummer(String dossiernummer) {
        this.dossiernummer = dossiernummer;
    }

    public String getIntaketype() {
        return this.intaketype;
    }

    public Vomaanvraagofmelding intaketype(String intaketype) {
        this.setIntaketype(intaketype);
        return this;
    }

    public void setIntaketype(String intaketype) {
        this.intaketype = intaketype;
    }

    public String getInternnummer() {
        return this.internnummer;
    }

    public Vomaanvraagofmelding internnummer(String internnummer) {
        this.setInternnummer(internnummer);
        return this;
    }

    public void setInternnummer(String internnummer) {
        this.internnummer = internnummer;
    }

    public String getKadastraleaanduiding() {
        return this.kadastraleaanduiding;
    }

    public Vomaanvraagofmelding kadastraleaanduiding(String kadastraleaanduiding) {
        this.setKadastraleaanduiding(kadastraleaanduiding);
        return this;
    }

    public void setKadastraleaanduiding(String kadastraleaanduiding) {
        this.kadastraleaanduiding = kadastraleaanduiding;
    }

    public String getKenmerk() {
        return this.kenmerk;
    }

    public Vomaanvraagofmelding kenmerk(String kenmerk) {
        this.setKenmerk(kenmerk);
        return this;
    }

    public void setKenmerk(String kenmerk) {
        this.kenmerk = kenmerk;
    }

    public String getLocatie() {
        return this.locatie;
    }

    public Vomaanvraagofmelding locatie(String locatie) {
        this.setLocatie(locatie);
        return this;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getLocatieomschrijving() {
        return this.locatieomschrijving;
    }

    public Vomaanvraagofmelding locatieomschrijving(String locatieomschrijving) {
        this.setLocatieomschrijving(locatieomschrijving);
        return this;
    }

    public void setLocatieomschrijving(String locatieomschrijving) {
        this.locatieomschrijving = locatieomschrijving;
    }

    public String getToelichting() {
        return this.toelichting;
    }

    public Vomaanvraagofmelding toelichting(String toelichting) {
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
        if (!(o instanceof Vomaanvraagofmelding)) {
            return false;
        }
        return getId() != null && getId().equals(((Vomaanvraagofmelding) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vomaanvraagofmelding{" +
            "id=" + getId() +
            ", activiteiten='" + getActiviteiten() + "'" +
            ", adres='" + getAdres() + "'" +
            ", bagid='" + getBagid() + "'" +
            ", dossiernummer='" + getDossiernummer() + "'" +
            ", intaketype='" + getIntaketype() + "'" +
            ", internnummer='" + getInternnummer() + "'" +
            ", kadastraleaanduiding='" + getKadastraleaanduiding() + "'" +
            ", kenmerk='" + getKenmerk() + "'" +
            ", locatie='" + getLocatie() + "'" +
            ", locatieomschrijving='" + getLocatieomschrijving() + "'" +
            ", toelichting='" + getToelichting() + "'" +
            "}";
    }
}
