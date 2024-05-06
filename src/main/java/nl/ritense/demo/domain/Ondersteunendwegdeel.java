package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Ondersteunendwegdeel.
 */
@Entity
@Table(name = "ondersteunendwegdeel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ondersteunendwegdeel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheidondersteunendwegdeel")
    private LocalDate datumbegingeldigheidondersteunendwegdeel;

    @Column(name = "datumeindegeldigheidondersteunendwegdeel")
    private LocalDate datumeindegeldigheidondersteunendwegdeel;

    @Column(name = "functieondersteunendwegdeel")
    private String functieondersteunendwegdeel;

    @Column(name = "fysiekvoorkomenondersteunendwegdeel")
    private String fysiekvoorkomenondersteunendwegdeel;

    @Column(name = "geometrieondersteunendwegdeel")
    private String geometrieondersteunendwegdeel;

    @Column(name = "identificatieondersteunendwegdeel")
    private String identificatieondersteunendwegdeel;

    @Column(name = "kruinlijngeometrieondersteunendwegdeel")
    private String kruinlijngeometrieondersteunendwegdeel;

    @Column(name = "lod_0_geometrieondersteunendwegdeel")
    private String lod0geometrieondersteunendwegdeel;

    @Column(name = "ondersteunendwegdeeloptalud")
    private String ondersteunendwegdeeloptalud;

    @Column(name = "plusfunctieondersteunendwegdeel")
    private String plusfunctieondersteunendwegdeel;

    @Column(name = "plusfysiekvoorkomenondersteunendwegdeel")
    private String plusfysiekvoorkomenondersteunendwegdeel;

    @Column(name = "relatievehoogteliggingondersteunendwegdeel")
    private String relatievehoogteliggingondersteunendwegdeel;

    @Column(name = "statusondersteunendwegdeel")
    private String statusondersteunendwegdeel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ondersteunendwegdeel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbegingeldigheidondersteunendwegdeel() {
        return this.datumbegingeldigheidondersteunendwegdeel;
    }

    public Ondersteunendwegdeel datumbegingeldigheidondersteunendwegdeel(LocalDate datumbegingeldigheidondersteunendwegdeel) {
        this.setDatumbegingeldigheidondersteunendwegdeel(datumbegingeldigheidondersteunendwegdeel);
        return this;
    }

    public void setDatumbegingeldigheidondersteunendwegdeel(LocalDate datumbegingeldigheidondersteunendwegdeel) {
        this.datumbegingeldigheidondersteunendwegdeel = datumbegingeldigheidondersteunendwegdeel;
    }

    public LocalDate getDatumeindegeldigheidondersteunendwegdeel() {
        return this.datumeindegeldigheidondersteunendwegdeel;
    }

    public Ondersteunendwegdeel datumeindegeldigheidondersteunendwegdeel(LocalDate datumeindegeldigheidondersteunendwegdeel) {
        this.setDatumeindegeldigheidondersteunendwegdeel(datumeindegeldigheidondersteunendwegdeel);
        return this;
    }

    public void setDatumeindegeldigheidondersteunendwegdeel(LocalDate datumeindegeldigheidondersteunendwegdeel) {
        this.datumeindegeldigheidondersteunendwegdeel = datumeindegeldigheidondersteunendwegdeel;
    }

    public String getFunctieondersteunendwegdeel() {
        return this.functieondersteunendwegdeel;
    }

    public Ondersteunendwegdeel functieondersteunendwegdeel(String functieondersteunendwegdeel) {
        this.setFunctieondersteunendwegdeel(functieondersteunendwegdeel);
        return this;
    }

    public void setFunctieondersteunendwegdeel(String functieondersteunendwegdeel) {
        this.functieondersteunendwegdeel = functieondersteunendwegdeel;
    }

    public String getFysiekvoorkomenondersteunendwegdeel() {
        return this.fysiekvoorkomenondersteunendwegdeel;
    }

    public Ondersteunendwegdeel fysiekvoorkomenondersteunendwegdeel(String fysiekvoorkomenondersteunendwegdeel) {
        this.setFysiekvoorkomenondersteunendwegdeel(fysiekvoorkomenondersteunendwegdeel);
        return this;
    }

    public void setFysiekvoorkomenondersteunendwegdeel(String fysiekvoorkomenondersteunendwegdeel) {
        this.fysiekvoorkomenondersteunendwegdeel = fysiekvoorkomenondersteunendwegdeel;
    }

    public String getGeometrieondersteunendwegdeel() {
        return this.geometrieondersteunendwegdeel;
    }

    public Ondersteunendwegdeel geometrieondersteunendwegdeel(String geometrieondersteunendwegdeel) {
        this.setGeometrieondersteunendwegdeel(geometrieondersteunendwegdeel);
        return this;
    }

    public void setGeometrieondersteunendwegdeel(String geometrieondersteunendwegdeel) {
        this.geometrieondersteunendwegdeel = geometrieondersteunendwegdeel;
    }

    public String getIdentificatieondersteunendwegdeel() {
        return this.identificatieondersteunendwegdeel;
    }

    public Ondersteunendwegdeel identificatieondersteunendwegdeel(String identificatieondersteunendwegdeel) {
        this.setIdentificatieondersteunendwegdeel(identificatieondersteunendwegdeel);
        return this;
    }

    public void setIdentificatieondersteunendwegdeel(String identificatieondersteunendwegdeel) {
        this.identificatieondersteunendwegdeel = identificatieondersteunendwegdeel;
    }

    public String getKruinlijngeometrieondersteunendwegdeel() {
        return this.kruinlijngeometrieondersteunendwegdeel;
    }

    public Ondersteunendwegdeel kruinlijngeometrieondersteunendwegdeel(String kruinlijngeometrieondersteunendwegdeel) {
        this.setKruinlijngeometrieondersteunendwegdeel(kruinlijngeometrieondersteunendwegdeel);
        return this;
    }

    public void setKruinlijngeometrieondersteunendwegdeel(String kruinlijngeometrieondersteunendwegdeel) {
        this.kruinlijngeometrieondersteunendwegdeel = kruinlijngeometrieondersteunendwegdeel;
    }

    public String getLod0geometrieondersteunendwegdeel() {
        return this.lod0geometrieondersteunendwegdeel;
    }

    public Ondersteunendwegdeel lod0geometrieondersteunendwegdeel(String lod0geometrieondersteunendwegdeel) {
        this.setLod0geometrieondersteunendwegdeel(lod0geometrieondersteunendwegdeel);
        return this;
    }

    public void setLod0geometrieondersteunendwegdeel(String lod0geometrieondersteunendwegdeel) {
        this.lod0geometrieondersteunendwegdeel = lod0geometrieondersteunendwegdeel;
    }

    public String getOndersteunendwegdeeloptalud() {
        return this.ondersteunendwegdeeloptalud;
    }

    public Ondersteunendwegdeel ondersteunendwegdeeloptalud(String ondersteunendwegdeeloptalud) {
        this.setOndersteunendwegdeeloptalud(ondersteunendwegdeeloptalud);
        return this;
    }

    public void setOndersteunendwegdeeloptalud(String ondersteunendwegdeeloptalud) {
        this.ondersteunendwegdeeloptalud = ondersteunendwegdeeloptalud;
    }

    public String getPlusfunctieondersteunendwegdeel() {
        return this.plusfunctieondersteunendwegdeel;
    }

    public Ondersteunendwegdeel plusfunctieondersteunendwegdeel(String plusfunctieondersteunendwegdeel) {
        this.setPlusfunctieondersteunendwegdeel(plusfunctieondersteunendwegdeel);
        return this;
    }

    public void setPlusfunctieondersteunendwegdeel(String plusfunctieondersteunendwegdeel) {
        this.plusfunctieondersteunendwegdeel = plusfunctieondersteunendwegdeel;
    }

    public String getPlusfysiekvoorkomenondersteunendwegdeel() {
        return this.plusfysiekvoorkomenondersteunendwegdeel;
    }

    public Ondersteunendwegdeel plusfysiekvoorkomenondersteunendwegdeel(String plusfysiekvoorkomenondersteunendwegdeel) {
        this.setPlusfysiekvoorkomenondersteunendwegdeel(plusfysiekvoorkomenondersteunendwegdeel);
        return this;
    }

    public void setPlusfysiekvoorkomenondersteunendwegdeel(String plusfysiekvoorkomenondersteunendwegdeel) {
        this.plusfysiekvoorkomenondersteunendwegdeel = plusfysiekvoorkomenondersteunendwegdeel;
    }

    public String getRelatievehoogteliggingondersteunendwegdeel() {
        return this.relatievehoogteliggingondersteunendwegdeel;
    }

    public Ondersteunendwegdeel relatievehoogteliggingondersteunendwegdeel(String relatievehoogteliggingondersteunendwegdeel) {
        this.setRelatievehoogteliggingondersteunendwegdeel(relatievehoogteliggingondersteunendwegdeel);
        return this;
    }

    public void setRelatievehoogteliggingondersteunendwegdeel(String relatievehoogteliggingondersteunendwegdeel) {
        this.relatievehoogteliggingondersteunendwegdeel = relatievehoogteliggingondersteunendwegdeel;
    }

    public String getStatusondersteunendwegdeel() {
        return this.statusondersteunendwegdeel;
    }

    public Ondersteunendwegdeel statusondersteunendwegdeel(String statusondersteunendwegdeel) {
        this.setStatusondersteunendwegdeel(statusondersteunendwegdeel);
        return this;
    }

    public void setStatusondersteunendwegdeel(String statusondersteunendwegdeel) {
        this.statusondersteunendwegdeel = statusondersteunendwegdeel;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ondersteunendwegdeel)) {
            return false;
        }
        return getId() != null && getId().equals(((Ondersteunendwegdeel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ondersteunendwegdeel{" +
            "id=" + getId() +
            ", datumbegingeldigheidondersteunendwegdeel='" + getDatumbegingeldigheidondersteunendwegdeel() + "'" +
            ", datumeindegeldigheidondersteunendwegdeel='" + getDatumeindegeldigheidondersteunendwegdeel() + "'" +
            ", functieondersteunendwegdeel='" + getFunctieondersteunendwegdeel() + "'" +
            ", fysiekvoorkomenondersteunendwegdeel='" + getFysiekvoorkomenondersteunendwegdeel() + "'" +
            ", geometrieondersteunendwegdeel='" + getGeometrieondersteunendwegdeel() + "'" +
            ", identificatieondersteunendwegdeel='" + getIdentificatieondersteunendwegdeel() + "'" +
            ", kruinlijngeometrieondersteunendwegdeel='" + getKruinlijngeometrieondersteunendwegdeel() + "'" +
            ", lod0geometrieondersteunendwegdeel='" + getLod0geometrieondersteunendwegdeel() + "'" +
            ", ondersteunendwegdeeloptalud='" + getOndersteunendwegdeeloptalud() + "'" +
            ", plusfunctieondersteunendwegdeel='" + getPlusfunctieondersteunendwegdeel() + "'" +
            ", plusfysiekvoorkomenondersteunendwegdeel='" + getPlusfysiekvoorkomenondersteunendwegdeel() + "'" +
            ", relatievehoogteliggingondersteunendwegdeel='" + getRelatievehoogteliggingondersteunendwegdeel() + "'" +
            ", statusondersteunendwegdeel='" + getStatusondersteunendwegdeel() + "'" +
            "}";
    }
}
