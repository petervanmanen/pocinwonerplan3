package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Omgevingsdocument.
 */
@Entity
@Table(name = "omgevingsdocument")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Omgevingsdocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bevatOmgevingsdocument")
    @JsonIgnoreProperties(
        value = {
            "werkingsgebiedRegelteksts",
            "isgerelateerdRegelteksts",
            "heeftthemaThemas",
            "heeftidealisatieIdealisaties",
            "werkingsgebiedLocaties",
            "bevatOmgevingsdocument",
            "werkingsgebiedRegeltekst2",
            "isgerelateerdRegeltekst2",
        },
        allowSetters = true
    )
    private Set<Regeltekst> bevatRegelteksts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Omgevingsdocument id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Regeltekst> getBevatRegelteksts() {
        return this.bevatRegelteksts;
    }

    public void setBevatRegelteksts(Set<Regeltekst> regelteksts) {
        if (this.bevatRegelteksts != null) {
            this.bevatRegelteksts.forEach(i -> i.setBevatOmgevingsdocument(null));
        }
        if (regelteksts != null) {
            regelteksts.forEach(i -> i.setBevatOmgevingsdocument(this));
        }
        this.bevatRegelteksts = regelteksts;
    }

    public Omgevingsdocument bevatRegelteksts(Set<Regeltekst> regelteksts) {
        this.setBevatRegelteksts(regelteksts);
        return this;
    }

    public Omgevingsdocument addBevatRegeltekst(Regeltekst regeltekst) {
        this.bevatRegelteksts.add(regeltekst);
        regeltekst.setBevatOmgevingsdocument(this);
        return this;
    }

    public Omgevingsdocument removeBevatRegeltekst(Regeltekst regeltekst) {
        this.bevatRegelteksts.remove(regeltekst);
        regeltekst.setBevatOmgevingsdocument(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Omgevingsdocument)) {
            return false;
        }
        return getId() != null && getId().equals(((Omgevingsdocument) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Omgevingsdocument{" +
            "id=" + getId() +
            "}";
    }
}
