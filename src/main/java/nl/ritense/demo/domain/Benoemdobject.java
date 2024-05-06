package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Benoemdobject.
 */
@Entity
@Table(name = "benoemdobject")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Benoemdobject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheid")
    private LocalDate datumbegingeldigheid;

    @Column(name = "datumeindegeldigheid")
    private LocalDate datumeindegeldigheid;

    @Column(name = "geometriepunt")
    private String geometriepunt;

    @Column(name = "geometrievlak")
    private String geometrievlak;

    @Size(max = 100)
    @Column(name = "identificatie", length = 100)
    private String identificatie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Benoemdobject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbegingeldigheid() {
        return this.datumbegingeldigheid;
    }

    public Benoemdobject datumbegingeldigheid(LocalDate datumbegingeldigheid) {
        this.setDatumbegingeldigheid(datumbegingeldigheid);
        return this;
    }

    public void setDatumbegingeldigheid(LocalDate datumbegingeldigheid) {
        this.datumbegingeldigheid = datumbegingeldigheid;
    }

    public LocalDate getDatumeindegeldigheid() {
        return this.datumeindegeldigheid;
    }

    public Benoemdobject datumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.setDatumeindegeldigheid(datumeindegeldigheid);
        return this;
    }

    public void setDatumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.datumeindegeldigheid = datumeindegeldigheid;
    }

    public String getGeometriepunt() {
        return this.geometriepunt;
    }

    public Benoemdobject geometriepunt(String geometriepunt) {
        this.setGeometriepunt(geometriepunt);
        return this;
    }

    public void setGeometriepunt(String geometriepunt) {
        this.geometriepunt = geometriepunt;
    }

    public String getGeometrievlak() {
        return this.geometrievlak;
    }

    public Benoemdobject geometrievlak(String geometrievlak) {
        this.setGeometrievlak(geometrievlak);
        return this;
    }

    public void setGeometrievlak(String geometrievlak) {
        this.geometrievlak = geometrievlak;
    }

    public String getIdentificatie() {
        return this.identificatie;
    }

    public Benoemdobject identificatie(String identificatie) {
        this.setIdentificatie(identificatie);
        return this;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Benoemdobject)) {
            return false;
        }
        return getId() != null && getId().equals(((Benoemdobject) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Benoemdobject{" +
            "id=" + getId() +
            ", datumbegingeldigheid='" + getDatumbegingeldigheid() + "'" +
            ", datumeindegeldigheid='" + getDatumeindegeldigheid() + "'" +
            ", geometriepunt='" + getGeometriepunt() + "'" +
            ", geometrievlak='" + getGeometrievlak() + "'" +
            ", identificatie='" + getIdentificatie() + "'" +
            "}";
    }
}
