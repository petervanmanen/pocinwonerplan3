package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Score.
 */
@Entity
@Table(name = "score")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Score implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "datum")
    private String datum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "scorebijleeggebiedScores" }, allowSetters = true)
    private Leefgebied scorebijleeggebiedLeefgebied;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "hoogtescoreScores" }, allowSetters = true)
    private Scoresoort hoogtescoreScoresoort;

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
    private Client heeftClient;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Score id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatum() {
        return this.datum;
    }

    public Score datum(String datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public Leefgebied getScorebijleeggebiedLeefgebied() {
        return this.scorebijleeggebiedLeefgebied;
    }

    public void setScorebijleeggebiedLeefgebied(Leefgebied leefgebied) {
        this.scorebijleeggebiedLeefgebied = leefgebied;
    }

    public Score scorebijleeggebiedLeefgebied(Leefgebied leefgebied) {
        this.setScorebijleeggebiedLeefgebied(leefgebied);
        return this;
    }

    public Scoresoort getHoogtescoreScoresoort() {
        return this.hoogtescoreScoresoort;
    }

    public void setHoogtescoreScoresoort(Scoresoort scoresoort) {
        this.hoogtescoreScoresoort = scoresoort;
    }

    public Score hoogtescoreScoresoort(Scoresoort scoresoort) {
        this.setHoogtescoreScoresoort(scoresoort);
        return this;
    }

    public Client getHeeftClient() {
        return this.heeftClient;
    }

    public void setHeeftClient(Client client) {
        this.heeftClient = client;
    }

    public Score heeftClient(Client client) {
        this.setHeeftClient(client);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Score)) {
            return false;
        }
        return getId() != null && getId().equals(((Score) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Score{" +
            "id=" + getId() +
            ", datum='" + getDatum() + "'" +
            "}";
    }
}
