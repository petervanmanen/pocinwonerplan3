package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Geoobject.
 */
@Entity
@Table(name = "geoobject")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Geoobject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumbegingeldigheid")
    private String datumbegingeldigheid;

    @Column(name = "datumeindegeldigheid")
    private String datumeindegeldigheid;

    @Column(name = "geometriesoort")
    private String geometriesoort;

    @Column(name = "identificatie")
    private String identificatie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Geoobject id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatumbegingeldigheid() {
        return this.datumbegingeldigheid;
    }

    public Geoobject datumbegingeldigheid(String datumbegingeldigheid) {
        this.setDatumbegingeldigheid(datumbegingeldigheid);
        return this;
    }

    public void setDatumbegingeldigheid(String datumbegingeldigheid) {
        this.datumbegingeldigheid = datumbegingeldigheid;
    }

    public String getDatumeindegeldigheid() {
        return this.datumeindegeldigheid;
    }

    public Geoobject datumeindegeldigheid(String datumeindegeldigheid) {
        this.setDatumeindegeldigheid(datumeindegeldigheid);
        return this;
    }

    public void setDatumeindegeldigheid(String datumeindegeldigheid) {
        this.datumeindegeldigheid = datumeindegeldigheid;
    }

    public String getGeometriesoort() {
        return this.geometriesoort;
    }

    public Geoobject geometriesoort(String geometriesoort) {
        this.setGeometriesoort(geometriesoort);
        return this;
    }

    public void setGeometriesoort(String geometriesoort) {
        this.geometriesoort = geometriesoort;
    }

    public String getIdentificatie() {
        return this.identificatie;
    }

    public Geoobject identificatie(String identificatie) {
        this.setIdentificatie(identificatie);
        return this;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Geoobject)) {
            return false;
        }
        return getId() != null && getId().equals(((Geoobject) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Geoobject{" +
            "id=" + getId() +
            ", datumbegingeldigheid='" + getDatumbegingeldigheid() + "'" +
            ", datumeindegeldigheid='" + getDatumeindegeldigheid() + "'" +
            ", geometriesoort='" + getGeometriesoort() + "'" +
            ", identificatie='" + getIdentificatie() + "'" +
            "}";
    }
}
