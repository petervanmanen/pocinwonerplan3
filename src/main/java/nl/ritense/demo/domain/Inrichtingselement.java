package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Inrichtingselement.
 */
@Entity
@Table(name = "inrichtingselement")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Inrichtingselement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheidinrichtingselement")
    private LocalDate datumbegingeldigheidinrichtingselement;

    @Column(name = "datumeindegeldigheidinrichtingselement")
    private LocalDate datumeindegeldigheidinrichtingselement;

    @Column(name = "geometrieinrichtingselement")
    private String geometrieinrichtingselement;

    @Column(name = "identificatieinrichtingselement")
    private String identificatieinrichtingselement;

    @Column(name = "lod_0_geometrieinrichtingselement")
    private String lod0geometrieinrichtingselement;

    @Column(name = "plustypeinrichtingselement")
    private String plustypeinrichtingselement;

    @Column(name = "relatievehoogteligginginrichtingselement")
    private String relatievehoogteligginginrichtingselement;

    @Column(name = "statusinrichtingselement")
    private String statusinrichtingselement;

    @Column(name = "typeinrichtingselement")
    private String typeinrichtingselement;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Inrichtingselement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumbegingeldigheidinrichtingselement() {
        return this.datumbegingeldigheidinrichtingselement;
    }

    public Inrichtingselement datumbegingeldigheidinrichtingselement(LocalDate datumbegingeldigheidinrichtingselement) {
        this.setDatumbegingeldigheidinrichtingselement(datumbegingeldigheidinrichtingselement);
        return this;
    }

    public void setDatumbegingeldigheidinrichtingselement(LocalDate datumbegingeldigheidinrichtingselement) {
        this.datumbegingeldigheidinrichtingselement = datumbegingeldigheidinrichtingselement;
    }

    public LocalDate getDatumeindegeldigheidinrichtingselement() {
        return this.datumeindegeldigheidinrichtingselement;
    }

    public Inrichtingselement datumeindegeldigheidinrichtingselement(LocalDate datumeindegeldigheidinrichtingselement) {
        this.setDatumeindegeldigheidinrichtingselement(datumeindegeldigheidinrichtingselement);
        return this;
    }

    public void setDatumeindegeldigheidinrichtingselement(LocalDate datumeindegeldigheidinrichtingselement) {
        this.datumeindegeldigheidinrichtingselement = datumeindegeldigheidinrichtingselement;
    }

    public String getGeometrieinrichtingselement() {
        return this.geometrieinrichtingselement;
    }

    public Inrichtingselement geometrieinrichtingselement(String geometrieinrichtingselement) {
        this.setGeometrieinrichtingselement(geometrieinrichtingselement);
        return this;
    }

    public void setGeometrieinrichtingselement(String geometrieinrichtingselement) {
        this.geometrieinrichtingselement = geometrieinrichtingselement;
    }

    public String getIdentificatieinrichtingselement() {
        return this.identificatieinrichtingselement;
    }

    public Inrichtingselement identificatieinrichtingselement(String identificatieinrichtingselement) {
        this.setIdentificatieinrichtingselement(identificatieinrichtingselement);
        return this;
    }

    public void setIdentificatieinrichtingselement(String identificatieinrichtingselement) {
        this.identificatieinrichtingselement = identificatieinrichtingselement;
    }

    public String getLod0geometrieinrichtingselement() {
        return this.lod0geometrieinrichtingselement;
    }

    public Inrichtingselement lod0geometrieinrichtingselement(String lod0geometrieinrichtingselement) {
        this.setLod0geometrieinrichtingselement(lod0geometrieinrichtingselement);
        return this;
    }

    public void setLod0geometrieinrichtingselement(String lod0geometrieinrichtingselement) {
        this.lod0geometrieinrichtingselement = lod0geometrieinrichtingselement;
    }

    public String getPlustypeinrichtingselement() {
        return this.plustypeinrichtingselement;
    }

    public Inrichtingselement plustypeinrichtingselement(String plustypeinrichtingselement) {
        this.setPlustypeinrichtingselement(plustypeinrichtingselement);
        return this;
    }

    public void setPlustypeinrichtingselement(String plustypeinrichtingselement) {
        this.plustypeinrichtingselement = plustypeinrichtingselement;
    }

    public String getRelatievehoogteligginginrichtingselement() {
        return this.relatievehoogteligginginrichtingselement;
    }

    public Inrichtingselement relatievehoogteligginginrichtingselement(String relatievehoogteligginginrichtingselement) {
        this.setRelatievehoogteligginginrichtingselement(relatievehoogteligginginrichtingselement);
        return this;
    }

    public void setRelatievehoogteligginginrichtingselement(String relatievehoogteligginginrichtingselement) {
        this.relatievehoogteligginginrichtingselement = relatievehoogteligginginrichtingselement;
    }

    public String getStatusinrichtingselement() {
        return this.statusinrichtingselement;
    }

    public Inrichtingselement statusinrichtingselement(String statusinrichtingselement) {
        this.setStatusinrichtingselement(statusinrichtingselement);
        return this;
    }

    public void setStatusinrichtingselement(String statusinrichtingselement) {
        this.statusinrichtingselement = statusinrichtingselement;
    }

    public String getTypeinrichtingselement() {
        return this.typeinrichtingselement;
    }

    public Inrichtingselement typeinrichtingselement(String typeinrichtingselement) {
        this.setTypeinrichtingselement(typeinrichtingselement);
        return this;
    }

    public void setTypeinrichtingselement(String typeinrichtingselement) {
        this.typeinrichtingselement = typeinrichtingselement;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inrichtingselement)) {
            return false;
        }
        return getId() != null && getId().equals(((Inrichtingselement) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inrichtingselement{" +
            "id=" + getId() +
            ", datumbegingeldigheidinrichtingselement='" + getDatumbegingeldigheidinrichtingselement() + "'" +
            ", datumeindegeldigheidinrichtingselement='" + getDatumeindegeldigheidinrichtingselement() + "'" +
            ", geometrieinrichtingselement='" + getGeometrieinrichtingselement() + "'" +
            ", identificatieinrichtingselement='" + getIdentificatieinrichtingselement() + "'" +
            ", lod0geometrieinrichtingselement='" + getLod0geometrieinrichtingselement() + "'" +
            ", plustypeinrichtingselement='" + getPlustypeinrichtingselement() + "'" +
            ", relatievehoogteligginginrichtingselement='" + getRelatievehoogteligginginrichtingselement() + "'" +
            ", statusinrichtingselement='" + getStatusinrichtingselement() + "'" +
            ", typeinrichtingselement='" + getTypeinrichtingselement() + "'" +
            "}";
    }
}
