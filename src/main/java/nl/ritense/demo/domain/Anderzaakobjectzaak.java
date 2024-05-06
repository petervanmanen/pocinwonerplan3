package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Anderzaakobjectzaak.
 */
@Entity
@Table(name = "anderzaakobjectzaak")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Anderzaakobjectzaak implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "anderzaakobjectaanduiding")
    private String anderzaakobjectaanduiding;

    @Column(name = "anderzaakobjectlocatie")
    private String anderzaakobjectlocatie;

    @Column(name = "anderzaakobjectomschrijving")
    private String anderzaakobjectomschrijving;

    @Column(name = "anderzaakobjectregistratie")
    private String anderzaakobjectregistratie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Anderzaakobjectzaak id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnderzaakobjectaanduiding() {
        return this.anderzaakobjectaanduiding;
    }

    public Anderzaakobjectzaak anderzaakobjectaanduiding(String anderzaakobjectaanduiding) {
        this.setAnderzaakobjectaanduiding(anderzaakobjectaanduiding);
        return this;
    }

    public void setAnderzaakobjectaanduiding(String anderzaakobjectaanduiding) {
        this.anderzaakobjectaanduiding = anderzaakobjectaanduiding;
    }

    public String getAnderzaakobjectlocatie() {
        return this.anderzaakobjectlocatie;
    }

    public Anderzaakobjectzaak anderzaakobjectlocatie(String anderzaakobjectlocatie) {
        this.setAnderzaakobjectlocatie(anderzaakobjectlocatie);
        return this;
    }

    public void setAnderzaakobjectlocatie(String anderzaakobjectlocatie) {
        this.anderzaakobjectlocatie = anderzaakobjectlocatie;
    }

    public String getAnderzaakobjectomschrijving() {
        return this.anderzaakobjectomschrijving;
    }

    public Anderzaakobjectzaak anderzaakobjectomschrijving(String anderzaakobjectomschrijving) {
        this.setAnderzaakobjectomschrijving(anderzaakobjectomschrijving);
        return this;
    }

    public void setAnderzaakobjectomschrijving(String anderzaakobjectomschrijving) {
        this.anderzaakobjectomschrijving = anderzaakobjectomschrijving;
    }

    public String getAnderzaakobjectregistratie() {
        return this.anderzaakobjectregistratie;
    }

    public Anderzaakobjectzaak anderzaakobjectregistratie(String anderzaakobjectregistratie) {
        this.setAnderzaakobjectregistratie(anderzaakobjectregistratie);
        return this;
    }

    public void setAnderzaakobjectregistratie(String anderzaakobjectregistratie) {
        this.anderzaakobjectregistratie = anderzaakobjectregistratie;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Anderzaakobjectzaak)) {
            return false;
        }
        return getId() != null && getId().equals(((Anderzaakobjectzaak) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Anderzaakobjectzaak{" +
            "id=" + getId() +
            ", anderzaakobjectaanduiding='" + getAnderzaakobjectaanduiding() + "'" +
            ", anderzaakobjectlocatie='" + getAnderzaakobjectlocatie() + "'" +
            ", anderzaakobjectomschrijving='" + getAnderzaakobjectomschrijving() + "'" +
            ", anderzaakobjectregistratie='" + getAnderzaakobjectregistratie() + "'" +
            "}";
    }
}
