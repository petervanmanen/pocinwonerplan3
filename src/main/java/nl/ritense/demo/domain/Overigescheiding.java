package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Overigescheiding.
 */
@Entity
@Table(name = "overigescheiding")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Overigescheiding implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheidoverigescheiding")
    private LocalDate datumbegingeldigheidoverigescheiding;

    @Column(name = "datumeindegeldigheidoverigescheiding")
    private LocalDate datumeindegeldigheidoverigescheiding;

    @Column(name = "geometrieoverigescheiding")
    private String geometrieoverigescheiding;

    @Column(name = "identificatieoverigescheiding")
    private String identificatieoverigescheiding;

    @Column(name = "lod_0_geometrieoverigescheiding")
    private String lod0geometrieoverigescheiding;

    @Column(name = "lod_1_geometrieoverigescheiding")
    private String lod1geometrieoverigescheiding;

    @Column(name = "lod_2_geometrieoverigescheiding")
    private String lod2geometrieoverigescheiding;

    @Column(name = "lod_3_geometrieoverigescheiding")
    private String lod3geometrieoverigescheiding;

    @Column(name = "relatievehoogteliggingoverigescheiding")
    private String relatievehoogteliggingoverigescheiding;

    @Column(name = "statusoverigescheiding")
    private String statusoverigescheiding;

    @Column(name = "typeoverigescheiding")
    private String typeoverigescheiding;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Overigescheiding id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbegingeldigheidoverigescheiding() {
        return this.datumbegingeldigheidoverigescheiding;
    }

    public Overigescheiding datumbegingeldigheidoverigescheiding(LocalDate datumbegingeldigheidoverigescheiding) {
        this.setDatumbegingeldigheidoverigescheiding(datumbegingeldigheidoverigescheiding);
        return this;
    }

    public void setDatumbegingeldigheidoverigescheiding(LocalDate datumbegingeldigheidoverigescheiding) {
        this.datumbegingeldigheidoverigescheiding = datumbegingeldigheidoverigescheiding;
    }

    public LocalDate getDatumeindegeldigheidoverigescheiding() {
        return this.datumeindegeldigheidoverigescheiding;
    }

    public Overigescheiding datumeindegeldigheidoverigescheiding(LocalDate datumeindegeldigheidoverigescheiding) {
        this.setDatumeindegeldigheidoverigescheiding(datumeindegeldigheidoverigescheiding);
        return this;
    }

    public void setDatumeindegeldigheidoverigescheiding(LocalDate datumeindegeldigheidoverigescheiding) {
        this.datumeindegeldigheidoverigescheiding = datumeindegeldigheidoverigescheiding;
    }

    public String getGeometrieoverigescheiding() {
        return this.geometrieoverigescheiding;
    }

    public Overigescheiding geometrieoverigescheiding(String geometrieoverigescheiding) {
        this.setGeometrieoverigescheiding(geometrieoverigescheiding);
        return this;
    }

    public void setGeometrieoverigescheiding(String geometrieoverigescheiding) {
        this.geometrieoverigescheiding = geometrieoverigescheiding;
    }

    public String getIdentificatieoverigescheiding() {
        return this.identificatieoverigescheiding;
    }

    public Overigescheiding identificatieoverigescheiding(String identificatieoverigescheiding) {
        this.setIdentificatieoverigescheiding(identificatieoverigescheiding);
        return this;
    }

    public void setIdentificatieoverigescheiding(String identificatieoverigescheiding) {
        this.identificatieoverigescheiding = identificatieoverigescheiding;
    }

    public String getLod0geometrieoverigescheiding() {
        return this.lod0geometrieoverigescheiding;
    }

    public Overigescheiding lod0geometrieoverigescheiding(String lod0geometrieoverigescheiding) {
        this.setLod0geometrieoverigescheiding(lod0geometrieoverigescheiding);
        return this;
    }

    public void setLod0geometrieoverigescheiding(String lod0geometrieoverigescheiding) {
        this.lod0geometrieoverigescheiding = lod0geometrieoverigescheiding;
    }

    public String getLod1geometrieoverigescheiding() {
        return this.lod1geometrieoverigescheiding;
    }

    public Overigescheiding lod1geometrieoverigescheiding(String lod1geometrieoverigescheiding) {
        this.setLod1geometrieoverigescheiding(lod1geometrieoverigescheiding);
        return this;
    }

    public void setLod1geometrieoverigescheiding(String lod1geometrieoverigescheiding) {
        this.lod1geometrieoverigescheiding = lod1geometrieoverigescheiding;
    }

    public String getLod2geometrieoverigescheiding() {
        return this.lod2geometrieoverigescheiding;
    }

    public Overigescheiding lod2geometrieoverigescheiding(String lod2geometrieoverigescheiding) {
        this.setLod2geometrieoverigescheiding(lod2geometrieoverigescheiding);
        return this;
    }

    public void setLod2geometrieoverigescheiding(String lod2geometrieoverigescheiding) {
        this.lod2geometrieoverigescheiding = lod2geometrieoverigescheiding;
    }

    public String getLod3geometrieoverigescheiding() {
        return this.lod3geometrieoverigescheiding;
    }

    public Overigescheiding lod3geometrieoverigescheiding(String lod3geometrieoverigescheiding) {
        this.setLod3geometrieoverigescheiding(lod3geometrieoverigescheiding);
        return this;
    }

    public void setLod3geometrieoverigescheiding(String lod3geometrieoverigescheiding) {
        this.lod3geometrieoverigescheiding = lod3geometrieoverigescheiding;
    }

    public String getRelatievehoogteliggingoverigescheiding() {
        return this.relatievehoogteliggingoverigescheiding;
    }

    public Overigescheiding relatievehoogteliggingoverigescheiding(String relatievehoogteliggingoverigescheiding) {
        this.setRelatievehoogteliggingoverigescheiding(relatievehoogteliggingoverigescheiding);
        return this;
    }

    public void setRelatievehoogteliggingoverigescheiding(String relatievehoogteliggingoverigescheiding) {
        this.relatievehoogteliggingoverigescheiding = relatievehoogteliggingoverigescheiding;
    }

    public String getStatusoverigescheiding() {
        return this.statusoverigescheiding;
    }

    public Overigescheiding statusoverigescheiding(String statusoverigescheiding) {
        this.setStatusoverigescheiding(statusoverigescheiding);
        return this;
    }

    public void setStatusoverigescheiding(String statusoverigescheiding) {
        this.statusoverigescheiding = statusoverigescheiding;
    }

    public String getTypeoverigescheiding() {
        return this.typeoverigescheiding;
    }

    public Overigescheiding typeoverigescheiding(String typeoverigescheiding) {
        this.setTypeoverigescheiding(typeoverigescheiding);
        return this;
    }

    public void setTypeoverigescheiding(String typeoverigescheiding) {
        this.typeoverigescheiding = typeoverigescheiding;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Overigescheiding)) {
            return false;
        }
        return getId() != null && getId().equals(((Overigescheiding) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Overigescheiding{" +
            "id=" + getId() +
            ", datumbegingeldigheidoverigescheiding='" + getDatumbegingeldigheidoverigescheiding() + "'" +
            ", datumeindegeldigheidoverigescheiding='" + getDatumeindegeldigheidoverigescheiding() + "'" +
            ", geometrieoverigescheiding='" + getGeometrieoverigescheiding() + "'" +
            ", identificatieoverigescheiding='" + getIdentificatieoverigescheiding() + "'" +
            ", lod0geometrieoverigescheiding='" + getLod0geometrieoverigescheiding() + "'" +
            ", lod1geometrieoverigescheiding='" + getLod1geometrieoverigescheiding() + "'" +
            ", lod2geometrieoverigescheiding='" + getLod2geometrieoverigescheiding() + "'" +
            ", lod3geometrieoverigescheiding='" + getLod3geometrieoverigescheiding() + "'" +
            ", relatievehoogteliggingoverigescheiding='" + getRelatievehoogteliggingoverigescheiding() + "'" +
            ", statusoverigescheiding='" + getStatusoverigescheiding() + "'" +
            ", typeoverigescheiding='" + getTypeoverigescheiding() + "'" +
            "}";
    }
}
