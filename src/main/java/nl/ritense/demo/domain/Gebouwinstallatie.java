package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Gebouwinstallatie.
 */
@Entity
@Table(name = "gebouwinstallatie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Gebouwinstallatie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheidgebouwinstallatie")
    private LocalDate datumbegingeldigheidgebouwinstallatie;

    @Column(name = "datumeindegeldigheidgebouwinstallatie")
    private LocalDate datumeindegeldigheidgebouwinstallatie;

    @Column(name = "geometriegebouwinstallatie")
    private String geometriegebouwinstallatie;

    @Column(name = "identificatiegebouwinstallatie")
    private String identificatiegebouwinstallatie;

    @Column(name = "lod_0_geometriegebouwinstallatie")
    private String lod0geometriegebouwinstallatie;

    @Column(name = "relatievehoogteligginggebouwinstallatie")
    private String relatievehoogteligginggebouwinstallatie;

    @Column(name = "statusgebouwinstallatie")
    private String statusgebouwinstallatie;

    @Column(name = "typegebouwinstallatie")
    private String typegebouwinstallatie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Gebouwinstallatie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbegingeldigheidgebouwinstallatie() {
        return this.datumbegingeldigheidgebouwinstallatie;
    }

    public Gebouwinstallatie datumbegingeldigheidgebouwinstallatie(LocalDate datumbegingeldigheidgebouwinstallatie) {
        this.setDatumbegingeldigheidgebouwinstallatie(datumbegingeldigheidgebouwinstallatie);
        return this;
    }

    public void setDatumbegingeldigheidgebouwinstallatie(LocalDate datumbegingeldigheidgebouwinstallatie) {
        this.datumbegingeldigheidgebouwinstallatie = datumbegingeldigheidgebouwinstallatie;
    }

    public LocalDate getDatumeindegeldigheidgebouwinstallatie() {
        return this.datumeindegeldigheidgebouwinstallatie;
    }

    public Gebouwinstallatie datumeindegeldigheidgebouwinstallatie(LocalDate datumeindegeldigheidgebouwinstallatie) {
        this.setDatumeindegeldigheidgebouwinstallatie(datumeindegeldigheidgebouwinstallatie);
        return this;
    }

    public void setDatumeindegeldigheidgebouwinstallatie(LocalDate datumeindegeldigheidgebouwinstallatie) {
        this.datumeindegeldigheidgebouwinstallatie = datumeindegeldigheidgebouwinstallatie;
    }

    public String getGeometriegebouwinstallatie() {
        return this.geometriegebouwinstallatie;
    }

    public Gebouwinstallatie geometriegebouwinstallatie(String geometriegebouwinstallatie) {
        this.setGeometriegebouwinstallatie(geometriegebouwinstallatie);
        return this;
    }

    public void setGeometriegebouwinstallatie(String geometriegebouwinstallatie) {
        this.geometriegebouwinstallatie = geometriegebouwinstallatie;
    }

    public String getIdentificatiegebouwinstallatie() {
        return this.identificatiegebouwinstallatie;
    }

    public Gebouwinstallatie identificatiegebouwinstallatie(String identificatiegebouwinstallatie) {
        this.setIdentificatiegebouwinstallatie(identificatiegebouwinstallatie);
        return this;
    }

    public void setIdentificatiegebouwinstallatie(String identificatiegebouwinstallatie) {
        this.identificatiegebouwinstallatie = identificatiegebouwinstallatie;
    }

    public String getLod0geometriegebouwinstallatie() {
        return this.lod0geometriegebouwinstallatie;
    }

    public Gebouwinstallatie lod0geometriegebouwinstallatie(String lod0geometriegebouwinstallatie) {
        this.setLod0geometriegebouwinstallatie(lod0geometriegebouwinstallatie);
        return this;
    }

    public void setLod0geometriegebouwinstallatie(String lod0geometriegebouwinstallatie) {
        this.lod0geometriegebouwinstallatie = lod0geometriegebouwinstallatie;
    }

    public String getRelatievehoogteligginggebouwinstallatie() {
        return this.relatievehoogteligginggebouwinstallatie;
    }

    public Gebouwinstallatie relatievehoogteligginggebouwinstallatie(String relatievehoogteligginggebouwinstallatie) {
        this.setRelatievehoogteligginggebouwinstallatie(relatievehoogteligginggebouwinstallatie);
        return this;
    }

    public void setRelatievehoogteligginggebouwinstallatie(String relatievehoogteligginggebouwinstallatie) {
        this.relatievehoogteligginggebouwinstallatie = relatievehoogteligginggebouwinstallatie;
    }

    public String getStatusgebouwinstallatie() {
        return this.statusgebouwinstallatie;
    }

    public Gebouwinstallatie statusgebouwinstallatie(String statusgebouwinstallatie) {
        this.setStatusgebouwinstallatie(statusgebouwinstallatie);
        return this;
    }

    public void setStatusgebouwinstallatie(String statusgebouwinstallatie) {
        this.statusgebouwinstallatie = statusgebouwinstallatie;
    }

    public String getTypegebouwinstallatie() {
        return this.typegebouwinstallatie;
    }

    public Gebouwinstallatie typegebouwinstallatie(String typegebouwinstallatie) {
        this.setTypegebouwinstallatie(typegebouwinstallatie);
        return this;
    }

    public void setTypegebouwinstallatie(String typegebouwinstallatie) {
        this.typegebouwinstallatie = typegebouwinstallatie;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gebouwinstallatie)) {
            return false;
        }
        return getId() != null && getId().equals(((Gebouwinstallatie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Gebouwinstallatie{" +
            "id=" + getId() +
            ", datumbegingeldigheidgebouwinstallatie='" + getDatumbegingeldigheidgebouwinstallatie() + "'" +
            ", datumeindegeldigheidgebouwinstallatie='" + getDatumeindegeldigheidgebouwinstallatie() + "'" +
            ", geometriegebouwinstallatie='" + getGeometriegebouwinstallatie() + "'" +
            ", identificatiegebouwinstallatie='" + getIdentificatiegebouwinstallatie() + "'" +
            ", lod0geometriegebouwinstallatie='" + getLod0geometriegebouwinstallatie() + "'" +
            ", relatievehoogteligginggebouwinstallatie='" + getRelatievehoogteligginggebouwinstallatie() + "'" +
            ", statusgebouwinstallatie='" + getStatusgebouwinstallatie() + "'" +
            ", typegebouwinstallatie='" + getTypegebouwinstallatie() + "'" +
            "}";
    }
}
