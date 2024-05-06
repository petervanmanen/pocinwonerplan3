package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Beschikking.
 */
@Entity
@Table(name = "beschikking")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Beschikking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 20)
    @Column(name = "code", length = 20)
    private String code;

    @Column(name = "commentaar")
    private String commentaar;

    @Column(name = "datumafgifte")
    private LocalDate datumafgifte;

    @Column(name = "grondslagen")
    private String grondslagen;

    @Column(name = "wet")
    private String wet;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "toewijzingBeschikking")
    @JsonIgnoreProperties(
        value = { "isopbasisvanDeclaratieregels", "levertvoorzieningLeverancier", "toewijzingBeschikking", "geleverdezorgLeverings" },
        allowSetters = true
    )
    private Set<Toewijzing> toewijzingToewijzings = new HashSet<>();

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
    private Client emptyClient;

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
    private Clientbegeleider geeftafClientbegeleider;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "isgebaseerdopBeschikking")
    @JsonIgnoreProperties(value = { "emptyBeperkingscores", "iseenBeperkingscategorie", "isgebaseerdopBeschikking" }, allowSetters = true)
    private Set<Beperking> isgebaseerdopBeperkings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "geleverdeprestatieBeschikking")
    @JsonIgnoreProperties(
        value = {
            "geleverdeprestatieBeschikking",
            "prestatievoorClient",
            "geleverdezorgToewijzing",
            "voorzieningVoorziening",
            "leverdeprestatieLeverancier",
        },
        allowSetters = true
    )
    private Set<Levering> geleverdeprestatieLeverings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "isvoorBeschikking")
    @JsonIgnoreProperties(
        value = { "isvoorBeschikking", "betreftClient", "valtbinnenDeclaratie", "isopbasisvanToewijzing" },
        allowSetters = true
    )
    private Set<Declaratieregel> isvoorDeclaratieregels = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Beschikking id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Beschikking code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCommentaar() {
        return this.commentaar;
    }

    public Beschikking commentaar(String commentaar) {
        this.setCommentaar(commentaar);
        return this;
    }

    public void setCommentaar(String commentaar) {
        this.commentaar = commentaar;
    }

    public LocalDate getDatumafgifte() {
        return this.datumafgifte;
    }

    public Beschikking datumafgifte(LocalDate datumafgifte) {
        this.setDatumafgifte(datumafgifte);
        return this;
    }

    public void setDatumafgifte(LocalDate datumafgifte) {
        this.datumafgifte = datumafgifte;
    }

    public String getGrondslagen() {
        return this.grondslagen;
    }

    public Beschikking grondslagen(String grondslagen) {
        this.setGrondslagen(grondslagen);
        return this;
    }

    public void setGrondslagen(String grondslagen) {
        this.grondslagen = grondslagen;
    }

    public String getWet() {
        return this.wet;
    }

    public Beschikking wet(String wet) {
        this.setWet(wet);
        return this;
    }

    public void setWet(String wet) {
        this.wet = wet;
    }

    public Set<Toewijzing> getToewijzingToewijzings() {
        return this.toewijzingToewijzings;
    }

    public void setToewijzingToewijzings(Set<Toewijzing> toewijzings) {
        if (this.toewijzingToewijzings != null) {
            this.toewijzingToewijzings.forEach(i -> i.setToewijzingBeschikking(null));
        }
        if (toewijzings != null) {
            toewijzings.forEach(i -> i.setToewijzingBeschikking(this));
        }
        this.toewijzingToewijzings = toewijzings;
    }

    public Beschikking toewijzingToewijzings(Set<Toewijzing> toewijzings) {
        this.setToewijzingToewijzings(toewijzings);
        return this;
    }

    public Beschikking addToewijzingToewijzing(Toewijzing toewijzing) {
        this.toewijzingToewijzings.add(toewijzing);
        toewijzing.setToewijzingBeschikking(this);
        return this;
    }

    public Beschikking removeToewijzingToewijzing(Toewijzing toewijzing) {
        this.toewijzingToewijzings.remove(toewijzing);
        toewijzing.setToewijzingBeschikking(null);
        return this;
    }

    public Client getEmptyClient() {
        return this.emptyClient;
    }

    public void setEmptyClient(Client client) {
        this.emptyClient = client;
    }

    public Beschikking emptyClient(Client client) {
        this.setEmptyClient(client);
        return this;
    }

    public Clientbegeleider getGeeftafClientbegeleider() {
        return this.geeftafClientbegeleider;
    }

    public void setGeeftafClientbegeleider(Clientbegeleider clientbegeleider) {
        this.geeftafClientbegeleider = clientbegeleider;
    }

    public Beschikking geeftafClientbegeleider(Clientbegeleider clientbegeleider) {
        this.setGeeftafClientbegeleider(clientbegeleider);
        return this;
    }

    public Set<Beperking> getIsgebaseerdopBeperkings() {
        return this.isgebaseerdopBeperkings;
    }

    public void setIsgebaseerdopBeperkings(Set<Beperking> beperkings) {
        if (this.isgebaseerdopBeperkings != null) {
            this.isgebaseerdopBeperkings.forEach(i -> i.setIsgebaseerdopBeschikking(null));
        }
        if (beperkings != null) {
            beperkings.forEach(i -> i.setIsgebaseerdopBeschikking(this));
        }
        this.isgebaseerdopBeperkings = beperkings;
    }

    public Beschikking isgebaseerdopBeperkings(Set<Beperking> beperkings) {
        this.setIsgebaseerdopBeperkings(beperkings);
        return this;
    }

    public Beschikking addIsgebaseerdopBeperking(Beperking beperking) {
        this.isgebaseerdopBeperkings.add(beperking);
        beperking.setIsgebaseerdopBeschikking(this);
        return this;
    }

    public Beschikking removeIsgebaseerdopBeperking(Beperking beperking) {
        this.isgebaseerdopBeperkings.remove(beperking);
        beperking.setIsgebaseerdopBeschikking(null);
        return this;
    }

    public Set<Levering> getGeleverdeprestatieLeverings() {
        return this.geleverdeprestatieLeverings;
    }

    public void setGeleverdeprestatieLeverings(Set<Levering> leverings) {
        if (this.geleverdeprestatieLeverings != null) {
            this.geleverdeprestatieLeverings.forEach(i -> i.setGeleverdeprestatieBeschikking(null));
        }
        if (leverings != null) {
            leverings.forEach(i -> i.setGeleverdeprestatieBeschikking(this));
        }
        this.geleverdeprestatieLeverings = leverings;
    }

    public Beschikking geleverdeprestatieLeverings(Set<Levering> leverings) {
        this.setGeleverdeprestatieLeverings(leverings);
        return this;
    }

    public Beschikking addGeleverdeprestatieLevering(Levering levering) {
        this.geleverdeprestatieLeverings.add(levering);
        levering.setGeleverdeprestatieBeschikking(this);
        return this;
    }

    public Beschikking removeGeleverdeprestatieLevering(Levering levering) {
        this.geleverdeprestatieLeverings.remove(levering);
        levering.setGeleverdeprestatieBeschikking(null);
        return this;
    }

    public Set<Declaratieregel> getIsvoorDeclaratieregels() {
        return this.isvoorDeclaratieregels;
    }

    public void setIsvoorDeclaratieregels(Set<Declaratieregel> declaratieregels) {
        if (this.isvoorDeclaratieregels != null) {
            this.isvoorDeclaratieregels.forEach(i -> i.setIsvoorBeschikking(null));
        }
        if (declaratieregels != null) {
            declaratieregels.forEach(i -> i.setIsvoorBeschikking(this));
        }
        this.isvoorDeclaratieregels = declaratieregels;
    }

    public Beschikking isvoorDeclaratieregels(Set<Declaratieregel> declaratieregels) {
        this.setIsvoorDeclaratieregels(declaratieregels);
        return this;
    }

    public Beschikking addIsvoorDeclaratieregel(Declaratieregel declaratieregel) {
        this.isvoorDeclaratieregels.add(declaratieregel);
        declaratieregel.setIsvoorBeschikking(this);
        return this;
    }

    public Beschikking removeIsvoorDeclaratieregel(Declaratieregel declaratieregel) {
        this.isvoorDeclaratieregels.remove(declaratieregel);
        declaratieregel.setIsvoorBeschikking(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Beschikking)) {
            return false;
        }
        return getId() != null && getId().equals(((Beschikking) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Beschikking{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", commentaar='" + getCommentaar() + "'" +
            ", datumafgifte='" + getDatumafgifte() + "'" +
            ", grondslagen='" + getGrondslagen() + "'" +
            ", wet='" + getWet() + "'" +
            "}";
    }
}
