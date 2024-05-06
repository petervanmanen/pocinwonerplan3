package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Parkeerscan.
 */
@Entity
@Table(name = "parkeerscan")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Parkeerscan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "codegebruiker")
    private String codegebruiker;

    @Column(name = "codescanvoertuig")
    private String codescanvoertuig;

    @Column(name = "coordinaten")
    private String coordinaten;

    @Column(name = "foto")
    private String foto;

    @Column(name = "kenteken")
    private String kenteken;

    @Column(name = "parkeerrecht")
    private Boolean parkeerrecht;

    @Column(name = "tijdstip")
    private String tijdstip;

    @Column(name = "transactieid")
    private String transactieid;

    @JsonIgnoreProperties(value = { "komtvoortuitParkeerscan" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Naheffing komtvoortuitNaheffing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "ingevoerddoorStremmings",
            "gewijzigddoorStremmings",
            "voertuitSchouwrondes",
            "aanvragerSubsidies",
            "isverantwoordelijkevoorZaaktypes",
            "geleverdviaLeverancier",
            "isBetrokkene",
            "uitgevoerddoorParkeerscans",
            "melderMeldings",
            "uitvoerderMeldings",
            "auteurNotities",
            "behandelaarSubsidies",
            "procesleiderAanbestedings",
            "inhuurGunnings",
            "metBalieafspraaks",
            "isgevoerddoorKlantcontacts",
            "rollenApplicaties",
            "afhandelendmedewerkerZaaks",
        },
        allowSetters = true
    )
    private Medewerker uitgevoerddoorMedewerker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "betreftParkeerrechts", "betreftParkeerscans" }, allowSetters = true)
    private Voertuig betreftVoertuig;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "bevatStraatsectie", "betreftParkeerscans" }, allowSetters = true)
    private Parkeervlak betreftParkeervlak;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Parkeerscan id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodegebruiker() {
        return this.codegebruiker;
    }

    public Parkeerscan codegebruiker(String codegebruiker) {
        this.setCodegebruiker(codegebruiker);
        return this;
    }

    public void setCodegebruiker(String codegebruiker) {
        this.codegebruiker = codegebruiker;
    }

    public String getCodescanvoertuig() {
        return this.codescanvoertuig;
    }

    public Parkeerscan codescanvoertuig(String codescanvoertuig) {
        this.setCodescanvoertuig(codescanvoertuig);
        return this;
    }

    public void setCodescanvoertuig(String codescanvoertuig) {
        this.codescanvoertuig = codescanvoertuig;
    }

    public String getCoordinaten() {
        return this.coordinaten;
    }

    public Parkeerscan coordinaten(String coordinaten) {
        this.setCoordinaten(coordinaten);
        return this;
    }

    public void setCoordinaten(String coordinaten) {
        this.coordinaten = coordinaten;
    }

    public String getFoto() {
        return this.foto;
    }

    public Parkeerscan foto(String foto) {
        this.setFoto(foto);
        return this;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getKenteken() {
        return this.kenteken;
    }

    public Parkeerscan kenteken(String kenteken) {
        this.setKenteken(kenteken);
        return this;
    }

    public void setKenteken(String kenteken) {
        this.kenteken = kenteken;
    }

    public Boolean getParkeerrecht() {
        return this.parkeerrecht;
    }

    public Parkeerscan parkeerrecht(Boolean parkeerrecht) {
        this.setParkeerrecht(parkeerrecht);
        return this;
    }

    public void setParkeerrecht(Boolean parkeerrecht) {
        this.parkeerrecht = parkeerrecht;
    }

    public String getTijdstip() {
        return this.tijdstip;
    }

    public Parkeerscan tijdstip(String tijdstip) {
        this.setTijdstip(tijdstip);
        return this;
    }

    public void setTijdstip(String tijdstip) {
        this.tijdstip = tijdstip;
    }

    public String getTransactieid() {
        return this.transactieid;
    }

    public Parkeerscan transactieid(String transactieid) {
        this.setTransactieid(transactieid);
        return this;
    }

    public void setTransactieid(String transactieid) {
        this.transactieid = transactieid;
    }

    public Naheffing getKomtvoortuitNaheffing() {
        return this.komtvoortuitNaheffing;
    }

    public void setKomtvoortuitNaheffing(Naheffing naheffing) {
        this.komtvoortuitNaheffing = naheffing;
    }

    public Parkeerscan komtvoortuitNaheffing(Naheffing naheffing) {
        this.setKomtvoortuitNaheffing(naheffing);
        return this;
    }

    public Medewerker getUitgevoerddoorMedewerker() {
        return this.uitgevoerddoorMedewerker;
    }

    public void setUitgevoerddoorMedewerker(Medewerker medewerker) {
        this.uitgevoerddoorMedewerker = medewerker;
    }

    public Parkeerscan uitgevoerddoorMedewerker(Medewerker medewerker) {
        this.setUitgevoerddoorMedewerker(medewerker);
        return this;
    }

    public Voertuig getBetreftVoertuig() {
        return this.betreftVoertuig;
    }

    public void setBetreftVoertuig(Voertuig voertuig) {
        this.betreftVoertuig = voertuig;
    }

    public Parkeerscan betreftVoertuig(Voertuig voertuig) {
        this.setBetreftVoertuig(voertuig);
        return this;
    }

    public Parkeervlak getBetreftParkeervlak() {
        return this.betreftParkeervlak;
    }

    public void setBetreftParkeervlak(Parkeervlak parkeervlak) {
        this.betreftParkeervlak = parkeervlak;
    }

    public Parkeerscan betreftParkeervlak(Parkeervlak parkeervlak) {
        this.setBetreftParkeervlak(parkeervlak);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Parkeerscan)) {
            return false;
        }
        return getId() != null && getId().equals(((Parkeerscan) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Parkeerscan{" +
            "id=" + getId() +
            ", codegebruiker='" + getCodegebruiker() + "'" +
            ", codescanvoertuig='" + getCodescanvoertuig() + "'" +
            ", coordinaten='" + getCoordinaten() + "'" +
            ", foto='" + getFoto() + "'" +
            ", kenteken='" + getKenteken() + "'" +
            ", parkeerrecht='" + getParkeerrecht() + "'" +
            ", tijdstip='" + getTijdstip() + "'" +
            ", transactieid='" + getTransactieid() + "'" +
            "}";
    }
}
