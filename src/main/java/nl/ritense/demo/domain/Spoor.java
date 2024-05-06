package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Spoor.
 */
@Entity
@Table(name = "spoor")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Spoor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "aard")
    private String aard;

    @Column(name = "beschrijving")
    private String beschrijving;

    @Size(max = 20)
    @Column(name = "datering", length = 20)
    private String datering;

    @Column(name = "datum")
    private LocalDate datum;

    @Size(max = 20)
    @Column(name = "hoogteboven", length = 20)
    private String hoogteboven;

    @Size(max = 20)
    @Column(name = "hoogteonder", length = 20)
    private String hoogteonder;

    @Column(name = "key")
    private String key;

    @Column(name = "keyvlak")
    private String keyvlak;

    @Column(name = "projectcd")
    private String projectcd;

    @Column(name = "putnummer")
    private String putnummer;

    @Column(name = "spoornummer")
    private String spoornummer;

    @Column(name = "vlaknummer")
    private String vlaknummer;

    @Size(max = 20)
    @Column(name = "vorm", length = 20)
    private String vorm;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftSpoor")
    @JsonIgnoreProperties(value = { "heeftVondsts", "heeftSpoor" }, allowSetters = true)
    private Set<Vulling> heeftVullings = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftSpoors", "heeftPut" }, allowSetters = true)
    private Vlak heeftVlak;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Spoor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAard() {
        return this.aard;
    }

    public Spoor aard(String aard) {
        this.setAard(aard);
        return this;
    }

    public void setAard(String aard) {
        this.aard = aard;
    }

    public String getBeschrijving() {
        return this.beschrijving;
    }

    public Spoor beschrijving(String beschrijving) {
        this.setBeschrijving(beschrijving);
        return this;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public String getDatering() {
        return this.datering;
    }

    public Spoor datering(String datering) {
        this.setDatering(datering);
        return this;
    }

    public void setDatering(String datering) {
        this.datering = datering;
    }

    public LocalDate getDatum() {
        return this.datum;
    }

    public Spoor datum(LocalDate datum) {
        this.setDatum(datum);
        return this;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getHoogteboven() {
        return this.hoogteboven;
    }

    public Spoor hoogteboven(String hoogteboven) {
        this.setHoogteboven(hoogteboven);
        return this;
    }

    public void setHoogteboven(String hoogteboven) {
        this.hoogteboven = hoogteboven;
    }

    public String getHoogteonder() {
        return this.hoogteonder;
    }

    public Spoor hoogteonder(String hoogteonder) {
        this.setHoogteonder(hoogteonder);
        return this;
    }

    public void setHoogteonder(String hoogteonder) {
        this.hoogteonder = hoogteonder;
    }

    public String getKey() {
        return this.key;
    }

    public Spoor key(String key) {
        this.setKey(key);
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKeyvlak() {
        return this.keyvlak;
    }

    public Spoor keyvlak(String keyvlak) {
        this.setKeyvlak(keyvlak);
        return this;
    }

    public void setKeyvlak(String keyvlak) {
        this.keyvlak = keyvlak;
    }

    public String getProjectcd() {
        return this.projectcd;
    }

    public Spoor projectcd(String projectcd) {
        this.setProjectcd(projectcd);
        return this;
    }

    public void setProjectcd(String projectcd) {
        this.projectcd = projectcd;
    }

    public String getPutnummer() {
        return this.putnummer;
    }

    public Spoor putnummer(String putnummer) {
        this.setPutnummer(putnummer);
        return this;
    }

    public void setPutnummer(String putnummer) {
        this.putnummer = putnummer;
    }

    public String getSpoornummer() {
        return this.spoornummer;
    }

    public Spoor spoornummer(String spoornummer) {
        this.setSpoornummer(spoornummer);
        return this;
    }

    public void setSpoornummer(String spoornummer) {
        this.spoornummer = spoornummer;
    }

    public String getVlaknummer() {
        return this.vlaknummer;
    }

    public Spoor vlaknummer(String vlaknummer) {
        this.setVlaknummer(vlaknummer);
        return this;
    }

    public void setVlaknummer(String vlaknummer) {
        this.vlaknummer = vlaknummer;
    }

    public String getVorm() {
        return this.vorm;
    }

    public Spoor vorm(String vorm) {
        this.setVorm(vorm);
        return this;
    }

    public void setVorm(String vorm) {
        this.vorm = vorm;
    }

    public Set<Vulling> getHeeftVullings() {
        return this.heeftVullings;
    }

    public void setHeeftVullings(Set<Vulling> vullings) {
        if (this.heeftVullings != null) {
            this.heeftVullings.forEach(i -> i.setHeeftSpoor(null));
        }
        if (vullings != null) {
            vullings.forEach(i -> i.setHeeftSpoor(this));
        }
        this.heeftVullings = vullings;
    }

    public Spoor heeftVullings(Set<Vulling> vullings) {
        this.setHeeftVullings(vullings);
        return this;
    }

    public Spoor addHeeftVulling(Vulling vulling) {
        this.heeftVullings.add(vulling);
        vulling.setHeeftSpoor(this);
        return this;
    }

    public Spoor removeHeeftVulling(Vulling vulling) {
        this.heeftVullings.remove(vulling);
        vulling.setHeeftSpoor(null);
        return this;
    }

    public Vlak getHeeftVlak() {
        return this.heeftVlak;
    }

    public void setHeeftVlak(Vlak vlak) {
        this.heeftVlak = vlak;
    }

    public Spoor heeftVlak(Vlak vlak) {
        this.setHeeftVlak(vlak);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Spoor)) {
            return false;
        }
        return getId() != null && getId().equals(((Spoor) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Spoor{" +
            "id=" + getId() +
            ", aard='" + getAard() + "'" +
            ", beschrijving='" + getBeschrijving() + "'" +
            ", datering='" + getDatering() + "'" +
            ", datum='" + getDatum() + "'" +
            ", hoogteboven='" + getHoogteboven() + "'" +
            ", hoogteonder='" + getHoogteonder() + "'" +
            ", key='" + getKey() + "'" +
            ", keyvlak='" + getKeyvlak() + "'" +
            ", projectcd='" + getProjectcd() + "'" +
            ", putnummer='" + getPutnummer() + "'" +
            ", spoornummer='" + getSpoornummer() + "'" +
            ", vlaknummer='" + getVlaknummer() + "'" +
            ", vorm='" + getVorm() + "'" +
            "}";
    }
}
