package nl.ritense.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Vulling.
 */
@Entity
@Table(name = "vulling")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vulling implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "grondsoort")
    private String grondsoort;

    @Column(name = "key")
    private String key;

    @Column(name = "keyspoor")
    private String keyspoor;

    @Column(name = "kleur")
    private String kleur;

    @Size(max = 20)
    @Column(name = "projectcd", length = 20)
    private String projectcd;

    @Column(name = "putnummer")
    private String putnummer;

    @Size(max = 20)
    @Column(name = "spoornummer", length = 20)
    private String spoornummer;

    @Column(name = "structuur")
    private String structuur;

    @Size(max = 20)
    @Column(name = "vlaknummer", length = 20)
    private String vlaknummer;

    @Size(max = 20)
    @Column(name = "vullingnummer", length = 20)
    private String vullingnummer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "heeftVulling")
    @JsonIgnoreProperties(value = { "bevatArtefacts", "heeftVulling" }, allowSetters = true)
    private Set<Vondst> heeftVondsts = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "heeftVullings", "heeftVlak" }, allowSetters = true)
    private Spoor heeftSpoor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vulling id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGrondsoort() {
        return this.grondsoort;
    }

    public Vulling grondsoort(String grondsoort) {
        this.setGrondsoort(grondsoort);
        return this;
    }

    public void setGrondsoort(String grondsoort) {
        this.grondsoort = grondsoort;
    }

    public String getKey() {
        return this.key;
    }

    public Vulling key(String key) {
        this.setKey(key);
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKeyspoor() {
        return this.keyspoor;
    }

    public Vulling keyspoor(String keyspoor) {
        this.setKeyspoor(keyspoor);
        return this;
    }

    public void setKeyspoor(String keyspoor) {
        this.keyspoor = keyspoor;
    }

    public String getKleur() {
        return this.kleur;
    }

    public Vulling kleur(String kleur) {
        this.setKleur(kleur);
        return this;
    }

    public void setKleur(String kleur) {
        this.kleur = kleur;
    }

    public String getProjectcd() {
        return this.projectcd;
    }

    public Vulling projectcd(String projectcd) {
        this.setProjectcd(projectcd);
        return this;
    }

    public void setProjectcd(String projectcd) {
        this.projectcd = projectcd;
    }

    public String getPutnummer() {
        return this.putnummer;
    }

    public Vulling putnummer(String putnummer) {
        this.setPutnummer(putnummer);
        return this;
    }

    public void setPutnummer(String putnummer) {
        this.putnummer = putnummer;
    }

    public String getSpoornummer() {
        return this.spoornummer;
    }

    public Vulling spoornummer(String spoornummer) {
        this.setSpoornummer(spoornummer);
        return this;
    }

    public void setSpoornummer(String spoornummer) {
        this.spoornummer = spoornummer;
    }

    public String getStructuur() {
        return this.structuur;
    }

    public Vulling structuur(String structuur) {
        this.setStructuur(structuur);
        return this;
    }

    public void setStructuur(String structuur) {
        this.structuur = structuur;
    }

    public String getVlaknummer() {
        return this.vlaknummer;
    }

    public Vulling vlaknummer(String vlaknummer) {
        this.setVlaknummer(vlaknummer);
        return this;
    }

    public void setVlaknummer(String vlaknummer) {
        this.vlaknummer = vlaknummer;
    }

    public String getVullingnummer() {
        return this.vullingnummer;
    }

    public Vulling vullingnummer(String vullingnummer) {
        this.setVullingnummer(vullingnummer);
        return this;
    }

    public void setVullingnummer(String vullingnummer) {
        this.vullingnummer = vullingnummer;
    }

    public Set<Vondst> getHeeftVondsts() {
        return this.heeftVondsts;
    }

    public void setHeeftVondsts(Set<Vondst> vondsts) {
        if (this.heeftVondsts != null) {
            this.heeftVondsts.forEach(i -> i.setHeeftVulling(null));
        }
        if (vondsts != null) {
            vondsts.forEach(i -> i.setHeeftVulling(this));
        }
        this.heeftVondsts = vondsts;
    }

    public Vulling heeftVondsts(Set<Vondst> vondsts) {
        this.setHeeftVondsts(vondsts);
        return this;
    }

    public Vulling addHeeftVondst(Vondst vondst) {
        this.heeftVondsts.add(vondst);
        vondst.setHeeftVulling(this);
        return this;
    }

    public Vulling removeHeeftVondst(Vondst vondst) {
        this.heeftVondsts.remove(vondst);
        vondst.setHeeftVulling(null);
        return this;
    }

    public Spoor getHeeftSpoor() {
        return this.heeftSpoor;
    }

    public void setHeeftSpoor(Spoor spoor) {
        this.heeftSpoor = spoor;
    }

    public Vulling heeftSpoor(Spoor spoor) {
        this.setHeeftSpoor(spoor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vulling)) {
            return false;
        }
        return getId() != null && getId().equals(((Vulling) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vulling{" +
            "id=" + getId() +
            ", grondsoort='" + getGrondsoort() + "'" +
            ", key='" + getKey() + "'" +
            ", keyspoor='" + getKeyspoor() + "'" +
            ", kleur='" + getKleur() + "'" +
            ", projectcd='" + getProjectcd() + "'" +
            ", putnummer='" + getPutnummer() + "'" +
            ", spoornummer='" + getSpoornummer() + "'" +
            ", structuur='" + getStructuur() + "'" +
            ", vlaknummer='" + getVlaknummer() + "'" +
            ", vullingnummer='" + getVullingnummer() + "'" +
            "}";
    }
}
