package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Verhuurbaareenheid.
 */
@Entity
@Table(name = "verhuurbaareenheid")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Verhuurbaareenheid implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "adres")
    private String adres;

    @Column(name = "afmeting")
    private String afmeting;

    @Column(name = "bezetting")
    private String bezetting;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "datumwerkelijkbegin")
    private LocalDate datumwerkelijkbegin;

    @Column(name = "datumwerkelijkeinde")
    private LocalDate datumwerkelijkeinde;

    @Column(name = "huurprijs")
    private String huurprijs;

    @Column(name = "identificatie")
    private String identificatie;

    @Column(name = "naam")
    private String naam;

    @Column(name = "nettoomtrek")
    private String nettoomtrek;

    @Column(name = "nettooppervlak")
    private String nettooppervlak;

    @Column(name = "opmerkingen")
    private String opmerkingen;

    @Column(name = "type")
    private String type;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Verhuurbaareenheid id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdres() {
        return this.adres;
    }

    public Verhuurbaareenheid adres(String adres) {
        this.setAdres(adres);
        return this;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getAfmeting() {
        return this.afmeting;
    }

    public Verhuurbaareenheid afmeting(String afmeting) {
        this.setAfmeting(afmeting);
        return this;
    }

    public void setAfmeting(String afmeting) {
        this.afmeting = afmeting;
    }

    public String getBezetting() {
        return this.bezetting;
    }

    public Verhuurbaareenheid bezetting(String bezetting) {
        this.setBezetting(bezetting);
        return this;
    }

    public void setBezetting(String bezetting) {
        this.bezetting = bezetting;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Verhuurbaareenheid datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Verhuurbaareenheid datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public LocalDate getDatumwerkelijkbegin() {
        return this.datumwerkelijkbegin;
    }

    public Verhuurbaareenheid datumwerkelijkbegin(LocalDate datumwerkelijkbegin) {
        this.setDatumwerkelijkbegin(datumwerkelijkbegin);
        return this;
    }

    public void setDatumwerkelijkbegin(LocalDate datumwerkelijkbegin) {
        this.datumwerkelijkbegin = datumwerkelijkbegin;
    }

    public LocalDate getDatumwerkelijkeinde() {
        return this.datumwerkelijkeinde;
    }

    public Verhuurbaareenheid datumwerkelijkeinde(LocalDate datumwerkelijkeinde) {
        this.setDatumwerkelijkeinde(datumwerkelijkeinde);
        return this;
    }

    public void setDatumwerkelijkeinde(LocalDate datumwerkelijkeinde) {
        this.datumwerkelijkeinde = datumwerkelijkeinde;
    }

    public String getHuurprijs() {
        return this.huurprijs;
    }

    public Verhuurbaareenheid huurprijs(String huurprijs) {
        this.setHuurprijs(huurprijs);
        return this;
    }

    public void setHuurprijs(String huurprijs) {
        this.huurprijs = huurprijs;
    }

    public String getIdentificatie() {
        return this.identificatie;
    }

    public Verhuurbaareenheid identificatie(String identificatie) {
        this.setIdentificatie(identificatie);
        return this;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public String getNaam() {
        return this.naam;
    }

    public Verhuurbaareenheid naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getNettoomtrek() {
        return this.nettoomtrek;
    }

    public Verhuurbaareenheid nettoomtrek(String nettoomtrek) {
        this.setNettoomtrek(nettoomtrek);
        return this;
    }

    public void setNettoomtrek(String nettoomtrek) {
        this.nettoomtrek = nettoomtrek;
    }

    public String getNettooppervlak() {
        return this.nettooppervlak;
    }

    public Verhuurbaareenheid nettooppervlak(String nettooppervlak) {
        this.setNettooppervlak(nettooppervlak);
        return this;
    }

    public void setNettooppervlak(String nettooppervlak) {
        this.nettooppervlak = nettooppervlak;
    }

    public String getOpmerkingen() {
        return this.opmerkingen;
    }

    public Verhuurbaareenheid opmerkingen(String opmerkingen) {
        this.setOpmerkingen(opmerkingen);
        return this;
    }

    public void setOpmerkingen(String opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public String getType() {
        return this.type;
    }

    public Verhuurbaareenheid type(String type) {
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
        if (!(o instanceof Verhuurbaareenheid)) {
            return false;
        }
        return getId() != null && getId().equals(((Verhuurbaareenheid) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Verhuurbaareenheid{" +
            "id=" + getId() +
            ", adres='" + getAdres() + "'" +
            ", afmeting='" + getAfmeting() + "'" +
            ", bezetting='" + getBezetting() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", datumwerkelijkbegin='" + getDatumwerkelijkbegin() + "'" +
            ", datumwerkelijkeinde='" + getDatumwerkelijkeinde() + "'" +
            ", huurprijs='" + getHuurprijs() + "'" +
            ", identificatie='" + getIdentificatie() + "'" +
            ", naam='" + getNaam() + "'" +
            ", nettoomtrek='" + getNettoomtrek() + "'" +
            ", nettooppervlak='" + getNettooppervlak() + "'" +
            ", opmerkingen='" + getOpmerkingen() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
