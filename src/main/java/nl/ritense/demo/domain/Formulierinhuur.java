package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Formulierinhuur.
 */
@Entity
@Table(name = "formulierinhuur")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Formulierinhuur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "akkoordfinancieeladviseur")
    private String akkoordfinancieeladviseur;

    @Column(name = "akkoordhradviseur")
    private String akkoordhradviseur;

    @Column(name = "datuminganginhuur")
    private LocalDate datuminganginhuur;

    @Column(name = "functienaaminhuur")
    private String functienaaminhuur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Formulierinhuur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAkkoordfinancieeladviseur() {
        return this.akkoordfinancieeladviseur;
    }

    public Formulierinhuur akkoordfinancieeladviseur(String akkoordfinancieeladviseur) {
        this.setAkkoordfinancieeladviseur(akkoordfinancieeladviseur);
        return this;
    }

    public void setAkkoordfinancieeladviseur(String akkoordfinancieeladviseur) {
        this.akkoordfinancieeladviseur = akkoordfinancieeladviseur;
    }

    public String getAkkoordhradviseur() {
        return this.akkoordhradviseur;
    }

    public Formulierinhuur akkoordhradviseur(String akkoordhradviseur) {
        this.setAkkoordhradviseur(akkoordhradviseur);
        return this;
    }

    public void setAkkoordhradviseur(String akkoordhradviseur) {
        this.akkoordhradviseur = akkoordhradviseur;
    }

    public LocalDate getDatuminganginhuur() {
        return this.datuminganginhuur;
    }

    public Formulierinhuur datuminganginhuur(LocalDate datuminganginhuur) {
        this.setDatuminganginhuur(datuminganginhuur);
        return this;
    }

    public void setDatuminganginhuur(LocalDate datuminganginhuur) {
        this.datuminganginhuur = datuminganginhuur;
    }

    public String getFunctienaaminhuur() {
        return this.functienaaminhuur;
    }

    public Formulierinhuur functienaaminhuur(String functienaaminhuur) {
        this.setFunctienaaminhuur(functienaaminhuur);
        return this;
    }

    public void setFunctienaaminhuur(String functienaaminhuur) {
        this.functienaaminhuur = functienaaminhuur;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Formulierinhuur)) {
            return false;
        }
        return getId() != null && getId().equals(((Formulierinhuur) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Formulierinhuur{" +
            "id=" + getId() +
            ", akkoordfinancieeladviseur='" + getAkkoordfinancieeladviseur() + "'" +
            ", akkoordhradviseur='" + getAkkoordhradviseur() + "'" +
            ", datuminganginhuur='" + getDatuminganginhuur() + "'" +
            ", functienaaminhuur='" + getFunctienaaminhuur() + "'" +
            "}";
    }
}
