package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Taalniveau.
 */
@Entity
@Table(name = "taalniveau")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Taalniveau implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 8)
    @Column(name = "mondeling", length = 8)
    private String mondeling;

    @Size(max = 8)
    @Column(name = "schriftelijk", length = 8)
    private String schriftelijk;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "heefttaalniveauTaalniveaus")
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
    private Set<Client> heefttaalniveauClients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Taalniveau id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMondeling() {
        return this.mondeling;
    }

    public Taalniveau mondeling(String mondeling) {
        this.setMondeling(mondeling);
        return this;
    }

    public void setMondeling(String mondeling) {
        this.mondeling = mondeling;
    }

    public String getSchriftelijk() {
        return this.schriftelijk;
    }

    public Taalniveau schriftelijk(String schriftelijk) {
        this.setSchriftelijk(schriftelijk);
        return this;
    }

    public void setSchriftelijk(String schriftelijk) {
        this.schriftelijk = schriftelijk;
    }

    public Set<Client> getHeefttaalniveauClients() {
        return this.heefttaalniveauClients;
    }

    public void setHeefttaalniveauClients(Set<Client> clients) {
        if (this.heefttaalniveauClients != null) {
            this.heefttaalniveauClients.forEach(i -> i.removeHeefttaalniveauTaalniveau(this));
        }
        if (clients != null) {
            clients.forEach(i -> i.addHeefttaalniveauTaalniveau(this));
        }
        this.heefttaalniveauClients = clients;
    }

    public Taalniveau heefttaalniveauClients(Set<Client> clients) {
        this.setHeefttaalniveauClients(clients);
        return this;
    }

    public Taalniveau addHeefttaalniveauClient(Client client) {
        this.heefttaalniveauClients.add(client);
        client.getHeefttaalniveauTaalniveaus().add(this);
        return this;
    }

    public Taalniveau removeHeefttaalniveauClient(Client client) {
        this.heefttaalniveauClients.remove(client);
        client.getHeefttaalniveauTaalniveaus().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Taalniveau)) {
            return false;
        }
        return getId() != null && getId().equals(((Taalniveau) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Taalniveau{" +
            "id=" + getId() +
            ", mondeling='" + getMondeling() + "'" +
            ", schriftelijk='" + getSchriftelijk() + "'" +
            "}";
    }
}
