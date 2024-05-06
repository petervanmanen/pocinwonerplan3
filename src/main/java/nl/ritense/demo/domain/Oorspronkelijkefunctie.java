package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Oorspronkelijkefunctie.
 */
@Entity
@Table(name = "oorspronkelijkefunctie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Oorspronkelijkefunctie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "functie")
    private String functie;

    @Column(name = "functiesoort")
    private String functiesoort;

    @Column(name = "hoofdcategorie")
    private String hoofdcategorie;

    @Column(name = "hoofdfunctie")
    private String hoofdfunctie;

    @Column(name = "subcategorie")
    private String subcategorie;

    @Column(name = "toelichting")
    private String toelichting;

    @Column(name = "verbijzondering")
    private String verbijzondering;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Oorspronkelijkefunctie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFunctie() {
        return this.functie;
    }

    public Oorspronkelijkefunctie functie(String functie) {
        this.setFunctie(functie);
        return this;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }

    public String getFunctiesoort() {
        return this.functiesoort;
    }

    public Oorspronkelijkefunctie functiesoort(String functiesoort) {
        this.setFunctiesoort(functiesoort);
        return this;
    }

    public void setFunctiesoort(String functiesoort) {
        this.functiesoort = functiesoort;
    }

    public String getHoofdcategorie() {
        return this.hoofdcategorie;
    }

    public Oorspronkelijkefunctie hoofdcategorie(String hoofdcategorie) {
        this.setHoofdcategorie(hoofdcategorie);
        return this;
    }

    public void setHoofdcategorie(String hoofdcategorie) {
        this.hoofdcategorie = hoofdcategorie;
    }

    public String getHoofdfunctie() {
        return this.hoofdfunctie;
    }

    public Oorspronkelijkefunctie hoofdfunctie(String hoofdfunctie) {
        this.setHoofdfunctie(hoofdfunctie);
        return this;
    }

    public void setHoofdfunctie(String hoofdfunctie) {
        this.hoofdfunctie = hoofdfunctie;
    }

    public String getSubcategorie() {
        return this.subcategorie;
    }

    public Oorspronkelijkefunctie subcategorie(String subcategorie) {
        this.setSubcategorie(subcategorie);
        return this;
    }

    public void setSubcategorie(String subcategorie) {
        this.subcategorie = subcategorie;
    }

    public String getToelichting() {
        return this.toelichting;
    }

    public Oorspronkelijkefunctie toelichting(String toelichting) {
        this.setToelichting(toelichting);
        return this;
    }

    public void setToelichting(String toelichting) {
        this.toelichting = toelichting;
    }

    public String getVerbijzondering() {
        return this.verbijzondering;
    }

    public Oorspronkelijkefunctie verbijzondering(String verbijzondering) {
        this.setVerbijzondering(verbijzondering);
        return this;
    }

    public void setVerbijzondering(String verbijzondering) {
        this.verbijzondering = verbijzondering;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Oorspronkelijkefunctie)) {
            return false;
        }
        return getId() != null && getId().equals(((Oorspronkelijkefunctie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Oorspronkelijkefunctie{" +
            "id=" + getId() +
            ", functie='" + getFunctie() + "'" +
            ", functiesoort='" + getFunctiesoort() + "'" +
            ", hoofdcategorie='" + getHoofdcategorie() + "'" +
            ", hoofdfunctie='" + getHoofdfunctie() + "'" +
            ", subcategorie='" + getSubcategorie() + "'" +
            ", toelichting='" + getToelichting() + "'" +
            ", verbijzondering='" + getVerbijzondering() + "'" +
            "}";
    }
}
