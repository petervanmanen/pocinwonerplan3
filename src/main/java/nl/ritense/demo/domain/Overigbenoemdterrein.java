package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Overigbenoemdterrein.
 */
@Entity
@Table(name = "overigbenoemdterrein")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Overigbenoemdterrein implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "gebruiksdoeloverigbenoemdterrein")
    private String gebruiksdoeloverigbenoemdterrein;

    @Column(name = "overigbenoemdterreinidentificatie")
    private String overigbenoemdterreinidentificatie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Overigbenoemdterrein id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGebruiksdoeloverigbenoemdterrein() {
        return this.gebruiksdoeloverigbenoemdterrein;
    }

    public Overigbenoemdterrein gebruiksdoeloverigbenoemdterrein(String gebruiksdoeloverigbenoemdterrein) {
        this.setGebruiksdoeloverigbenoemdterrein(gebruiksdoeloverigbenoemdterrein);
        return this;
    }

    public void setGebruiksdoeloverigbenoemdterrein(String gebruiksdoeloverigbenoemdterrein) {
        this.gebruiksdoeloverigbenoemdterrein = gebruiksdoeloverigbenoemdterrein;
    }

    public String getOverigbenoemdterreinidentificatie() {
        return this.overigbenoemdterreinidentificatie;
    }

    public Overigbenoemdterrein overigbenoemdterreinidentificatie(String overigbenoemdterreinidentificatie) {
        this.setOverigbenoemdterreinidentificatie(overigbenoemdterreinidentificatie);
        return this;
    }

    public void setOverigbenoemdterreinidentificatie(String overigbenoemdterreinidentificatie) {
        this.overigbenoemdterreinidentificatie = overigbenoemdterreinidentificatie;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Overigbenoemdterrein)) {
            return false;
        }
        return getId() != null && getId().equals(((Overigbenoemdterrein) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Overigbenoemdterrein{" +
            "id=" + getId() +
            ", gebruiksdoeloverigbenoemdterrein='" + getGebruiksdoeloverigbenoemdterrein() + "'" +
            ", overigbenoemdterreinidentificatie='" + getOverigbenoemdterreinidentificatie() + "'" +
            "}";
    }
}
