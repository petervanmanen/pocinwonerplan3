package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Samengesteldenaamnatuurlijkpersoon.
 */
@Entity
@Table(name = "samengesteldenaamnatuurlijkpersoon")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Samengesteldenaamnatuurlijkpersoon implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "adellijketitel")
    private String adellijketitel;

    @Column(name = "geslachtsnaamstam")
    private String geslachtsnaamstam;

    @Column(name = "namenreeks")
    private String namenreeks;

    @Column(name = "predicaat")
    private String predicaat;

    @Column(name = "scheidingsteken")
    private String scheidingsteken;

    @Column(name = "voornamen")
    private String voornamen;

    @Column(name = "voorvoegsel")
    private String voorvoegsel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Samengesteldenaamnatuurlijkpersoon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdellijketitel() {
        return this.adellijketitel;
    }

    public Samengesteldenaamnatuurlijkpersoon adellijketitel(String adellijketitel) {
        this.setAdellijketitel(adellijketitel);
        return this;
    }

    public void setAdellijketitel(String adellijketitel) {
        this.adellijketitel = adellijketitel;
    }

    public String getGeslachtsnaamstam() {
        return this.geslachtsnaamstam;
    }

    public Samengesteldenaamnatuurlijkpersoon geslachtsnaamstam(String geslachtsnaamstam) {
        this.setGeslachtsnaamstam(geslachtsnaamstam);
        return this;
    }

    public void setGeslachtsnaamstam(String geslachtsnaamstam) {
        this.geslachtsnaamstam = geslachtsnaamstam;
    }

    public String getNamenreeks() {
        return this.namenreeks;
    }

    public Samengesteldenaamnatuurlijkpersoon namenreeks(String namenreeks) {
        this.setNamenreeks(namenreeks);
        return this;
    }

    public void setNamenreeks(String namenreeks) {
        this.namenreeks = namenreeks;
    }

    public String getPredicaat() {
        return this.predicaat;
    }

    public Samengesteldenaamnatuurlijkpersoon predicaat(String predicaat) {
        this.setPredicaat(predicaat);
        return this;
    }

    public void setPredicaat(String predicaat) {
        this.predicaat = predicaat;
    }

    public String getScheidingsteken() {
        return this.scheidingsteken;
    }

    public Samengesteldenaamnatuurlijkpersoon scheidingsteken(String scheidingsteken) {
        this.setScheidingsteken(scheidingsteken);
        return this;
    }

    public void setScheidingsteken(String scheidingsteken) {
        this.scheidingsteken = scheidingsteken;
    }

    public String getVoornamen() {
        return this.voornamen;
    }

    public Samengesteldenaamnatuurlijkpersoon voornamen(String voornamen) {
        this.setVoornamen(voornamen);
        return this;
    }

    public void setVoornamen(String voornamen) {
        this.voornamen = voornamen;
    }

    public String getVoorvoegsel() {
        return this.voorvoegsel;
    }

    public Samengesteldenaamnatuurlijkpersoon voorvoegsel(String voorvoegsel) {
        this.setVoorvoegsel(voorvoegsel);
        return this;
    }

    public void setVoorvoegsel(String voorvoegsel) {
        this.voorvoegsel = voorvoegsel;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Samengesteldenaamnatuurlijkpersoon)) {
            return false;
        }
        return getId() != null && getId().equals(((Samengesteldenaamnatuurlijkpersoon) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Samengesteldenaamnatuurlijkpersoon{" +
            "id=" + getId() +
            ", adellijketitel='" + getAdellijketitel() + "'" +
            ", geslachtsnaamstam='" + getGeslachtsnaamstam() + "'" +
            ", namenreeks='" + getNamenreeks() + "'" +
            ", predicaat='" + getPredicaat() + "'" +
            ", scheidingsteken='" + getScheidingsteken() + "'" +
            ", voornamen='" + getVoornamen() + "'" +
            ", voorvoegsel='" + getVoorvoegsel() + "'" +
            "}";
    }
}
