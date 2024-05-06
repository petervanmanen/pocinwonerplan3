package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Trajectactiviteitsoort.
 */
@Entity
@Table(name = "trajectactiviteitsoort")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Trajectactiviteitsoort implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @Column(name = "aanleverensrg", length = 20)
    private String aanleverensrg;

    @Column(name = "omschrijving")
    private String omschrijving;

    @Size(max = 20)
    @Column(name = "typetrajectsrg", length = 20)
    private String typetrajectsrg;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Trajectactiviteitsoort id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAanleverensrg() {
        return this.aanleverensrg;
    }

    public Trajectactiviteitsoort aanleverensrg(String aanleverensrg) {
        this.setAanleverensrg(aanleverensrg);
        return this;
    }

    public void setAanleverensrg(String aanleverensrg) {
        this.aanleverensrg = aanleverensrg;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Trajectactiviteitsoort omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getTypetrajectsrg() {
        return this.typetrajectsrg;
    }

    public Trajectactiviteitsoort typetrajectsrg(String typetrajectsrg) {
        this.setTypetrajectsrg(typetrajectsrg);
        return this;
    }

    public void setTypetrajectsrg(String typetrajectsrg) {
        this.typetrajectsrg = typetrajectsrg;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trajectactiviteitsoort)) {
            return false;
        }
        return getId() != null && getId().equals(((Trajectactiviteitsoort) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Trajectactiviteitsoort{" +
            "id=" + getId() +
            ", aanleverensrg='" + getAanleverensrg() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            ", typetrajectsrg='" + getTypetrajectsrg() + "'" +
            "}";
    }
}
