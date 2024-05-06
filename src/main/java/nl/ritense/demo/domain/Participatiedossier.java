package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A Participatiedossier.
 */
@Entity
@Table(name = "participatiedossier")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Participatiedossier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 100)
    @Column(name = "arbeidsvermogen", length = 100)
    private String arbeidsvermogen;

    @Column(name = "begeleiderscode")
    private String begeleiderscode;

    @Column(name = "beschutwerk")
    private String beschutwerk;

    @Column(name = "clientbegeleiderid")
    private String clientbegeleiderid;

    @Column(name = "datumeindebegeleiding")
    private String datumeindebegeleiding;

    @Column(name = "datumstartbegeleiding")
    private String datumstartbegeleiding;

    @Column(name = "indicatiedoelgroepregister")
    private String indicatiedoelgroepregister;

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
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Clientbegeleider emptyClientbegeleider;

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
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "heeftParticipatiedossier")
    private Client heeftClient;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Participatiedossier id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArbeidsvermogen() {
        return this.arbeidsvermogen;
    }

    public Participatiedossier arbeidsvermogen(String arbeidsvermogen) {
        this.setArbeidsvermogen(arbeidsvermogen);
        return this;
    }

    public void setArbeidsvermogen(String arbeidsvermogen) {
        this.arbeidsvermogen = arbeidsvermogen;
    }

    public String getBegeleiderscode() {
        return this.begeleiderscode;
    }

    public Participatiedossier begeleiderscode(String begeleiderscode) {
        this.setBegeleiderscode(begeleiderscode);
        return this;
    }

    public void setBegeleiderscode(String begeleiderscode) {
        this.begeleiderscode = begeleiderscode;
    }

    public String getBeschutwerk() {
        return this.beschutwerk;
    }

    public Participatiedossier beschutwerk(String beschutwerk) {
        this.setBeschutwerk(beschutwerk);
        return this;
    }

    public void setBeschutwerk(String beschutwerk) {
        this.beschutwerk = beschutwerk;
    }

    public String getClientbegeleiderid() {
        return this.clientbegeleiderid;
    }

    public Participatiedossier clientbegeleiderid(String clientbegeleiderid) {
        this.setClientbegeleiderid(clientbegeleiderid);
        return this;
    }

    public void setClientbegeleiderid(String clientbegeleiderid) {
        this.clientbegeleiderid = clientbegeleiderid;
    }

    public String getDatumeindebegeleiding() {
        return this.datumeindebegeleiding;
    }

    public Participatiedossier datumeindebegeleiding(String datumeindebegeleiding) {
        this.setDatumeindebegeleiding(datumeindebegeleiding);
        return this;
    }

    public void setDatumeindebegeleiding(String datumeindebegeleiding) {
        this.datumeindebegeleiding = datumeindebegeleiding;
    }

    public String getDatumstartbegeleiding() {
        return this.datumstartbegeleiding;
    }

    public Participatiedossier datumstartbegeleiding(String datumstartbegeleiding) {
        this.setDatumstartbegeleiding(datumstartbegeleiding);
        return this;
    }

    public void setDatumstartbegeleiding(String datumstartbegeleiding) {
        this.datumstartbegeleiding = datumstartbegeleiding;
    }

    public String getIndicatiedoelgroepregister() {
        return this.indicatiedoelgroepregister;
    }

    public Participatiedossier indicatiedoelgroepregister(String indicatiedoelgroepregister) {
        this.setIndicatiedoelgroepregister(indicatiedoelgroepregister);
        return this;
    }

    public void setIndicatiedoelgroepregister(String indicatiedoelgroepregister) {
        this.indicatiedoelgroepregister = indicatiedoelgroepregister;
    }

    public Clientbegeleider getEmptyClientbegeleider() {
        return this.emptyClientbegeleider;
    }

    public void setEmptyClientbegeleider(Clientbegeleider clientbegeleider) {
        this.emptyClientbegeleider = clientbegeleider;
    }

    public Participatiedossier emptyClientbegeleider(Clientbegeleider clientbegeleider) {
        this.setEmptyClientbegeleider(clientbegeleider);
        return this;
    }

    public Client getHeeftClient() {
        return this.heeftClient;
    }

    public void setHeeftClient(Client client) {
        if (this.heeftClient != null) {
            this.heeftClient.setHeeftParticipatiedossier(null);
        }
        if (client != null) {
            client.setHeeftParticipatiedossier(this);
        }
        this.heeftClient = client;
    }

    public Participatiedossier heeftClient(Client client) {
        this.setHeeftClient(client);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Participatiedossier)) {
            return false;
        }
        return getId() != null && getId().equals(((Participatiedossier) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Participatiedossier{" +
            "id=" + getId() +
            ", arbeidsvermogen='" + getArbeidsvermogen() + "'" +
            ", begeleiderscode='" + getBegeleiderscode() + "'" +
            ", beschutwerk='" + getBeschutwerk() + "'" +
            ", clientbegeleiderid='" + getClientbegeleiderid() + "'" +
            ", datumeindebegeleiding='" + getDatumeindebegeleiding() + "'" +
            ", datumstartbegeleiding='" + getDatumstartbegeleiding() + "'" +
            ", indicatiedoelgroepregister='" + getIndicatiedoelgroepregister() + "'" +
            "}";
    }
}
