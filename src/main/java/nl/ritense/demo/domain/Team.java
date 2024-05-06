package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Team.
 */
@Entity
@Table(name = "team")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "naam")
    private String naam;

    @Column(name = "omschrijving")
    private String omschrijving;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "maaktonderdeeluitvanTeam")
    @JsonIgnoreProperties(
        value = {
            "geeftafBeschikkings",
            "emptyInkomensvoorzienings",
            "maaktonderdeeluitvanTeam",
            "begeleidtTraject",
            "ondersteuntclientClients",
            "emptyParticipatiedossier",
        },
        allowSetters = true
    )
    private Set<Clientbegeleider> maaktonderdeeluitvanClientbegeleiders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Team id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return this.naam;
    }

    public Team naam(String naam) {
        this.setNaam(naam);
        return this;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getOmschrijving() {
        return this.omschrijving;
    }

    public Team omschrijving(String omschrijving) {
        this.setOmschrijving(omschrijving);
        return this;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public Set<Clientbegeleider> getMaaktonderdeeluitvanClientbegeleiders() {
        return this.maaktonderdeeluitvanClientbegeleiders;
    }

    public void setMaaktonderdeeluitvanClientbegeleiders(Set<Clientbegeleider> clientbegeleiders) {
        if (this.maaktonderdeeluitvanClientbegeleiders != null) {
            this.maaktonderdeeluitvanClientbegeleiders.forEach(i -> i.setMaaktonderdeeluitvanTeam(null));
        }
        if (clientbegeleiders != null) {
            clientbegeleiders.forEach(i -> i.setMaaktonderdeeluitvanTeam(this));
        }
        this.maaktonderdeeluitvanClientbegeleiders = clientbegeleiders;
    }

    public Team maaktonderdeeluitvanClientbegeleiders(Set<Clientbegeleider> clientbegeleiders) {
        this.setMaaktonderdeeluitvanClientbegeleiders(clientbegeleiders);
        return this;
    }

    public Team addMaaktonderdeeluitvanClientbegeleider(Clientbegeleider clientbegeleider) {
        this.maaktonderdeeluitvanClientbegeleiders.add(clientbegeleider);
        clientbegeleider.setMaaktonderdeeluitvanTeam(this);
        return this;
    }

    public Team removeMaaktonderdeeluitvanClientbegeleider(Clientbegeleider clientbegeleider) {
        this.maaktonderdeeluitvanClientbegeleiders.remove(clientbegeleider);
        clientbegeleider.setMaaktonderdeeluitvanTeam(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Team)) {
            return false;
        }
        return getId() != null && getId().equals(((Team) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Team{" +
            "id=" + getId() +
            ", naam='" + getNaam() + "'" +
            ", omschrijving='" + getOmschrijving() + "'" +
            "}";
    }
}
