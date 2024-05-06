package nl.ritense.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Vthzaak.
 */
@Entity
@Table(name = "vthzaak")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vthzaak implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @Column(name = "behandelaar", length = 20)
    private String behandelaar;

    @Column(name = "bevoegdgezag")
    private String bevoegdgezag;

    @Column(name = "prioritering")
    private String prioritering;

    @Size(max = 20)
    @Column(name = "teambehandelaar", length = 20)
    private String teambehandelaar;

    @Column(name = "uitvoerendeinstantie")
    private String uitvoerendeinstantie;

    @Column(name = "verkamering")
    private String verkamering;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vthzaak id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBehandelaar() {
        return this.behandelaar;
    }

    public Vthzaak behandelaar(String behandelaar) {
        this.setBehandelaar(behandelaar);
        return this;
    }

    public void setBehandelaar(String behandelaar) {
        this.behandelaar = behandelaar;
    }

    public String getBevoegdgezag() {
        return this.bevoegdgezag;
    }

    public Vthzaak bevoegdgezag(String bevoegdgezag) {
        this.setBevoegdgezag(bevoegdgezag);
        return this;
    }

    public void setBevoegdgezag(String bevoegdgezag) {
        this.bevoegdgezag = bevoegdgezag;
    }

    public String getPrioritering() {
        return this.prioritering;
    }

    public Vthzaak prioritering(String prioritering) {
        this.setPrioritering(prioritering);
        return this;
    }

    public void setPrioritering(String prioritering) {
        this.prioritering = prioritering;
    }

    public String getTeambehandelaar() {
        return this.teambehandelaar;
    }

    public Vthzaak teambehandelaar(String teambehandelaar) {
        this.setTeambehandelaar(teambehandelaar);
        return this;
    }

    public void setTeambehandelaar(String teambehandelaar) {
        this.teambehandelaar = teambehandelaar;
    }

    public String getUitvoerendeinstantie() {
        return this.uitvoerendeinstantie;
    }

    public Vthzaak uitvoerendeinstantie(String uitvoerendeinstantie) {
        this.setUitvoerendeinstantie(uitvoerendeinstantie);
        return this;
    }

    public void setUitvoerendeinstantie(String uitvoerendeinstantie) {
        this.uitvoerendeinstantie = uitvoerendeinstantie;
    }

    public String getVerkamering() {
        return this.verkamering;
    }

    public Vthzaak verkamering(String verkamering) {
        this.setVerkamering(verkamering);
        return this;
    }

    public void setVerkamering(String verkamering) {
        this.verkamering = verkamering;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vthzaak)) {
            return false;
        }
        return getId() != null && getId().equals(((Vthzaak) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vthzaak{" +
            "id=" + getId() +
            ", behandelaar='" + getBehandelaar() + "'" +
            ", bevoegdgezag='" + getBevoegdgezag() + "'" +
            ", prioritering='" + getPrioritering() + "'" +
            ", teambehandelaar='" + getTeambehandelaar() + "'" +
            ", uitvoerendeinstantie='" + getUitvoerendeinstantie() + "'" +
            ", verkamering='" + getVerkamering() + "'" +
            "}";
    }
}
