package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Trajectsoort.
 */
@Entity
@Table(name = "trajectsoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Trajectsoort implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "istrajectsoortTrajectsoort")
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
    private Set<Traject> istrajectsoortTrajects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Trajectsoort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Trajectsoort naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Trajectsoort omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Traject> getIstrajectsoortTrajects() {
        return this.istrajectsoortTrajects;
    }

    public void setIstrajectsoortTrajects(Set<Traject> trajects) {
        if (this.istrajectsoortTrajects != null) {
            this.istrajectsoortTrajects.forEach(i -> i.setIstrajectsoortTrajectsoort(null));
        }
        if (trajects != null) {
            trajects.forEach(i -> i.setIstrajectsoortTrajectsoort(this));
        }
        this.istrajectsoortTrajects = trajects;
    }

    public Trajectsoort istrajectsoortTrajects(Set<Traject> trajects) {
        this.setIstrajectsoortTrajects(trajects);
        return this;
    }

    public Trajectsoort addIstrajectsoortTraject(Traject traject) {
        this.istrajectsoortTrajects.add(traject);
        traject.setIstrajectsoortTrajectsoort(this);
        return this;
    }

    public Trajectsoort removeIstrajectsoortTraject(Traject traject) {
        this.istrajectsoortTrajects.remove(traject);
        traject.setIstrajectsoortTrajectsoort(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trajectsoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Trajectsoort) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Trajectsoort{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
