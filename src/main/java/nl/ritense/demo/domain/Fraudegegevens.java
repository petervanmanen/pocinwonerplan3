package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Fraudegegevens.
 */
@Entity
@Table(name = "fraudegegevens")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Fraudegegevens implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bedragfraude")
    private String bedragfraude;

    @Column(name = "datumeindefraude")
    private String datumeindefraude;

    @Column(name = "datumgeconstateerd")
    private String datumgeconstateerd;

    @Column(name = "datumstartfraude")
    private String datumstartfraude;

    @Column(name = "verrekening")
    private String verrekening;

    @Column(name = "vorderingen")
    private String vorderingen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "isvansoortFraudegegevens" }, allowSetters = true)
    private Fraudesoort isvansoortFraudesoort;

    @ManyToOne(fetch = FetchType.LAZY)
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
    private Client heeftfraudegegevensClient;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Fraudegegevens id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBedragfraude() {
        return this.bedragfraude;
    }

    public Fraudegegevens bedragfraude(String bedragfraude) {
        this.setBedragfraude(bedragfraude);
        return this;
    }

    public void setBedragfraude(String bedragfraude) {
        this.bedragfraude = bedragfraude;
    }

    public String getDatumeindefraude() {
        return this.datumeindefraude;
    }

    public Fraudegegevens datumeindefraude(String datumeindefraude) {
        this.setDatumeindefraude(datumeindefraude);
        return this;
    }

    public void setDatumeindefraude(String datumeindefraude) {
        this.datumeindefraude = datumeindefraude;
    }

    public String getDatumgeconstateerd() {
        return this.datumgeconstateerd;
    }

    public Fraudegegevens datumgeconstateerd(String datumgeconstateerd) {
        this.setDatumgeconstateerd(datumgeconstateerd);
        return this;
    }

    public void setDatumgeconstateerd(String datumgeconstateerd) {
        this.datumgeconstateerd = datumgeconstateerd;
    }

    public String getDatumstartfraude() {
        return this.datumstartfraude;
    }

    public Fraudegegevens datumstartfraude(String datumstartfraude) {
        this.setDatumstartfraude(datumstartfraude);
        return this;
    }

    public void setDatumstartfraude(String datumstartfraude) {
        this.datumstartfraude = datumstartfraude;
    }

    public String getVerrekening() {
        return this.verrekening;
    }

    public Fraudegegevens verrekening(String verrekening) {
        this.setVerrekening(verrekening);
        return this;
    }

    public void setVerrekening(String verrekening) {
        this.verrekening = verrekening;
    }

    public String getVorderingen() {
        return this.vorderingen;
    }

    public Fraudegegevens vorderingen(String vorderingen) {
        this.setVorderingen(vorderingen);
        return this;
    }

    public void setVorderingen(String vorderingen) {
        this.vorderingen = vorderingen;
    }

    public Fraudesoort getIsvansoortFraudesoort() {
        return this.isvansoortFraudesoort;
    }

    public void setIsvansoortFraudesoort(Fraudesoort fraudesoort) {
        this.isvansoortFraudesoort = fraudesoort;
    }

    public Fraudegegevens isvansoortFraudesoort(Fraudesoort fraudesoort) {
        this.setIsvansoortFraudesoort(fraudesoort);
        return this;
    }

    public Client getHeeftfraudegegevensClient() {
        return this.heeftfraudegegevensClient;
    }

    public void setHeeftfraudegegevensClient(Client client) {
        this.heeftfraudegegevensClient = client;
    }

    public Fraudegegevens heeftfraudegegevensClient(Client client) {
        this.setHeeftfraudegegevensClient(client);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fraudegegevens)) {
            return false;
        }
        return getId() != null && getId().equals(((Fraudegegevens) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fraudegegevens{" +
            "id=" + getId() +
            ", bedragfraude='" + getBedragfraude() + "'" +
            ", datumeindefraude='" + getDatumeindefraude() + "'" +
            ", datumgeconstateerd='" + getDatumgeconstateerd() + "'" +
            ", datumstartfraude='" + getDatumstartfraude() + "'" +
            ", verrekening='" + getVerrekening() + "'" +
            ", vorderingen='" + getVorderingen() + "'" +
            "}";
    }
}
