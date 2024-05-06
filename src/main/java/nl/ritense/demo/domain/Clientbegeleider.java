package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Clientbegeleider.
 */
@Entity
@Table(name = "clientbegeleider")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Clientbegeleider implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "begeleiderscode")
    private String begeleiderscode;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "geeftafClientbegeleider")
    @JsonIgnoreProperties(
        value = {
            "toewijzingToewijzings",
            "emptyClient",
            "geeftafClientbegeleider",
            "isgebaseerdopBeperkings",
            "geleverdeprestatieLeverings",
            "isvoorDeclaratieregels",
        },
        allowSetters = true
    )
    private Set<Beschikking> geeftafBeschikkings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "emptyClientbegeleider")
    @JsonIgnoreProperties(
        value = {
            "heeftvoorzieningClient",
            "emptyClientbegeleider",
            "issoortvoorzieningInkomensvoorzieningsoort",
            "voorzieningbijstandspartijClients",
        },
        allowSetters = true
    )
    private Set<Inkomensvoorziening> emptyInkomensvoorzienings = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "maaktonderdeeluitvanClientbegeleiders" }, allowSetters = true)
    private Team maaktonderdeeluitvanTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftresultaatResultaat",
            "heeftuitstroomredenUitstroomreden",
            "heeftprojectProjects",
            "istrajectsoortTrajectsoort",
            "heeftparticipatietrajectClient",
            "heefttrajectClient",
            "begeleidtClientbegeleiders",
        },
        allowSetters = true
    )
    private Traject begeleidtTraject;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_clientbegeleider__ondersteuntclient_client",
        joinColumns = @JoinColumn(name = "clientbegeleider_id"),
        inverseJoinColumns = @JoinColumn(name = "ondersteuntclient_client_id")
    )
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
    private Set<Client> ondersteuntclientClients = new HashSet<>();

    @JsonIgnoreProperties(value = { "emptyClientbegeleider", "heeftClient" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "emptyClientbegeleider")
    private Participatiedossier emptyParticipatiedossier;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Clientbegeleider id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBegeleiderscode() {
        return this.begeleiderscode;
    }

    public Clientbegeleider begeleiderscode(String begeleiderscode) {
        this.setBegeleiderscode(begeleiderscode);
        return this;
    }

    public void setBegeleiderscode(String begeleiderscode) {
        this.begeleiderscode = begeleiderscode;
    }

    public Set<Beschikking> getGeeftafBeschikkings() {
        return this.geeftafBeschikkings;
    }

    public void setGeeftafBeschikkings(Set<Beschikking> beschikkings) {
        if (this.geeftafBeschikkings != null) {
            this.geeftafBeschikkings.forEach(i -> i.setGeeftafClientbegeleider(null));
        }
        if (beschikkings != null) {
            beschikkings.forEach(i -> i.setGeeftafClientbegeleider(this));
        }
        this.geeftafBeschikkings = beschikkings;
    }

    public Clientbegeleider geeftafBeschikkings(Set<Beschikking> beschikkings) {
        this.setGeeftafBeschikkings(beschikkings);
        return this;
    }

    public Clientbegeleider addGeeftafBeschikking(Beschikking beschikking) {
        this.geeftafBeschikkings.add(beschikking);
        beschikking.setGeeftafClientbegeleider(this);
        return this;
    }

    public Clientbegeleider removeGeeftafBeschikking(Beschikking beschikking) {
        this.geeftafBeschikkings.remove(beschikking);
        beschikking.setGeeftafClientbegeleider(null);
        return this;
    }

    public Set<Inkomensvoorziening> getEmptyInkomensvoorzienings() {
        return this.emptyInkomensvoorzienings;
    }

    public void setEmptyInkomensvoorzienings(Set<Inkomensvoorziening> inkomensvoorzienings) {
        if (this.emptyInkomensvoorzienings != null) {
            this.emptyInkomensvoorzienings.forEach(i -> i.setEmptyClientbegeleider(null));
        }
        if (inkomensvoorzienings != null) {
            inkomensvoorzienings.forEach(i -> i.setEmptyClientbegeleider(this));
        }
        this.emptyInkomensvoorzienings = inkomensvoorzienings;
    }

    public Clientbegeleider emptyInkomensvoorzienings(Set<Inkomensvoorziening> inkomensvoorzienings) {
        this.setEmptyInkomensvoorzienings(inkomensvoorzienings);
        return this;
    }

    public Clientbegeleider addEmptyInkomensvoorziening(Inkomensvoorziening inkomensvoorziening) {
        this.emptyInkomensvoorzienings.add(inkomensvoorziening);
        inkomensvoorziening.setEmptyClientbegeleider(this);
        return this;
    }

    public Clientbegeleider removeEmptyInkomensvoorziening(Inkomensvoorziening inkomensvoorziening) {
        this.emptyInkomensvoorzienings.remove(inkomensvoorziening);
        inkomensvoorziening.setEmptyClientbegeleider(null);
        return this;
    }

    public Team getMaaktonderdeeluitvanTeam() {
        return this.maaktonderdeeluitvanTeam;
    }

    public void setMaaktonderdeeluitvanTeam(Team team) {
        this.maaktonderdeeluitvanTeam = team;
    }

    public Clientbegeleider maaktonderdeeluitvanTeam(Team team) {
        this.setMaaktonderdeeluitvanTeam(team);
        return this;
    }

    public Traject getBegeleidtTraject() {
        return this.begeleidtTraject;
    }

    public void setBegeleidtTraject(Traject traject) {
        this.begeleidtTraject = traject;
    }

    public Clientbegeleider begeleidtTraject(Traject traject) {
        this.setBegeleidtTraject(traject);
        return this;
    }

    public Set<Client> getOndersteuntclientClients() {
        return this.ondersteuntclientClients;
    }

    public void setOndersteuntclientClients(Set<Client> clients) {
        this.ondersteuntclientClients = clients;
    }

    public Clientbegeleider ondersteuntclientClients(Set<Client> clients) {
        this.setOndersteuntclientClients(clients);
        return this;
    }

    public Clientbegeleider addOndersteuntclientClient(Client client) {
        this.ondersteuntclientClients.add(client);
        return this;
    }

    public Clientbegeleider removeOndersteuntclientClient(Client client) {
        this.ondersteuntclientClients.remove(client);
        return this;
    }

    public Participatiedossier getEmptyParticipatiedossier() {
        return this.emptyParticipatiedossier;
    }

    public void setEmptyParticipatiedossier(Participatiedossier participatiedossier) {
        if (this.emptyParticipatiedossier != null) {
            this.emptyParticipatiedossier.setEmptyClientbegeleider(null);
        }
        if (participatiedossier != null) {
            participatiedossier.setEmptyClientbegeleider(this);
        }
        this.emptyParticipatiedossier = participatiedossier;
    }

    public Clientbegeleider emptyParticipatiedossier(Participatiedossier participatiedossier) {
        this.setEmptyParticipatiedossier(participatiedossier);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Clientbegeleider)) {
            return false;
        }
        return getId() != null && getId().equals(((Clientbegeleider) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Clientbegeleider{" +
            "id=" + getId() +
            ", begeleiderscode='" + getBegeleiderscode() + "'" +
            "}";
    }
}
