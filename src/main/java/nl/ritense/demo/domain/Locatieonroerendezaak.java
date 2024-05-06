package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Locatieonroerendezaak.
 */
@Entity
@Table(name = "locatieonroerendezaak")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Locatieonroerendezaak implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "adrestype")
    private String adrestype;

    @Column(name = "cultuurcodebebouwd")
    private String cultuurcodebebouwd;

    @Column(name = "datumbegingeldigheid")
    private LocalDate datumbegingeldigheid;

    @Column(name = "datumeindegeldigheid")
    private LocalDate datumeindegeldigheid;

    @Column(name = "geometrie")
    private String geometrie;

    @Column(name = "locatieomschrijving")
    private String locatieomschrijving;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Locatieonroerendezaak id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdrestype() {
        return this.adrestype;
    }

    public Locatieonroerendezaak adrestype(String adrestype) {
        this.setAdrestype(adrestype);
        return this;
    }

    public void setAdrestype(String adrestype) {
        this.adrestype = adrestype;
    }

    public String getCultuurcodebebouwd() {
        return this.cultuurcodebebouwd;
    }

    public Locatieonroerendezaak cultuurcodebebouwd(String cultuurcodebebouwd) {
        this.setCultuurcodebebouwd(cultuurcodebebouwd);
        return this;
    }

    public void setCultuurcodebebouwd(String cultuurcodebebouwd) {
        this.cultuurcodebebouwd = cultuurcodebebouwd;
    }

    public LocalDate getDatumbegingeldigheid() {
        return this.datumbegingeldigheid;
    }

    public Locatieonroerendezaak datumbegingeldigheid(LocalDate datumbegingeldigheid) {
        this.setDatumbegingeldigheid(datumbegingeldigheid);
        return this;
    }

    public void setDatumbegingeldigheid(LocalDate datumbegingeldigheid) {
        this.datumbegingeldigheid = datumbegingeldigheid;
    }

    public LocalDate getDatumeindegeldigheid() {
        return this.datumeindegeldigheid;
    }

    public Locatieonroerendezaak datumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.setDatumeindegeldigheid(datumeindegeldigheid);
        return this;
    }

    public void setDatumeindegeldigheid(LocalDate datumeindegeldigheid) {
        this.datumeindegeldigheid = datumeindegeldigheid;
    }

    public String getGeometrie() {
        return this.geometrie;
    }

    public Locatieonroerendezaak geometrie(String geometrie) {
        this.setGeometrie(geometrie);
        return this;
    }

    public void setGeometrie(String geometrie) {
        this.geometrie = geometrie;
    }

    public String getLocatieomschrijving() {
        return this.locatieomschrijving;
    }

    public Locatieonroerendezaak locatieomschrijving(String locatieomschrijving) {
        this.setLocatieomschrijving(locatieomschrijving);
        return this;
    }

    public void setLocatieomschrijving(String locatieomschrijving) {
        this.locatieomschrijving = locatieomschrijving;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Locatieonroerendezaak)) {
            return false;
        }
        return getId() != null && getId().equals(((Locatieonroerendezaak) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Locatieonroerendezaak{" +
            "id=" + getId() +
            ", adrestype='" + getAdrestype() + "'" +
            ", cultuurcodebebouwd='" + getCultuurcodebebouwd() + "'" +
            ", datumbegingeldigheid='" + getDatumbegingeldigheid() + "'" +
            ", datumeindegeldigheid='" + getDatumeindegeldigheid() + "'" +
            ", geometrie='" + getGeometrie() + "'" +
            ", locatieomschrijving='" + getLocatieomschrijving() + "'" +
            "}";
    }
}
