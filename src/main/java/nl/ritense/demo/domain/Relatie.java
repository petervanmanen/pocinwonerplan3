package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Relatie.
 */
@Entity
@Table(name = "relatie")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Relatie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "relatiesoort")
    private String relatiesoort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "issoortRelaties" }, allowSetters = true)
    private Relatiesoort issoortRelatiesoort;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_relatie__maaktonderdeelvan_huishouden",
        joinColumns = @JoinColumn(name = "relatie_id"),
        inverseJoinColumns = @JoinColumn(name = "maaktonderdeelvan_huishouden_id")
    )
    @JsonIgnoreProperties(value = { "maaktonderdeeluitvanClients", "maaktonderdeelvanRelaties" }, allowSetters = true)
    private Set<Huishouden> maaktonderdeelvanHuishoudens = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "heeftrelatieRelaties")
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
    private Set<Client> heeftrelatieClients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Relatie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRelatiesoort() {
        return this.relatiesoort;
    }

    public Relatie relatiesoort(String relatiesoort) {
        this.setRelatiesoort(relatiesoort);
        return this;
    }

    public void setRelatiesoort(String relatiesoort) {
        this.relatiesoort = relatiesoort;
    }

    public Relatiesoort getIssoortRelatiesoort() {
        return this.issoortRelatiesoort;
    }

    public void setIssoortRelatiesoort(Relatiesoort relatiesoort) {
        this.issoortRelatiesoort = relatiesoort;
    }

    public Relatie issoortRelatiesoort(Relatiesoort relatiesoort) {
        this.setIssoortRelatiesoort(relatiesoort);
        return this;
    }

    public Set<Huishouden> getMaaktonderdeelvanHuishoudens() {
        return this.maaktonderdeelvanHuishoudens;
    }

    public void setMaaktonderdeelvanHuishoudens(Set<Huishouden> huishoudens) {
        this.maaktonderdeelvanHuishoudens = huishoudens;
    }

    public Relatie maaktonderdeelvanHuishoudens(Set<Huishouden> huishoudens) {
        this.setMaaktonderdeelvanHuishoudens(huishoudens);
        return this;
    }

    public Relatie addMaaktonderdeelvanHuishouden(Huishouden huishouden) {
        this.maaktonderdeelvanHuishoudens.add(huishouden);
        return this;
    }

    public Relatie removeMaaktonderdeelvanHuishouden(Huishouden huishouden) {
        this.maaktonderdeelvanHuishoudens.remove(huishouden);
        return this;
    }

    public Set<Client> getHeeftrelatieClients() {
        return this.heeftrelatieClients;
    }

    public void setHeeftrelatieClients(Set<Client> clients) {
        if (this.heeftrelatieClients != null) {
            this.heeftrelatieClients.forEach(i -> i.removeHeeftrelatieRelatie(this));
        }
        if (clients != null) {
            clients.forEach(i -> i.addHeeftrelatieRelatie(this));
        }
        this.heeftrelatieClients = clients;
    }

    public Relatie heeftrelatieClients(Set<Client> clients) {
        this.setHeeftrelatieClients(clients);
        return this;
    }

    public Relatie addHeeftrelatieClient(Client client) {
        this.heeftrelatieClients.add(client);
        client.getHeeftrelatieRelaties().add(this);
        return this;
    }

    public Relatie removeHeeftrelatieClient(Client client) {
        this.heeftrelatieClients.remove(client);
        client.getHeeftrelatieRelaties().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Relatie)) {
            return false;
        }
        return getId() != null && getId().equals(((Relatie) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Relatie{" +
            "id=" + getId() +
            ", relatiesoort='" + getRelatiesoort() + "'" +
            "}";
    }
}
