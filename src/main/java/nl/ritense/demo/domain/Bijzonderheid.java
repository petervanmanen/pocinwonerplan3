package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Bijzonderheid.
 */
@Entity
@Table(name = "bijzonderheid")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bijzonderheid implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "omschrijving")
    private String omschrijving;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "isvansoortBijzonderheids" }, allowSetters = true)
    private Bijzonderheidsoort isvansoortBijzonderheidsoort;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bijzonderheid id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Bijzonderheid omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Bijzonderheidsoort getIsvansoortBijzonderheidsoort() {
        return this.isvansoortBijzonderheidsoort;
    }

    public void setIsvansoortBijzonderheidsoort(Bijzonderheidsoort bijzonderheidsoort) {
        this.isvansoortBijzonderheidsoort = bijzonderheidsoort;
    }

    public Bijzonderheid isvansoortBijzonderheidsoort(Bijzonderheidsoort bijzonderheidsoort) {
        this.setIsvansoortBijzonderheidsoort(bijzonderheidsoort);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bijzonderheid)) {
            return false;
        }
        return getId() != null && getId().equals(((Bijzonderheid) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bijzonderheid{" +
            "id=" + getId() +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
