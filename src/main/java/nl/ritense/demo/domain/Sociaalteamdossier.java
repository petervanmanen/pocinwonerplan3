package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Sociaalteamdossier.
 */
@Entity
@Table(name = "sociaalteamdossier")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Sociaalteamdossier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumeinde")
    private String datumeinde;

    @Column(name = "datumstart")
    private String datumstart;

    @Column(name = "datumvaststelling")
    private String datumvaststelling;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Size(max = 20)
    @Column(name = "status", length = 20)
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Sociaalteamdossier id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatumeinde() {
        return this.datumeinde;
    }

    public Sociaalteamdossier datumeinde(String datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(String datumeinde) {
        this.datumeinde = datumeinde;
    }

    public String getDatumstart() {
        return this.datumstart;
    }

    public Sociaalteamdossier datumstart(String datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(String datumstart) {
        this.datumstart = datumstart;
    }

    public String getDatumvaststelling() {
        return this.datumvaststelling;
    }

    public Sociaalteamdossier datumvaststelling(String datumvaststelling) {
        this.setDatumvaststelling(datumvaststelling);
        return this;
    }

    public void setDatumvaststelling(String datumvaststelling) {
        this.datumvaststelling = datumvaststelling;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Sociaalteamdossier omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getStatus() {
        return this.status;
    }

    public Sociaalteamdossier status(String status) {
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
        if (!(o instanceof Sociaalteamdossier)) {
            return false;
        }
        return getId() != null && getId().equals(((Sociaalteamdossier) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sociaalteamdossier{" +
            "id=" + getId() +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", datumvaststelling='" + getDatumvaststelling() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
