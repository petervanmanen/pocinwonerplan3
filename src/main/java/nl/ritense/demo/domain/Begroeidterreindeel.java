package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Begroeidterreindeel.
 */
@Entity
@Table(name = "begroeidterreindeel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Begroeidterreindeel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "begroeidterreindeeloptalud")
    private String begroeidterreindeeloptalud;

    @Column(name = "datumbegingeldigheidbegroeidterreindeel")
    private LocalDate datumbegingeldigheidbegroeidterreindeel;

    @Column(name = "datumeindegeldigheidbegroeidterreindeel")
    private LocalDate datumeindegeldigheidbegroeidterreindeel;

    @Column(name = "fysiekvoorkomenbegroeidterreindeel")
    private String fysiekvoorkomenbegroeidterreindeel;

    @Column(name = "geometriebegroeidterreindeel")
    private String geometriebegroeidterreindeel;

    @Column(name = "identificatiebegroeidterreindeel")
    private String identificatiebegroeidterreindeel;

    @Column(name = "kruinlijngeometriebegroeidterreindeel")
    private String kruinlijngeometriebegroeidterreindeel;

    @Column(name = "lod_0_geometriebegroeidterreindeel")
    private String lod0geometriebegroeidterreindeel;

    @Column(name = "plusfysiekvoorkomenbegroeidterreindeel")
    private String plusfysiekvoorkomenbegroeidterreindeel;

    @Column(name = "relatievehoogteliggingbegroeidterreindeel")
    private String relatievehoogteliggingbegroeidterreindeel;

    @Column(name = "statusbegroeidterreindeel")
    private String statusbegroeidterreindeel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Begroeidterreindeel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBegroeidterreindeeloptalud() {
        return this.begroeidterreindeeloptalud;
    }

    public Begroeidterreindeel begroeidterreindeeloptalud(String begroeidterreindeeloptalud) {
        this.setBegroeidterreindeeloptalud(begroeidterreindeeloptalud);
        return this;
    }

    public void setBegroeidterreindeeloptalud(String begroeidterreindeeloptalud) {
        this.begroeidterreindeeloptalud = begroeidterreindeeloptalud;
    }

    public LocalDate getDatumbegingeldigheidbegroeidterreindeel() {
        return this.datumbegingeldigheidbegroeidterreindeel;
    }

    public Begroeidterreindeel datumbegingeldigheidbegroeidterreindeel(LocalDate datumbegingeldigheidbegroeidterreindeel) {
        this.setDatumbegingeldigheidbegroeidterreindeel(datumbegingeldigheidbegroeidterreindeel);
        return this;
    }

    public void setDatumbegingeldigheidbegroeidterreindeel(LocalDate datumbegingeldigheidbegroeidterreindeel) {
        this.datumbegingeldigheidbegroeidterreindeel = datumbegingeldigheidbegroeidterreindeel;
    }

    public LocalDate getDatumeindegeldigheidbegroeidterreindeel() {
        return this.datumeindegeldigheidbegroeidterreindeel;
    }

    public Begroeidterreindeel datumeindegeldigheidbegroeidterreindeel(LocalDate datumeindegeldigheidbegroeidterreindeel) {
        this.setDatumeindegeldigheidbegroeidterreindeel(datumeindegeldigheidbegroeidterreindeel);
        return this;
    }

    public void setDatumeindegeldigheidbegroeidterreindeel(LocalDate datumeindegeldigheidbegroeidterreindeel) {
        this.datumeindegeldigheidbegroeidterreindeel = datumeindegeldigheidbegroeidterreindeel;
    }

    public String getFysiekvoorkomenbegroeidterreindeel() {
        return this.fysiekvoorkomenbegroeidterreindeel;
    }

    public Begroeidterreindeel fysiekvoorkomenbegroeidterreindeel(String fysiekvoorkomenbegroeidterreindeel) {
        this.setFysiekvoorkomenbegroeidterreindeel(fysiekvoorkomenbegroeidterreindeel);
        return this;
    }

    public void setFysiekvoorkomenbegroeidterreindeel(String fysiekvoorkomenbegroeidterreindeel) {
        this.fysiekvoorkomenbegroeidterreindeel = fysiekvoorkomenbegroeidterreindeel;
    }

    public String getGeometriebegroeidterreindeel() {
        return this.geometriebegroeidterreindeel;
    }

    public Begroeidterreindeel geometriebegroeidterreindeel(String geometriebegroeidterreindeel) {
        this.setGeometriebegroeidterreindeel(geometriebegroeidterreindeel);
        return this;
    }

    public void setGeometriebegroeidterreindeel(String geometriebegroeidterreindeel) {
        this.geometriebegroeidterreindeel = geometriebegroeidterreindeel;
    }

    public String getIdentificatiebegroeidterreindeel() {
        return this.identificatiebegroeidterreindeel;
    }

    public Begroeidterreindeel identificatiebegroeidterreindeel(String identificatiebegroeidterreindeel) {
        this.setIdentificatiebegroeidterreindeel(identificatiebegroeidterreindeel);
        return this;
    }

    public void setIdentificatiebegroeidterreindeel(String identificatiebegroeidterreindeel) {
        this.identificatiebegroeidterreindeel = identificatiebegroeidterreindeel;
    }

    public String getKruinlijngeometriebegroeidterreindeel() {
        return this.kruinlijngeometriebegroeidterreindeel;
    }

    public Begroeidterreindeel kruinlijngeometriebegroeidterreindeel(String kruinlijngeometriebegroeidterreindeel) {
        this.setKruinlijngeometriebegroeidterreindeel(kruinlijngeometriebegroeidterreindeel);
        return this;
    }

    public void setKruinlijngeometriebegroeidterreindeel(String kruinlijngeometriebegroeidterreindeel) {
        this.kruinlijngeometriebegroeidterreindeel = kruinlijngeometriebegroeidterreindeel;
    }

    public String getLod0geometriebegroeidterreindeel() {
        return this.lod0geometriebegroeidterreindeel;
    }

    public Begroeidterreindeel lod0geometriebegroeidterreindeel(String lod0geometriebegroeidterreindeel) {
        this.setLod0geometriebegroeidterreindeel(lod0geometriebegroeidterreindeel);
        return this;
    }

    public void setLod0geometriebegroeidterreindeel(String lod0geometriebegroeidterreindeel) {
        this.lod0geometriebegroeidterreindeel = lod0geometriebegroeidterreindeel;
    }

    public String getPlusfysiekvoorkomenbegroeidterreindeel() {
        return this.plusfysiekvoorkomenbegroeidterreindeel;
    }

    public Begroeidterreindeel plusfysiekvoorkomenbegroeidterreindeel(String plusfysiekvoorkomenbegroeidterreindeel) {
        this.setPlusfysiekvoorkomenbegroeidterreindeel(plusfysiekvoorkomenbegroeidterreindeel);
        return this;
    }

    public void setPlusfysiekvoorkomenbegroeidterreindeel(String plusfysiekvoorkomenbegroeidterreindeel) {
        this.plusfysiekvoorkomenbegroeidterreindeel = plusfysiekvoorkomenbegroeidterreindeel;
    }

    public String getRelatievehoogteliggingbegroeidterreindeel() {
        return this.relatievehoogteliggingbegroeidterreindeel;
    }

    public Begroeidterreindeel relatievehoogteliggingbegroeidterreindeel(String relatievehoogteliggingbegroeidterreindeel) {
        this.setRelatievehoogteliggingbegroeidterreindeel(relatievehoogteliggingbegroeidterreindeel);
        return this;
    }

    public void setRelatievehoogteliggingbegroeidterreindeel(String relatievehoogteliggingbegroeidterreindeel) {
        this.relatievehoogteliggingbegroeidterreindeel = relatievehoogteliggingbegroeidterreindeel;
    }

    public String getStatusbegroeidterreindeel() {
        return this.statusbegroeidterreindeel;
    }

    public Begroeidterreindeel statusbegroeidterreindeel(String statusbegroeidterreindeel) {
        this.setStatusbegroeidterreindeel(statusbegroeidterreindeel);
        return this;
    }

    public void setStatusbegroeidterreindeel(String statusbegroeidterreindeel) {
        this.statusbegroeidterreindeel = statusbegroeidterreindeel;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Begroeidterreindeel)) {
            return false;
        }
        return getId() != null && getId().equals(((Begroeidterreindeel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Begroeidterreindeel{" +
            "id=" + getId() +
            ", begroeidterreindeeloptalud='" + getBegroeidterreindeeloptalud() + "'" +
            ", datumbegingeldigheidbegroeidterreindeel='" + getDatumbegingeldigheidbegroeidterreindeel() + "'" +
            ", datumeindegeldigheidbegroeidterreindeel='" + getDatumeindegeldigheidbegroeidterreindeel() + "'" +
            ", fysiekvoorkomenbegroeidterreindeel='" + getFysiekvoorkomenbegroeidterreindeel() + "'" +
            ", geometriebegroeidterreindeel='" + getGeometriebegroeidterreindeel() + "'" +
            ", identificatiebegroeidterreindeel='" + getIdentificatiebegroeidterreindeel() + "'" +
            ", kruinlijngeometriebegroeidterreindeel='" + getKruinlijngeometriebegroeidterreindeel() + "'" +
            ", lod0geometriebegroeidterreindeel='" + getLod0geometriebegroeidterreindeel() + "'" +
            ", plusfysiekvoorkomenbegroeidterreindeel='" + getPlusfysiekvoorkomenbegroeidterreindeel() + "'" +
            ", relatievehoogteliggingbegroeidterreindeel='" + getRelatievehoogteliggingbegroeidterreindeel() + "'" +
            ", statusbegroeidterreindeel='" + getStatusbegroeidterreindeel() + "'" +
            "}";
    }
}
