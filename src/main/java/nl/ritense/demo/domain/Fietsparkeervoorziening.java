package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Fietsparkeervoorziening.
 */
@Entity
@Table(name = "fietsparkeervoorziening")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Fietsparkeervoorziening implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aantalparkeerplaatsen")
    private String aantalparkeerplaatsen;

    @Column(name = "type")
    private String type;

    @Column(name = "typeplus")
    private String typeplus;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Fietsparkeervoorziening id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAantalparkeerplaatsen() {
        return this.aantalparkeerplaatsen;
    }

    public Fietsparkeervoorziening aantalparkeerplaatsen(String aantalparkeerplaatsen) {
        this.setAantalparkeerplaatsen(aantalparkeerplaatsen);
        return this;
    }

    public void setAantalparkeerplaatsen(String aantalparkeerplaatsen) {
        this.aantalparkeerplaatsen = aantalparkeerplaatsen;
    }

    public String getType() {
        return this.type;
    }

    public Fietsparkeervoorziening type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeplus() {
        return this.typeplus;
    }

    public Fietsparkeervoorziening typeplus(String typeplus) {
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
        if (!(o instanceof Fietsparkeervoorziening)) {
            return false;
        }
        return getId() != null && getId().equals(((Fietsparkeervoorziening) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fietsparkeervoorziening{" +
            "id=" + getId() +
            ", aantalparkeerplaatsen='" + getAantalparkeerplaatsen() + "'" +
            ", type='" + getType() + "'" +
            ", typeplus='" + getTypeplus() + "'" +
            "}";
    }
}
