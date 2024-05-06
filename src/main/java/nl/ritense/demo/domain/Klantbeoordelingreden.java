package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Klantbeoordelingreden.
 */
@Entity
@Table(name = "klantbeoordelingreden")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Klantbeoordelingreden implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 100)
    @Column(name = "reden", length = 100)
    private String reden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftKlantbeoordelingredens", "heeftZaak", "doetBetrokkene" }, allowSetters = true)
    private Klantbeoordeling heeftKlantbeoordeling;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Klantbeoordelingreden id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReden() {
        return this.reden;
    }

    public Klantbeoordelingreden reden(String reden) {
        this.setReden(reden);
        return this;
    }

    public void setReden(String reden) {
        this.reden = reden;
    }

    public Klantbeoordeling getHeeftKlantbeoordeling() {
        return this.heeftKlantbeoordeling;
    }

    public void setHeeftKlantbeoordeling(Klantbeoordeling klantbeoordeling) {
        this.heeftKlantbeoordeling = klantbeoordeling;
    }

    public Klantbeoordelingreden heeftKlantbeoordeling(Klantbeoordeling klantbeoordeling) {
        this.setHeeftKlantbeoordeling(klantbeoordeling);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Klantbeoordelingreden)) {
            return false;
        }
        return getId() != null && getId().equals(((Klantbeoordelingreden) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Klantbeoordelingreden{" +
            "id=" + getId() +
            ", reden='" + getReden() + "'" +
            "}";
    }
}
