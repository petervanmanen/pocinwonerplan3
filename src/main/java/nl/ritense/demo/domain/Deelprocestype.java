package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Deelprocestype.
 */
@Entity
@Table(name = "deelprocestype")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Deelprocestype implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "omschrijving")
    private String omschrijving;

    @JsonIgnoreProperties(value = { "heeftProducttypes", "heeftZaaktypes", "isdeelvanDeelprocestype" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Bedrijfsprocestype isdeelvanBedrijfsprocestype;

    @JsonIgnoreProperties(value = { "isvanDeelprocestype" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "isvanDeelprocestype")
    private Deelproces isvanDeelproces;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Deelprocestype id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Deelprocestype omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Bedrijfsprocestype getIsdeelvanBedrijfsprocestype() {
        return this.isdeelvanBedrijfsprocestype;
    }

    public void setIsdeelvanBedrijfsprocestype(Bedrijfsprocestype bedrijfsprocestype) {
        this.isdeelvanBedrijfsprocestype = bedrijfsprocestype;
    }

    public Deelprocestype isdeelvanBedrijfsprocestype(Bedrijfsprocestype bedrijfsprocestype) {
        this.setIsdeelvanBedrijfsprocestype(bedrijfsprocestype);
        return this;
    }

    public Deelproces getIsvanDeelproces() {
        return this.isvanDeelproces;
    }

    public void setIsvanDeelproces(Deelproces deelproces) {
        if (this.isvanDeelproces != null) {
            this.isvanDeelproces.setIsvanDeelprocestype(null);
        }
        if (deelproces != null) {
            deelproces.setIsvanDeelprocestype(this);
        }
        this.isvanDeelproces = deelproces;
    }

    public Deelprocestype isvanDeelproces(Deelproces deelproces) {
        this.setIsvanDeelproces(deelproces);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Deelprocestype)) {
            return false;
        }
        return getId() != null && getId().equals(((Deelprocestype) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Deelprocestype{" +
            "id=" + getId() +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
