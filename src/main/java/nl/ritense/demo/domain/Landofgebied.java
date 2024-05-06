package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Landofgebied.
 */
@Entity
@Table(name = "landofgebied")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Landofgebied implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumeindeland")
    private LocalDate datumeindeland;

    @Column(name = "datumingangland")
    private LocalDate datumingangland;

    @Column(name = "landcode")
    private String landcode;

    @Column(name = "landcodeiso")
    private String landcodeiso;

    @Column(name = "landnaam")
    private String landnaam;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Landofgebied id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumeindeland() {
        return this.datumeindeland;
    }

    public Landofgebied datumeindeland(LocalDate datumeindeland) {
        this.setDatumeindeland(datumeindeland);
        return this;
    }

    public void setDatumeindeland(LocalDate datumeindeland) {
        this.datumeindeland = datumeindeland;
    }

    public LocalDate getDatumingangland() {
        return this.datumingangland;
    }

    public Landofgebied datumingangland(LocalDate datumingangland) {
        this.setDatumingangland(datumingangland);
        return this;
    }

    public void setDatumingangland(LocalDate datumingangland) {
        this.datumingangland = datumingangland;
    }

    public String getLandcode() {
        return this.landcode;
    }

    public Landofgebied landcode(String landcode) {
        this.setLandcode(landcode);
        return this;
    }

    public void setLandcode(String landcode) {
        this.landcode = landcode;
    }

    public String getLandcodeiso() {
        return this.landcodeiso;
    }

    public Landofgebied landcodeiso(String landcodeiso) {
        this.setLandcodeiso(landcodeiso);
        return this;
    }

    public void setLandcodeiso(String landcodeiso) {
        this.landcodeiso = landcodeiso;
    }

    public String getLandnaam() {
        return this.landnaam;
    }

    public Landofgebied landnaam(String landnaam) {
        this.setLandnaam(landnaam);
        return this;
    }

    public void setLandnaam(String landnaam) {
        this.landnaam = landnaam;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Landofgebied)) {
            return false;
        }
        return getId() != null && getId().equals(((Landofgebied) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Landofgebied{" +
            "id=" + getId() +
            ", datumeindeland='" + getDatumeindeland() + "'" +
            ", datumingangland='" + getDatumingangland() + "'" +
            ", landcode='" + getLandcode() + "'" +
            ", landcodeiso='" + getLandcodeiso() + "'" +
            ", landnaam='" + getLandnaam() + "'" +
            "}";
    }
}
