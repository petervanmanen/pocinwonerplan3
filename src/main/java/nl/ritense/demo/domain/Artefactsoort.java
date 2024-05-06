package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Artefactsoort.
 */
@Entity
@Table(name = "artefactsoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Artefactsoort implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 8)
    @Column(name = "code", length = 8)
    private String code;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "isvansoortArtefactsoort")
    @JsonIgnoreProperties(value = { "zitinDoos", "isvansoortArtefactsoort", "bevatVondst" }, allowSetters = true)
    private Set<Artefact> isvansoortArtefacts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Artefactsoort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Artefactsoort code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNaam() {
        return this.naam;
    }

    public Artefactsoort naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Artefactsoort omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Artefact> getIsvansoortArtefacts() {
        return this.isvansoortArtefacts;
    }

    public void setIsvansoortArtefacts(Set<Artefact> artefacts) {
        if (this.isvansoortArtefacts != null) {
            this.isvansoortArtefacts.forEach(i -> i.setIsvansoortArtefactsoort(null));
        }
        if (artefacts != null) {
            artefacts.forEach(i -> i.setIsvansoortArtefactsoort(this));
        }
        this.isvansoortArtefacts = artefacts;
    }

    public Artefactsoort isvansoortArtefacts(Set<Artefact> artefacts) {
        this.setIsvansoortArtefacts(artefacts);
        return this;
    }

    public Artefactsoort addIsvansoortArtefact(Artefact artefact) {
        this.isvansoortArtefacts.add(artefact);
        artefact.setIsvansoortArtefactsoort(this);
        return this;
    }

    public Artefactsoort removeIsvansoortArtefact(Artefact artefact) {
        this.isvansoortArtefacts.remove(artefact);
        artefact.setIsvansoortArtefactsoort(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Artefactsoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Artefactsoort) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Artefactsoort{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
