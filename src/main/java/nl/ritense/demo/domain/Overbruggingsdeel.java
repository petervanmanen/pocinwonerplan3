package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Overbruggingsdeel.
 */
@Entity
@Table(name = "overbruggingsdeel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Overbruggingsdeel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheidoverbruggingsdeel")
    private LocalDate datumbegingeldigheidoverbruggingsdeel;

    @Column(name = "datumeindegeldigheidoverbruggingsdeel")
    private LocalDate datumeindegeldigheidoverbruggingsdeel;

    @Column(name = "geometrieoverbruggingsdeel")
    private String geometrieoverbruggingsdeel;

    @Column(name = "hoortbijtypeoverbrugging")
    private String hoortbijtypeoverbrugging;

    @Column(name = "identificatieoverbruggingsdeel")
    private String identificatieoverbruggingsdeel;

    @Column(name = "lod_0_geometrieoverbruggingsdeel")
    private String lod0geometrieoverbruggingsdeel;

    @Column(name = "overbruggingisbeweegbaar")
    private String overbruggingisbeweegbaar;

    @Column(name = "relatievehoogteliggingoverbruggingsdeel")
    private String relatievehoogteliggingoverbruggingsdeel;

    @Column(name = "statusoverbruggingsdeel")
    private String statusoverbruggingsdeel;

    @Column(name = "typeoverbruggingsdeel")
    private String typeoverbruggingsdeel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Overbruggingsdeel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbegingeldigheidoverbruggingsdeel() {
        return this.datumbegingeldigheidoverbruggingsdeel;
    }

    public Overbruggingsdeel datumbegingeldigheidoverbruggingsdeel(LocalDate datumbegingeldigheidoverbruggingsdeel) {
        this.setDatumbegingeldigheidoverbruggingsdeel(datumbegingeldigheidoverbruggingsdeel);
        return this;
    }

    public void setDatumbegingeldigheidoverbruggingsdeel(LocalDate datumbegingeldigheidoverbruggingsdeel) {
        this.datumbegingeldigheidoverbruggingsdeel = datumbegingeldigheidoverbruggingsdeel;
    }

    public LocalDate getDatumeindegeldigheidoverbruggingsdeel() {
        return this.datumeindegeldigheidoverbruggingsdeel;
    }

    public Overbruggingsdeel datumeindegeldigheidoverbruggingsdeel(LocalDate datumeindegeldigheidoverbruggingsdeel) {
        this.setDatumeindegeldigheidoverbruggingsdeel(datumeindegeldigheidoverbruggingsdeel);
        return this;
    }

    public void setDatumeindegeldigheidoverbruggingsdeel(LocalDate datumeindegeldigheidoverbruggingsdeel) {
        this.datumeindegeldigheidoverbruggingsdeel = datumeindegeldigheidoverbruggingsdeel;
    }

    public String getGeometrieoverbruggingsdeel() {
        return this.geometrieoverbruggingsdeel;
    }

    public Overbruggingsdeel geometrieoverbruggingsdeel(String geometrieoverbruggingsdeel) {
        this.setGeometrieoverbruggingsdeel(geometrieoverbruggingsdeel);
        return this;
    }

    public void setGeometrieoverbruggingsdeel(String geometrieoverbruggingsdeel) {
        this.geometrieoverbruggingsdeel = geometrieoverbruggingsdeel;
    }

    public String getHoortbijtypeoverbrugging() {
        return this.hoortbijtypeoverbrugging;
    }

    public Overbruggingsdeel hoortbijtypeoverbrugging(String hoortbijtypeoverbrugging) {
        this.setHoortbijtypeoverbrugging(hoortbijtypeoverbrugging);
        return this;
    }

    public void setHoortbijtypeoverbrugging(String hoortbijtypeoverbrugging) {
        this.hoortbijtypeoverbrugging = hoortbijtypeoverbrugging;
    }

    public String getIdentificatieoverbruggingsdeel() {
        return this.identificatieoverbruggingsdeel;
    }

    public Overbruggingsdeel identificatieoverbruggingsdeel(String identificatieoverbruggingsdeel) {
        this.setIdentificatieoverbruggingsdeel(identificatieoverbruggingsdeel);
        return this;
    }

    public void setIdentificatieoverbruggingsdeel(String identificatieoverbruggingsdeel) {
        this.identificatieoverbruggingsdeel = identificatieoverbruggingsdeel;
    }

    public String getLod0geometrieoverbruggingsdeel() {
        return this.lod0geometrieoverbruggingsdeel;
    }

    public Overbruggingsdeel lod0geometrieoverbruggingsdeel(String lod0geometrieoverbruggingsdeel) {
        this.setLod0geometrieoverbruggingsdeel(lod0geometrieoverbruggingsdeel);
        return this;
    }

    public void setLod0geometrieoverbruggingsdeel(String lod0geometrieoverbruggingsdeel) {
        this.lod0geometrieoverbruggingsdeel = lod0geometrieoverbruggingsdeel;
    }

    public String getOverbruggingisbeweegbaar() {
        return this.overbruggingisbeweegbaar;
    }

    public Overbruggingsdeel overbruggingisbeweegbaar(String overbruggingisbeweegbaar) {
        this.setOverbruggingisbeweegbaar(overbruggingisbeweegbaar);
        return this;
    }

    public void setOverbruggingisbeweegbaar(String overbruggingisbeweegbaar) {
        this.overbruggingisbeweegbaar = overbruggingisbeweegbaar;
    }

    public String getRelatievehoogteliggingoverbruggingsdeel() {
        return this.relatievehoogteliggingoverbruggingsdeel;
    }

    public Overbruggingsdeel relatievehoogteliggingoverbruggingsdeel(String relatievehoogteliggingoverbruggingsdeel) {
        this.setRelatievehoogteliggingoverbruggingsdeel(relatievehoogteliggingoverbruggingsdeel);
        return this;
    }

    public void setRelatievehoogteliggingoverbruggingsdeel(String relatievehoogteliggingoverbruggingsdeel) {
        this.relatievehoogteliggingoverbruggingsdeel = relatievehoogteliggingoverbruggingsdeel;
    }

    public String getStatusoverbruggingsdeel() {
        return this.statusoverbruggingsdeel;
    }

    public Overbruggingsdeel statusoverbruggingsdeel(String statusoverbruggingsdeel) {
        this.setStatusoverbruggingsdeel(statusoverbruggingsdeel);
        return this;
    }

    public void setStatusoverbruggingsdeel(String statusoverbruggingsdeel) {
        this.statusoverbruggingsdeel = statusoverbruggingsdeel;
    }

    public String getTypeoverbruggingsdeel() {
        return this.typeoverbruggingsdeel;
    }

    public Overbruggingsdeel typeoverbruggingsdeel(String typeoverbruggingsdeel) {
        this.setTypeoverbruggingsdeel(typeoverbruggingsdeel);
        return this;
    }

    public void setTypeoverbruggingsdeel(String typeoverbruggingsdeel) {
        this.typeoverbruggingsdeel = typeoverbruggingsdeel;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Overbruggingsdeel)) {
            return false;
        }
        return getId() != null && getId().equals(((Overbruggingsdeel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Overbruggingsdeel{" +
            "id=" + getId() +
            ", datumbegingeldigheidoverbruggingsdeel='" + getDatumbegingeldigheidoverbruggingsdeel() + "'" +
            ", datumeindegeldigheidoverbruggingsdeel='" + getDatumeindegeldigheidoverbruggingsdeel() + "'" +
            ", geometrieoverbruggingsdeel='" + getGeometrieoverbruggingsdeel() + "'" +
            ", hoortbijtypeoverbrugging='" + getHoortbijtypeoverbrugging() + "'" +
            ", identificatieoverbruggingsdeel='" + getIdentificatieoverbruggingsdeel() + "'" +
            ", lod0geometrieoverbruggingsdeel='" + getLod0geometrieoverbruggingsdeel() + "'" +
            ", overbruggingisbeweegbaar='" + getOverbruggingisbeweegbaar() + "'" +
            ", relatievehoogteliggingoverbruggingsdeel='" + getRelatievehoogteliggingoverbruggingsdeel() + "'" +
            ", statusoverbruggingsdeel='" + getStatusoverbruggingsdeel() + "'" +
            ", typeoverbruggingsdeel='" + getTypeoverbruggingsdeel() + "'" +
            "}";
    }
}
