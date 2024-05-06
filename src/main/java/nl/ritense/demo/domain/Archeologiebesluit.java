package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Archeologiebesluit.
 */
@Entity
@Table(name = "archeologiebesluit")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Archeologiebesluit implements Serializable {

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Archeologiebesluit id(Long id) {
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

    public Archeologiebesluit heeftProject(Project project) {
        this.setHeeftProject(project);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Archeologiebesluit)) {
            return false;
        }
        return getId() != null && getId().equals(((Archeologiebesluit) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Archeologiebesluit{" +
            "id=" + getId() +
            "}";
    }
}
