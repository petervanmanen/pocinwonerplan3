package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Bouwtype.
 */
@Entity
@Table(name = "bouwtype")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bouwtype implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "hoofdcategorie")
    private String hoofdcategorie;

    @Column(name = "subcategorie")
    private String subcategorie;

    @Column(name = "toelichting")
    private String toelichting;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bouwtype id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHoofdcategorie() {
        return this.hoofdcategorie;
    }

    public Bouwtype hoofdcategorie(String hoofdcategorie) {
        this.setHoofdcategorie(hoofdcategorie);
        return this;
    }

    public void setHoofdcategorie(String hoofdcategorie) {
        this.hoofdcategorie = hoofdcategorie;
    }

    public String getSubcategorie() {
        return this.subcategorie;
    }

    public Bouwtype subcategorie(String subcategorie) {
        this.setSubcategorie(subcategorie);
        return this;
    }

    public void setSubcategorie(String subcategorie) {
        this.subcategorie = subcategorie;
    }

    public String getToelichting() {
        return this.toelichting;
    }

    public Bouwtype toelichting(String toelichting) {
        this.setToelichting(toelichting);
        return this;
    }

    public void setToelichting(String toelichting) {
        this.toelichting = toelichting;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bouwtype)) {
            return false;
        }
        return getId() != null && getId().equals(((Bouwtype) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bouwtype{" +
            "id=" + getId() +
            ", hoofdcategorie='" + getHoofdcategorie() + "'" +
            ", subcategorie='" + getSubcategorie() + "'" +
            ", toelichting='" + getToelichting() + "'" +
            "}";
    }
}
