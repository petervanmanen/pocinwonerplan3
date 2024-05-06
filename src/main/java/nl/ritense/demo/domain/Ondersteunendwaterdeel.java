package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Ondersteunendwaterdeel.
 */
@Entity
@Table(name = "ondersteunendwaterdeel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ondersteunendwaterdeel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheidondersteunendwaterdeel")
    private LocalDate datumbegingeldigheidondersteunendwaterdeel;

    @Column(name = "datumeindegeldigheidondersteunendwaterdeel")
    private LocalDate datumeindegeldigheidondersteunendwaterdeel;

    @Column(name = "geometrieondersteunendwaterdeel")
    private String geometrieondersteunendwaterdeel;

    @Column(name = "identificatieondersteunendwaterdeel")
    private String identificatieondersteunendwaterdeel;

    @Column(name = "plustypeondersteunendwaterdeel")
    private String plustypeondersteunendwaterdeel;

    @Column(name = "relatievehoogteliggingondersteunendwaterdeel")
    private String relatievehoogteliggingondersteunendwaterdeel;

    @Column(name = "statusondersteunendwaterdeel")
    private String statusondersteunendwaterdeel;

    @Column(name = "typeondersteunendwaterdeel")
    private String typeondersteunendwaterdeel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ondersteunendwaterdeel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbegingeldigheidondersteunendwaterdeel() {
        return this.datumbegingeldigheidondersteunendwaterdeel;
    }

    public Ondersteunendwaterdeel datumbegingeldigheidondersteunendwaterdeel(LocalDate datumbegingeldigheidondersteunendwaterdeel) {
        this.setDatumbegingeldigheidondersteunendwaterdeel(datumbegingeldigheidondersteunendwaterdeel);
        return this;
    }

    public void setDatumbegingeldigheidondersteunendwaterdeel(LocalDate datumbegingeldigheidondersteunendwaterdeel) {
        this.datumbegingeldigheidondersteunendwaterdeel = datumbegingeldigheidondersteunendwaterdeel;
    }

    public LocalDate getDatumeindegeldigheidondersteunendwaterdeel() {
        return this.datumeindegeldigheidondersteunendwaterdeel;
    }

    public Ondersteunendwaterdeel datumeindegeldigheidondersteunendwaterdeel(LocalDate datumeindegeldigheidondersteunendwaterdeel) {
        this.setDatumeindegeldigheidondersteunendwaterdeel(datumeindegeldigheidondersteunendwaterdeel);
        return this;
    }

    public void setDatumeindegeldigheidondersteunendwaterdeel(LocalDate datumeindegeldigheidondersteunendwaterdeel) {
        this.datumeindegeldigheidondersteunendwaterdeel = datumeindegeldigheidondersteunendwaterdeel;
    }

    public String getGeometrieondersteunendwaterdeel() {
        return this.geometrieondersteunendwaterdeel;
    }

    public Ondersteunendwaterdeel geometrieondersteunendwaterdeel(String geometrieondersteunendwaterdeel) {
        this.setGeometrieondersteunendwaterdeel(geometrieondersteunendwaterdeel);
        return this;
    }

    public void setGeometrieondersteunendwaterdeel(String geometrieondersteunendwaterdeel) {
        this.geometrieondersteunendwaterdeel = geometrieondersteunendwaterdeel;
    }

    public String getIdentificatieondersteunendwaterdeel() {
        return this.identificatieondersteunendwaterdeel;
    }

    public Ondersteunendwaterdeel identificatieondersteunendwaterdeel(String identificatieondersteunendwaterdeel) {
        this.setIdentificatieondersteunendwaterdeel(identificatieondersteunendwaterdeel);
        return this;
    }

    public void setIdentificatieondersteunendwaterdeel(String identificatieondersteunendwaterdeel) {
        this.identificatieondersteunendwaterdeel = identificatieondersteunendwaterdeel;
    }

    public String getPlustypeondersteunendwaterdeel() {
        return this.plustypeondersteunendwaterdeel;
    }

    public Ondersteunendwaterdeel plustypeondersteunendwaterdeel(String plustypeondersteunendwaterdeel) {
        this.setPlustypeondersteunendwaterdeel(plustypeondersteunendwaterdeel);
        return this;
    }

    public void setPlustypeondersteunendwaterdeel(String plustypeondersteunendwaterdeel) {
        this.plustypeondersteunendwaterdeel = plustypeondersteunendwaterdeel;
    }

    public String getRelatievehoogteliggingondersteunendwaterdeel() {
        return this.relatievehoogteliggingondersteunendwaterdeel;
    }

    public Ondersteunendwaterdeel relatievehoogteliggingondersteunendwaterdeel(String relatievehoogteliggingondersteunendwaterdeel) {
        this.setRelatievehoogteliggingondersteunendwaterdeel(relatievehoogteliggingondersteunendwaterdeel);
        return this;
    }

    public void setRelatievehoogteliggingondersteunendwaterdeel(String relatievehoogteliggingondersteunendwaterdeel) {
        this.relatievehoogteliggingondersteunendwaterdeel = relatievehoogteliggingondersteunendwaterdeel;
    }

    public String getStatusondersteunendwaterdeel() {
        return this.statusondersteunendwaterdeel;
    }

    public Ondersteunendwaterdeel statusondersteunendwaterdeel(String statusondersteunendwaterdeel) {
        this.setStatusondersteunendwaterdeel(statusondersteunendwaterdeel);
        return this;
    }

    public void setStatusondersteunendwaterdeel(String statusondersteunendwaterdeel) {
        this.statusondersteunendwaterdeel = statusondersteunendwaterdeel;
    }

    public String getTypeondersteunendwaterdeel() {
        return this.typeondersteunendwaterdeel;
    }

    public Ondersteunendwaterdeel typeondersteunendwaterdeel(String typeondersteunendwaterdeel) {
        this.setTypeondersteunendwaterdeel(typeondersteunendwaterdeel);
        return this;
    }

    public void setTypeondersteunendwaterdeel(String typeondersteunendwaterdeel) {
        this.typeondersteunendwaterdeel = typeondersteunendwaterdeel;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ondersteunendwaterdeel)) {
            return false;
        }
        return getId() != null && getId().equals(((Ondersteunendwaterdeel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ondersteunendwaterdeel{" +
            "id=" + getId() +
            ", datumbegingeldigheidondersteunendwaterdeel='" + getDatumbegingeldigheidondersteunendwaterdeel() + "'" +
            ", datumeindegeldigheidondersteunendwaterdeel='" + getDatumeindegeldigheidondersteunendwaterdeel() + "'" +
            ", geometrieondersteunendwaterdeel='" + getGeometrieondersteunendwaterdeel() + "'" +
            ", identificatieondersteunendwaterdeel='" + getIdentificatieondersteunendwaterdeel() + "'" +
            ", plustypeondersteunendwaterdeel='" + getPlustypeondersteunendwaterdeel() + "'" +
            ", relatievehoogteliggingondersteunendwaterdeel='" + getRelatievehoogteliggingondersteunendwaterdeel() + "'" +
            ", statusondersteunendwaterdeel='" + getStatusondersteunendwaterdeel() + "'" +
            ", typeondersteunendwaterdeel='" + getTypeondersteunendwaterdeel() + "'" +
            "}";
    }
}
