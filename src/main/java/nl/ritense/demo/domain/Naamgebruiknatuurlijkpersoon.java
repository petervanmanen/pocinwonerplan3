package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Naamgebruiknatuurlijkpersoon.
 */
@Entity
@Table(name = "naamgebruiknatuurlijkpersoon")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Naamgebruiknatuurlijkpersoon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aanhefaanschrijving")
    private String aanhefaanschrijving;

    @Column(name = "adellijketitelnaamgebruik")
    private String adellijketitelnaamgebruik;

    @Column(name = "geslachtsnaamstamnaamgebruik")
    private String geslachtsnaamstamnaamgebruik;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Naamgebruiknatuurlijkpersoon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAanhefaanschrijving() {
        return this.aanhefaanschrijving;
    }

    public Naamgebruiknatuurlijkpersoon aanhefaanschrijving(String aanhefaanschrijving) {
        this.setAanhefaanschrijving(aanhefaanschrijving);
        return this;
    }

    public void setAanhefaanschrijving(String aanhefaanschrijving) {
        this.aanhefaanschrijving = aanhefaanschrijving;
    }

    public String getAdellijketitelnaamgebruik() {
        return this.adellijketitelnaamgebruik;
    }

    public Naamgebruiknatuurlijkpersoon adellijketitelnaamgebruik(String adellijketitelnaamgebruik) {
        this.setAdellijketitelnaamgebruik(adellijketitelnaamgebruik);
        return this;
    }

    public void setAdellijketitelnaamgebruik(String adellijketitelnaamgebruik) {
        this.adellijketitelnaamgebruik = adellijketitelnaamgebruik;
    }

    public String getGeslachtsnaamstamnaamgebruik() {
        return this.geslachtsnaamstamnaamgebruik;
    }

    public Naamgebruiknatuurlijkpersoon geslachtsnaamstamnaamgebruik(String geslachtsnaamstamnaamgebruik) {
        this.setGeslachtsnaamstamnaamgebruik(geslachtsnaamstamnaamgebruik);
        return this;
    }

    public void setGeslachtsnaamstamnaamgebruik(String geslachtsnaamstamnaamgebruik) {
        this.geslachtsnaamstamnaamgebruik = geslachtsnaamstamnaamgebruik;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Naamgebruiknatuurlijkpersoon)) {
            return false;
        }
        return getId() != null && getId().equals(((Naamgebruiknatuurlijkpersoon) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Naamgebruiknatuurlijkpersoon{" +
            "id=" + getId() +
            ", aanhefaanschrijving='" + getAanhefaanschrijving() + "'" +
            ", adellijketitelnaamgebruik='" + getAdellijketitelnaamgebruik() + "'" +
            ", geslachtsnaamstamnaamgebruik='" + getGeslachtsnaamstamnaamgebruik() + "'" +
            "}";
    }
}
