package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Projectactiviteit.
 */
@Entity
@Table(name = "projectactiviteit")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Projectactiviteit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Project heeftProject;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "gedefinieerddoorProjectactiviteit")
    @JsonIgnoreProperties(value = { "gedefinieerddoorProjectactiviteit", "bevatVerzoek" }, allowSetters = true)
    private Set<Specificatie> gedefinieerddoorSpecificaties = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "betreftProjectactiviteits")
    @JsonIgnoreProperties(
        value = {
            "leidttotZaak",
            "bevatSpecificaties",
            "betrefteerderverzoekVerzoek",
            "betreftProjectactiviteits",
            "betreftProjectlocaties",
            "betreftActiviteits",
            "betreftLocaties",
            "heeftalsverantwoordelijkeInitiatiefnemer",
            "betrefteerderverzoekVerzoek2s",
        },
        allowSetters = true
    )
    private Set<Verzoek> betreftVerzoeks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Projectactiviteit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getHeeftProject() {
        return this.heeftProject;
    }

    public void setHeeftProject(Project project) {
        this.heeftProject = project;
    }

    public Projectactiviteit heeftProject(Project project) {
        this.setHeeftProject(project);
        return this;
    }

    public Set<Specificatie> getGedefinieerddoorSpecificaties() {
        return this.gedefinieerddoorSpecificaties;
    }

    public void setGedefinieerddoorSpecificaties(Set<Specificatie> specificaties) {
        if (this.gedefinieerddoorSpecificaties != null) {
            this.gedefinieerddoorSpecificaties.forEach(i -> i.setGedefinieerddoorProjectactiviteit(null));
        }
        if (specificaties != null) {
            specificaties.forEach(i -> i.setGedefinieerddoorProjectactiviteit(this));
        }
        this.gedefinieerddoorSpecificaties = specificaties;
    }

    public Projectactiviteit gedefinieerddoorSpecificaties(Set<Specificatie> specificaties) {
        this.setGedefinieerddoorSpecificaties(specificaties);
        return this;
    }

    public Projectactiviteit addGedefinieerddoorSpecificatie(Specificatie specificatie) {
        this.gedefinieerddoorSpecificaties.add(specificatie);
        specificatie.setGedefinieerddoorProjectactiviteit(this);
        return this;
    }

    public Projectactiviteit removeGedefinieerddoorSpecificatie(Specificatie specificatie) {
        this.gedefinieerddoorSpecificaties.remove(specificatie);
        specificatie.setGedefinieerddoorProjectactiviteit(null);
        return this;
    }

    public Set<Verzoek> getBetreftVerzoeks() {
        return this.betreftVerzoeks;
    }

    public void setBetreftVerzoeks(Set<Verzoek> verzoeks) {
        if (this.betreftVerzoeks != null) {
            this.betreftVerzoeks.forEach(i -> i.removeBetreftProjectactiviteit(this));
        }
        if (verzoeks != null) {
            verzoeks.forEach(i -> i.addBetreftProjectactiviteit(this));
        }
        this.betreftVerzoeks = verzoeks;
    }

    public Projectactiviteit betreftVerzoeks(Set<Verzoek> verzoeks) {
        this.setBetreftVerzoeks(verzoeks);
        return this;
    }

    public Projectactiviteit addBetreftVerzoek(Verzoek verzoek) {
        this.betreftVerzoeks.add(verzoek);
        verzoek.getBetreftProjectactiviteits().add(this);
        return this;
    }

    public Projectactiviteit removeBetreftVerzoek(Verzoek verzoek) {
        this.betreftVerzoeks.remove(verzoek);
        verzoek.getBetreftProjectactiviteits().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Projectactiviteit)) {
            return false;
        }
        return getId() != null && getId().equals(((Projectactiviteit) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Projectactiviteit{" +
            "id=" + getId() +
            "}";
    }
}
