package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Overigbouwwerk.
 */
@Entity
@Table(name = "overigbouwwerk")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Overigbouwwerk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheidoverigbouwwerk")
    private LocalDate datumbegingeldigheidoverigbouwwerk;

    @Column(name = "datumeindegeldigheidoverigbouwwerk")
    private LocalDate datumeindegeldigheidoverigbouwwerk;

    @Column(name = "geometrieoverigbouwwerk")
    private String geometrieoverigbouwwerk;

    @Column(name = "identificatieoverigbouwwerk")
    private String identificatieoverigbouwwerk;

    @Column(name = "lod_0_geometrieoverigbouwwerk")
    private String lod0geometrieoverigbouwwerk;

    @Column(name = "lod_1_geometrieoverigbouwwerk")
    private String lod1geometrieoverigbouwwerk;

    @Column(name = "lod_2_geometrieoverigbouwwerk")
    private String lod2geometrieoverigbouwwerk;

    @Column(name = "lod_3_geometrieoverigbouwwerk")
    private String lod3geometrieoverigbouwwerk;

    @Column(name = "relatievehoogteliggingoverigbouwwerk")
    private String relatievehoogteliggingoverigbouwwerk;

    @Column(name = "statusoverigbouwwerk")
    private String statusoverigbouwwerk;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Overigbouwwerk id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbegingeldigheidoverigbouwwerk() {
        return this.datumbegingeldigheidoverigbouwwerk;
    }

    public Overigbouwwerk datumbegingeldigheidoverigbouwwerk(LocalDate datumbegingeldigheidoverigbouwwerk) {
        this.setDatumbegingeldigheidoverigbouwwerk(datumbegingeldigheidoverigbouwwerk);
        return this;
    }

    public void setDatumbegingeldigheidoverigbouwwerk(LocalDate datumbegingeldigheidoverigbouwwerk) {
        this.datumbegingeldigheidoverigbouwwerk = datumbegingeldigheidoverigbouwwerk;
    }

    public LocalDate getDatumeindegeldigheidoverigbouwwerk() {
        return this.datumeindegeldigheidoverigbouwwerk;
    }

    public Overigbouwwerk datumeindegeldigheidoverigbouwwerk(LocalDate datumeindegeldigheidoverigbouwwerk) {
        this.setDatumeindegeldigheidoverigbouwwerk(datumeindegeldigheidoverigbouwwerk);
        return this;
    }

    public void setDatumeindegeldigheidoverigbouwwerk(LocalDate datumeindegeldigheidoverigbouwwerk) {
        this.datumeindegeldigheidoverigbouwwerk = datumeindegeldigheidoverigbouwwerk;
    }

    public String getGeometrieoverigbouwwerk() {
        return this.geometrieoverigbouwwerk;
    }

    public Overigbouwwerk geometrieoverigbouwwerk(String geometrieoverigbouwwerk) {
        this.setGeometrieoverigbouwwerk(geometrieoverigbouwwerk);
        return this;
    }

    public void setGeometrieoverigbouwwerk(String geometrieoverigbouwwerk) {
        this.geometrieoverigbouwwerk = geometrieoverigbouwwerk;
    }

    public String getIdentificatieoverigbouwwerk() {
        return this.identificatieoverigbouwwerk;
    }

    public Overigbouwwerk identificatieoverigbouwwerk(String identificatieoverigbouwwerk) {
        this.setIdentificatieoverigbouwwerk(identificatieoverigbouwwerk);
        return this;
    }

    public void setIdentificatieoverigbouwwerk(String identificatieoverigbouwwerk) {
        this.identificatieoverigbouwwerk = identificatieoverigbouwwerk;
    }

    public String getLod0geometrieoverigbouwwerk() {
        return this.lod0geometrieoverigbouwwerk;
    }

    public Overigbouwwerk lod0geometrieoverigbouwwerk(String lod0geometrieoverigbouwwerk) {
        this.setLod0geometrieoverigbouwwerk(lod0geometrieoverigbouwwerk);
        return this;
    }

    public void setLod0geometrieoverigbouwwerk(String lod0geometrieoverigbouwwerk) {
        this.lod0geometrieoverigbouwwerk = lod0geometrieoverigbouwwerk;
    }

    public String getLod1geometrieoverigbouwwerk() {
        return this.lod1geometrieoverigbouwwerk;
    }

    public Overigbouwwerk lod1geometrieoverigbouwwerk(String lod1geometrieoverigbouwwerk) {
        this.setLod1geometrieoverigbouwwerk(lod1geometrieoverigbouwwerk);
        return this;
    }

    public void setLod1geometrieoverigbouwwerk(String lod1geometrieoverigbouwwerk) {
        this.lod1geometrieoverigbouwwerk = lod1geometrieoverigbouwwerk;
    }

    public String getLod2geometrieoverigbouwwerk() {
        return this.lod2geometrieoverigbouwwerk;
    }

    public Overigbouwwerk lod2geometrieoverigbouwwerk(String lod2geometrieoverigbouwwerk) {
        this.setLod2geometrieoverigbouwwerk(lod2geometrieoverigbouwwerk);
        return this;
    }

    public void setLod2geometrieoverigbouwwerk(String lod2geometrieoverigbouwwerk) {
        this.lod2geometrieoverigbouwwerk = lod2geometrieoverigbouwwerk;
    }

    public String getLod3geometrieoverigbouwwerk() {
        return this.lod3geometrieoverigbouwwerk;
    }

    public Overigbouwwerk lod3geometrieoverigbouwwerk(String lod3geometrieoverigbouwwerk) {
        this.setLod3geometrieoverigbouwwerk(lod3geometrieoverigbouwwerk);
        return this;
    }

    public void setLod3geometrieoverigbouwwerk(String lod3geometrieoverigbouwwerk) {
        this.lod3geometrieoverigbouwwerk = lod3geometrieoverigbouwwerk;
    }

    public String getRelatievehoogteliggingoverigbouwwerk() {
        return this.relatievehoogteliggingoverigbouwwerk;
    }

    public Overigbouwwerk relatievehoogteliggingoverigbouwwerk(String relatievehoogteliggingoverigbouwwerk) {
        this.setRelatievehoogteliggingoverigbouwwerk(relatievehoogteliggingoverigbouwwerk);
        return this;
    }

    public void setRelatievehoogteliggingoverigbouwwerk(String relatievehoogteliggingoverigbouwwerk) {
        this.relatievehoogteliggingoverigbouwwerk = relatievehoogteliggingoverigbouwwerk;
    }

    public String getStatusoverigbouwwerk() {
        return this.statusoverigbouwwerk;
    }

    public Overigbouwwerk statusoverigbouwwerk(String statusoverigbouwwerk) {
        this.setStatusoverigbouwwerk(statusoverigbouwwerk);
        return this;
    }

    public void setStatusoverigbouwwerk(String statusoverigbouwwerk) {
        this.statusoverigbouwwerk = statusoverigbouwwerk;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Overigbouwwerk)) {
            return false;
        }
        return getId() != null && getId().equals(((Overigbouwwerk) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Overigbouwwerk{" +
            "id=" + getId() +
            ", datumbegingeldigheidoverigbouwwerk='" + getDatumbegingeldigheidoverigbouwwerk() + "'" +
            ", datumeindegeldigheidoverigbouwwerk='" + getDatumeindegeldigheidoverigbouwwerk() + "'" +
            ", geometrieoverigbouwwerk='" + getGeometrieoverigbouwwerk() + "'" +
            ", identificatieoverigbouwwerk='" + getIdentificatieoverigbouwwerk() + "'" +
            ", lod0geometrieoverigbouwwerk='" + getLod0geometrieoverigbouwwerk() + "'" +
            ", lod1geometrieoverigbouwwerk='" + getLod1geometrieoverigbouwwerk() + "'" +
            ", lod2geometrieoverigbouwwerk='" + getLod2geometrieoverigbouwwerk() + "'" +
            ", lod3geometrieoverigbouwwerk='" + getLod3geometrieoverigbouwwerk() + "'" +
            ", relatievehoogteliggingoverigbouwwerk='" + getRelatievehoogteliggingoverigbouwwerk() + "'" +
            ", statusoverigbouwwerk='" + getStatusoverigbouwwerk() + "'" +
            "}";
    }
}
