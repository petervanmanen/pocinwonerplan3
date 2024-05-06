package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Inkomensvoorziening.
 */
@Entity
@Table(name = "inkomensvoorziening")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Inkomensvoorziening implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bedrag")
    private String bedrag;

    @Column(name = "datumeinde")
    private String datumeinde;

    @Column(name = "datumstart")
    private String datumstart;

    @Column(name = "datumtoekenning")
    private String datumtoekenning;

    @Column(name = "eenmalig")
    private Boolean eenmalig;

    @Size(max = 100)
    @Column(name = "groep", length = 100)
    private String groep;

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
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "heeftvoorzieningInkomensvoorziening")
    private Client heeftvoorzieningClient;

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Clientbegeleider emptyClientbegeleider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "issoortvoorzieningInkomensvoorzienings" }, allowSetters = true)
    private Inkomensvoorzieningsoort issoortvoorzieningInkomensvoorzieningsoort;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "voorzieningbijstandspartijInkomensvoorzienings")
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
    private Set<Client> voorzieningbijstandspartijClients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Inkomensvoorziening id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBedrag() {
        return this.bedrag;
    }

    public Inkomensvoorziening bedrag(String bedrag) {
        this.setBedrag(bedrag);
        return this;
    }

    public void setBedrag(String bedrag) {
        this.bedrag = bedrag;
    }

    public String getDatumeinde() {
        return this.datumeinde;
    }

    public Inkomensvoorziening datumeinde(String datumeinde) {
        this.setDatumeinde(datumeinde);
        return this;
    }

    public void setDatumeinde(String datumeinde) {
        this.datumeinde = datumeinde;
    }

    public String getDatumstart() {
        return this.datumstart;
    }

    public Inkomensvoorziening datumstart(String datumstart) {
        this.setDatumstart(datumstart);
        return this;
    }

    public void setDatumstart(String datumstart) {
        this.datumstart = datumstart;
    }

    public String getDatumtoekenning() {
        return this.datumtoekenning;
    }

    public Inkomensvoorziening datumtoekenning(String datumtoekenning) {
        this.setDatumtoekenning(datumtoekenning);
        return this;
    }

    public void setDatumtoekenning(String datumtoekenning) {
        this.datumtoekenning = datumtoekenning;
    }

    public Boolean getEenmalig() {
        return this.eenmalig;
    }

    public Inkomensvoorziening eenmalig(Boolean eenmalig) {
        this.setEenmalig(eenmalig);
        return this;
    }

    public void setEenmalig(Boolean eenmalig) {
        this.eenmalig = eenmalig;
    }

    public String getGroep() {
        return this.groep;
    }

    public Inkomensvoorziening groep(String groep) {
        this.setGroep(groep);
        return this;
    }

    public void setGroep(String groep) {
        this.groep = groep;
    }

    public Client getHeeftvoorzieningClient() {
        return this.heeftvoorzieningClient;
    }

    public void setHeeftvoorzieningClient(Client client) {
        if (this.heeftvoorzieningClient != null) {
            this.heeftvoorzieningClient.setHeeftvoorzieningInkomensvoorziening(null);
        }
        if (client != null) {
            client.setHeeftvoorzieningInkomensvoorziening(this);
        }
        this.heeftvoorzieningClient = client;
    }

    public Inkomensvoorziening heeftvoorzieningClient(Client client) {
        this.setHeeftvoorzieningClient(client);
        return this;
    }

    public Clientbegeleider getEmptyClientbegeleider() {
        return this.emptyClientbegeleider;
    }

    public void setEmptyClientbegeleider(Clientbegeleider clientbegeleider) {
        this.emptyClientbegeleider = clientbegeleider;
    }

    public Inkomensvoorziening emptyClientbegeleider(Clientbegeleider clientbegeleider) {
        this.setEmptyClientbegeleider(clientbegeleider);
        return this;
    }

    public Inkomensvoorzieningsoort getIssoortvoorzieningInkomensvoorzieningsoort() {
        return this.issoortvoorzieningInkomensvoorzieningsoort;
    }

    public void setIssoortvoorzieningInkomensvoorzieningsoort(Inkomensvoorzieningsoort inkomensvoorzieningsoort) {
        this.issoortvoorzieningInkomensvoorzieningsoort = inkomensvoorzieningsoort;
    }

    public Inkomensvoorziening issoortvoorzieningInkomensvoorzieningsoort(Inkomensvoorzieningsoort inkomensvoorzieningsoort) {
        this.setIssoortvoorzieningInkomensvoorzieningsoort(inkomensvoorzieningsoort);
        return this;
    }

    public Set<Client> getVoorzieningbijstandspartijClients() {
        return this.voorzieningbijstandspartijClients;
    }

    public void setVoorzieningbijstandspartijClients(Set<Client> clients) {
        if (this.voorzieningbijstandspartijClients != null) {
            this.voorzieningbijstandspartijClients.forEach(i -> i.removeVoorzieningbijstandspartijInkomensvoorziening(this));
        }
        if (clients != null) {
            clients.forEach(i -> i.addVoorzieningbijstandspartijInkomensvoorziening(this));
        }
        this.voorzieningbijstandspartijClients = clients;
    }

    public Inkomensvoorziening voorzieningbijstandspartijClients(Set<Client> clients) {
        this.setVoorzieningbijstandspartijClients(clients);
        return this;
    }

    public Inkomensvoorziening addVoorzieningbijstandspartijClient(Client client) {
        this.voorzieningbijstandspartijClients.add(client);
        client.getVoorzieningbijstandspartijInkomensvoorzienings().add(this);
        return this;
    }

    public Inkomensvoorziening removeVoorzieningbijstandspartijClient(Client client) {
        this.voorzieningbijstandspartijClients.remove(client);
        client.getVoorzieningbijstandspartijInkomensvoorzienings().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inkomensvoorziening)) {
            return false;
        }
        return getId() != null && getId().equals(((Inkomensvoorziening) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inkomensvoorziening{" +
            "id=" + getId() +
            ", bedrag='" + getBedrag() + "'" +
            ", datumeinde='" + getDatumeinde() + "'" +
            ", datumstart='" + getDatumstart() + "'" +
            ", datumtoekenning='" + getDatumtoekenning() + "'" +
            ", eenmalig='" + getEenmalig() + "'" +
            ", groep='" + getGroep() + "'" +
            "}";
    }
}
