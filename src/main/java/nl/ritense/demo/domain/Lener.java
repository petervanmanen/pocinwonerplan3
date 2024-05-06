package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Lener.
 */
@Entity
@Table(name = "lener")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Lener implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "opmerkingen")
    private String opmerkingen;

    @ManyToMany(fetch = FetchType.LAZY)
    @NotNull
    @JoinTable(
        name = "rel_lener__is_bruikleen",
        joinColumns = @JoinColumn(name = "lener_id"),
        inverseJoinColumns = @JoinColumn(name = "is_bruikleen_id")
    )
    @JsonIgnoreProperties(value = { "isbedoeldvoorTentoonstellings", "betreftMuseumobjects", "isLeners" }, allowSetters = true)
    private Set<Bruikleen> isBruikleens = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Lener id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpmerkingen() {
        return this.opmerkingen;
    }

    public Lener opmerkingen(String opmerkingen) {
        this.setOpmerkingen(opmerkingen);
        return this;
    }

    public void setOpmerkingen(String opmerkingen) {
        this.opmerkingen = opmerkingen;
    }

    public Set<Bruikleen> getIsBruikleens() {
        return this.isBruikleens;
    }

    public void setIsBruikleens(Set<Bruikleen> bruikleens) {
        this.isBruikleens = bruikleens;
    }

    public Lener isBruikleens(Set<Bruikleen> bruikleens) {
        this.setIsBruikleens(bruikleens);
        return this;
    }

    public Lener addIsBruikleen(Bruikleen bruikleen) {
        this.isBruikleens.add(bruikleen);
        return this;
    }

    public Lener removeIsBruikleen(Bruikleen bruikleen) {
        this.isBruikleens.remove(bruikleen);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lener)) {
            return false;
        }
        return getId() != null && getId().equals(((Lener) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Lener{" +
            "id=" + getId() +
            ", opmerkingen='" + getOpmerkingen() + "'" +
            "}";
    }
}
