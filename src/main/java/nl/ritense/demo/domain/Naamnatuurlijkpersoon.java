package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Naamnatuurlijkpersoon.
 */
@Entity
@Table(name = "naamnatuurlijkpersoon")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Naamnatuurlijkpersoon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 10)
    @Column(name = "adellijketitelofpredikaat", length = 10)
    private String adellijketitelofpredikaat;

    @Column(name = "geslachtsnaam")
    private String geslachtsnaam;

    @Column(name = "voornamen")
    private String voornamen;

    @Column(name = "voorvoegselgeslachtsnaam")
    private String voorvoegselgeslachtsnaam;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Naamnatuurlijkpersoon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdellijketitelofpredikaat() {
        return this.adellijketitelofpredikaat;
    }

    public Naamnatuurlijkpersoon adellijketitelofpredikaat(String adellijketitelofpredikaat) {
        this.setAdellijketitelofpredikaat(adellijketitelofpredikaat);
        return this;
    }

    public void setAdellijketitelofpredikaat(String adellijketitelofpredikaat) {
        this.adellijketitelofpredikaat = adellijketitelofpredikaat;
    }

    public String getGeslachtsnaam() {
        return this.geslachtsnaam;
    }

    public Naamnatuurlijkpersoon geslachtsnaam(String geslachtsnaam) {
        this.setGeslachtsnaam(geslachtsnaam);
        return this;
    }

    public void setGeslachtsnaam(String geslachtsnaam) {
        this.geslachtsnaam = geslachtsnaam;
    }

    public String getVoornamen() {
        return this.voornamen;
    }

    public Naamnatuurlijkpersoon voornamen(String voornamen) {
        this.setVoornamen(voornamen);
        return this;
    }

    public void setVoornamen(String voornamen) {
        this.voornamen = voornamen;
    }

    public String getVoorvoegselgeslachtsnaam() {
        return this.voorvoegselgeslachtsnaam;
    }

    public Naamnatuurlijkpersoon voorvoegselgeslachtsnaam(String voorvoegselgeslachtsnaam) {
        this.setVoorvoegselgeslachtsnaam(voorvoegselgeslachtsnaam);
        return this;
    }

    public void setVoorvoegselgeslachtsnaam(String voorvoegselgeslachtsnaam) {
        this.voorvoegselgeslachtsnaam = voorvoegselgeslachtsnaam;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Naamnatuurlijkpersoon)) {
            return false;
        }
        return getId() != null && getId().equals(((Naamnatuurlijkpersoon) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Naamnatuurlijkpersoon{" +
            "id=" + getId() +
            ", adellijketitelofpredikaat='" + getAdellijketitelofpredikaat() + "'" +
            ", geslachtsnaam='" + getGeslachtsnaam() + "'" +
            ", voornamen='" + getVoornamen() + "'" +
            ", voorvoegselgeslachtsnaam='" + getVoorvoegselgeslachtsnaam() + "'" +
            "}";
    }
}
