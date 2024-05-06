package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Locatiekadastraleonroerendezaak.
 */
@Entity
@Table(name = "locatiekadastraleonroerendezaak")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Locatiekadastraleonroerendezaak implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aardcultuurbebouwd")
    private String aardcultuurbebouwd;

    @Column(name = "locatieomschrijving")
    private String locatieomschrijving;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Locatiekadastraleonroerendezaak id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAardcultuurbebouwd() {
        return this.aardcultuurbebouwd;
    }

    public Locatiekadastraleonroerendezaak aardcultuurbebouwd(String aardcultuurbebouwd) {
        this.setAardcultuurbebouwd(aardcultuurbebouwd);
        return this;
    }

    public void setAardcultuurbebouwd(String aardcultuurbebouwd) {
        this.aardcultuurbebouwd = aardcultuurbebouwd;
    }

    public String getLocatieomschrijving() {
        return this.locatieomschrijving;
    }

    public Locatiekadastraleonroerendezaak locatieomschrijving(String locatieomschrijving) {
        this.setLocatieomschrijving(locatieomschrijving);
        return this;
    }

    public void setLocatieomschrijving(String locatieomschrijving) {
        this.locatieomschrijving = locatieomschrijving;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Locatiekadastraleonroerendezaak)) {
            return false;
        }
        return getId() != null && getId().equals(((Locatiekadastraleonroerendezaak) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Locatiekadastraleonroerendezaak{" +
            "id=" + getId() +
            ", aardcultuurbebouwd='" + getAardcultuurbebouwd() + "'" +
            ", locatieomschrijving='" + getLocatieomschrijving() + "'" +
            "}";
    }
}
