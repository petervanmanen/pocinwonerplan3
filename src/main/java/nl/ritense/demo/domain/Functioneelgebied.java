package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Functioneelgebied.
 */
@Entity
@Table(name = "functioneelgebied")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Functioneelgebied implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @Column(name = "functioneelgebiedcode", length = 20)
    private String functioneelgebiedcode;

    @Column(name = "functioneelgebiednaam")
    private String functioneelgebiednaam;

    @Column(name = "omtrek")
    private String omtrek;

    @Column(name = "oppervlakte")
    private String oppervlakte;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Functioneelgebied id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFunctioneelgebiedcode() {
        return this.functioneelgebiedcode;
    }

    public Functioneelgebied functioneelgebiedcode(String functioneelgebiedcode) {
        this.setFunctioneelgebiedcode(functioneelgebiedcode);
        return this;
    }

    public void setFunctioneelgebiedcode(String functioneelgebiedcode) {
        this.functioneelgebiedcode = functioneelgebiedcode;
    }

    public String getFunctioneelgebiednaam() {
        return this.functioneelgebiednaam;
    }

    public Functioneelgebied functioneelgebiednaam(String functioneelgebiednaam) {
        this.setFunctioneelgebiednaam(functioneelgebiednaam);
        return this;
    }

    public void setFunctioneelgebiednaam(String functioneelgebiednaam) {
        this.functioneelgebiednaam = functioneelgebiednaam;
    }

    public String getOmtrek() {
        return this.omtrek;
    }

    public Functioneelgebied omtrek(String omtrek) {
        this.setOmtrek(omtrek);
        return this;
    }

    public void setOmtrek(String omtrek) {
        this.omtrek = omtrek;
    }

    public String getOppervlakte() {
        return this.oppervlakte;
    }

    public Functioneelgebied oppervlakte(String oppervlakte) {
        this.setOppervlakte(oppervlakte);
        return this;
    }

    public void setOppervlakte(String oppervlakte) {
        this.oppervlakte = oppervlakte;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Functioneelgebied)) {
            return false;
        }
        return getId() != null && getId().equals(((Functioneelgebied) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Functioneelgebied{" +
            "id=" + getId() +
            ", functioneelgebiedcode='" + getFunctioneelgebiedcode() + "'" +
            ", functioneelgebiednaam='" + getFunctioneelgebiednaam() + "'" +
            ", omtrek='" + getOmtrek() + "'" +
            ", oppervlakte='" + getOppervlakte() + "'" +
            "}";
    }
}
