package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Aanduidingverblijfsrecht.
 */
@Entity
@Table(name = "aanduidingverblijfsrecht")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Aanduidingverblijfsrecht implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumaanvanggeldigheidverblijfsrecht")
    private LocalDate datumaanvanggeldigheidverblijfsrecht;

    @Column(name = "datumeindegeldigheidverblijfsrecht")
    private LocalDate datumeindegeldigheidverblijfsrecht;

    @Column(name = "verblijfsrechtnummer")
    private String verblijfsrechtnummer;

    @Column(name = "verblijfsrechtomschrijving")
    private String verblijfsrechtomschrijving;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Aanduidingverblijfsrecht id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumaanvanggeldigheidverblijfsrecht() {
        return this.datumaanvanggeldigheidverblijfsrecht;
    }

    public Aanduidingverblijfsrecht datumaanvanggeldigheidverblijfsrecht(LocalDate datumaanvanggeldigheidverblijfsrecht) {
        this.setDatumaanvanggeldigheidverblijfsrecht(datumaanvanggeldigheidverblijfsrecht);
        return this;
    }

    public void setDatumaanvanggeldigheidverblijfsrecht(LocalDate datumaanvanggeldigheidverblijfsrecht) {
        this.datumaanvanggeldigheidverblijfsrecht = datumaanvanggeldigheidverblijfsrecht;
    }

    public LocalDate getDatumeindegeldigheidverblijfsrecht() {
        return this.datumeindegeldigheidverblijfsrecht;
    }

    public Aanduidingverblijfsrecht datumeindegeldigheidverblijfsrecht(LocalDate datumeindegeldigheidverblijfsrecht) {
        this.setDatumeindegeldigheidverblijfsrecht(datumeindegeldigheidverblijfsrecht);
        return this;
    }

    public void setDatumeindegeldigheidverblijfsrecht(LocalDate datumeindegeldigheidverblijfsrecht) {
        this.datumeindegeldigheidverblijfsrecht = datumeindegeldigheidverblijfsrecht;
    }

    public String getVerblijfsrechtnummer() {
        return this.verblijfsrechtnummer;
    }

    public Aanduidingverblijfsrecht verblijfsrechtnummer(String verblijfsrechtnummer) {
        this.setVerblijfsrechtnummer(verblijfsrechtnummer);
        return this;
    }

    public void setVerblijfsrechtnummer(String verblijfsrechtnummer) {
        this.verblijfsrechtnummer = verblijfsrechtnummer;
    }

    public String getVerblijfsrechtomschrijving() {
        return this.verblijfsrechtomschrijving;
    }

    public Aanduidingverblijfsrecht verblijfsrechtomschrijving(String verblijfsrechtomschrijving) {
        this.setVerblijfsrechtomschrijving(verblijfsrechtomschrijving);
        return this;
    }

    public void setVerblijfsrechtomschrijving(String verblijfsrechtomschrijving) {
        this.verblijfsrechtomschrijving = verblijfsrechtomschrijving;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Aanduidingverblijfsrecht)) {
            return false;
        }
        return getId() != null && getId().equals(((Aanduidingverblijfsrecht) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Aanduidingverblijfsrecht{" +
            "id=" + getId() +
            ", datumaanvanggeldigheidverblijfsrecht='" + getDatumaanvanggeldigheidverblijfsrecht() + "'" +
            ", datumeindegeldigheidverblijfsrecht='" + getDatumeindegeldigheidverblijfsrecht() + "'" +
            ", verblijfsrechtnummer='" + getVerblijfsrechtnummer() + "'" +
            ", verblijfsrechtomschrijving='" + getVerblijfsrechtomschrijving() + "'" +
            "}";
    }
}
