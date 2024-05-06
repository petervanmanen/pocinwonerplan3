package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Debiteur.
 */
@Entity
@Table(name = "debiteur")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Debiteur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftDebiteur")
    @JsonIgnoreProperties(
        value = { "heeftFactuurregels", "schrijftopKostenplaats", "gedektviaInkooporder", "crediteurLeverancier", "heeftDebiteur" },
        allowSetters = true
    )
    private Set<Factuur> heeftFactuurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Debiteur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Factuur> getHeeftFactuurs() {
        return this.heeftFactuurs;
    }

    public void setHeeftFactuurs(Set<Factuur> factuurs) {
        if (this.heeftFactuurs != null) {
            this.heeftFactuurs.forEach(i -> i.setHeeftDebiteur(null));
        }
        if (factuurs != null) {
            factuurs.forEach(i -> i.setHeeftDebiteur(this));
        }
        this.heeftFactuurs = factuurs;
    }

    public Debiteur heeftFactuurs(Set<Factuur> factuurs) {
        this.setHeeftFactuurs(factuurs);
        return this;
    }

    public Debiteur addHeeftFactuur(Factuur factuur) {
        this.heeftFactuurs.add(factuur);
        factuur.setHeeftDebiteur(this);
        return this;
    }

    public Debiteur removeHeeftFactuur(Factuur factuur) {
        this.heeftFactuurs.remove(factuur);
        factuur.setHeeftDebiteur(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Debiteur)) {
            return false;
        }
        return getId() != null && getId().equals(((Debiteur) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Debiteur{" +
            "id=" + getId() +
            "}";
    }
}
