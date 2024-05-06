package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Resultaat.
 */
@Entity
@Table(name = "resultaat")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Resultaat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datum")
    private String datum;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "soortresultaatResultaats" }, allowSetters = true)
    private Resultaatsoort soortresultaatResultaatsoort;

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
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "heeftresultaatResultaat")
    private Project heeftresultaatProject;

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
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "heeftresultaatResultaat")
    private Traject heeftresultaatTraject;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Resultaat id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatum() {
        return this.datum;
    }

    public Resultaat datum(String datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getNaam() {
        return this.naam;
    }

    public Resultaat naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Resultaat omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Resultaatsoort getSoortresultaatResultaatsoort() {
        return this.soortresultaatResultaatsoort;
    }

    public void setSoortresultaatResultaatsoort(Resultaatsoort resultaatsoort) {
        this.soortresultaatResultaatsoort = resultaatsoort;
    }

    public Resultaat soortresultaatResultaatsoort(Resultaatsoort resultaatsoort) {
        this.setSoortresultaatResultaatsoort(resultaatsoort);
        return this;
    }

    public Project getHeeftresultaatProject() {
        return this.heeftresultaatProject;
    }

    public void setHeeftresultaatProject(Project project) {
        if (this.heeftresultaatProject != null) {
            this.heeftresultaatProject.setHeeftresultaatResultaat(null);
        }
        if (project != null) {
            project.setHeeftresultaatResultaat(this);
        }
        this.heeftresultaatProject = project;
    }

    public Resultaat heeftresultaatProject(Project project) {
        this.setHeeftresultaatProject(project);
        return this;
    }

    public Traject getHeeftresultaatTraject() {
        return this.heeftresultaatTraject;
    }

    public void setHeeftresultaatTraject(Traject traject) {
        if (this.heeftresultaatTraject != null) {
            this.heeftresultaatTraject.setHeeftresultaatResultaat(null);
        }
        if (traject != null) {
            traject.setHeeftresultaatResultaat(this);
        }
        this.heeftresultaatTraject = traject;
    }

    public Resultaat heeftresultaatTraject(Traject traject) {
        this.setHeeftresultaatTraject(traject);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resultaat)) {
            return false;
        }
        return getId() != null && getId().equals(((Resultaat) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Resultaat{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
