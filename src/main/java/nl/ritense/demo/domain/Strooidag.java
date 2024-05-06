package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Strooidag.
 */
@Entity
@Table(name = "strooidag")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Strooidag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datum")
    private LocalDate datum;

    @Column(name = "maximumtemperatuur")
    private String maximumtemperatuur;

    @Column(name = "minimumtemperatuur")
    private String minimumtemperatuur;

    @Column(name = "tijdmaximumtemperatuur")
    private String tijdmaximumtemperatuur;

    @Column(name = "tijdminimumtemperatuur")
    private String tijdminimumtemperatuur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Strooidag id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public Strooidag datum(LocalDate datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getMaximumtemperatuur() {
        return this.maximumtemperatuur;
    }

    public Strooidag maximumtemperatuur(String maximumtemperatuur) {
        this.setMaximumtemperatuur(maximumtemperatuur);
        return this;
    }

    public void setMaximumtemperatuur(String maximumtemperatuur) {
        this.maximumtemperatuur = maximumtemperatuur;
    }

    public String getMinimumtemperatuur() {
        return this.minimumtemperatuur;
    }

    public Strooidag minimumtemperatuur(String minimumtemperatuur) {
        this.setMinimumtemperatuur(minimumtemperatuur);
        return this;
    }

    public void setMinimumtemperatuur(String minimumtemperatuur) {
        this.minimumtemperatuur = minimumtemperatuur;
    }

    public String getTijdmaximumtemperatuur() {
        return this.tijdmaximumtemperatuur;
    }

    public Strooidag tijdmaximumtemperatuur(String tijdmaximumtemperatuur) {
        this.setTijdmaximumtemperatuur(tijdmaximumtemperatuur);
        return this;
    }

    public void setTijdmaximumtemperatuur(String tijdmaximumtemperatuur) {
        this.tijdmaximumtemperatuur = tijdmaximumtemperatuur;
    }

    public String getTijdminimumtemperatuur() {
        return this.tijdminimumtemperatuur;
    }

    public Strooidag tijdminimumtemperatuur(String tijdminimumtemperatuur) {
        this.setTijdminimumtemperatuur(tijdminimumtemperatuur);
        return this;
    }

    public void setTijdminimumtemperatuur(String tijdminimumtemperatuur) {
        this.tijdminimumtemperatuur = tijdminimumtemperatuur;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Strooidag)) {
            return false;
        }
        return getId() != null && getId().equals(((Strooidag) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Strooidag{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            ", maximumtemperatuur='" + getMaximumtemperatuur() + "'" +
            ", minimumtemperatuur='" + getMinimumtemperatuur() + "'" +
            ", tijdmaximumtemperatuur='" + getTijdmaximumtemperatuur() + "'" +
            ", tijdminimumtemperatuur='" + getTijdminimumtemperatuur() + "'" +
            "}";
    }
}
