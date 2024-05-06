package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Kunstwerkdeel.
 */
@Entity
@Table(name = "kunstwerkdeel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Kunstwerkdeel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheidkunstwerkdeel")
    private LocalDate datumbegingeldigheidkunstwerkdeel;

    @Column(name = "datumeindegeldigheidkunstwerkdeel")
    private LocalDate datumeindegeldigheidkunstwerkdeel;

    @Column(name = "geometriekunstwerkdeel")
    private String geometriekunstwerkdeel;

    @Column(name = "identificatiekunstwerkdeel")
    private String identificatiekunstwerkdeel;

    @Column(name = "lod_0_geometriekunstwerkdeel")
    private String lod0geometriekunstwerkdeel;

    @Column(name = "lod_1_geometriekunstwerkdeel")
    private String lod1geometriekunstwerkdeel;

    @Column(name = "lod_2_geometriekunstwerkdeel")
    private String lod2geometriekunstwerkdeel;

    @Column(name = "lod_3_geometriekunstwerkdeel")
    private String lod3geometriekunstwerkdeel;

    @Column(name = "relatievehoogteliggingkunstwerkdeel")
    private String relatievehoogteliggingkunstwerkdeel;

    @Column(name = "statuskunstwerkdeel")
    private String statuskunstwerkdeel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Kunstwerkdeel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbegingeldigheidkunstwerkdeel() {
        return this.datumbegingeldigheidkunstwerkdeel;
    }

    public Kunstwerkdeel datumbegingeldigheidkunstwerkdeel(LocalDate datumbegingeldigheidkunstwerkdeel) {
        this.setDatumbegingeldigheidkunstwerkdeel(datumbegingeldigheidkunstwerkdeel);
        return this;
    }

    public void setDatumbegingeldigheidkunstwerkdeel(LocalDate datumbegingeldigheidkunstwerkdeel) {
        this.datumbegingeldigheidkunstwerkdeel = datumbegingeldigheidkunstwerkdeel;
    }

    public LocalDate getDatumeindegeldigheidkunstwerkdeel() {
        return this.datumeindegeldigheidkunstwerkdeel;
    }

    public Kunstwerkdeel datumeindegeldigheidkunstwerkdeel(LocalDate datumeindegeldigheidkunstwerkdeel) {
        this.setDatumeindegeldigheidkunstwerkdeel(datumeindegeldigheidkunstwerkdeel);
        return this;
    }

    public void setDatumeindegeldigheidkunstwerkdeel(LocalDate datumeindegeldigheidkunstwerkdeel) {
        this.datumeindegeldigheidkunstwerkdeel = datumeindegeldigheidkunstwerkdeel;
    }

    public String getGeometriekunstwerkdeel() {
        return this.geometriekunstwerkdeel;
    }

    public Kunstwerkdeel geometriekunstwerkdeel(String geometriekunstwerkdeel) {
        this.setGeometriekunstwerkdeel(geometriekunstwerkdeel);
        return this;
    }

    public void setGeometriekunstwerkdeel(String geometriekunstwerkdeel) {
        this.geometriekunstwerkdeel = geometriekunstwerkdeel;
    }

    public String getIdentificatiekunstwerkdeel() {
        return this.identificatiekunstwerkdeel;
    }

    public Kunstwerkdeel identificatiekunstwerkdeel(String identificatiekunstwerkdeel) {
        this.setIdentificatiekunstwerkdeel(identificatiekunstwerkdeel);
        return this;
    }

    public void setIdentificatiekunstwerkdeel(String identificatiekunstwerkdeel) {
        this.identificatiekunstwerkdeel = identificatiekunstwerkdeel;
    }

    public String getLod0geometriekunstwerkdeel() {
        return this.lod0geometriekunstwerkdeel;
    }

    public Kunstwerkdeel lod0geometriekunstwerkdeel(String lod0geometriekunstwerkdeel) {
        this.setLod0geometriekunstwerkdeel(lod0geometriekunstwerkdeel);
        return this;
    }

    public void setLod0geometriekunstwerkdeel(String lod0geometriekunstwerkdeel) {
        this.lod0geometriekunstwerkdeel = lod0geometriekunstwerkdeel;
    }

    public String getLod1geometriekunstwerkdeel() {
        return this.lod1geometriekunstwerkdeel;
    }

    public Kunstwerkdeel lod1geometriekunstwerkdeel(String lod1geometriekunstwerkdeel) {
        this.setLod1geometriekunstwerkdeel(lod1geometriekunstwerkdeel);
        return this;
    }

    public void setLod1geometriekunstwerkdeel(String lod1geometriekunstwerkdeel) {
        this.lod1geometriekunstwerkdeel = lod1geometriekunstwerkdeel;
    }

    public String getLod2geometriekunstwerkdeel() {
        return this.lod2geometriekunstwerkdeel;
    }

    public Kunstwerkdeel lod2geometriekunstwerkdeel(String lod2geometriekunstwerkdeel) {
        this.setLod2geometriekunstwerkdeel(lod2geometriekunstwerkdeel);
        return this;
    }

    public void setLod2geometriekunstwerkdeel(String lod2geometriekunstwerkdeel) {
        this.lod2geometriekunstwerkdeel = lod2geometriekunstwerkdeel;
    }

    public String getLod3geometriekunstwerkdeel() {
        return this.lod3geometriekunstwerkdeel;
    }

    public Kunstwerkdeel lod3geometriekunstwerkdeel(String lod3geometriekunstwerkdeel) {
        this.setLod3geometriekunstwerkdeel(lod3geometriekunstwerkdeel);
        return this;
    }

    public void setLod3geometriekunstwerkdeel(String lod3geometriekunstwerkdeel) {
        this.lod3geometriekunstwerkdeel = lod3geometriekunstwerkdeel;
    }

    public String getRelatievehoogteliggingkunstwerkdeel() {
        return this.relatievehoogteliggingkunstwerkdeel;
    }

    public Kunstwerkdeel relatievehoogteliggingkunstwerkdeel(String relatievehoogteliggingkunstwerkdeel) {
        this.setRelatievehoogteliggingkunstwerkdeel(relatievehoogteliggingkunstwerkdeel);
        return this;
    }

    public void setRelatievehoogteliggingkunstwerkdeel(String relatievehoogteliggingkunstwerkdeel) {
        this.relatievehoogteliggingkunstwerkdeel = relatievehoogteliggingkunstwerkdeel;
    }

    public String getStatuskunstwerkdeel() {
        return this.statuskunstwerkdeel;
    }

    public Kunstwerkdeel statuskunstwerkdeel(String statuskunstwerkdeel) {
        this.setStatuskunstwerkdeel(statuskunstwerkdeel);
        return this;
    }

    public void setStatuskunstwerkdeel(String statuskunstwerkdeel) {
        this.statuskunstwerkdeel = statuskunstwerkdeel;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Kunstwerkdeel)) {
            return false;
        }
        return getId() != null && getId().equals(((Kunstwerkdeel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Kunstwerkdeel{" +
            "id=" + getId() +
            ", datumbegingeldigheidkunstwerkdeel='" + getDatumbegingeldigheidkunstwerkdeel() + "'" +
            ", datumeindegeldigheidkunstwerkdeel='" + getDatumeindegeldigheidkunstwerkdeel() + "'" +
            ", geometriekunstwerkdeel='" + getGeometriekunstwerkdeel() + "'" +
            ", identificatiekunstwerkdeel='" + getIdentificatiekunstwerkdeel() + "'" +
            ", lod0geometriekunstwerkdeel='" + getLod0geometriekunstwerkdeel() + "'" +
            ", lod1geometriekunstwerkdeel='" + getLod1geometriekunstwerkdeel() + "'" +
            ", lod2geometriekunstwerkdeel='" + getLod2geometriekunstwerkdeel() + "'" +
            ", lod3geometriekunstwerkdeel='" + getLod3geometriekunstwerkdeel() + "'" +
            ", relatievehoogteliggingkunstwerkdeel='" + getRelatievehoogteliggingkunstwerkdeel() + "'" +
            ", statuskunstwerkdeel='" + getStatuskunstwerkdeel() + "'" +
            "}";
    }
}
