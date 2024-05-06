package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Strijdigheidofnietigheid.
 */
@Entity
@Table(name = "strijdigheidofnietigheid")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Strijdigheidofnietigheid implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aanduidingstrijdigheidnietigheid")
    private String aanduidingstrijdigheidnietigheid;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Strijdigheidofnietigheid id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAanduidingstrijdigheidnietigheid() {
        return this.aanduidingstrijdigheidnietigheid;
    }

    public Strijdigheidofnietigheid aanduidingstrijdigheidnietigheid(String aanduidingstrijdigheidnietigheid) {
        this.setAanduidingstrijdigheidnietigheid(aanduidingstrijdigheidnietigheid);
        return this;
    }

    public void setAanduidingstrijdigheidnietigheid(String aanduidingstrijdigheidnietigheid) {
        this.aanduidingstrijdigheidnietigheid = aanduidingstrijdigheidnietigheid;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Strijdigheidofnietigheid)) {
            return false;
        }
        return getId() != null && getId().equals(((Strijdigheidofnietigheid) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Strijdigheidofnietigheid{" +
            "id=" + getId() +
            ", aanduidingstrijdigheidnietigheid='" + getAanduidingstrijdigheidnietigheid() + "'" +
            "}";
    }
}
