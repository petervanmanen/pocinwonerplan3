package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Traject.
 */
@Entity
@Table(name = "traject")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Traject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumeinde")
    private String datumeinde;

    @Column(name = "datumstart")
    private String datumstart;

    @Column(name = "datumtoekenning")
    private String datumtoekenning;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Column(name = "resultaat")
    private String resultaat;

    @JsonIgnoreProperties(value = { "soortresultaatResultaatsoort", "heeftresultaatProject", "heeftresultaatTraject" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Resultaat heeftresultaatResultaat;

    @JsonIgnoreProperties(
        value = { "soortuitstroomredenUitstroomredensoort", "heeftuitstroomredenProject", "heeftuitstroomredenTraject" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Uitstroomreden heeftuitstroomredenUitstroomreden;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftprojectTraject")
    @JsonIgnoreProperties(
        value = {
            "heeftuitstroomredenUitstroomreden",
            "heeftresultaatResultaat",
            "heeftArcheologiebesluits",
            "heeftBorings",
            "heeftPuts",
            "heeftProjectlocaties",
            "heeftProjectactiviteits",
            "soortprojectProjectsoort",
            "wordtbegrensddoorLocaties",
            "heeftKostenplaats",
            "hoortbijVindplaats",
            "heeftprojectTraject",
            "betreftZaaks",
        },
        allowSetters = true
    )
    private Set<Project> heeftprojectProjects = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "istrajectsoortTrajects" }, allowSetters = true)
    private Trajectsoort istrajectsoortTrajectsoort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftParticipatiedossier",
            "heeftvoorzieningInkomensvoorziening",
            "heeftScores",
            "leverttegenprestatieTegenprestaties",
            "heeftparticipatietrajectTrajects",
            "heeftfraudegegevensFraudegegevens",
            "heeftregelingRegelings",
            "heefttrajectTrajects",
            "valtbinnendoelgroepDoelgroep",
            "heeftrelatieRelaties",
            "voorzieningbijstandspartijInkomensvoorzienings",
            "maaktonderdeeluitvanHuishoudens",
            "heefttaalniveauTaalniveaus",
            "emptyBeschikkings",
            "prestatievoorLeverings",
            "betreftDeclaratieregels",
            "ondersteuntclientClientbegeleiders",
        },
        allowSetters = true
    )
    private Client heeftparticipatietrajectClient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftParticipatiedossier",
            "heeftvoorzieningInkomensvoorziening",
            "heeftScores",
            "leverttegenprestatieTegenprestaties",
            "heeftparticipatietrajectTrajects",
            "heeftfraudegegevensFraudegegevens",
            "heeftregelingRegelings",
            "heefttrajectTrajects",
            "valtbinnendoelgroepDoelgroep",
            "heeftrelatieRelaties",
            "voorzieningbijstandspartijInkomensvoorzienings",
            "maaktonderdeeluitvanHuishoudens",
            "heefttaalniveauTaalniveaus",
            "emptyBeschikkings",
            "prestatievoorLeverings",
            "betreftDeclaratieregels",
            "ondersteuntclientClientbegeleiders",
        },
        allowSetters = true
    )
    private Client heefttrajectClient;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "begeleidtTraject")
    @JsonIgnoreProperties(
        value = {
            "geeftafBeschikkings",
            "emptyInkomensvoorzienings",
            "maaktonderdeeluitvanTeam",
            "begeleidtTraject",
            "ondersteuntclientClients",
            "emptyParticipatiedossier",
        },
        allowSetters = true
    )
    private Set<Clientbegeleider> begeleidtClientbegeleiders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Traject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatumeinde() {
        return this.datumeinde;
    }

    public Traject datumeinde(String datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(String datumeinde) {
        this.datumeinde = datumeinde;
    }

    public String getDatumstart() {
        return this.datumstart;
    }

    public Traject datumstart(String datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(String datumstart) {
        this.datumstart = datumstart;
    }

    public String getDatumtoekenning() {
        return this.datumtoekenning;
    }

    public Traject datumtoekenning(String datumtoekenning) {
        this.setDatumtoekenning(datumtoekenning);
        return this;
    }

    public void setDatumtoekenning(String datumtoekenning) {
        this.datumtoekenning = datumtoekenning;
    }

    public String getNaam() {
        return this.naam;
    }

    public Traject naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Traject omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getResultaat() {
        return this.resultaat;
    }

    public Traject resultaat(String resultaat) {
        this.setResultaat(resultaat);
        return this;
    }

    public void setResultaat(String resultaat) {
        this.resultaat = resultaat;
    }

    public Resultaat getHeeftresultaatResultaat() {
        return this.heeftresultaatResultaat;
    }

    public void setHeeftresultaatResultaat(Resultaat resultaat) {
        this.heeftresultaatResultaat = resultaat;
    }

    public Traject heeftresultaatResultaat(Resultaat resultaat) {
        this.setHeeftresultaatResultaat(resultaat);
        return this;
    }

    public Uitstroomreden getHeeftuitstroomredenUitstroomreden() {
        return this.heeftuitstroomredenUitstroomreden;
    }

    public void setHeeftuitstroomredenUitstroomreden(Uitstroomreden uitstroomreden) {
        this.heeftuitstroomredenUitstroomreden = uitstroomreden;
    }

    public Traject heeftuitstroomredenUitstroomreden(Uitstroomreden uitstroomreden) {
        this.setHeeftuitstroomredenUitstroomreden(uitstroomreden);
        return this;
    }

    public Set<Project> getHeeftprojectProjects() {
        return this.heeftprojectProjects;
    }

    public void setHeeftprojectProjects(Set<Project> projects) {
        if (this.heeftprojectProjects != null) {
            this.heeftprojectProjects.forEach(i -> i.setHeeftprojectTraject(null));
        }
        if (projects != null) {
            projects.forEach(i -> i.setHeeftprojectTraject(this));
        }
        this.heeftprojectProjects = projects;
    }

    public Traject heeftprojectProjects(Set<Project> projects) {
        this.setHeeftprojectProjects(projects);
        return this;
    }

    public Traject addHeeftprojectProject(Project project) {
        this.heeftprojectProjects.add(project);
        project.setHeeftprojectTraject(this);
        return this;
    }

    public Traject removeHeeftprojectProject(Project project) {
        this.heeftprojectProjects.remove(project);
        project.setHeeftprojectTraject(null);
        return this;
    }

    public Trajectsoort getIstrajectsoortTrajectsoort() {
        return this.istrajectsoortTrajectsoort;
    }

    public void setIstrajectsoortTrajectsoort(Trajectsoort trajectsoort) {
        this.istrajectsoortTrajectsoort = trajectsoort;
    }

    public Traject istrajectsoortTrajectsoort(Trajectsoort trajectsoort) {
        this.setIstrajectsoortTrajectsoort(trajectsoort);
        return this;
    }

    public Client getHeeftparticipatietrajectClient() {
        return this.heeftparticipatietrajectClient;
    }

    public void setHeeftparticipatietrajectClient(Client client) {
        this.heeftparticipatietrajectClient = client;
    }

    public Traject heeftparticipatietrajectClient(Client client) {
        this.setHeeftparticipatietrajectClient(client);
        return this;
    }

    public Client getHeefttrajectClient() {
        return this.heefttrajectClient;
    }

    public void setHeefttrajectClient(Client client) {
        this.heefttrajectClient = client;
    }

    public Traject heefttrajectClient(Client client) {
        this.setHeefttrajectClient(client);
        return this;
    }

    public Set<Clientbegeleider> getBegeleidtClientbegeleiders() {
        return this.begeleidtClientbegeleiders;
    }

    public void setBegeleidtClientbegeleiders(Set<Clientbegeleider> clientbegeleiders) {
        if (this.begeleidtClientbegeleiders != null) {
            this.begeleidtClientbegeleiders.forEach(i -> i.setBegeleidtTraject(null));
        }
        if (clientbegeleiders != null) {
            clientbegeleiders.forEach(i -> i.setBegeleidtTraject(this));
        }
        this.begeleidtClientbegeleiders = clientbegeleiders;
    }

    public Traject begeleidtClientbegeleiders(Set<Clientbegeleider> clientbegeleiders) {
        this.setBegeleidtClientbegeleiders(clientbegeleiders);
        return this;
    }

    public Traject addBegeleidtClientbegeleider(Clientbegeleider clientbegeleider) {
        this.begeleidtClientbegeleiders.add(clientbegeleider);
        clientbegeleider.setBegeleidtTraject(this);
        return this;
    }

    public Traject removeBegeleidtClientbegeleider(Clientbegeleider clientbegeleider) {
        this.begeleidtClientbegeleiders.remove(clientbegeleider);
        clientbegeleider.setBegeleidtTraject(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Traject)) {
            return false;
        }
        return getId() != null && getId().equals(((Traject) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Traject{" +
            "id=" + getId() +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", datumtoekenning='" + getDatumtoekenning() + "'" +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", resultaat='" + getResultaat() + "'" +
            "}";
    }
}
