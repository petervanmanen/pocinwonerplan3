package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Huishouden.
 */
@Entity
@Table(name = "huishouden")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Huishouden implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "maaktonderdeeluitvanHuishoudens")
    @JsonIgnoreProperties(
        value = {
            "heeftParticipatiedossier",
            "heeftvoorzieningInkomensvoorziening",
            "heeftScores",
            "leverttegenprestatieTegenprestaties",
            "heeftparticipatietrajectTrajects",
            "heeftfraudegegevensFraudegegevens",
            "heeftregelingRegelings",
            "heefttrajectTrajects",
            "valtbinnendoelgroepDoelgroep",
            "heeftrelatieRelaties",
            "voorzieningbijstandspartijInkomensvoorzienings",
            "maaktonderdeeluitvanHuishoudens",
            "heefttaalniveauTaalniveaus",
            "emptyBeschikkings",
            "prestatievoorLeverings",
            "betreftDeclaratieregels",
            "ondersteuntclientClientbegeleiders",
        },
        allowSetters = true
    )
    private Set<Client> maaktonderdeeluitvanClients = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "maaktonderdeelvanHuishoudens")
    @JsonIgnoreProperties(value = { "issoortRelatiesoort", "maaktonderdeelvanHuishoudens", "heeftrelatieClients" }, allowSetters = true)
    private Set<Relatie> maaktonderdeelvanRelaties = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Huishouden id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Client> getMaaktonderdeeluitvanClients() {
        return this.maaktonderdeeluitvanClients;
    }

    public void setMaaktonderdeeluitvanClients(Set<Client> clients) {
        if (this.maaktonderdeeluitvanClients != null) {
            this.maaktonderdeeluitvanClients.forEach(i -> i.removeMaaktonderdeeluitvanHuishouden(this));
        }
        if (clients != null) {
            clients.forEach(i -> i.addMaaktonderdeeluitvanHuishouden(this));
        }
        this.maaktonderdeeluitvanClients = clients;
    }

    public Huishouden maaktonderdeeluitvanClients(Set<Client> clients) {
        this.setMaaktonderdeeluitvanClients(clients);
        return this;
    }

    public Huishouden addMaaktonderdeeluitvanClient(Client client) {
        this.maaktonderdeeluitvanClients.add(client);
        client.getMaaktonderdeeluitvanHuishoudens().add(this);
        return this;
    }

    public Huishouden removeMaaktonderdeeluitvanClient(Client client) {
        this.maaktonderdeeluitvanClients.remove(client);
        client.getMaaktonderdeeluitvanHuishoudens().remove(this);
        return this;
    }

    public Set<Relatie> getMaaktonderdeelvanRelaties() {
        return this.maaktonderdeelvanRelaties;
    }

    public void setMaaktonderdeelvanRelaties(Set<Relatie> relaties) {
        if (this.maaktonderdeelvanRelaties != null) {
            this.maaktonderdeelvanRelaties.forEach(i -> i.removeMaaktonderdeelvanHuishouden(this));
        }
        if (relaties != null) {
            relaties.forEach(i -> i.addMaaktonderdeelvanHuishouden(this));
        }
        this.maaktonderdeelvanRelaties = relaties;
    }

    public Huishouden maaktonderdeelvanRelaties(Set<Relatie> relaties) {
        this.setMaaktonderdeelvanRelaties(relaties);
        return this;
    }

    public Huishouden addMaaktonderdeelvanRelatie(Relatie relatie) {
        this.maaktonderdeelvanRelaties.add(relatie);
        relatie.getMaaktonderdeelvanHuishoudens().add(this);
        return this;
    }

    public Huishouden removeMaaktonderdeelvanRelatie(Relatie relatie) {
        this.maaktonderdeelvanRelaties.remove(relatie);
        relatie.getMaaktonderdeelvanHuishoudens().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Huishouden)) {
            return false;
        }
        return getId() != null && getId().equals(((Huishouden) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Huishouden{" +
            "id=" + getId() +
            "}";
    }
}
