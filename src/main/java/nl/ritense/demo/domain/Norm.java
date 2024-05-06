package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Norm.
 */
@Entity
@Table(name = "norm")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Norm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nen_3610_id")
    private String nen3610id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bevatNorm")
    @JsonIgnoreProperties(value = { "geldtvoorLocaties", "bevatNorm" }, allowSetters = true)
    private Set<Normwaarde> bevatNormwaardes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Norm id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNen3610id() {
        return this.nen3610id;
    }

    public Norm nen3610id(String nen3610id) {
        this.setNen3610id(nen3610id);
        return this;
    }

    public void setNen3610id(String nen3610id) {
        this.nen3610id = nen3610id;
    }

    public Set<Normwaarde> getBevatNormwaardes() {
        return this.bevatNormwaardes;
    }

    public void setBevatNormwaardes(Set<Normwaarde> normwaardes) {
        if (this.bevatNormwaardes != null) {
            this.bevatNormwaardes.forEach(i -> i.setBevatNorm(null));
        }
        if (normwaardes != null) {
            normwaardes.forEach(i -> i.setBevatNorm(this));
        }
        this.bevatNormwaardes = normwaardes;
    }

    public Norm bevatNormwaardes(Set<Normwaarde> normwaardes) {
        this.setBevatNormwaardes(normwaardes);
        return this;
    }

    public Norm addBevatNormwaarde(Normwaarde normwaarde) {
        this.bevatNormwaardes.add(normwaarde);
        normwaarde.setBevatNorm(this);
        return this;
    }

    public Norm removeBevatNormwaarde(Normwaarde normwaarde) {
        this.bevatNormwaardes.remove(normwaarde);
        normwaarde.setBevatNorm(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Norm)) {
            return false;
        }
        return getId() != null && getId().equals(((Norm) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Norm{" +
            "id=" + getId() +
            ", nen3610id='" + getNen3610id() + "'" +
            "}";
    }
}
