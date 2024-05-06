package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Naamaanschrijvingnatuurlijkpersoon.
 */
@Entity
@Table(name = "naamaanschrijvingnatuurlijkpersoon")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Naamaanschrijvingnatuurlijkpersoon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aanhefaanschrijving")
    private String aanhefaanschrijving;

    @Column(name = "geslachtsnaamaanschrijving")
    private String geslachtsnaamaanschrijving;

    @Size(max = 20)
    @Column(name = "voorlettersaanschrijving", length = 20)
    private String voorlettersaanschrijving;

    @Column(name = "voornamenaanschrijving")
    private String voornamenaanschrijving;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Naamaanschrijvingnatuurlijkpersoon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAanhefaanschrijving() {
        return this.aanhefaanschrijving;
    }

    public Naamaanschrijvingnatuurlijkpersoon aanhefaanschrijving(String aanhefaanschrijving) {
        this.setAanhefaanschrijving(aanhefaanschrijving);
        return this;
    }

    public void setAanhefaanschrijving(String aanhefaanschrijving) {
        this.aanhefaanschrijving = aanhefaanschrijving;
    }

    public String getGeslachtsnaamaanschrijving() {
        return this.geslachtsnaamaanschrijving;
    }

    public Naamaanschrijvingnatuurlijkpersoon geslachtsnaamaanschrijving(String geslachtsnaamaanschrijving) {
        this.setGeslachtsnaamaanschrijving(geslachtsnaamaanschrijving);
        return this;
    }

    public void setGeslachtsnaamaanschrijving(String geslachtsnaamaanschrijving) {
        this.geslachtsnaamaanschrijving = geslachtsnaamaanschrijving;
    }

    public String getVoorlettersaanschrijving() {
        return this.voorlettersaanschrijving;
    }

    public Naamaanschrijvingnatuurlijkpersoon voorlettersaanschrijving(String voorlettersaanschrijving) {
        this.setVoorlettersaanschrijving(voorlettersaanschrijving);
        return this;
    }

    public void setVoorlettersaanschrijving(String voorlettersaanschrijving) {
        this.voorlettersaanschrijving = voorlettersaanschrijving;
    }

    public String getVoornamenaanschrijving() {
        return this.voornamenaanschrijving;
    }

    public Naamaanschrijvingnatuurlijkpersoon voornamenaanschrijving(String voornamenaanschrijving) {
        this.setVoornamenaanschrijving(voornamenaanschrijving);
        return this;
    }

    public void setVoornamenaanschrijving(String voornamenaanschrijving) {
        this.voornamenaanschrijving = voornamenaanschrijving;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Naamaanschrijvingnatuurlijkpersoon)) {
            return false;
        }
        return getId() != null && getId().equals(((Naamaanschrijvingnatuurlijkpersoon) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Naamaanschrijvingnatuurlijkpersoon{" +
            "id=" + getId() +
            ", aanhefaanschrijving='" + getAanhefaanschrijving() + "'" +
            ", geslachtsnaamaanschrijving='" + getGeslachtsnaamaanschrijving() + "'" +
            ", voorlettersaanschrijving='" + getVoorlettersaanschrijving() + "'" +
            ", voornamenaanschrijving='" + getVoornamenaanschrijving() + "'" +
            "}";
    }
}
