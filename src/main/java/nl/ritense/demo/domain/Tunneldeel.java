package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Tunneldeel.
 */
@Entity
@Table(name = "tunneldeel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tunneldeel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheidtunneldeel")
    private LocalDate datumbegingeldigheidtunneldeel;

    @Column(name = "datumeindegeldigheidtunneldeel")
    private LocalDate datumeindegeldigheidtunneldeel;

    @Column(name = "geometrietunneldeel")
    private String geometrietunneldeel;

    @Column(name = "identificatietunneldeel")
    private String identificatietunneldeel;

    @Column(name = "lod_0_geometrietunneldeel")
    private String lod0geometrietunneldeel;

    @Column(name = "relatievehoogteliggingtunneldeel")
    private String relatievehoogteliggingtunneldeel;

    @Column(name = "statustunneldeel")
    private String statustunneldeel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tunneldeel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbegingeldigheidtunneldeel() {
        return this.datumbegingeldigheidtunneldeel;
    }

    public Tunneldeel datumbegingeldigheidtunneldeel(LocalDate datumbegingeldigheidtunneldeel) {
        this.setDatumbegingeldigheidtunneldeel(datumbegingeldigheidtunneldeel);
        return this;
    }

    public void setDatumbegingeldigheidtunneldeel(LocalDate datumbegingeldigheidtunneldeel) {
        this.datumbegingeldigheidtunneldeel = datumbegingeldigheidtunneldeel;
    }

    public LocalDate getDatumeindegeldigheidtunneldeel() {
        return this.datumeindegeldigheidtunneldeel;
    }

    public Tunneldeel datumeindegeldigheidtunneldeel(LocalDate datumeindegeldigheidtunneldeel) {
        this.setDatumeindegeldigheidtunneldeel(datumeindegeldigheidtunneldeel);
        return this;
    }

    public void setDatumeindegeldigheidtunneldeel(LocalDate datumeindegeldigheidtunneldeel) {
        this.datumeindegeldigheidtunneldeel = datumeindegeldigheidtunneldeel;
    }

    public String getGeometrietunneldeel() {
        return this.geometrietunneldeel;
    }

    public Tunneldeel geometrietunneldeel(String geometrietunneldeel) {
        this.setGeometrietunneldeel(geometrietunneldeel);
        return this;
    }

    public void setGeometrietunneldeel(String geometrietunneldeel) {
        this.geometrietunneldeel = geometrietunneldeel;
    }

    public String getIdentificatietunneldeel() {
        return this.identificatietunneldeel;
    }

    public Tunneldeel identificatietunneldeel(String identificatietunneldeel) {
        this.setIdentificatietunneldeel(identificatietunneldeel);
        return this;
    }

    public void setIdentificatietunneldeel(String identificatietunneldeel) {
        this.identificatietunneldeel = identificatietunneldeel;
    }

    public String getLod0geometrietunneldeel() {
        return this.lod0geometrietunneldeel;
    }

    public Tunneldeel lod0geometrietunneldeel(String lod0geometrietunneldeel) {
        this.setLod0geometrietunneldeel(lod0geometrietunneldeel);
        return this;
    }

    public void setLod0geometrietunneldeel(String lod0geometrietunneldeel) {
        this.lod0geometrietunneldeel = lod0geometrietunneldeel;
    }

    public String getRelatievehoogteliggingtunneldeel() {
        return this.relatievehoogteliggingtunneldeel;
    }

    public Tunneldeel relatievehoogteliggingtunneldeel(String relatievehoogteliggingtunneldeel) {
        this.setRelatievehoogteliggingtunneldeel(relatievehoogteliggingtunneldeel);
        return this;
    }

    public void setRelatievehoogteliggingtunneldeel(String relatievehoogteliggingtunneldeel) {
        this.relatievehoogteliggingtunneldeel = relatievehoogteliggingtunneldeel;
    }

    public String getStatustunneldeel() {
        return this.statustunneldeel;
    }

    public Tunneldeel statustunneldeel(String statustunneldeel) {
        this.setStatustunneldeel(statustunneldeel);
        return this;
    }

    public void setStatustunneldeel(String statustunneldeel) {
        this.statustunneldeel = statustunneldeel;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tunneldeel)) {
            return false;
        }
        return getId() != null && getId().equals(((Tunneldeel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tunneldeel{" +
            "id=" + getId() +
            ", datumbegingeldigheidtunneldeel='" + getDatumbegingeldigheidtunneldeel() + "'" +
            ", datumeindegeldigheidtunneldeel='" + getDatumeindegeldigheidtunneldeel() + "'" +
            ", geometrietunneldeel='" + getGeometrietunneldeel() + "'" +
            ", identificatietunneldeel='" + getIdentificatietunneldeel() + "'" +
            ", lod0geometrietunneldeel='" + getLod0geometrietunneldeel() + "'" +
            ", relatievehoogteliggingtunneldeel='" + getRelatievehoogteliggingtunneldeel() + "'" +
            ", statustunneldeel='" + getStatustunneldeel() + "'" +
            "}";
    }
}
