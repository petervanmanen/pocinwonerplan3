package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Deelproces.
 */
@Entity
@Table(name = "deelproces")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Deelproces implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumafgehandeld")
    private LocalDate datumafgehandeld;

    @Column(name = "datumgepland")
    private LocalDate datumgepland;

    @JsonIgnoreProperties(value = { "isdeelvanBedrijfsprocestype", "isvanDeelproces" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Deelprocestype isvanDeelprocestype;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Deelproces id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumafgehandeld() {
        return this.datumafgehandeld;
    }

    public Deelproces datumafgehandeld(LocalDate datumafgehandeld) {
        this.setDatumafgehandeld(datumafgehandeld);
        return this;
    }

    public void setDatumafgehandeld(LocalDate datumafgehandeld) {
        this.datumafgehandeld = datumafgehandeld;
    }

    public LocalDate getDatumgepland() {
        return this.datumgepland;
    }

    public Deelproces datumgepland(LocalDate datumgepland) {
        this.setDatumgepland(datumgepland);
        return this;
    }

    public void setDatumgepland(LocalDate datumgepland) {
        this.datumgepland = datumgepland;
    }

    public Deelprocestype getIsvanDeelprocestype() {
        return this.isvanDeelprocestype;
    }

    public void setIsvanDeelprocestype(Deelprocestype deelprocestype) {
        this.isvanDeelprocestype = deelprocestype;
    }

    public Deelproces isvanDeelprocestype(Deelprocestype deelprocestype) {
        this.setIsvanDeelprocestype(deelprocestype);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deelproces)) {
            return false;
        }
        return getId() != null && getId().equals(((Deelproces) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Deelproces{" +
            "id=" + getId() +
            ", datumafgehandeld='" + getDatumafgehandeld() + "'" +
            ", datumgepland='" + getDatumgepland() + "'" +
            "}";
    }
}
