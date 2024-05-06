package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Uitstroomreden.
 */
@Entity
@Table(name = "uitstroomreden")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Uitstroomreden implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datum")
    private String datum;

    @Column(name = "omschrijving")
    private String omschrijving;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "soortuitstroomredenUitstroomredens" }, allowSetters = true)
    private Uitstroomredensoort soortuitstroomredenUitstroomredensoort;

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
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "heeftuitstroomredenUitstroomreden")
    private Project heeftuitstroomredenProject;

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
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "heeftuitstroomredenUitstroomreden")
    private Traject heeftuitstroomredenTraject;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Uitstroomreden id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatum() {
        return this.datum;
    }

    public Uitstroomreden datum(String datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Uitstroomreden omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Uitstroomredensoort getSoortuitstroomredenUitstroomredensoort() {
        return this.soortuitstroomredenUitstroomredensoort;
    }

    public void setSoortuitstroomredenUitstroomredensoort(Uitstroomredensoort uitstroomredensoort) {
        this.soortuitstroomredenUitstroomredensoort = uitstroomredensoort;
    }

    public Uitstroomreden soortuitstroomredenUitstroomredensoort(Uitstroomredensoort uitstroomredensoort) {
        this.setSoortuitstroomredenUitstroomredensoort(uitstroomredensoort);
        return this;
    }

    public Project getHeeftuitstroomredenProject() {
        return this.heeftuitstroomredenProject;
    }

    public void setHeeftuitstroomredenProject(Project project) {
        if (this.heeftuitstroomredenProject != null) {
            this.heeftuitstroomredenProject.setHeeftuitstroomredenUitstroomreden(null);
        }
        if (project != null) {
            project.setHeeftuitstroomredenUitstroomreden(this);
        }
        this.heeftuitstroomredenProject = project;
    }

    public Uitstroomreden heeftuitstroomredenProject(Project project) {
        this.setHeeftuitstroomredenProject(project);
        return this;
    }

    public Traject getHeeftuitstroomredenTraject() {
        return this.heeftuitstroomredenTraject;
    }

    public void setHeeftuitstroomredenTraject(Traject traject) {
        if (this.heeftuitstroomredenTraject != null) {
            this.heeftuitstroomredenTraject.setHeeftuitstroomredenUitstroomreden(null);
        }
        if (traject != null) {
            traject.setHeeftuitstroomredenUitstroomreden(this);
        }
        this.heeftuitstroomredenTraject = traject;
    }

    public Uitstroomreden heeftuitstroomredenTraject(Traject traject) {
        this.setHeeftuitstroomredenTraject(traject);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Uitstroomreden)) {
            return false;
        }
        return getId() != null && getId().equals(((Uitstroomreden) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Uitstroomreden{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
