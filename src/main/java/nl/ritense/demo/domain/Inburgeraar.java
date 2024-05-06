package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Inburgeraar.
 */
@Entity
@Table(name = "inburgeraar")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Inburgeraar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "doelgroep")
    private String doelgroep;

    @Column(name = "gedetailleerdedoelgroep")
    private String gedetailleerdedoelgroep;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Inburgeraar id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoelgroep() {
        return this.doelgroep;
    }

    public Inburgeraar doelgroep(String doelgroep) {
        this.setDoelgroep(doelgroep);
        return this;
    }

    public void setDoelgroep(String doelgroep) {
        this.doelgroep = doelgroep;
    }

    public String getGedetailleerdedoelgroep() {
        return this.gedetailleerdedoelgroep;
    }

    public Inburgeraar gedetailleerdedoelgroep(String gedetailleerdedoelgroep) {
        this.setGedetailleerdedoelgroep(gedetailleerdedoelgroep);
        return this;
    }

    public void setGedetailleerdedoelgroep(String gedetailleerdedoelgroep) {
        this.gedetailleerdedoelgroep = gedetailleerdedoelgroep;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inburgeraar)) {
            return false;
        }
        return getId() != null && getId().equals(((Inburgeraar) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inburgeraar{" +
            "id=" + getId() +
            ", doelgroep='" + getDoelgroep() + "'" +
            ", gedetailleerdedoelgroep='" + getGedetailleerdedoelgroep() + "'" +
            "}";
    }
}
