package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Taak.
 */
@Entity
@Table(name = "taak")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Taak implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "projectleiderRapportagemoments",
            "aanvragerSubsidies",
            "heeftTenaamstellings",
            "betrokkenenKadastralemutaties",
            "isIndiener",
            "houderParkeervergunnings",
            "verstrekkerSubsidies",
            "projectleiderTaaks",
            "heeftVastgoedcontracts",
        },
        allowSetters = true
    )
    private Rechtspersoon projectleiderRechtspersoon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftZaak",
            "heeftRapportagemoments",
            "heeftTaaks",
            "valtbinnenSector",
            "behandelaarMedewerker",
            "verstrekkerRechtspersoon",
            "heeftKostenplaats",
            "heeftDocument",
            "betreftSubsidieaanvraag",
            "betreftSubsidiebeschikking",
            "aanvragerRechtspersoon",
            "aanvragerMedewerker",
        },
        allowSetters = true
    )
    private Subsidie heeftSubsidie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Taak id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rechtspersoon getProjectleiderRechtspersoon() {
        return this.projectleiderRechtspersoon;
    }

    public void setProjectleiderRechtspersoon(Rechtspersoon rechtspersoon) {
        this.projectleiderRechtspersoon = rechtspersoon;
    }

    public Taak projectleiderRechtspersoon(Rechtspersoon rechtspersoon) {
        this.setProjectleiderRechtspersoon(rechtspersoon);
        return this;
    }

    public Subsidie getHeeftSubsidie() {
        return this.heeftSubsidie;
    }

    public void setHeeftSubsidie(Subsidie subsidie) {
        this.heeftSubsidie = subsidie;
    }

    public Taak heeftSubsidie(Subsidie subsidie) {
        this.setHeeftSubsidie(subsidie);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Taak)) {
            return false;
        }
        return getId() != null && getId().equals(((Taak) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Taak{" +
            "id=" + getId() +
            "}";
    }
}
