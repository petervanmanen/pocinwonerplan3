package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A Binnenlocatie.
 */
@Entity
@Table(name = "binnenlocatie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Binnenlocatie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "adres")
    private String adres;

    @Column(name = "bouwjaar")
    private String bouwjaar;

    @Column(name = "gemeentelijk")
    private Boolean gemeentelijk;

    @Column(name = "geschattekostenperjaar", precision = 21, scale = 2)
    private BigDecimal geschattekostenperjaar;

    @Column(name = "gymzaal")
    private String gymzaal;

    @Column(name = "klokurenonderwijs")
    private String klokurenonderwijs;

    @Column(name = "klokurenverenigingen")
    private String klokurenverenigingen;

    @Column(name = "locatie")
    private String locatie;

    @Column(name = "onderhoudsniveau")
    private String onderhoudsniveau;

    @Column(name = "onderhoudsstatus")
    private String onderhoudsstatus;

    @Column(name = "sporthal")
    private String sporthal;

    @Column(name = "vloeroppervlakte")
    private String vloeroppervlakte;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "inspectierapportBinnenlocatie")
    @JsonIgnoreProperties(
        value = {
            "heeftkenmerkIdentificatiekenmerk",
            "isvanDocumenttype",
            "isvastgelegdinVerkeersbesluit",
            "isvastgelegdinBesluit",
            "inspectierapportBinnenlocatie",
            "heeftRapportagemoment",
            "heeftSubsidies",
            "heeftdocumentenApplicaties",
            "kanvastgelegdzijnalsBesluits",
            "kentZaaks",
        },
        allowSetters = true
    )
    private Set<Document> inspectierapportDocuments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftVastgoedobject", "maaktdeeluitvanPands", "isgevestigdinBinnenlocaties" }, allowSetters = true)
    private Verblijfsobject isgevestigdinVerblijfsobject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "bedientBinnenlocaties", "valtbinnenAreaals" }, allowSetters = true)
    private Wijk bedientWijk;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_binnenlocatie__heeft_belijning",
        joinColumns = @JoinColumn(name = "binnenlocatie_id"),
        inverseJoinColumns = @JoinColumn(name = "heeft_belijning_id")
    )
    @JsonIgnoreProperties(value = { "heeftBinnenlocaties", "heeftVelds" }, allowSetters = true)
    private Set<Belijning> heeftBelijnings = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_binnenlocatie__heeft_sportmateriaal",
        joinColumns = @JoinColumn(name = "binnenlocatie_id"),
        inverseJoinColumns = @JoinColumn(name = "heeft_sportmateriaal_id")
    )
    @JsonIgnoreProperties(value = { "heeftBinnenlocaties" }, allowSetters = true)
    private Set<Sportmateriaal> heeftSportmateriaals = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Binnenlocatie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdres() {
        return this.adres;
    }

    public Binnenlocatie adres(String adres) {
        this.setAdres(adres);
        return this;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getBouwjaar() {
        return this.bouwjaar;
    }

    public Binnenlocatie bouwjaar(String bouwjaar) {
        this.setBouwjaar(bouwjaar);
        return this;
    }

    public void setBouwjaar(String bouwjaar) {
        this.bouwjaar = bouwjaar;
    }

    public Boolean getGemeentelijk() {
        return this.gemeentelijk;
    }

    public Binnenlocatie gemeentelijk(Boolean gemeentelijk) {
        this.setGemeentelijk(gemeentelijk);
        return this;
    }

    public void setGemeentelijk(Boolean gemeentelijk) {
        this.gemeentelijk = gemeentelijk;
    }

    public BigDecimal getGeschattekostenperjaar() {
        return this.geschattekostenperjaar;
    }

    public Binnenlocatie geschattekostenperjaar(BigDecimal geschattekostenperjaar) {
        this.setGeschattekostenperjaar(geschattekostenperjaar);
        return this;
    }

    public void setGeschattekostenperjaar(BigDecimal geschattekostenperjaar) {
        this.geschattekostenperjaar = geschattekostenperjaar;
    }

    public String getGymzaal() {
        return this.gymzaal;
    }

    public Binnenlocatie gymzaal(String gymzaal) {
        this.setGymzaal(gymzaal);
        return this;
    }

    public void setGymzaal(String gymzaal) {
        this.gymzaal = gymzaal;
    }

    public String getKlokurenonderwijs() {
        return this.klokurenonderwijs;
    }

    public Binnenlocatie klokurenonderwijs(String klokurenonderwijs) {
        this.setKlokurenonderwijs(klokurenonderwijs);
        return this;
    }

    public void setKlokurenonderwijs(String klokurenonderwijs) {
        this.klokurenonderwijs = klokurenonderwijs;
    }

    public String getKlokurenverenigingen() {
        return this.klokurenverenigingen;
    }

    public Binnenlocatie klokurenverenigingen(String klokurenverenigingen) {
        this.setKlokurenverenigingen(klokurenverenigingen);
        return this;
    }

    public void setKlokurenverenigingen(String klokurenverenigingen) {
        this.klokurenverenigingen = klokurenverenigingen;
    }

    public String getLocatie() {
        return this.locatie;
    }

    public Binnenlocatie locatie(String locatie) {
        this.setLocatie(locatie);
        return this;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getOnderhoudsniveau() {
        return this.onderhoudsniveau;
    }

    public Binnenlocatie onderhoudsniveau(String onderhoudsniveau) {
        this.setOnderhoudsniveau(onderhoudsniveau);
        return this;
    }

    public void setOnderhoudsniveau(String onderhoudsniveau) {
        this.onderhoudsniveau = onderhoudsniveau;
    }

    public String getOnderhoudsstatus() {
        return this.onderhoudsstatus;
    }

    public Binnenlocatie onderhoudsstatus(String onderhoudsstatus) {
        this.setOnderhoudsstatus(onderhoudsstatus);
        return this;
    }

    public void setOnderhoudsstatus(String onderhoudsstatus) {
        this.onderhoudsstatus = onderhoudsstatus;
    }

    public String getSporthal() {
        return this.sporthal;
    }

    public Binnenlocatie sporthal(String sporthal) {
        this.setSporthal(sporthal);
        return this;
    }

    public void setSporthal(String sporthal) {
        this.sporthal = sporthal;
    }

    public String getVloeroppervlakte() {
        return this.vloeroppervlakte;
    }

    public Binnenlocatie vloeroppervlakte(String vloeroppervlakte) {
        this.setVloeroppervlakte(vloeroppervlakte);
        return this;
    }

    public void setVloeroppervlakte(String vloeroppervlakte) {
        this.vloeroppervlakte = vloeroppervlakte;
    }

    public Set<Document> getInspectierapportDocuments() {
        return this.inspectierapportDocuments;
    }

    public void setInspectierapportDocuments(Set<Document> documents) {
        if (this.inspectierapportDocuments != null) {
            this.inspectierapportDocuments.forEach(i -> i.setInspectierapportBinnenlocatie(null));
        }
        if (documents != null) {
            documents.forEach(i -> i.setInspectierapportBinnenlocatie(this));
        }
        this.inspectierapportDocuments = documents;
    }

    public Binnenlocatie inspectierapportDocuments(Set<Document> documents) {
        this.setInspectierapportDocuments(documents);
        return this;
    }

    public Binnenlocatie addInspectierapportDocument(Document document) {
        this.inspectierapportDocuments.add(document);
        document.setInspectierapportBinnenlocatie(this);
        return this;
    }

    public Binnenlocatie removeInspectierapportDocument(Document document) {
        this.inspectierapportDocuments.remove(document);
        document.setInspectierapportBinnenlocatie(null);
        return this;
    }

    public Verblijfsobject getIsgevestigdinVerblijfsobject() {
        return this.isgevestigdinVerblijfsobject;
    }

    public void setIsgevestigdinVerblijfsobject(Verblijfsobject verblijfsobject) {
        this.isgevestigdinVerblijfsobject = verblijfsobject;
    }

    public Binnenlocatie isgevestigdinVerblijfsobject(Verblijfsobject verblijfsobject) {
        this.setIsgevestigdinVerblijfsobject(verblijfsobject);
        return this;
    }

    public Wijk getBedientWijk() {
        return this.bedientWijk;
    }

    public void setBedientWijk(Wijk wijk) {
        this.bedientWijk = wijk;
    }

    public Binnenlocatie bedientWijk(Wijk wijk) {
        this.setBedientWijk(wijk);
        return this;
    }

    public Set<Belijning> getHeeftBelijnings() {
        return this.heeftBelijnings;
    }

    public void setHeeftBelijnings(Set<Belijning> belijnings) {
        this.heeftBelijnings = belijnings;
    }

    public Binnenlocatie heeftBelijnings(Set<Belijning> belijnings) {
        this.setHeeftBelijnings(belijnings);
        return this;
    }

    public Binnenlocatie addHeeftBelijning(Belijning belijning) {
        this.heeftBelijnings.add(belijning);
        return this;
    }

    public Binnenlocatie removeHeeftBelijning(Belijning belijning) {
        this.heeftBelijnings.remove(belijning);
        return this;
    }

    public Set<Sportmateriaal> getHeeftSportmateriaals() {
        return this.heeftSportmateriaals;
    }

    public void setHeeftSportmateriaals(Set<Sportmateriaal> sportmateriaals) {
        this.heeftSportmateriaals = sportmateriaals;
    }

    public Binnenlocatie heeftSportmateriaals(Set<Sportmateriaal> sportmateriaals) {
        this.setHeeftSportmateriaals(sportmateriaals);
        return this;
    }

    public Binnenlocatie addHeeftSportmateriaal(Sportmateriaal sportmateriaal) {
        this.heeftSportmateriaals.add(sportmateriaal);
        return this;
    }

    public Binnenlocatie removeHeeftSportmateriaal(Sportmateriaal sportmateriaal) {
        this.heeftSportmateriaals.remove(sportmateriaal);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Binnenlocatie)) {
            return false;
        }
        return getId() != null && getId().equals(((Binnenlocatie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Binnenlocatie{" +
            "id=" + getId() +
            ", adres='" + getAdres() + "'" +
            ", bouwjaar='" + getBouwjaar() + "'" +
            ", gemeentelijk='" + getGemeentelijk() + "'" +
            ", geschattekostenperjaar=" + getGeschattekostenperjaar() +
            ", gymzaal='" + getGymzaal() + "'" +
            ", klokurenonderwijs='" + getKlokurenonderwijs() + "'" +
            ", klokurenverenigingen='" + getKlokurenverenigingen() + "'" +
            ", locatie='" + getLocatie() + "'" +
            ", onderhoudsniveau='" + getOnderhoudsniveau() + "'" +
            ", onderhoudsstatus='" + getOnderhoudsstatus() + "'" +
            ", sporthal='" + getSporthal() + "'" +
            ", vloeroppervlakte='" + getVloeroppervlakte() + "'" +
            "}";
    }
}
