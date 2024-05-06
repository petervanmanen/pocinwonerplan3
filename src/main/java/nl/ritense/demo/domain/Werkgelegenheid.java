package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Werkgelegenheid.
 */
@Entity
@Table(name = "werkgelegenheid")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Werkgelegenheid implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aantalfulltimemannen")
    private String aantalfulltimemannen;

    @Column(name = "aantalfulltimevrouwen")
    private String aantalfulltimevrouwen;

    @Column(name = "aantalparttimemannen")
    private String aantalparttimemannen;

    @Column(name = "aantalparttimevrouwen")
    private String aantalparttimevrouwen;

    @Column(name = "grootteklasse")
    private String grootteklasse;

    @JsonIgnoreProperties(value = { "heeftWerkgelegenheid", "heeftalslocatieadresNummeraanduiding", "bijContacts" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "heeftWerkgelegenheid")
    private Vestiging heeftVestiging;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Werkgelegenheid id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAantalfulltimemannen() {
        return this.aantalfulltimemannen;
    }

    public Werkgelegenheid aantalfulltimemannen(String aantalfulltimemannen) {
        this.setAantalfulltimemannen(aantalfulltimemannen);
        return this;
    }

    public void setAantalfulltimemannen(String aantalfulltimemannen) {
        this.aantalfulltimemannen = aantalfulltimemannen;
    }

    public String getAantalfulltimevrouwen() {
        return this.aantalfulltimevrouwen;
    }

    public Werkgelegenheid aantalfulltimevrouwen(String aantalfulltimevrouwen) {
        this.setAantalfulltimevrouwen(aantalfulltimevrouwen);
        return this;
    }

    public void setAantalfulltimevrouwen(String aantalfulltimevrouwen) {
        this.aantalfulltimevrouwen = aantalfulltimevrouwen;
    }

    public String getAantalparttimemannen() {
        return this.aantalparttimemannen;
    }

    public Werkgelegenheid aantalparttimemannen(String aantalparttimemannen) {
        this.setAantalparttimemannen(aantalparttimemannen);
        return this;
    }

    public void setAantalparttimemannen(String aantalparttimemannen) {
        this.aantalparttimemannen = aantalparttimemannen;
    }

    public String getAantalparttimevrouwen() {
        return this.aantalparttimevrouwen;
    }

    public Werkgelegenheid aantalparttimevrouwen(String aantalparttimevrouwen) {
        this.setAantalparttimevrouwen(aantalparttimevrouwen);
        return this;
    }

    public void setAantalparttimevrouwen(String aantalparttimevrouwen) {
        this.aantalparttimevrouwen = aantalparttimevrouwen;
    }

    public String getGrootteklasse() {
        return this.grootteklasse;
    }

    public Werkgelegenheid grootteklasse(String grootteklasse) {
        this.setGrootteklasse(grootteklasse);
        return this;
    }

    public void setGrootteklasse(String grootteklasse) {
        this.grootteklasse = grootteklasse;
    }

    public Vestiging getHeeftVestiging() {
        return this.heeftVestiging;
    }

    public void setHeeftVestiging(Vestiging vestiging) {
        if (this.heeftVestiging != null) {
            this.heeftVestiging.setHeeftWerkgelegenheid(null);
        }
        if (vestiging != null) {
            vestiging.setHeeftWerkgelegenheid(this);
        }
        this.heeftVestiging = vestiging;
    }

    public Werkgelegenheid heeftVestiging(Vestiging vestiging) {
        this.setHeeftVestiging(vestiging);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Werkgelegenheid)) {
            return false;
        }
        return getId() != null && getId().equals(((Werkgelegenheid) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Werkgelegenheid{" +
            "id=" + getId() +
            ", aantalfulltimemannen='" + getAantalfulltimemannen() + "'" +
            ", aantalfulltimevrouwen='" + getAantalfulltimevrouwen() + "'" +
            ", aantalparttimemannen='" + getAantalparttimemannen() + "'" +
            ", aantalparttimevrouwen='" + getAantalparttimevrouwen() + "'" +
            ", grootteklasse='" + getGrootteklasse() + "'" +
            "}";
    }
}
