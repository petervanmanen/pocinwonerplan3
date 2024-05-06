package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Onbegroeidterreindeel.
 */
@Entity
@Table(name = "onbegroeidterreindeel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Onbegroeidterreindeel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheidonbegroeidterreindeel")
    private LocalDate datumbegingeldigheidonbegroeidterreindeel;

    @Column(name = "datumeindegeldigheidonbegroeidterreindeel")
    private LocalDate datumeindegeldigheidonbegroeidterreindeel;

    @Column(name = "fysiekvoorkomenonbegroeidterreindeel")
    private String fysiekvoorkomenonbegroeidterreindeel;

    @Column(name = "geometrieonbegroeidterreindeel")
    private String geometrieonbegroeidterreindeel;

    @Column(name = "identificatieonbegroeidterreindeel")
    private String identificatieonbegroeidterreindeel;

    @Column(name = "kruinlijngeometrieonbegroeidterreindeel")
    private String kruinlijngeometrieonbegroeidterreindeel;

    @Column(name = "onbegroeidterreindeeloptalud")
    private String onbegroeidterreindeeloptalud;

    @Column(name = "plusfysiekvoorkomenonbegroeidterreindeel")
    private String plusfysiekvoorkomenonbegroeidterreindeel;

    @Column(name = "relatievehoogteliggingonbegroeidterreindeel")
    private String relatievehoogteliggingonbegroeidterreindeel;

    @Column(name = "statusonbegroeidterreindeel")
    private String statusonbegroeidterreindeel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Onbegroeidterreindeel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbegingeldigheidonbegroeidterreindeel() {
        return this.datumbegingeldigheidonbegroeidterreindeel;
    }

    public Onbegroeidterreindeel datumbegingeldigheidonbegroeidterreindeel(LocalDate datumbegingeldigheidonbegroeidterreindeel) {
        this.setDatumbegingeldigheidonbegroeidterreindeel(datumbegingeldigheidonbegroeidterreindeel);
        return this;
    }

    public void setDatumbegingeldigheidonbegroeidterreindeel(LocalDate datumbegingeldigheidonbegroeidterreindeel) {
        this.datumbegingeldigheidonbegroeidterreindeel = datumbegingeldigheidonbegroeidterreindeel;
    }

    public LocalDate getDatumeindegeldigheidonbegroeidterreindeel() {
        return this.datumeindegeldigheidonbegroeidterreindeel;
    }

    public Onbegroeidterreindeel datumeindegeldigheidonbegroeidterreindeel(LocalDate datumeindegeldigheidonbegroeidterreindeel) {
        this.setDatumeindegeldigheidonbegroeidterreindeel(datumeindegeldigheidonbegroeidterreindeel);
        return this;
    }

    public void setDatumeindegeldigheidonbegroeidterreindeel(LocalDate datumeindegeldigheidonbegroeidterreindeel) {
        this.datumeindegeldigheidonbegroeidterreindeel = datumeindegeldigheidonbegroeidterreindeel;
    }

    public String getFysiekvoorkomenonbegroeidterreindeel() {
        return this.fysiekvoorkomenonbegroeidterreindeel;
    }

    public Onbegroeidterreindeel fysiekvoorkomenonbegroeidterreindeel(String fysiekvoorkomenonbegroeidterreindeel) {
        this.setFysiekvoorkomenonbegroeidterreindeel(fysiekvoorkomenonbegroeidterreindeel);
        return this;
    }

    public void setFysiekvoorkomenonbegroeidterreindeel(String fysiekvoorkomenonbegroeidterreindeel) {
        this.fysiekvoorkomenonbegroeidterreindeel = fysiekvoorkomenonbegroeidterreindeel;
    }

    public String getGeometrieonbegroeidterreindeel() {
        return this.geometrieonbegroeidterreindeel;
    }

    public Onbegroeidterreindeel geometrieonbegroeidterreindeel(String geometrieonbegroeidterreindeel) {
        this.setGeometrieonbegroeidterreindeel(geometrieonbegroeidterreindeel);
        return this;
    }

    public void setGeometrieonbegroeidterreindeel(String geometrieonbegroeidterreindeel) {
        this.geometrieonbegroeidterreindeel = geometrieonbegroeidterreindeel;
    }

    public String getIdentificatieonbegroeidterreindeel() {
        return this.identificatieonbegroeidterreindeel;
    }

    public Onbegroeidterreindeel identificatieonbegroeidterreindeel(String identificatieonbegroeidterreindeel) {
        this.setIdentificatieonbegroeidterreindeel(identificatieonbegroeidterreindeel);
        return this;
    }

    public void setIdentificatieonbegroeidterreindeel(String identificatieonbegroeidterreindeel) {
        this.identificatieonbegroeidterreindeel = identificatieonbegroeidterreindeel;
    }

    public String getKruinlijngeometrieonbegroeidterreindeel() {
        return this.kruinlijngeometrieonbegroeidterreindeel;
    }

    public Onbegroeidterreindeel kruinlijngeometrieonbegroeidterreindeel(String kruinlijngeometrieonbegroeidterreindeel) {
        this.setKruinlijngeometrieonbegroeidterreindeel(kruinlijngeometrieonbegroeidterreindeel);
        return this;
    }

    public void setKruinlijngeometrieonbegroeidterreindeel(String kruinlijngeometrieonbegroeidterreindeel) {
        this.kruinlijngeometrieonbegroeidterreindeel = kruinlijngeometrieonbegroeidterreindeel;
    }

    public String getOnbegroeidterreindeeloptalud() {
        return this.onbegroeidterreindeeloptalud;
    }

    public Onbegroeidterreindeel onbegroeidterreindeeloptalud(String onbegroeidterreindeeloptalud) {
        this.setOnbegroeidterreindeeloptalud(onbegroeidterreindeeloptalud);
        return this;
    }

    public void setOnbegroeidterreindeeloptalud(String onbegroeidterreindeeloptalud) {
        this.onbegroeidterreindeeloptalud = onbegroeidterreindeeloptalud;
    }

    public String getPlusfysiekvoorkomenonbegroeidterreindeel() {
        return this.plusfysiekvoorkomenonbegroeidterreindeel;
    }

    public Onbegroeidterreindeel plusfysiekvoorkomenonbegroeidterreindeel(String plusfysiekvoorkomenonbegroeidterreindeel) {
        this.setPlusfysiekvoorkomenonbegroeidterreindeel(plusfysiekvoorkomenonbegroeidterreindeel);
        return this;
    }

    public void setPlusfysiekvoorkomenonbegroeidterreindeel(String plusfysiekvoorkomenonbegroeidterreindeel) {
        this.plusfysiekvoorkomenonbegroeidterreindeel = plusfysiekvoorkomenonbegroeidterreindeel;
    }

    public String getRelatievehoogteliggingonbegroeidterreindeel() {
        return this.relatievehoogteliggingonbegroeidterreindeel;
    }

    public Onbegroeidterreindeel relatievehoogteliggingonbegroeidterreindeel(String relatievehoogteliggingonbegroeidterreindeel) {
        this.setRelatievehoogteliggingonbegroeidterreindeel(relatievehoogteliggingonbegroeidterreindeel);
        return this;
    }

    public void setRelatievehoogteliggingonbegroeidterreindeel(String relatievehoogteliggingonbegroeidterreindeel) {
        this.relatievehoogteliggingonbegroeidterreindeel = relatievehoogteliggingonbegroeidterreindeel;
    }

    public String getStatusonbegroeidterreindeel() {
        return this.statusonbegroeidterreindeel;
    }

    public Onbegroeidterreindeel statusonbegroeidterreindeel(String statusonbegroeidterreindeel) {
        this.setStatusonbegroeidterreindeel(statusonbegroeidterreindeel);
        return this;
    }

    public void setStatusonbegroeidterreindeel(String statusonbegroeidterreindeel) {
        this.statusonbegroeidterreindeel = statusonbegroeidterreindeel;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Onbegroeidterreindeel)) {
            return false;
        }
        return getId() != null && getId().equals(((Onbegroeidterreindeel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Onbegroeidterreindeel{" +
            "id=" + getId() +
            ", datumbegingeldigheidonbegroeidterreindeel='" + getDatumbegingeldigheidonbegroeidterreindeel() + "'" +
            ", datumeindegeldigheidonbegroeidterreindeel='" + getDatumeindegeldigheidonbegroeidterreindeel() + "'" +
            ", fysiekvoorkomenonbegroeidterreindeel='" + getFysiekvoorkomenonbegroeidterreindeel() + "'" +
            ", geometrieonbegroeidterreindeel='" + getGeometrieonbegroeidterreindeel() + "'" +
            ", identificatieonbegroeidterreindeel='" + getIdentificatieonbegroeidterreindeel() + "'" +
            ", kruinlijngeometrieonbegroeidterreindeel='" + getKruinlijngeometrieonbegroeidterreindeel() + "'" +
            ", onbegroeidterreindeeloptalud='" + getOnbegroeidterreindeeloptalud() + "'" +
            ", plusfysiekvoorkomenonbegroeidterreindeel='" + getPlusfysiekvoorkomenonbegroeidterreindeel() + "'" +
            ", relatievehoogteliggingonbegroeidterreindeel='" + getRelatievehoogteliggingonbegroeidterreindeel() + "'" +
            ", statusonbegroeidterreindeel='" + getStatusonbegroeidterreindeel() + "'" +
            "}";
    }
}
