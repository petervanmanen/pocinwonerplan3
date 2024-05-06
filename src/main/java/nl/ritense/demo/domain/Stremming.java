package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Stremming.
 */
@Entity
@Table(name = "stremming")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Stremming implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aantalgehinderden")
    private String aantalgehinderden;

    @Column(name = "datumaanmelding")
    private String datumaanmelding;

    @Column(name = "datumeinde")
    private String datumeinde;

    @Column(name = "datumstart")
    private String datumstart;

    @Column(name = "datumwijziging")
    private String datumwijziging;

    @Column(name = "delentoegestaan")
    private Boolean delentoegestaan;

    @Column(name = "geschiktvoorpublicatie")
    private Boolean geschiktvoorpublicatie;

    @Column(name = "hinderklasse")
    private String hinderklasse;

    @Column(name = "locatie")
    private String locatie;

    @Column(name = "naam")
    private String naam;

    @Column(name = "status")
    private String status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_stremming__betreft_wegdeel",
        joinColumns = @JoinColumn(name = "stremming_id"),
        inverseJoinColumns = @JoinColumn(name = "betreft_wegdeel_id")
    )
    @JsonIgnoreProperties(value = { "betreftStremmings" }, allowSetters = true)
    private Set<Wegdeel> betreftWegdeels = new HashSet<>();

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
    private Medewerker ingevoerddoorMedewerker;

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
    private Medewerker gewijzigddoorMedewerker;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Stremming id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAantalgehinderden() {
        return this.aantalgehinderden;
    }

    public Stremming aantalgehinderden(String aantalgehinderden) {
        this.setAantalgehinderden(aantalgehinderden);
        return this;
    }

    public void setAantalgehinderden(String aantalgehinderden) {
        this.aantalgehinderden = aantalgehinderden;
    }

    public String getDatumaanmelding() {
        return this.datumaanmelding;
    }

    public Stremming datumaanmelding(String datumaanmelding) {
        this.setDatumaanmelding(datumaanmelding);
        return this;
    }

    public void setDatumaanmelding(String datumaanmelding) {
        this.datumaanmelding = datumaanmelding;
    }

    public String getDatumeinde() {
        return this.datumeinde;
    }

    public Stremming datumeinde(String datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(String datumeinde) {
        this.datumeinde = datumeinde;
    }

    public String getDatumstart() {
        return this.datumstart;
    }

    public Stremming datumstart(String datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(String datumstart) {
        this.datumstart = datumstart;
    }

    public String getDatumwijziging() {
        return this.datumwijziging;
    }

    public Stremming datumwijziging(String datumwijziging) {
        this.setDatumwijziging(datumwijziging);
        return this;
    }

    public void setDatumwijziging(String datumwijziging) {
        this.datumwijziging = datumwijziging;
    }

    public Boolean getDelentoegestaan() {
        return this.delentoegestaan;
    }

    public Stremming delentoegestaan(Boolean delentoegestaan) {
        this.setDelentoegestaan(delentoegestaan);
        return this;
    }

    public void setDelentoegestaan(Boolean delentoegestaan) {
        this.delentoegestaan = delentoegestaan;
    }

    public Boolean getGeschiktvoorpublicatie() {
        return this.geschiktvoorpublicatie;
    }

    public Stremming geschiktvoorpublicatie(Boolean geschiktvoorpublicatie) {
        this.setGeschiktvoorpublicatie(geschiktvoorpublicatie);
        return this;
    }

    public void setGeschiktvoorpublicatie(Boolean geschiktvoorpublicatie) {
        this.geschiktvoorpublicatie = geschiktvoorpublicatie;
    }

    public String getHinderklasse() {
        return this.hinderklasse;
    }

    public Stremming hinderklasse(String hinderklasse) {
        this.setHinderklasse(hinderklasse);
        return this;
    }

    public void setHinderklasse(String hinderklasse) {
        this.hinderklasse = hinderklasse;
    }

    public String getLocatie() {
        return this.locatie;
    }

    public Stremming locatie(String locatie) {
        this.setLocatie(locatie);
        return this;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getNaam() {
        return this.naam;
    }

    public Stremming naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getStatus() {
        return this.status;
    }

    public Stremming status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Wegdeel> getBetreftWegdeels() {
        return this.betreftWegdeels;
    }

    public void setBetreftWegdeels(Set<Wegdeel> wegdeels) {
        this.betreftWegdeels = wegdeels;
    }

    public Stremming betreftWegdeels(Set<Wegdeel> wegdeels) {
        this.setBetreftWegdeels(wegdeels);
        return this;
    }

    public Stremming addBetreftWegdeel(Wegdeel wegdeel) {
        this.betreftWegdeels.add(wegdeel);
        return this;
    }

    public Stremming removeBetreftWegdeel(Wegdeel wegdeel) {
        this.betreftWegdeels.remove(wegdeel);
        return this;
    }

    public Medewerker getIngevoerddoorMedewerker() {
        return this.ingevoerddoorMedewerker;
    }

    public void setIngevoerddoorMedewerker(Medewerker medewerker) {
        this.ingevoerddoorMedewerker = medewerker;
    }

    public Stremming ingevoerddoorMedewerker(Medewerker medewerker) {
        this.setIngevoerddoorMedewerker(medewerker);
        return this;
    }

    public Medewerker getGewijzigddoorMedewerker() {
        return this.gewijzigddoorMedewerker;
    }

    public void setGewijzigddoorMedewerker(Medewerker medewerker) {
        this.gewijzigddoorMedewerker = medewerker;
    }

    public Stremming gewijzigddoorMedewerker(Medewerker medewerker) {
        this.setGewijzigddoorMedewerker(medewerker);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stremming)) {
            return false;
        }
        return getId() != null && getId().equals(((Stremming) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Stremming{" +
            "id=" + getId() +
            ", aantalgehinderden='" + getAantalgehinderden() + "'" +
            ", datumaanmelding='" + getDatumaanmelding() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", datumwijziging='" + getDatumwijziging() + "'" +
            ", delentoegestaan='" + getDelentoegestaan() + "'" +
            ", geschiktvoorpublicatie='" + getGeschiktvoorpublicatie() + "'" +
            ", hinderklasse='" + getHinderklasse() + "'" +
            ", locatie='" + getLocatie() + "'" +
            ", naam='" + getNaam() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
