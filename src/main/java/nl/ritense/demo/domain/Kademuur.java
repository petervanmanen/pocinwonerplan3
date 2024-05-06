package nl.ritense.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Kademuur.
 */
@Entity
@Table(name = "kademuur")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Kademuur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "belastingklassenieuw")
    private String belastingklassenieuw;

    @Column(name = "belastingklasseoud")
    private String belastingklasseoud;

    @Column(name = "grijpstenen")
    private Boolean grijpstenen;

    @Column(name = "hoogtebovenkantkademuur")
    private String hoogtebovenkantkademuur;

    @Column(name = "materiaalbovenkantkademuur")
    private String materiaalbovenkantkademuur;

    @Column(name = "oppervlaktebovenkantkademuur")
    private String oppervlaktebovenkantkademuur;

    @Column(name = "reddingslijn")
    private Boolean reddingslijn;

    @Column(name = "type")
    private String type;

    @Column(name = "typebovenkantkademuur")
    private String typebovenkantkademuur;

    @Column(name = "typefundering")
    private String typefundering;

    @Column(name = "typeverankering")
    private String typeverankering;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Kademuur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBelastingklassenieuw() {
        return this.belastingklassenieuw;
    }

    public Kademuur belastingklassenieuw(String belastingklassenieuw) {
        this.setBelastingklassenieuw(belastingklassenieuw);
        return this;
    }

    public void setBelastingklassenieuw(String belastingklassenieuw) {
        this.belastingklassenieuw = belastingklassenieuw;
    }

    public String getBelastingklasseoud() {
        return this.belastingklasseoud;
    }

    public Kademuur belastingklasseoud(String belastingklasseoud) {
        this.setBelastingklasseoud(belastingklasseoud);
        return this;
    }

    public void setBelastingklasseoud(String belastingklasseoud) {
        this.belastingklasseoud = belastingklasseoud;
    }

    public Boolean getGrijpstenen() {
        return this.grijpstenen;
    }

    public Kademuur grijpstenen(Boolean grijpstenen) {
        this.setGrijpstenen(grijpstenen);
        return this;
    }

    public void setGrijpstenen(Boolean grijpstenen) {
        this.grijpstenen = grijpstenen;
    }

    public String getHoogtebovenkantkademuur() {
        return this.hoogtebovenkantkademuur;
    }

    public Kademuur hoogtebovenkantkademuur(String hoogtebovenkantkademuur) {
        this.setHoogtebovenkantkademuur(hoogtebovenkantkademuur);
        return this;
    }

    public void setHoogtebovenkantkademuur(String hoogtebovenkantkademuur) {
        this.hoogtebovenkantkademuur = hoogtebovenkantkademuur;
    }

    public String getMateriaalbovenkantkademuur() {
        return this.materiaalbovenkantkademuur;
    }

    public Kademuur materiaalbovenkantkademuur(String materiaalbovenkantkademuur) {
        this.setMateriaalbovenkantkademuur(materiaalbovenkantkademuur);
        return this;
    }

    public void setMateriaalbovenkantkademuur(String materiaalbovenkantkademuur) {
        this.materiaalbovenkantkademuur = materiaalbovenkantkademuur;
    }

    public String getOppervlaktebovenkantkademuur() {
        return this.oppervlaktebovenkantkademuur;
    }

    public Kademuur oppervlaktebovenkantkademuur(String oppervlaktebovenkantkademuur) {
        this.setOppervlaktebovenkantkademuur(oppervlaktebovenkantkademuur);
        return this;
    }

    public void setOppervlaktebovenkantkademuur(String oppervlaktebovenkantkademuur) {
        this.oppervlaktebovenkantkademuur = oppervlaktebovenkantkademuur;
    }

    public Boolean getReddingslijn() {
        return this.reddingslijn;
    }

    public Kademuur reddingslijn(Boolean reddingslijn) {
        this.setReddingslijn(reddingslijn);
        return this;
    }

    public void setReddingslijn(Boolean reddingslijn) {
        this.reddingslijn = reddingslijn;
    }

    public String getType() {
        return this.type;
    }

    public Kademuur type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypebovenkantkademuur() {
        return this.typebovenkantkademuur;
    }

    public Kademuur typebovenkantkademuur(String typebovenkantkademuur) {
        this.setTypebovenkantkademuur(typebovenkantkademuur);
        return this;
    }

    public void setTypebovenkantkademuur(String typebovenkantkademuur) {
        this.typebovenkantkademuur = typebovenkantkademuur;
    }

    public String getTypefundering() {
        return this.typefundering;
    }

    public Kademuur typefundering(String typefundering) {
        this.setTypefundering(typefundering);
        return this;
    }

    public void setTypefundering(String typefundering) {
        this.typefundering = typefundering;
    }

    public String getTypeverankering() {
        return this.typeverankering;
    }

    public Kademuur typeverankering(String typeverankering) {
        this.setTypeverankering(typeverankering);
        return this;
    }

    public void setTypeverankering(String typeverankering) {
        this.typeverankering = typeverankering;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Kademuur)) {
            return false;
        }
        return getId() != null && getId().equals(((Kademuur) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Kademuur{" +
            "id=" + getId() +
            ", belastingklassenieuw='" + getBelastingklassenieuw() + "'" +
            ", belastingklasseoud='" + getBelastingklasseoud() + "'" +
            ", grijpstenen='" + getGrijpstenen() + "'" +
            ", hoogtebovenkantkademuur='" + getHoogtebovenkantkademuur() + "'" +
            ", materiaalbovenkantkademuur='" + getMateriaalbovenkantkademuur() + "'" +
            ", oppervlaktebovenkantkademuur='" + getOppervlaktebovenkantkademuur() + "'" +
            ", reddingslijn='" + getReddingslijn() + "'" +
            ", type='" + getType() + "'" +
            ", typebovenkantkademuur='" + getTypebovenkantkademuur() + "'" +
            ", typefundering='" + getTypefundering() + "'" +
            ", typeverankering='" + getTypeverankering() + "'" +
            "}";
    }
}
