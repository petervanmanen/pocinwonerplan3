package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Zelfredzaamheidmatrix.
 */
@Entity
@Table(name = "zelfredzaamheidmatrix")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Zelfredzaamheidmatrix implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumeindegeldigheid")
    private String datumeindegeldigheid;

    @Column(name = "datumstartgeldigheid")
    private String datumstartgeldigheid;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Zelfredzaamheidmatrix id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatumeindegeldigheid() {
        return this.datumeindegeldigheid;
    }

    public Zelfredzaamheidmatrix datumeindegeldigheid(String datumeindegeldigheid) {
        this.setDatumeindegeldigheid(datumeindegeldigheid);
        return this;
    }

    public void setDatumeindegeldigheid(String datumeindegeldigheid) {
        this.datumeindegeldigheid = datumeindegeldigheid;
    }

    public String getDatumstartgeldigheid() {
        return this.datumstartgeldigheid;
    }

    public Zelfredzaamheidmatrix datumstartgeldigheid(String datumstartgeldigheid) {
        this.setDatumstartgeldigheid(datumstartgeldigheid);
        return this;
    }

    public void setDatumstartgeldigheid(String datumstartgeldigheid) {
        this.datumstartgeldigheid = datumstartgeldigheid;
    }

    public String getNaam() {
        return this.naam;
    }

    public Zelfredzaamheidmatrix naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Zelfredzaamheidmatrix omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Zelfredzaamheidmatrix)) {
            return false;
        }
        return getId() != null && getId().equals(((Zelfredzaamheidmatrix) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Zelfredzaamheidmatrix{" +
            "id=" + getId() +
            ", datumeindegeldigheid='" + getDatumeindegeldigheid() + "'" +
            ", datumstartgeldigheid='" + getDatumstartgeldigheid() + "'" +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
