package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Raadsstuk.
 */
@Entity
@Table(name = "raadsstuk")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Raadsstuk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "besloten")
    private String besloten;

    @Column(name = "datumexpiratie")
    private String datumexpiratie;

    @Column(name = "datumpublicatie")
    private String datumpublicatie;

    @Column(name = "datumregistratie")
    private String datumregistratie;

    @Column(name = "typeraadsstuk")
    private String typeraadsstuk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftRaadsstuks", "heeftKostenplaats" }, allowSetters = true)
    private Taakveld heeftTaakveld;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_raadsstuk__behandelt_agendapunt",
        joinColumns = @JoinColumn(name = "raadsstuk_id"),
        inverseJoinColumns = @JoinColumn(name = "behandelt_agendapunt_id")
    )
    @JsonIgnoreProperties(value = { "heeftVergadering", "hoortbijStemmings", "behandeltRaadsstuks" }, allowSetters = true)
    private Set<Agendapunt> behandeltAgendapunts = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_raadsstuk__hoortbij_programma",
        joinColumns = @JoinColumn(name = "raadsstuk_id"),
        inverseJoinColumns = @JoinColumn(name = "hoortbij_programma_id")
    )
    @JsonIgnoreProperties(
        value = {
            "bestaatuitActiviteits",
            "binnenprogrammaPlans",
            "heeftKostenplaats",
            "voorProgrammasoorts",
            "voorMuseumrelatie",
            "hoortbijRaadsstuks",
        },
        allowSetters = true
    )
    private Set<Programma> hoortbijProgrammas = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_raadsstuk__wordtbehandeldin_vergadering",
        joinColumns = @JoinColumn(name = "raadsstuk_id"),
        inverseJoinColumns = @JoinColumn(name = "wordtbehandeldin_vergadering_id")
    )
    @JsonIgnoreProperties(
        value = { "heeftverslagRaadsstuk", "heeftAgendapunts", "heeftRaadscommissie", "wordtbehandeldinRaadsstuks" },
        allowSetters = true
    )
    private Set<Vergadering> wordtbehandeldinVergaderings = new HashSet<>();

    @JsonIgnoreProperties(
        value = { "heeftverslagRaadsstuk", "heeftAgendapunts", "heeftRaadscommissie", "wordtbehandeldinRaadsstuks" },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "heeftverslagRaadsstuk")
    private Vergadering heeftverslagVergadering;

    @JsonIgnoreProperties(value = { "betreftRaadsstuk", "hoortbijAgendapunt" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "betreftRaadsstuk")
    private Stemming betreftStemming;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "heeftRaadsstuks", "hoofdcategorieMeldings", "subcategorieMeldings", "gekwalificeerdLeveranciers" },
        allowSetters = true
    )
    private Categorie heeftCategorie;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "hoortbijRaadsstuks")
    @JsonIgnoreProperties(value = { "hoortbijRaadsstuks" }, allowSetters = true)
    private Set<Dossier> hoortbijDossiers = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "heeftRaadsstuks")
    @JsonIgnoreProperties(value = { "isCollegelid", "isRaadslid", "isRechtspersoon", "heeftRaadsstuks" }, allowSetters = true)
    private Set<Indiener> heeftIndieners = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Raadsstuk id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBesloten() {
        return this.besloten;
    }

    public Raadsstuk besloten(String besloten) {
        this.setBesloten(besloten);
        return this;
    }

    public void setBesloten(String besloten) {
        this.besloten = besloten;
    }

    public String getDatumexpiratie() {
        return this.datumexpiratie;
    }

    public Raadsstuk datumexpiratie(String datumexpiratie) {
        this.setDatumexpiratie(datumexpiratie);
        return this;
    }

    public void setDatumexpiratie(String datumexpiratie) {
        this.datumexpiratie = datumexpiratie;
    }

    public String getDatumpublicatie() {
        return this.datumpublicatie;
    }

    public Raadsstuk datumpublicatie(String datumpublicatie) {
        this.setDatumpublicatie(datumpublicatie);
        return this;
    }

    public void setDatumpublicatie(String datumpublicatie) {
        this.datumpublicatie = datumpublicatie;
    }

    public String getDatumregistratie() {
        return this.datumregistratie;
    }

    public Raadsstuk datumregistratie(String datumregistratie) {
        this.setDatumregistratie(datumregistratie);
        return this;
    }

    public void setDatumregistratie(String datumregistratie) {
        this.datumregistratie = datumregistratie;
    }

    public String getTyperaadsstuk() {
        return this.typeraadsstuk;
    }

    public Raadsstuk typeraadsstuk(String typeraadsstuk) {
        this.setTyperaadsstuk(typeraadsstuk);
        return this;
    }

    public void setTyperaadsstuk(String typeraadsstuk) {
        this.typeraadsstuk = typeraadsstuk;
    }

    public Taakveld getHeeftTaakveld() {
        return this.heeftTaakveld;
    }

    public void setHeeftTaakveld(Taakveld taakveld) {
        this.heeftTaakveld = taakveld;
    }

    public Raadsstuk heeftTaakveld(Taakveld taakveld) {
        this.setHeeftTaakveld(taakveld);
        return this;
    }

    public Set<Agendapunt> getBehandeltAgendapunts() {
        return this.behandeltAgendapunts;
    }

    public void setBehandeltAgendapunts(Set<Agendapunt> agendapunts) {
        this.behandeltAgendapunts = agendapunts;
    }

    public Raadsstuk behandeltAgendapunts(Set<Agendapunt> agendapunts) {
        this.setBehandeltAgendapunts(agendapunts);
        return this;
    }

    public Raadsstuk addBehandeltAgendapunt(Agendapunt agendapunt) {
        this.behandeltAgendapunts.add(agendapunt);
        return this;
    }

    public Raadsstuk removeBehandeltAgendapunt(Agendapunt agendapunt) {
        this.behandeltAgendapunts.remove(agendapunt);
        return this;
    }

    public Set<Programma> getHoortbijProgrammas() {
        return this.hoortbijProgrammas;
    }

    public void setHoortbijProgrammas(Set<Programma> programmas) {
        this.hoortbijProgrammas = programmas;
    }

    public Raadsstuk hoortbijProgrammas(Set<Programma> programmas) {
        this.setHoortbijProgrammas(programmas);
        return this;
    }

    public Raadsstuk addHoortbijProgramma(Programma programma) {
        this.hoortbijProgrammas.add(programma);
        return this;
    }

    public Raadsstuk removeHoortbijProgramma(Programma programma) {
        this.hoortbijProgrammas.remove(programma);
        return this;
    }

    public Set<Vergadering> getWordtbehandeldinVergaderings() {
        return this.wordtbehandeldinVergaderings;
    }

    public void setWordtbehandeldinVergaderings(Set<Vergadering> vergaderings) {
        this.wordtbehandeldinVergaderings = vergaderings;
    }

    public Raadsstuk wordtbehandeldinVergaderings(Set<Vergadering> vergaderings) {
        this.setWordtbehandeldinVergaderings(vergaderings);
        return this;
    }

    public Raadsstuk addWordtbehandeldinVergadering(Vergadering vergadering) {
        this.wordtbehandeldinVergaderings.add(vergadering);
        return this;
    }

    public Raadsstuk removeWordtbehandeldinVergadering(Vergadering vergadering) {
        this.wordtbehandeldinVergaderings.remove(vergadering);
        return this;
    }

    public Vergadering getHeeftverslagVergadering() {
        return this.heeftverslagVergadering;
    }

    public void setHeeftverslagVergadering(Vergadering vergadering) {
        if (this.heeftverslagVergadering != null) {
            this.heeftverslagVergadering.setHeeftverslagRaadsstuk(null);
        }
        if (vergadering != null) {
            vergadering.setHeeftverslagRaadsstuk(this);
        }
        this.heeftverslagVergadering = vergadering;
    }

    public Raadsstuk heeftverslagVergadering(Vergadering vergadering) {
        this.setHeeftverslagVergadering(vergadering);
        return this;
    }

    public Stemming getBetreftStemming() {
        return this.betreftStemming;
    }

    public void setBetreftStemming(Stemming stemming) {
        if (this.betreftStemming != null) {
            this.betreftStemming.setBetreftRaadsstuk(null);
        }
        if (stemming != null) {
            stemming.setBetreftRaadsstuk(this);
        }
        this.betreftStemming = stemming;
    }

    public Raadsstuk betreftStemming(Stemming stemming) {
        this.setBetreftStemming(stemming);
        return this;
    }

    public Categorie getHeeftCategorie() {
        return this.heeftCategorie;
    }

    public void setHeeftCategorie(Categorie categorie) {
        this.heeftCategorie = categorie;
    }

    public Raadsstuk heeftCategorie(Categorie categorie) {
        this.setHeeftCategorie(categorie);
        return this;
    }

    public Set<Dossier> getHoortbijDossiers() {
        return this.hoortbijDossiers;
    }

    public void setHoortbijDossiers(Set<Dossier> dossiers) {
        if (this.hoortbijDossiers != null) {
            this.hoortbijDossiers.forEach(i -> i.removeHoortbijRaadsstuk(this));
        }
        if (dossiers != null) {
            dossiers.forEach(i -> i.addHoortbijRaadsstuk(this));
        }
        this.hoortbijDossiers = dossiers;
    }

    public Raadsstuk hoortbijDossiers(Set<Dossier> dossiers) {
        this.setHoortbijDossiers(dossiers);
        return this;
    }

    public Raadsstuk addHoortbijDossier(Dossier dossier) {
        this.hoortbijDossiers.add(dossier);
        dossier.getHoortbijRaadsstuks().add(this);
        return this;
    }

    public Raadsstuk removeHoortbijDossier(Dossier dossier) {
        this.hoortbijDossiers.remove(dossier);
        dossier.getHoortbijRaadsstuks().remove(this);
        return this;
    }

    public Set<Indiener> getHeeftIndieners() {
        return this.heeftIndieners;
    }

    public void setHeeftIndieners(Set<Indiener> indieners) {
        if (this.heeftIndieners != null) {
            this.heeftIndieners.forEach(i -> i.removeHeeftRaadsstuk(this));
        }
        if (indieners != null) {
            indieners.forEach(i -> i.addHeeftRaadsstuk(this));
        }
        this.heeftIndieners = indieners;
    }

    public Raadsstuk heeftIndieners(Set<Indiener> indieners) {
        this.setHeeftIndieners(indieners);
        return this;
    }

    public Raadsstuk addHeeftIndiener(Indiener indiener) {
        this.heeftIndieners.add(indiener);
        indiener.getHeeftRaadsstuks().add(this);
        return this;
    }

    public Raadsstuk removeHeeftIndiener(Indiener indiener) {
        this.heeftIndieners.remove(indiener);
        indiener.getHeeftRaadsstuks().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Raadsstuk)) {
            return false;
        }
        return getId() != null && getId().equals(((Raadsstuk) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Raadsstuk{" +
            "id=" + getId() +
            ", besloten='" + getBesloten() + "'" +
            ", datumexpiratie='" + getDatumexpiratie() + "'" +
            ", datumpublicatie='" + getDatumpublicatie() + "'" +
            ", datumregistratie='" + getDatumregistratie() + "'" +
            ", typeraadsstuk='" + getTyperaadsstuk() + "'" +
            "}";
    }
}
