package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Openbareactiviteit.
 */
@Entity
@Table(name = "openbareactiviteit")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Openbareactiviteit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumeinde")
    private LocalDate datumeinde;

    @Column(name = "datumstart")
    private LocalDate datumstart;

    @Column(name = "evenmentnaam")
    private String evenmentnaam;

    @Column(name = "locatieomschrijving")
    private String locatieomschrijving;

    @Column(name = "status")
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Openbareactiviteit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumeinde() {
        return this.datumeinde;
    }

    public Openbareactiviteit datumeinde(LocalDate datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(LocalDate datumeinde) {
        this.datumeinde = datumeinde;
    }

    public LocalDate getDatumstart() {
        return this.datumstart;
    }

    public Openbareactiviteit datumstart(LocalDate datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(LocalDate datumstart) {
        this.datumstart = datumstart;
    }

    public String getEvenmentnaam() {
        return this.evenmentnaam;
    }

    public Openbareactiviteit evenmentnaam(String evenmentnaam) {
        this.setEvenmentnaam(evenmentnaam);
        return this;
    }

    public void setEvenmentnaam(String evenmentnaam) {
        this.evenmentnaam = evenmentnaam;
    }

    public String getLocatieomschrijving() {
        return this.locatieomschrijving;
    }

    public Openbareactiviteit locatieomschrijving(String locatieomschrijving) {
        this.setLocatieomschrijving(locatieomschrijving);
        return this;
    }

    public void setLocatieomschrijving(String locatieomschrijving) {
        this.locatieomschrijving = locatieomschrijving;
    }

    public String getStatus() {
        return this.status;
    }

    public Openbareactiviteit status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Openbareactiviteit)) {
            return false;
        }
        return getId() != null && getId().equals(((Openbareactiviteit) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Openbareactiviteit{" +
            "id=" + getId() +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", evenmentnaam='" + getEvenmentnaam() + "'" +
            ", locatieomschrijving='" + getLocatieomschrijving() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
