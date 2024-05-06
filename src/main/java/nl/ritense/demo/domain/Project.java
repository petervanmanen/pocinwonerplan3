package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "coordinaten")
    private String coordinaten;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "jaartot")
    private String jaartot;

    @Column(name = "jaarvan")
    private String jaarvan;

    @Column(name = "locatie")
    private String locatie;

    @Column(name = "naam")
    private String naam;

    @Column(name = "naamcode")
    private String naamcode;

    @Column(name = "projectcd")
    private String projectcd;

    @Column(name = "toponiem")
    private String toponiem;

    @Column(name = "trefwoorden")
    private String trefwoorden;

    @JsonIgnoreProperties(
        value = { "soortuitstroomredenUitstroomredensoort", "heeftuitstroomredenProject", "heeftuitstroomredenTraject" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Uitstroomreden heeftuitstroomredenUitstroomreden;

    @JsonIgnoreProperties(value = { "soortresultaatResultaatsoort", "heeftresultaatProject", "heeftresultaatTraject" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Resultaat heeftresultaatResultaat;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftProject")
    @JsonIgnoreProperties(value = { "heeftProject" }, allowSetters = true)
    private Set<Archeologiebesluit> heeftArcheologiebesluits = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftProject")
    @JsonIgnoreProperties(value = { "heeftProject" }, allowSetters = true)
    private Set<Boring> heeftBorings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftProject")
    @JsonIgnoreProperties(value = { "heeftVlaks", "heeftlocatieLocaties", "heeftProject" }, allowSetters = true)
    private Set<Put> heeftPuts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftProject")
    @JsonIgnoreProperties(value = { "betreftLocatie", "heeftProject", "betreftVerzoeks" }, allowSetters = true)
    private Set<Projectlocatie> heeftProjectlocaties = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftProject")
    @JsonIgnoreProperties(value = { "heeftProject", "gedefinieerddoorSpecificaties", "betreftVerzoeks" }, allowSetters = true)
    private Set<Projectactiviteit> heeftProjectactiviteits = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "soortprojectProjects" }, allowSetters = true)
    private Projectsoort soortprojectProjectsoort;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_project__wordtbegrensddoor_locatie",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "wordtbegrensddoor_locatie_id")
    )
    @JsonIgnoreProperties(
        value = {
            "heeftContainers",
            "betreftMeldings",
            "gestoptopOphaalmoments",
            "betreftProjectlocaties",
            "heeftlocatiePuts",
            "wordtbegrensddoorProjects",
            "betreftVerzoeks",
            "werkingsgebiedRegelteksts",
            "isverbondenmetActiviteits",
            "verwijstnaarGebiedsaanwijzings",
            "geldtvoorNormwaardes",
        },
        allowSetters = true
    )
    private Set<Locatie> wordtbegrensddoorLocaties = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_project__heeft_kostenplaats",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "heeft_kostenplaats_id")
    )
    @JsonIgnoreProperties(
        value = {
            "heeftVastgoedobjects",
            "heeftWerkorders",
            "heeftSubrekenings",
            "heeftInkooporders",
            "heeftTaakvelds",
            "heeftProgrammas",
            "heeftSubsidies",
            "heeftSubsidiecomponents",
            "betreftBegrotingregels",
            "schrijftopFactuurs",
            "heeftbetrekkingopMutaties",
            "heeftProducts",
            "heeftHoofdrekenings",
            "heeftProjects",
        },
        allowSetters = true
    )
    private Set<Kostenplaats> heeftKostenplaats = new HashSet<>();

    @JsonIgnoreProperties(
        value = {
            "hoortbijProject", "istevindeninDepot", "istevindeninKast", "istevindeninPlank", "istevindeninStelling", "heeftArchiefstuks",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "hoortbijProject")
    private Vindplaats hoortbijVindplaats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftresultaatResultaat",
            "heeftuitstroomredenUitstroomreden",
            "heeftprojectProjects",
            "istrajectsoortTrajectsoort",
            "heeftparticipatietrajectClient",
            "heefttrajectClient",
            "begeleidtClientbegeleiders",
        },
        allowSetters = true
    )
    private Traject heeftprojectTraject;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftProject")
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
    private Set<Zaak> betreftZaaks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Project id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCoordinaten() {
        return this.coordinaten;
    }

    public Project coordinaten(String coordinaten) {
        this.setCoordinaten(coordinaten);
        return this;
    }

    public void setCoordinaten(String coordinaten) {
        this.coordinaten = coordinaten;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Project datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Project datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public String getJaartot() {
        return this.jaartot;
    }

    public Project jaartot(String jaartot) {
        this.setJaartot(jaartot);
        return this;
    }

    public void setJaartot(String jaartot) {
        this.jaartot = jaartot;
    }

    public String getJaarvan() {
        return this.jaarvan;
    }

    public Project jaarvan(String jaarvan) {
        this.setJaarvan(jaarvan);
        return this;
    }

    public void setJaarvan(String jaarvan) {
        this.jaarvan = jaarvan;
    }

    public String getLocatie() {
        return this.locatie;
    }

    public Project locatie(String locatie) {
        this.setLocatie(locatie);
        return this;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getNaam() {
        return this.naam;
    }

    public Project naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getNaamcode() {
        return this.naamcode;
    }

    public Project naamcode(String naamcode) {
        this.setNaamcode(naamcode);
        return this;
    }

    public void setNaamcode(String naamcode) {
        this.naamcode = naamcode;
    }

    public String getProjectcd() {
        return this.projectcd;
    }

    public Project projectcd(String projectcd) {
        this.setProjectcd(projectcd);
        return this;
    }

    public void setProjectcd(String projectcd) {
        this.projectcd = projectcd;
    }

    public String getToponiem() {
        return this.toponiem;
    }

    public Project toponiem(String toponiem) {
        this.setToponiem(toponiem);
        return this;
    }

    public void setToponiem(String toponiem) {
        this.toponiem = toponiem;
    }

    public String getTrefwoorden() {
        return this.trefwoorden;
    }

    public Project trefwoorden(String trefwoorden) {
        this.setTrefwoorden(trefwoorden);
        return this;
    }

    public void setTrefwoorden(String trefwoorden) {
        this.trefwoorden = trefwoorden;
    }

    public Uitstroomreden getHeeftuitstroomredenUitstroomreden() {
        return this.heeftuitstroomredenUitstroomreden;
    }

    public void setHeeftuitstroomredenUitstroomreden(Uitstroomreden uitstroomreden) {
        this.heeftuitstroomredenUitstroomreden = uitstroomreden;
    }

    public Project heeftuitstroomredenUitstroomreden(Uitstroomreden uitstroomreden) {
        this.setHeeftuitstroomredenUitstroomreden(uitstroomreden);
        return this;
    }

    public Resultaat getHeeftresultaatResultaat() {
        return this.heeftresultaatResultaat;
    }

    public void setHeeftresultaatResultaat(Resultaat resultaat) {
        this.heeftresultaatResultaat = resultaat;
    }

    public Project heeftresultaatResultaat(Resultaat resultaat) {
        this.setHeeftresultaatResultaat(resultaat);
        return this;
    }

    public Set<Archeologiebesluit> getHeeftArcheologiebesluits() {
        return this.heeftArcheologiebesluits;
    }

    public void setHeeftArcheologiebesluits(Set<Archeologiebesluit> archeologiebesluits) {
        if (this.heeftArcheologiebesluits != null) {
            this.heeftArcheologiebesluits.forEach(i -> i.setHeeftProject(null));
        }
        if (archeologiebesluits != null) {
            archeologiebesluits.forEach(i -> i.setHeeftProject(this));
        }
        this.heeftArcheologiebesluits = archeologiebesluits;
    }

    public Project heeftArcheologiebesluits(Set<Archeologiebesluit> archeologiebesluits) {
        this.setHeeftArcheologiebesluits(archeologiebesluits);
        return this;
    }

    public Project addHeeftArcheologiebesluit(Archeologiebesluit archeologiebesluit) {
        this.heeftArcheologiebesluits.add(archeologiebesluit);
        archeologiebesluit.setHeeftProject(this);
        return this;
    }

    public Project removeHeeftArcheologiebesluit(Archeologiebesluit archeologiebesluit) {
        this.heeftArcheologiebesluits.remove(archeologiebesluit);
        archeologiebesluit.setHeeftProject(null);
        return this;
    }

    public Set<Boring> getHeeftBorings() {
        return this.heeftBorings;
    }

    public void setHeeftBorings(Set<Boring> borings) {
        if (this.heeftBorings != null) {
            this.heeftBorings.forEach(i -> i.setHeeftProject(null));
        }
        if (borings != null) {
            borings.forEach(i -> i.setHeeftProject(this));
        }
        this.heeftBorings = borings;
    }

    public Project heeftBorings(Set<Boring> borings) {
        this.setHeeftBorings(borings);
        return this;
    }

    public Project addHeeftBoring(Boring boring) {
        this.heeftBorings.add(boring);
        boring.setHeeftProject(this);
        return this;
    }

    public Project removeHeeftBoring(Boring boring) {
        this.heeftBorings.remove(boring);
        boring.setHeeftProject(null);
        return this;
    }

    public Set<Put> getHeeftPuts() {
        return this.heeftPuts;
    }

    public void setHeeftPuts(Set<Put> puts) {
        if (this.heeftPuts != null) {
            this.heeftPuts.forEach(i -> i.setHeeftProject(null));
        }
        if (puts != null) {
            puts.forEach(i -> i.setHeeftProject(this));
        }
        this.heeftPuts = puts;
    }

    public Project heeftPuts(Set<Put> puts) {
        this.setHeeftPuts(puts);
        return this;
    }

    public Project addHeeftPut(Put put) {
        this.heeftPuts.add(put);
        put.setHeeftProject(this);
        return this;
    }

    public Project removeHeeftPut(Put put) {
        this.heeftPuts.remove(put);
        put.setHeeftProject(null);
        return this;
    }

    public Set<Projectlocatie> getHeeftProjectlocaties() {
        return this.heeftProjectlocaties;
    }

    public void setHeeftProjectlocaties(Set<Projectlocatie> projectlocaties) {
        if (this.heeftProjectlocaties != null) {
            this.heeftProjectlocaties.forEach(i -> i.setHeeftProject(null));
        }
        if (projectlocaties != null) {
            projectlocaties.forEach(i -> i.setHeeftProject(this));
        }
        this.heeftProjectlocaties = projectlocaties;
    }

    public Project heeftProjectlocaties(Set<Projectlocatie> projectlocaties) {
        this.setHeeftProjectlocaties(projectlocaties);
        return this;
    }

    public Project addHeeftProjectlocatie(Projectlocatie projectlocatie) {
        this.heeftProjectlocaties.add(projectlocatie);
        projectlocatie.setHeeftProject(this);
        return this;
    }

    public Project removeHeeftProjectlocatie(Projectlocatie projectlocatie) {
        this.heeftProjectlocaties.remove(projectlocatie);
        projectlocatie.setHeeftProject(null);
        return this;
    }

    public Set<Projectactiviteit> getHeeftProjectactiviteits() {
        return this.heeftProjectactiviteits;
    }

    public void setHeeftProjectactiviteits(Set<Projectactiviteit> projectactiviteits) {
        if (this.heeftProjectactiviteits != null) {
            this.heeftProjectactiviteits.forEach(i -> i.setHeeftProject(null));
        }
        if (projectactiviteits != null) {
            projectactiviteits.forEach(i -> i.setHeeftProject(this));
        }
        this.heeftProjectactiviteits = projectactiviteits;
    }

    public Project heeftProjectactiviteits(Set<Projectactiviteit> projectactiviteits) {
        this.setHeeftProjectactiviteits(projectactiviteits);
        return this;
    }

    public Project addHeeftProjectactiviteit(Projectactiviteit projectactiviteit) {
        this.heeftProjectactiviteits.add(projectactiviteit);
        projectactiviteit.setHeeftProject(this);
        return this;
    }

    public Project removeHeeftProjectactiviteit(Projectactiviteit projectactiviteit) {
        this.heeftProjectactiviteits.remove(projectactiviteit);
        projectactiviteit.setHeeftProject(null);
        return this;
    }

    public Projectsoort getSoortprojectProjectsoort() {
        return this.soortprojectProjectsoort;
    }

    public void setSoortprojectProjectsoort(Projectsoort projectsoort) {
        this.soortprojectProjectsoort = projectsoort;
    }

    public Project soortprojectProjectsoort(Projectsoort projectsoort) {
        this.setSoortprojectProjectsoort(projectsoort);
        return this;
    }

    public Set<Locatie> getWordtbegrensddoorLocaties() {
        return this.wordtbegrensddoorLocaties;
    }

    public void setWordtbegrensddoorLocaties(Set<Locatie> locaties) {
        this.wordtbegrensddoorLocaties = locaties;
    }

    public Project wordtbegrensddoorLocaties(Set<Locatie> locaties) {
        this.setWordtbegrensddoorLocaties(locaties);
        return this;
    }

    public Project addWordtbegrensddoorLocatie(Locatie locatie) {
        this.wordtbegrensddoorLocaties.add(locatie);
        return this;
    }

    public Project removeWordtbegrensddoorLocatie(Locatie locatie) {
        this.wordtbegrensddoorLocaties.remove(locatie);
        return this;
    }

    public Set<Kostenplaats> getHeeftKostenplaats() {
        return this.heeftKostenplaats;
    }

    public void setHeeftKostenplaats(Set<Kostenplaats> kostenplaats) {
        this.heeftKostenplaats = kostenplaats;
    }

    public Project heeftKostenplaats(Set<Kostenplaats> kostenplaats) {
        this.setHeeftKostenplaats(kostenplaats);
        return this;
    }

    public Project addHeeftKostenplaats(Kostenplaats kostenplaats) {
        this.heeftKostenplaats.add(kostenplaats);
        return this;
    }

    public Project removeHeeftKostenplaats(Kostenplaats kostenplaats) {
        this.heeftKostenplaats.remove(kostenplaats);
        return this;
    }

    public Vindplaats getHoortbijVindplaats() {
        return this.hoortbijVindplaats;
    }

    public void setHoortbijVindplaats(Vindplaats vindplaats) {
        if (this.hoortbijVindplaats != null) {
            this.hoortbijVindplaats.setHoortbijProject(null);
        }
        if (vindplaats != null) {
            vindplaats.setHoortbijProject(this);
        }
        this.hoortbijVindplaats = vindplaats;
    }

    public Project hoortbijVindplaats(Vindplaats vindplaats) {
        this.setHoortbijVindplaats(vindplaats);
        return this;
    }

    public Traject getHeeftprojectTraject() {
        return this.heeftprojectTraject;
    }

    public void setHeeftprojectTraject(Traject traject) {
        this.heeftprojectTraject = traject;
    }

    public Project heeftprojectTraject(Traject traject) {
        this.setHeeftprojectTraject(traject);
        return this;
    }

    public Set<Zaak> getBetreftZaaks() {
        return this.betreftZaaks;
    }

    public void setBetreftZaaks(Set<Zaak> zaaks) {
        if (this.betreftZaaks != null) {
            this.betreftZaaks.forEach(i -> i.setBetreftProject(null));
        }
        if (zaaks != null) {
            zaaks.forEach(i -> i.setBetreftProject(this));
        }
        this.betreftZaaks = zaaks;
    }

    public Project betreftZaaks(Set<Zaak> zaaks) {
        this.setBetreftZaaks(zaaks);
        return this;
    }

    public Project addBetreftZaak(Zaak zaak) {
        this.betreftZaaks.add(zaak);
        zaak.setBetreftProject(this);
        return this;
    }

    public Project removeBetreftZaak(Zaak zaak) {
        this.betreftZaaks.remove(zaak);
        zaak.setBetreftProject(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return getId() != null && getId().equals(((Project) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", coordinaten='" + getCoordinaten() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", jaartot='" + getJaartot() + "'" +
            ", jaarvan='" + getJaarvan() + "'" +
            ", locatie='" + getLocatie() + "'" +
            ", naam='" + getNaam() + "'" +
            ", naamcode='" + getNaamcode() + "'" +
            ", projectcd='" + getProjectcd() + "'" +
            ", toponiem='" + getToponiem() + "'" +
            ", trefwoorden='" + getTrefwoorden() + "'" +
            "}";
    }
}
