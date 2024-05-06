package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Archiefstuk.
 */
@Entity
@Table(name = "archiefstuk")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Archiefstuk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "beschrijving")
    private String beschrijving;

    @Size(max = 20)
    @Column(name = "inventarisnummer", length = 20)
    private String inventarisnummer;

    @Column(name = "omvang")
    private String omvang;

    @Column(name = "openbaarheidsbeperking")
    private String openbaarheidsbeperking;

    @Column(name = "trefwoorden")
    private String trefwoorden;

    @Column(name = "uiterlijkevorm")
    private String uiterlijkevorm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "heeftRechthebbende", "valtbinnenArchiefcategories", "stamtuitPeriodes", "isonderdeelvanArchiefstuks", "hoortbijIndelings",
        },
        allowSetters = true
    )
    private Archief isonderdeelvanArchief;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftArchiefstuks" }, allowSetters = true)
    private Uitgever heeftUitgever;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "hoortbijProject", "istevindeninDepot", "istevindeninKast", "istevindeninPlank", "istevindeninStelling", "heeftArchiefstuks",
        },
        allowSetters = true
    )
    private Vindplaats heeftVindplaats;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_archiefstuk__heeft_ordeningsschema",
        joinColumns = @JoinColumn(name = "archiefstuk_id"),
        inverseJoinColumns = @JoinColumn(name = "heeft_ordeningsschema_id")
    )
    @JsonIgnoreProperties(value = { "heeftArchiefstuks" }, allowSetters = true)
    private Set<Ordeningsschema> heeftOrdeningsschemas = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_archiefstuk__stamtuit_periode",
        joinColumns = @JoinColumn(name = "archiefstuk_id"),
        inverseJoinColumns = @JoinColumn(name = "stamtuit_periode_id")
    )
    @JsonIgnoreProperties(value = { "stamtuitArchiefs", "stamtuitArchiefstuks", "binnenHoofdstuks" }, allowSetters = true)
    private Set<Periode> stamtuitPeriodes = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "valtbinnenArchiefstuks", "valtbinnenIndelings", "hoortbijArchief", "valtbinnenIndeling2" },
        allowSetters = true
    )
    private Indeling valtbinnenIndeling;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "voorArchiefstuks")
    @JsonIgnoreProperties(value = { "voorArchiefstuks", "doetBezoeker" }, allowSetters = true)
    private Set<Aanvraag> voorAanvraags = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Archiefstuk id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeschrijving() {
        return this.beschrijving;
    }

    public Archiefstuk beschrijving(String beschrijving) {
        this.setBeschrijving(beschrijving);
        return this;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public String getInventarisnummer() {
        return this.inventarisnummer;
    }

    public Archiefstuk inventarisnummer(String inventarisnummer) {
        this.setInventarisnummer(inventarisnummer);
        return this;
    }

    public void setInventarisnummer(String inventarisnummer) {
        this.inventarisnummer = inventarisnummer;
    }

    public String getOmvang() {
        return this.omvang;
    }

    public Archiefstuk omvang(String omvang) {
        this.setOmvang(omvang);
        return this;
    }

    public void setOmvang(String omvang) {
        this.omvang = omvang;
    }

    public String getOpenbaarheidsbeperking() {
        return this.openbaarheidsbeperking;
    }

    public Archiefstuk openbaarheidsbeperking(String openbaarheidsbeperking) {
        this.setOpenbaarheidsbeperking(openbaarheidsbeperking);
        return this;
    }

    public void setOpenbaarheidsbeperking(String openbaarheidsbeperking) {
        this.openbaarheidsbeperking = openbaarheidsbeperking;
    }

    public String getTrefwoorden() {
        return this.trefwoorden;
    }

    public Archiefstuk trefwoorden(String trefwoorden) {
        this.setTrefwoorden(trefwoorden);
        return this;
    }

    public void setTrefwoorden(String trefwoorden) {
        this.trefwoorden = trefwoorden;
    }

    public String getUiterlijkevorm() {
        return this.uiterlijkevorm;
    }

    public Archiefstuk uiterlijkevorm(String uiterlijkevorm) {
        this.setUiterlijkevorm(uiterlijkevorm);
        return this;
    }

    public void setUiterlijkevorm(String uiterlijkevorm) {
        this.uiterlijkevorm = uiterlijkevorm;
    }

    public Archief getIsonderdeelvanArchief() {
        return this.isonderdeelvanArchief;
    }

    public void setIsonderdeelvanArchief(Archief archief) {
        this.isonderdeelvanArchief = archief;
    }

    public Archiefstuk isonderdeelvanArchief(Archief archief) {
        this.setIsonderdeelvanArchief(archief);
        return this;
    }

    public Uitgever getHeeftUitgever() {
        return this.heeftUitgever;
    }

    public void setHeeftUitgever(Uitgever uitgever) {
        this.heeftUitgever = uitgever;
    }

    public Archiefstuk heeftUitgever(Uitgever uitgever) {
        this.setHeeftUitgever(uitgever);
        return this;
    }

    public Vindplaats getHeeftVindplaats() {
        return this.heeftVindplaats;
    }

    public void setHeeftVindplaats(Vindplaats vindplaats) {
        this.heeftVindplaats = vindplaats;
    }

    public Archiefstuk heeftVindplaats(Vindplaats vindplaats) {
        this.setHeeftVindplaats(vindplaats);
        return this;
    }

    public Set<Ordeningsschema> getHeeftOrdeningsschemas() {
        return this.heeftOrdeningsschemas;
    }

    public void setHeeftOrdeningsschemas(Set<Ordeningsschema> ordeningsschemas) {
        this.heeftOrdeningsschemas = ordeningsschemas;
    }

    public Archiefstuk heeftOrdeningsschemas(Set<Ordeningsschema> ordeningsschemas) {
        this.setHeeftOrdeningsschemas(ordeningsschemas);
        return this;
    }

    public Archiefstuk addHeeftOrdeningsschema(Ordeningsschema ordeningsschema) {
        this.heeftOrdeningsschemas.add(ordeningsschema);
        return this;
    }

    public Archiefstuk removeHeeftOrdeningsschema(Ordeningsschema ordeningsschema) {
        this.heeftOrdeningsschemas.remove(ordeningsschema);
        return this;
    }

    public Set<Periode> getStamtuitPeriodes() {
        return this.stamtuitPeriodes;
    }

    public void setStamtuitPeriodes(Set<Periode> periodes) {
        this.stamtuitPeriodes = periodes;
    }

    public Archiefstuk stamtuitPeriodes(Set<Periode> periodes) {
        this.setStamtuitPeriodes(periodes);
        return this;
    }

    public Archiefstuk addStamtuitPeriode(Periode periode) {
        this.stamtuitPeriodes.add(periode);
        return this;
    }

    public Archiefstuk removeStamtuitPeriode(Periode periode) {
        this.stamtuitPeriodes.remove(periode);
        return this;
    }

    public Indeling getValtbinnenIndeling() {
        return this.valtbinnenIndeling;
    }

    public void setValtbinnenIndeling(Indeling indeling) {
        this.valtbinnenIndeling = indeling;
    }

    public Archiefstuk valtbinnenIndeling(Indeling indeling) {
        this.setValtbinnenIndeling(indeling);
        return this;
    }

    public Set<Aanvraag> getVoorAanvraags() {
        return this.voorAanvraags;
    }

    public void setVoorAanvraags(Set<Aanvraag> aanvraags) {
        if (this.voorAanvraags != null) {
            this.voorAanvraags.forEach(i -> i.removeVoorArchiefstuk(this));
        }
        if (aanvraags != null) {
            aanvraags.forEach(i -> i.addVoorArchiefstuk(this));
        }
        this.voorAanvraags = aanvraags;
    }

    public Archiefstuk voorAanvraags(Set<Aanvraag> aanvraags) {
        this.setVoorAanvraags(aanvraags);
        return this;
    }

    public Archiefstuk addVoorAanvraag(Aanvraag aanvraag) {
        this.voorAanvraags.add(aanvraag);
        aanvraag.getVoorArchiefstuks().add(this);
        return this;
    }

    public Archiefstuk removeVoorAanvraag(Aanvraag aanvraag) {
        this.voorAanvraags.remove(aanvraag);
        aanvraag.getVoorArchiefstuks().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Archiefstuk)) {
            return false;
        }
        return getId() != null && getId().equals(((Archiefstuk) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Archiefstuk{" +
            "id=" + getId() +
            ", beschrijving='" + getBeschrijving() + "'" +
            ", inventarisnummer='" + getInventarisnummer() + "'" +
            ", omvang='" + getOmvang() + "'" +
            ", openbaarheidsbeperking='" + getOpenbaarheidsbeperking() + "'" +
            ", trefwoorden='" + getTrefwoorden() + "'" +
            ", uiterlijkevorm='" + getUiterlijkevorm() + "'" +
            "}";
    }
}
