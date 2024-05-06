package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Betrokkene.
 */
@Entity
@Table(name = "betrokkene")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Betrokkene implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "adresbinnenland")
    private String adresbinnenland;

    @Column(name = "adresbuitenland")
    private String adresbuitenland;

    @Column(name = "identificatie")
    private String identificatie;

    @Column(name = "naam")
    private String naam;

    @Column(name = "rol")
    private String rol;

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Medewerker isMedewerker;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doetBetrokkene")
    @JsonIgnoreProperties(value = { "heeftKlantbeoordelingredens", "heeftZaak", "doetBetrokkene" }, allowSetters = true)
    private Set<Klantbeoordeling> doetKlantbeoordelings = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @NotNull
    @JoinTable(
        name = "rel_betrokkene__oefentuit_zaak",
        joinColumns = @JoinColumn(name = "betrokkene_id"),
        inverseJoinColumns = @JoinColumn(name = "oefentuit_zaak_id")
    )
    @JsonIgnoreProperties(
        value = {
            "heeftproductProducttype",
            "heeftKlantbeoordeling",
            "heeftHeffing",
            "heeftbetalingBetalings",
            "heeftStatuses",
            "betreftProject",
            "isvanZaaktype",
            "kentDocuments",
            "afhandelendmedewerkerMedewerkers",
            "leidttotVerzoek",
            "heeftSubsidie",
            "betreftAanbesteding",
            "heeftbetrekkingopBalieafspraaks",
            "isuitkomstvanBesluits",
            "heeftbetrekkingopKlantcontacts",
            "heeftGrondslags",
            "uitgevoerdbinnenBedrijfsproces",
            "oefentuitBetrokkenes",
        },
        allowSetters = true
    )
    private Set<Zaak> oefentuitZaaks = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftklantcontactenBetrokkene")
    @JsonIgnoreProperties(
        value = {
            "heeftklantcontactenBetrokkene",
            "heeftbetrekkingopZaak",
            "isgevoerddoorMedewerker",
            "mondtuitinBalieafspraak",
            "heeftTelefoononderwerp",
            "mondtuitinTelefoontje",
        },
        allowSetters = true
    )
    private Set<Klantcontact> heeftklantcontactenKlantcontacts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Betrokkene id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresbinnenland() {
        return this.adresbinnenland;
    }

    public Betrokkene adresbinnenland(String adresbinnenland) {
        this.setAdresbinnenland(adresbinnenland);
        return this;
    }

    public void setAdresbinnenland(String adresbinnenland) {
        this.adresbinnenland = adresbinnenland;
    }

    public String getAdresbuitenland() {
        return this.adresbuitenland;
    }

    public Betrokkene adresbuitenland(String adresbuitenland) {
        this.setAdresbuitenland(adresbuitenland);
        return this;
    }

    public void setAdresbuitenland(String adresbuitenland) {
        this.adresbuitenland = adresbuitenland;
    }

    public String getIdentificatie() {
        return this.identificatie;
    }

    public Betrokkene identificatie(String identificatie) {
        this.setIdentificatie(identificatie);
        return this;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    public String getNaam() {
        return this.naam;
    }

    public Betrokkene naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getRol() {
        return this.rol;
    }

    public Betrokkene rol(String rol) {
        this.setRol(rol);
        return this;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Medewerker getIsMedewerker() {
        return this.isMedewerker;
    }

    public void setIsMedewerker(Medewerker medewerker) {
        this.isMedewerker = medewerker;
    }

    public Betrokkene isMedewerker(Medewerker medewerker) {
        this.setIsMedewerker(medewerker);
        return this;
    }

    public Set<Klantbeoordeling> getDoetKlantbeoordelings() {
        return this.doetKlantbeoordelings;
    }

    public void setDoetKlantbeoordelings(Set<Klantbeoordeling> klantbeoordelings) {
        if (this.doetKlantbeoordelings != null) {
            this.doetKlantbeoordelings.forEach(i -> i.setDoetBetrokkene(null));
        }
        if (klantbeoordelings != null) {
            klantbeoordelings.forEach(i -> i.setDoetBetrokkene(this));
        }
        this.doetKlantbeoordelings = klantbeoordelings;
    }

    public Betrokkene doetKlantbeoordelings(Set<Klantbeoordeling> klantbeoordelings) {
        this.setDoetKlantbeoordelings(klantbeoordelings);
        return this;
    }

    public Betrokkene addDoetKlantbeoordeling(Klantbeoordeling klantbeoordeling) {
        this.doetKlantbeoordelings.add(klantbeoordeling);
        klantbeoordeling.setDoetBetrokkene(this);
        return this;
    }

    public Betrokkene removeDoetKlantbeoordeling(Klantbeoordeling klantbeoordeling) {
        this.doetKlantbeoordelings.remove(klantbeoordeling);
        klantbeoordeling.setDoetBetrokkene(null);
        return this;
    }

    public Set<Zaak> getOefentuitZaaks() {
        return this.oefentuitZaaks;
    }

    public void setOefentuitZaaks(Set<Zaak> zaaks) {
        this.oefentuitZaaks = zaaks;
    }

    public Betrokkene oefentuitZaaks(Set<Zaak> zaaks) {
        this.setOefentuitZaaks(zaaks);
        return this;
    }

    public Betrokkene addOefentuitZaak(Zaak zaak) {
        this.oefentuitZaaks.add(zaak);
        return this;
    }

    public Betrokkene removeOefentuitZaak(Zaak zaak) {
        this.oefentuitZaaks.remove(zaak);
        return this;
    }

    public Set<Klantcontact> getHeeftklantcontactenKlantcontacts() {
        return this.heeftklantcontactenKlantcontacts;
    }

    public void setHeeftklantcontactenKlantcontacts(Set<Klantcontact> klantcontacts) {
        if (this.heeftklantcontactenKlantcontacts != null) {
            this.heeftklantcontactenKlantcontacts.forEach(i -> i.setHeeftklantcontactenBetrokkene(null));
        }
        if (klantcontacts != null) {
            klantcontacts.forEach(i -> i.setHeeftklantcontactenBetrokkene(this));
        }
        this.heeftklantcontactenKlantcontacts = klantcontacts;
    }

    public Betrokkene heeftklantcontactenKlantcontacts(Set<Klantcontact> klantcontacts) {
        this.setHeeftklantcontactenKlantcontacts(klantcontacts);
        return this;
    }

    public Betrokkene addHeeftklantcontactenKlantcontact(Klantcontact klantcontact) {
        this.heeftklantcontactenKlantcontacts.add(klantcontact);
        klantcontact.setHeeftklantcontactenBetrokkene(this);
        return this;
    }

    public Betrokkene removeHeeftklantcontactenKlantcontact(Klantcontact klantcontact) {
        this.heeftklantcontactenKlantcontacts.remove(klantcontact);
        klantcontact.setHeeftklantcontactenBetrokkene(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Betrokkene)) {
            return false;
        }
        return getId() != null && getId().equals(((Betrokkene) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Betrokkene{" +
            "id=" + getId() +
            ", adresbinnenland='" + getAdresbinnenland() + "'" +
            ", adresbuitenland='" + getAdresbuitenland() + "'" +
            ", identificatie='" + getIdentificatie() + "'" +
            ", naam='" + getNaam() + "'" +
            ", rol='" + getRol() + "'" +
            "}";
    }
}
