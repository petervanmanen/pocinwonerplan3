package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Redenverliesnationaliteit.
 */
@Entity
@Table(name = "redenverliesnationaliteit")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Redenverliesnationaliteit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datumaanvanggeldigheidverlies")
    private LocalDate datumaanvanggeldigheidverlies;

    @Column(name = "datumeindegeldigheidverlies")
    private LocalDate datumeindegeldigheidverlies;

    @Column(name = "omschrijvingverlies")
    private String omschrijvingverlies;

    @Column(name = "redennummerverlies")
    private String redennummerverlies;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Redenverliesnationaliteit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDatumaanvanggeldigheidverlies() {
        return this.datumaanvanggeldigheidverlies;
    }

    public Redenverliesnationaliteit datumaanvanggeldigheidverlies(LocalDate datumaanvanggeldigheidverlies) {
        this.setDatumaanvanggeldigheidverlies(datumaanvanggeldigheidverlies);
        return this;
    }

    public void setDatumaanvanggeldigheidverlies(LocalDate datumaanvanggeldigheidverlies) {
        this.datumaanvanggeldigheidverlies = datumaanvanggeldigheidverlies;
    }

    public LocalDate getDatumeindegeldigheidverlies() {
        return this.datumeindegeldigheidverlies;
    }

    public Redenverliesnationaliteit datumeindegeldigheidverlies(LocalDate datumeindegeldigheidverlies) {
        this.setDatumeindegeldigheidverlies(datumeindegeldigheidverlies);
        return this;
    }

    public void setDatumeindegeldigheidverlies(LocalDate datumeindegeldigheidverlies) {
        this.datumeindegeldigheidverlies = datumeindegeldigheidverlies;
    }

    public String getOmschrijvingverlies() {
        return this.omschrijvingverlies;
    }

    public Redenverliesnationaliteit omschrijvingverlies(String omschrijvingverlies) {
        this.setOmschrijvingverlies(omschrijvingverlies);
        return this;
    }

    public void setOmschrijvingverlies(String omschrijvingverlies) {
        this.omschrijvingverlies = omschrijvingverlies;
    }

    public String getRedennummerverlies() {
        return this.redennummerverlies;
    }

    public Redenverliesnationaliteit redennummerverlies(String redennummerverlies) {
        this.setRedennummerverlies(redennummerverlies);
        return this;
    }

    public void setRedennummerverlies(String redennummerverlies) {
        this.redennummerverlies = redennummerverlies;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Redenverliesnationaliteit)) {
            return false;
        }
        return getId() != null && getId().equals(((Redenverliesnationaliteit) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Redenverliesnationaliteit{" +
            "id=" + getId() +
            ", datumaanvanggeldigheidverlies='" + getDatumaanvanggeldigheidverlies() + "'" +
            ", datumeindegeldigheidverlies='" + getDatumeindegeldigheidverlies() + "'" +
            ", omschrijvingverlies='" + getOmschrijvingverlies() + "'" +
            ", redennummerverlies='" + getRedennummerverlies() + "'" +
            "}";
    }
}
