package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Waterdeel.
 */
@Entity
@Table(name = "waterdeel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Waterdeel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheidwaterdeel")
    private LocalDate datumbegingeldigheidwaterdeel;

    @Column(name = "datumeindegeldigheidwaterdeel")
    private LocalDate datumeindegeldigheidwaterdeel;

    @Column(name = "geometriewaterdeel")
    private String geometriewaterdeel;

    @Column(name = "identificatiewaterdeel")
    private String identificatiewaterdeel;

    @Column(name = "plustypewaterdeel")
    private String plustypewaterdeel;

    @Column(name = "relatievehoogteliggingwaterdeel")
    private String relatievehoogteliggingwaterdeel;

    @Column(name = "statuswaterdeel")
    private String statuswaterdeel;

    @Column(name = "typewaterdeel")
    private String typewaterdeel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Waterdeel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbegingeldigheidwaterdeel() {
        return this.datumbegingeldigheidwaterdeel;
    }

    public Waterdeel datumbegingeldigheidwaterdeel(LocalDate datumbegingeldigheidwaterdeel) {
        this.setDatumbegingeldigheidwaterdeel(datumbegingeldigheidwaterdeel);
        return this;
    }

    public void setDatumbegingeldigheidwaterdeel(LocalDate datumbegingeldigheidwaterdeel) {
        this.datumbegingeldigheidwaterdeel = datumbegingeldigheidwaterdeel;
    }

    public LocalDate getDatumeindegeldigheidwaterdeel() {
        return this.datumeindegeldigheidwaterdeel;
    }

    public Waterdeel datumeindegeldigheidwaterdeel(LocalDate datumeindegeldigheidwaterdeel) {
        this.setDatumeindegeldigheidwaterdeel(datumeindegeldigheidwaterdeel);
        return this;
    }

    public void setDatumeindegeldigheidwaterdeel(LocalDate datumeindegeldigheidwaterdeel) {
        this.datumeindegeldigheidwaterdeel = datumeindegeldigheidwaterdeel;
    }

    public String getGeometriewaterdeel() {
        return this.geometriewaterdeel;
    }

    public Waterdeel geometriewaterdeel(String geometriewaterdeel) {
        this.setGeometriewaterdeel(geometriewaterdeel);
        return this;
    }

    public void setGeometriewaterdeel(String geometriewaterdeel) {
        this.geometriewaterdeel = geometriewaterdeel;
    }

    public String getIdentificatiewaterdeel() {
        return this.identificatiewaterdeel;
    }

    public Waterdeel identificatiewaterdeel(String identificatiewaterdeel) {
        this.setIdentificatiewaterdeel(identificatiewaterdeel);
        return this;
    }

    public void setIdentificatiewaterdeel(String identificatiewaterdeel) {
        this.identificatiewaterdeel = identificatiewaterdeel;
    }

    public String getPlustypewaterdeel() {
        return this.plustypewaterdeel;
    }

    public Waterdeel plustypewaterdeel(String plustypewaterdeel) {
        this.setPlustypewaterdeel(plustypewaterdeel);
        return this;
    }

    public void setPlustypewaterdeel(String plustypewaterdeel) {
        this.plustypewaterdeel = plustypewaterdeel;
    }

    public String getRelatievehoogteliggingwaterdeel() {
        return this.relatievehoogteliggingwaterdeel;
    }

    public Waterdeel relatievehoogteliggingwaterdeel(String relatievehoogteliggingwaterdeel) {
        this.setRelatievehoogteliggingwaterdeel(relatievehoogteliggingwaterdeel);
        return this;
    }

    public void setRelatievehoogteliggingwaterdeel(String relatievehoogteliggingwaterdeel) {
        this.relatievehoogteliggingwaterdeel = relatievehoogteliggingwaterdeel;
    }

    public String getStatuswaterdeel() {
        return this.statuswaterdeel;
    }

    public Waterdeel statuswaterdeel(String statuswaterdeel) {
        this.setStatuswaterdeel(statuswaterdeel);
        return this;
    }

    public void setStatuswaterdeel(String statuswaterdeel) {
        this.statuswaterdeel = statuswaterdeel;
    }

    public String getTypewaterdeel() {
        return this.typewaterdeel;
    }

    public Waterdeel typewaterdeel(String typewaterdeel) {
        this.setTypewaterdeel(typewaterdeel);
        return this;
    }

    public void setTypewaterdeel(String typewaterdeel) {
        this.typewaterdeel = typewaterdeel;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Waterdeel)) {
            return false;
        }
        return getId() != null && getId().equals(((Waterdeel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Waterdeel{" +
            "id=" + getId() +
            ", datumbegingeldigheidwaterdeel='" + getDatumbegingeldigheidwaterdeel() + "'" +
            ", datumeindegeldigheidwaterdeel='" + getDatumeindegeldigheidwaterdeel() + "'" +
            ", geometriewaterdeel='" + getGeometriewaterdeel() + "'" +
            ", identificatiewaterdeel='" + getIdentificatiewaterdeel() + "'" +
            ", plustypewaterdeel='" + getPlustypewaterdeel() + "'" +
            ", relatievehoogteliggingwaterdeel='" + getRelatievehoogteliggingwaterdeel() + "'" +
            ", statuswaterdeel='" + getStatuswaterdeel() + "'" +
            ", typewaterdeel='" + getTypewaterdeel() + "'" +
            "}";
    }
}
