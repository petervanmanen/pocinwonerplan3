package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Migratieingeschrevennatuurlijkpersoon.
 */
@Entity
@Table(name = "migratieingeschrevennatuurlijkpersoon")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Migratieingeschrevennatuurlijkpersoon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aangevermigratie")
    private String aangevermigratie;

    @Column(name = "redenwijzigingmigratie")
    private String redenwijzigingmigratie;

    @Column(name = "soortmigratie")
    private String soortmigratie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Migratieingeschrevennatuurlijkpersoon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAangevermigratie() {
        return this.aangevermigratie;
    }

    public Migratieingeschrevennatuurlijkpersoon aangevermigratie(String aangevermigratie) {
        this.setAangevermigratie(aangevermigratie);
        return this;
    }

    public void setAangevermigratie(String aangevermigratie) {
        this.aangevermigratie = aangevermigratie;
    }

    public String getRedenwijzigingmigratie() {
        return this.redenwijzigingmigratie;
    }

    public Migratieingeschrevennatuurlijkpersoon redenwijzigingmigratie(String redenwijzigingmigratie) {
        this.setRedenwijzigingmigratie(redenwijzigingmigratie);
        return this;
    }

    public void setRedenwijzigingmigratie(String redenwijzigingmigratie) {
        this.redenwijzigingmigratie = redenwijzigingmigratie;
    }

    public String getSoortmigratie() {
        return this.soortmigratie;
    }

    public Migratieingeschrevennatuurlijkpersoon soortmigratie(String soortmigratie) {
        this.setSoortmigratie(soortmigratie);
        return this;
    }

    public void setSoortmigratie(String soortmigratie) {
        this.soortmigratie = soortmigratie;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Migratieingeschrevennatuurlijkpersoon)) {
            return false;
        }
        return getId() != null && getId().equals(((Migratieingeschrevennatuurlijkpersoon) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Migratieingeschrevennatuurlijkpersoon{" +
            "id=" + getId() +
            ", aangevermigratie='" + getAangevermigratie() + "'" +
            ", redenwijzigingmigratie='" + getRedenwijzigingmigratie() + "'" +
            ", soortmigratie='" + getSoortmigratie() + "'" +
            "}";
    }
}
