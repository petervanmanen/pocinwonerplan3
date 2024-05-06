package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Verkeersdrempel.
 */
@Entity
@Table(name = "verkeersdrempel")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Verkeersdrempel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 10)
    @Column(name = "ontwerpsnelheid", length = 10)
    private String ontwerpsnelheid;

    @Column(name = "type")
    private String type;

    @Column(name = "typeplus")
    private String typeplus;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Verkeersdrempel id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOntwerpsnelheid() {
        return this.ontwerpsnelheid;
    }

    public Verkeersdrempel ontwerpsnelheid(String ontwerpsnelheid) {
        this.setOntwerpsnelheid(ontwerpsnelheid);
        return this;
    }

    public void setOntwerpsnelheid(String ontwerpsnelheid) {
        this.ontwerpsnelheid = ontwerpsnelheid;
    }

    public String getType() {
        return this.type;
    }

    public Verkeersdrempel type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeplus() {
        return this.typeplus;
    }

    public Verkeersdrempel typeplus(String typeplus) {
        this.setTypeplus(typeplus);
        return this;
    }

    public void setTypeplus(String typeplus) {
        this.typeplus = typeplus;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Verkeersdrempel)) {
            return false;
        }
        return getId() != null && getId().equals(((Verkeersdrempel) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Verkeersdrempel{" +
            "id=" + getId() +
            ", ontwerpsnelheid='" + getOntwerpsnelheid() + "'" +
            ", type='" + getType() + "'" +
            ", typeplus='" + getTypeplus() + "'" +
            "}";
    }
}
