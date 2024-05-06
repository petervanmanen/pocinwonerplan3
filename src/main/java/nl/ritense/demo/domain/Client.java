package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "gezagsdragergekend")
    private Boolean gezagsdragergekend;

    @Column(name = "juridischestatus")
    private String juridischestatus;

    @Column(name = "wettelijkevertegenwoordiging")
    private String wettelijkevertegenwoordiging;

    @JsonIgnoreProperties(value = { "emptyClientbegeleider", "heeftClient" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Participatiedossier heeftParticipatiedossier;

    @JsonIgnoreProperties(
        value = {
            "heeftvoorzieningClient",
            "emptyClientbegeleider",
            "issoortvoorzieningInkomensvoorzieningsoort",
            "voorzieningbijstandspartijClients",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Inkomensvoorziening heeftvoorzieningInkomensvoorziening;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftClient")
    @JsonIgnoreProperties(value = { "scorebijleeggebiedLeefgebied", "hoogtescoreScoresoort", "heeftClient" }, allowSetters = true)
    private Set<Score> heeftScores = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "leverttegenprestatieClient")
    @JsonIgnoreProperties(value = { "leverttegenprestatieClient" }, allowSetters = true)
    private Set<Tegenprestatie> leverttegenprestatieTegenprestaties = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftparticipatietrajectClient")
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
    private Set<Traject> heeftparticipatietrajectTrajects = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftfraudegegevensClient")
    @JsonIgnoreProperties(value = { "isvansoortFraudesoort", "heeftfraudegegevensClient" }, allowSetters = true)
    private Set<Fraudegegevens> heeftfraudegegevensFraudegegevens = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftregelingClient")
    @JsonIgnoreProperties(value = { "isregelingsoortRegelingsoort", "heeftregelingClient" }, allowSetters = true)
    private Set<Regeling> heeftregelingRegelings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heefttrajectClient")
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
    private Set<Traject> heefttrajectTrajects = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "bestaatuitDoelgroeps", "bestaatuitDoelgroep2", "valtbinnendoelgroepClients", "valtbinnenMuseumrelaties" },
        allowSetters = true
    )
    private Doelgroep valtbinnendoelgroepDoelgroep;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_client__heeftrelatie_relatie",
        joinColumns = @JoinColumn(name = "client_id"),
        inverseJoinColumns = @JoinColumn(name = "heeftrelatie_relatie_id")
    )
    @JsonIgnoreProperties(value = { "issoortRelatiesoort", "maaktonderdeelvanHuishoudens", "heeftrelatieClients" }, allowSetters = true)
    private Set<Relatie> heeftrelatieRelaties = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @NotNull
    @JoinTable(
        name = "rel_client__voorzieningbijstandspartij_inkomensvoorziening",
        joinColumns = @JoinColumn(name = "client_id"),
        inverseJoinColumns = @JoinColumn(name = "voorzieningbijstandspartij_inkomensvoorziening_id")
    )
    @JsonIgnoreProperties(
        value = {
            "heeftvoorzieningClient",
            "emptyClientbegeleider",
            "issoortvoorzieningInkomensvoorzieningsoort",
            "voorzieningbijstandspartijClients",
        },
        allowSetters = true
    )
    private Set<Inkomensvoorziening> voorzieningbijstandspartijInkomensvoorzienings = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_client__maaktonderdeeluitvan_huishouden",
        joinColumns = @JoinColumn(name = "client_id"),
        inverseJoinColumns = @JoinColumn(name = "maaktonderdeeluitvan_huishouden_id")
    )
    @JsonIgnoreProperties(value = { "maaktonderdeeluitvanClients", "maaktonderdeelvanRelaties" }, allowSetters = true)
    private Set<Huishouden> maaktonderdeeluitvanHuishoudens = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_client__heefttaalniveau_taalniveau",
        joinColumns = @JoinColumn(name = "client_id"),
        inverseJoinColumns = @JoinColumn(name = "heefttaalniveau_taalniveau_id")
    )
    @JsonIgnoreProperties(value = { "heefttaalniveauClients" }, allowSetters = true)
    private Set<Taalniveau> heefttaalniveauTaalniveaus = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "emptyClient")
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
    private Set<Beschikking> emptyBeschikkings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "prestatievoorClient")
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
    private Set<Levering> prestatievoorLeverings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "betreftClient")
    @JsonIgnoreProperties(
        value = { "isvoorBeschikking", "betreftClient", "valtbinnenDeclaratie", "isopbasisvanToewijzing" },
        allowSetters = true
    )
    private Set<Declaratieregel> betreftDeclaratieregels = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "ondersteuntclientClients")
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
    private Set<Clientbegeleider> ondersteuntclientClientbegeleiders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Client id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Client code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getGezagsdragergekend() {
        return this.gezagsdragergekend;
    }

    public Client gezagsdragergekend(Boolean gezagsdragergekend) {
        this.setGezagsdragergekend(gezagsdragergekend);
        return this;
    }

    public void setGezagsdragergekend(Boolean gezagsdragergekend) {
        this.gezagsdragergekend = gezagsdragergekend;
    }

    public String getJuridischestatus() {
        return this.juridischestatus;
    }

    public Client juridischestatus(String juridischestatus) {
        this.setJuridischestatus(juridischestatus);
        return this;
    }

    public void setJuridischestatus(String juridischestatus) {
        this.juridischestatus = juridischestatus;
    }

    public String getWettelijkevertegenwoordiging() {
        return this.wettelijkevertegenwoordiging;
    }

    public Client wettelijkevertegenwoordiging(String wettelijkevertegenwoordiging) {
        this.setWettelijkevertegenwoordiging(wettelijkevertegenwoordiging);
        return this;
    }

    public void setWettelijkevertegenwoordiging(String wettelijkevertegenwoordiging) {
        this.wettelijkevertegenwoordiging = wettelijkevertegenwoordiging;
    }

    public Participatiedossier getHeeftParticipatiedossier() {
        return this.heeftParticipatiedossier;
    }

    public void setHeeftParticipatiedossier(Participatiedossier participatiedossier) {
        this.heeftParticipatiedossier = participatiedossier;
    }

    public Client heeftParticipatiedossier(Participatiedossier participatiedossier) {
        this.setHeeftParticipatiedossier(participatiedossier);
        return this;
    }

    public Inkomensvoorziening getHeeftvoorzieningInkomensvoorziening() {
        return this.heeftvoorzieningInkomensvoorziening;
    }

    public void setHeeftvoorzieningInkomensvoorziening(Inkomensvoorziening inkomensvoorziening) {
        this.heeftvoorzieningInkomensvoorziening = inkomensvoorziening;
    }

    public Client heeftvoorzieningInkomensvoorziening(Inkomensvoorziening inkomensvoorziening) {
        this.setHeeftvoorzieningInkomensvoorziening(inkomensvoorziening);
        return this;
    }

    public Set<Score> getHeeftScores() {
        return this.heeftScores;
    }

    public void setHeeftScores(Set<Score> scores) {
        if (this.heeftScores != null) {
            this.heeftScores.forEach(i -> i.setHeeftClient(null));
        }
        if (scores != null) {
            scores.forEach(i -> i.setHeeftClient(this));
        }
        this.heeftScores = scores;
    }

    public Client heeftScores(Set<Score> scores) {
        this.setHeeftScores(scores);
        return this;
    }

    public Client addHeeftScore(Score score) {
        this.heeftScores.add(score);
        score.setHeeftClient(this);
        return this;
    }

    public Client removeHeeftScore(Score score) {
        this.heeftScores.remove(score);
        score.setHeeftClient(null);
        return this;
    }

    public Set<Tegenprestatie> getLeverttegenprestatieTegenprestaties() {
        return this.leverttegenprestatieTegenprestaties;
    }

    public void setLeverttegenprestatieTegenprestaties(Set<Tegenprestatie> tegenprestaties) {
        if (this.leverttegenprestatieTegenprestaties != null) {
            this.leverttegenprestatieTegenprestaties.forEach(i -> i.setLeverttegenprestatieClient(null));
        }
        if (tegenprestaties != null) {
            tegenprestaties.forEach(i -> i.setLeverttegenprestatieClient(this));
        }
        this.leverttegenprestatieTegenprestaties = tegenprestaties;
    }

    public Client leverttegenprestatieTegenprestaties(Set<Tegenprestatie> tegenprestaties) {
        this.setLeverttegenprestatieTegenprestaties(tegenprestaties);
        return this;
    }

    public Client addLeverttegenprestatieTegenprestatie(Tegenprestatie tegenprestatie) {
        this.leverttegenprestatieTegenprestaties.add(tegenprestatie);
        tegenprestatie.setLeverttegenprestatieClient(this);
        return this;
    }

    public Client removeLeverttegenprestatieTegenprestatie(Tegenprestatie tegenprestatie) {
        this.leverttegenprestatieTegenprestaties.remove(tegenprestatie);
        tegenprestatie.setLeverttegenprestatieClient(null);
        return this;
    }

    public Set<Traject> getHeeftparticipatietrajectTrajects() {
        return this.heeftparticipatietrajectTrajects;
    }

    public void setHeeftparticipatietrajectTrajects(Set<Traject> trajects) {
        if (this.heeftparticipatietrajectTrajects != null) {
            this.heeftparticipatietrajectTrajects.forEach(i -> i.setHeeftparticipatietrajectClient(null));
        }
        if (trajects != null) {
            trajects.forEach(i -> i.setHeeftparticipatietrajectClient(this));
        }
        this.heeftparticipatietrajectTrajects = trajects;
    }

    public Client heeftparticipatietrajectTrajects(Set<Traject> trajects) {
        this.setHeeftparticipatietrajectTrajects(trajects);
        return this;
    }

    public Client addHeeftparticipatietrajectTraject(Traject traject) {
        this.heeftparticipatietrajectTrajects.add(traject);
        traject.setHeeftparticipatietrajectClient(this);
        return this;
    }

    public Client removeHeeftparticipatietrajectTraject(Traject traject) {
        this.heeftparticipatietrajectTrajects.remove(traject);
        traject.setHeeftparticipatietrajectClient(null);
        return this;
    }

    public Set<Fraudegegevens> getHeeftfraudegegevensFraudegegevens() {
        return this.heeftfraudegegevensFraudegegevens;
    }

    public void setHeeftfraudegegevensFraudegegevens(Set<Fraudegegevens> fraudegegevens) {
        if (this.heeftfraudegegevensFraudegegevens != null) {
            this.heeftfraudegegevensFraudegegevens.forEach(i -> i.setHeeftfraudegegevensClient(null));
        }
        if (fraudegegevens != null) {
            fraudegegevens.forEach(i -> i.setHeeftfraudegegevensClient(this));
        }
        this.heeftfraudegegevensFraudegegevens = fraudegegevens;
    }

    public Client heeftfraudegegevensFraudegegevens(Set<Fraudegegevens> fraudegegevens) {
        this.setHeeftfraudegegevensFraudegegevens(fraudegegevens);
        return this;
    }

    public Client addHeeftfraudegegevensFraudegegevens(Fraudegegevens fraudegegevens) {
        this.heeftfraudegegevensFraudegegevens.add(fraudegegevens);
        fraudegegevens.setHeeftfraudegegevensClient(this);
        return this;
    }

    public Client removeHeeftfraudegegevensFraudegegevens(Fraudegegevens fraudegegevens) {
        this.heeftfraudegegevensFraudegegevens.remove(fraudegegevens);
        fraudegegevens.setHeeftfraudegegevensClient(null);
        return this;
    }

    public Set<Regeling> getHeeftregelingRegelings() {
        return this.heeftregelingRegelings;
    }

    public void setHeeftregelingRegelings(Set<Regeling> regelings) {
        if (this.heeftregelingRegelings != null) {
            this.heeftregelingRegelings.forEach(i -> i.setHeeftregelingClient(null));
        }
        if (regelings != null) {
            regelings.forEach(i -> i.setHeeftregelingClient(this));
        }
        this.heeftregelingRegelings = regelings;
    }

    public Client heeftregelingRegelings(Set<Regeling> regelings) {
        this.setHeeftregelingRegelings(regelings);
        return this;
    }

    public Client addHeeftregelingRegeling(Regeling regeling) {
        this.heeftregelingRegelings.add(regeling);
        regeling.setHeeftregelingClient(this);
        return this;
    }

    public Client removeHeeftregelingRegeling(Regeling regeling) {
        this.heeftregelingRegelings.remove(regeling);
        regeling.setHeeftregelingClient(null);
        return this;
    }

    public Set<Traject> getHeefttrajectTrajects() {
        return this.heefttrajectTrajects;
    }

    public void setHeefttrajectTrajects(Set<Traject> trajects) {
        if (this.heefttrajectTrajects != null) {
            this.heefttrajectTrajects.forEach(i -> i.setHeefttrajectClient(null));
        }
        if (trajects != null) {
            trajects.forEach(i -> i.setHeefttrajectClient(this));
        }
        this.heefttrajectTrajects = trajects;
    }

    public Client heefttrajectTrajects(Set<Traject> trajects) {
        this.setHeefttrajectTrajects(trajects);
        return this;
    }

    public Client addHeefttrajectTraject(Traject traject) {
        this.heefttrajectTrajects.add(traject);
        traject.setHeefttrajectClient(this);
        return this;
    }

    public Client removeHeefttrajectTraject(Traject traject) {
        this.heefttrajectTrajects.remove(traject);
        traject.setHeefttrajectClient(null);
        return this;
    }

    public Doelgroep getValtbinnendoelgroepDoelgroep() {
        return this.valtbinnendoelgroepDoelgroep;
    }

    public void setValtbinnendoelgroepDoelgroep(Doelgroep doelgroep) {
        this.valtbinnendoelgroepDoelgroep = doelgroep;
    }

    public Client valtbinnendoelgroepDoelgroep(Doelgroep doelgroep) {
        this.setValtbinnendoelgroepDoelgroep(doelgroep);
        return this;
    }

    public Set<Relatie> getHeeftrelatieRelaties() {
        return this.heeftrelatieRelaties;
    }

    public void setHeeftrelatieRelaties(Set<Relatie> relaties) {
        this.heeftrelatieRelaties = relaties;
    }

    public Client heeftrelatieRelaties(Set<Relatie> relaties) {
        this.setHeeftrelatieRelaties(relaties);
        return this;
    }

    public Client addHeeftrelatieRelatie(Relatie relatie) {
        this.heeftrelatieRelaties.add(relatie);
        return this;
    }

    public Client removeHeeftrelatieRelatie(Relatie relatie) {
        this.heeftrelatieRelaties.remove(relatie);
        return this;
    }

    public Set<Inkomensvoorziening> getVoorzieningbijstandspartijInkomensvoorzienings() {
        return this.voorzieningbijstandspartijInkomensvoorzienings;
    }

    public void setVoorzieningbijstandspartijInkomensvoorzienings(Set<Inkomensvoorziening> inkomensvoorzienings) {
        this.voorzieningbijstandspartijInkomensvoorzienings = inkomensvoorzienings;
    }

    public Client voorzieningbijstandspartijInkomensvoorzienings(Set<Inkomensvoorziening> inkomensvoorzienings) {
        this.setVoorzieningbijstandspartijInkomensvoorzienings(inkomensvoorzienings);
        return this;
    }

    public Client addVoorzieningbijstandspartijInkomensvoorziening(Inkomensvoorziening inkomensvoorziening) {
        this.voorzieningbijstandspartijInkomensvoorzienings.add(inkomensvoorziening);
        return this;
    }

    public Client removeVoorzieningbijstandspartijInkomensvoorziening(Inkomensvoorziening inkomensvoorziening) {
        this.voorzieningbijstandspartijInkomensvoorzienings.remove(inkomensvoorziening);
        return this;
    }

    public Set<Huishouden> getMaaktonderdeeluitvanHuishoudens() {
        return this.maaktonderdeeluitvanHuishoudens;
    }

    public void setMaaktonderdeeluitvanHuishoudens(Set<Huishouden> huishoudens) {
        this.maaktonderdeeluitvanHuishoudens = huishoudens;
    }

    public Client maaktonderdeeluitvanHuishoudens(Set<Huishouden> huishoudens) {
        this.setMaaktonderdeeluitvanHuishoudens(huishoudens);
        return this;
    }

    public Client addMaaktonderdeeluitvanHuishouden(Huishouden huishouden) {
        this.maaktonderdeeluitvanHuishoudens.add(huishouden);
        return this;
    }

    public Client removeMaaktonderdeeluitvanHuishouden(Huishouden huishouden) {
        this.maaktonderdeeluitvanHuishoudens.remove(huishouden);
        return this;
    }

    public Set<Taalniveau> getHeefttaalniveauTaalniveaus() {
        return this.heefttaalniveauTaalniveaus;
    }

    public void setHeefttaalniveauTaalniveaus(Set<Taalniveau> taalniveaus) {
        this.heefttaalniveauTaalniveaus = taalniveaus;
    }

    public Client heefttaalniveauTaalniveaus(Set<Taalniveau> taalniveaus) {
        this.setHeefttaalniveauTaalniveaus(taalniveaus);
        return this;
    }

    public Client addHeefttaalniveauTaalniveau(Taalniveau taalniveau) {
        this.heefttaalniveauTaalniveaus.add(taalniveau);
        return this;
    }

    public Client removeHeefttaalniveauTaalniveau(Taalniveau taalniveau) {
        this.heefttaalniveauTaalniveaus.remove(taalniveau);
        return this;
    }

    public Set<Beschikking> getEmptyBeschikkings() {
        return this.emptyBeschikkings;
    }

    public void setEmptyBeschikkings(Set<Beschikking> beschikkings) {
        if (this.emptyBeschikkings != null) {
            this.emptyBeschikkings.forEach(i -> i.setEmptyClient(null));
        }
        if (beschikkings != null) {
            beschikkings.forEach(i -> i.setEmptyClient(this));
        }
        this.emptyBeschikkings = beschikkings;
    }

    public Client emptyBeschikkings(Set<Beschikking> beschikkings) {
        this.setEmptyBeschikkings(beschikkings);
        return this;
    }

    public Client addEmptyBeschikking(Beschikking beschikking) {
        this.emptyBeschikkings.add(beschikking);
        beschikking.setEmptyClient(this);
        return this;
    }

    public Client removeEmptyBeschikking(Beschikking beschikking) {
        this.emptyBeschikkings.remove(beschikking);
        beschikking.setEmptyClient(null);
        return this;
    }

    public Set<Levering> getPrestatievoorLeverings() {
        return this.prestatievoorLeverings;
    }

    public void setPrestatievoorLeverings(Set<Levering> leverings) {
        if (this.prestatievoorLeverings != null) {
            this.prestatievoorLeverings.forEach(i -> i.setPrestatievoorClient(null));
        }
        if (leverings != null) {
            leverings.forEach(i -> i.setPrestatievoorClient(this));
        }
        this.prestatievoorLeverings = leverings;
    }

    public Client prestatievoorLeverings(Set<Levering> leverings) {
        this.setPrestatievoorLeverings(leverings);
        return this;
    }

    public Client addPrestatievoorLevering(Levering levering) {
        this.prestatievoorLeverings.add(levering);
        levering.setPrestatievoorClient(this);
        return this;
    }

    public Client removePrestatievoorLevering(Levering levering) {
        this.prestatievoorLeverings.remove(levering);
        levering.setPrestatievoorClient(null);
        return this;
    }

    public Set<Declaratieregel> getBetreftDeclaratieregels() {
        return this.betreftDeclaratieregels;
    }

    public void setBetreftDeclaratieregels(Set<Declaratieregel> declaratieregels) {
        if (this.betreftDeclaratieregels != null) {
            this.betreftDeclaratieregels.forEach(i -> i.setBetreftClient(null));
        }
        if (declaratieregels != null) {
            declaratieregels.forEach(i -> i.setBetreftClient(this));
        }
        this.betreftDeclaratieregels = declaratieregels;
    }

    public Client betreftDeclaratieregels(Set<Declaratieregel> declaratieregels) {
        this.setBetreftDeclaratieregels(declaratieregels);
        return this;
    }

    public Client addBetreftDeclaratieregel(Declaratieregel declaratieregel) {
        this.betreftDeclaratieregels.add(declaratieregel);
        declaratieregel.setBetreftClient(this);
        return this;
    }

    public Client removeBetreftDeclaratieregel(Declaratieregel declaratieregel) {
        this.betreftDeclaratieregels.remove(declaratieregel);
        declaratieregel.setBetreftClient(null);
        return this;
    }

    public Set<Clientbegeleider> getOndersteuntclientClientbegeleiders() {
        return this.ondersteuntclientClientbegeleiders;
    }

    public void setOndersteuntclientClientbegeleiders(Set<Clientbegeleider> clientbegeleiders) {
        if (this.ondersteuntclientClientbegeleiders != null) {
            this.ondersteuntclientClientbegeleiders.forEach(i -> i.removeOndersteuntclientClient(this));
        }
        if (clientbegeleiders != null) {
            clientbegeleiders.forEach(i -> i.addOndersteuntclientClient(this));
        }
        this.ondersteuntclientClientbegeleiders = clientbegeleiders;
    }

    public Client ondersteuntclientClientbegeleiders(Set<Clientbegeleider> clientbegeleiders) {
        this.setOndersteuntclientClientbegeleiders(clientbegeleiders);
        return this;
    }

    public Client addOndersteuntclientClientbegeleider(Clientbegeleider clientbegeleider) {
        this.ondersteuntclientClientbegeleiders.add(clientbegeleider);
        clientbegeleider.getOndersteuntclientClients().add(this);
        return this;
    }

    public Client removeOndersteuntclientClientbegeleider(Clientbegeleider clientbegeleider) {
        this.ondersteuntclientClientbegeleiders.remove(clientbegeleider);
        clientbegeleider.getOndersteuntclientClients().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return getId() != null && getId().equals(((Client) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", gezagsdragergekend='" + getGezagsdragergekend() + "'" +
            ", juridischestatus='" + getJuridischestatus() + "'" +
            ", wettelijkevertegenwoordiging='" + getWettelijkevertegenwoordiging() + "'" +
            "}";
    }
}
