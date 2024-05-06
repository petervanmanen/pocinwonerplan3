package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Lijn.
 */
@Entity
@Table(name = "lijn")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Lijn implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "lijn")
    private String lijn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "omvatLijns" }, allowSetters = true)
    private Lijnengroep omvatLijnengroep;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Lijn id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLijn() {
        return this.lijn;
    }

    public Lijn lijn(String lijn) {
        this.setLijn(lijn);
        return this;
    }

    public void setLijn(String lijn) {
        this.lijn = lijn;
    }

    public Lijnengroep getOmvatLijnengroep() {
        return this.omvatLijnengroep;
    }

    public void setOmvatLijnengroep(Lijnengroep lijnengroep) {
        this.omvatLijnengroep = lijnengroep;
    }

    public Lijn omvatLijnengroep(Lijnengroep lijnengroep) {
        this.setOmvatLijnengroep(lijnengroep);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lijn)) {
            return false;
        }
        return getId() != null && getId().equals(((Lijn) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Lijn{" +
            "id=" + getId() +
            ", lijn='" + getLijn() + "'" +
            "}";
    }
}
