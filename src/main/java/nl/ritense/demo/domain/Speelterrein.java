package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Speelterrein.
 */
@Entity
@Table(name = "speelterrein")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Speelterrein implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "jaarherinrichting")
    private String jaarherinrichting;

    @Column(name = "speelterreinleeftijddoelgroep")
    private String speelterreinleeftijddoelgroep;

    @Column(name = "type")
    private String type;

    @Column(name = "typeplus")
    private String typeplus;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Speelterrein id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJaarherinrichting() {
        return this.jaarherinrichting;
    }

    public Speelterrein jaarherinrichting(String jaarherinrichting) {
        this.setJaarherinrichting(jaarherinrichting);
        return this;
    }

    public void setJaarherinrichting(String jaarherinrichting) {
        this.jaarherinrichting = jaarherinrichting;
    }

    public String getSpeelterreinleeftijddoelgroep() {
        return this.speelterreinleeftijddoelgroep;
    }

    public Speelterrein speelterreinleeftijddoelgroep(String speelterreinleeftijddoelgroep) {
        this.setSpeelterreinleeftijddoelgroep(speelterreinleeftijddoelgroep);
        return this;
    }

    public void setSpeelterreinleeftijddoelgroep(String speelterreinleeftijddoelgroep) {
        this.speelterreinleeftijddoelgroep = speelterreinleeftijddoelgroep;
    }

    public String getType() {
        return this.type;
    }

    public Speelterrein type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeplus() {
        return this.typeplus;
    }

    public Speelterrein typeplus(String typeplus) {
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
        if (!(o instanceof Speelterrein)) {
            return false;
        }
        return getId() != null && getId().equals(((Speelterrein) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Speelterrein{" +
            "id=" + getId() +
            ", jaarherinrichting='" + getJaarherinrichting() + "'" +
            ", speelterreinleeftijddoelgroep='" + getSpeelterreinleeftijddoelgroep() + "'" +
            ", type='" + getType() + "'" +
            ", typeplus='" + getTypeplus() + "'" +
            "}";
    }
}
