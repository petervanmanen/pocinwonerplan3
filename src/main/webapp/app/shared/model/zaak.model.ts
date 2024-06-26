import { IProducttype } from 'app/shared/model/producttype.model';
import { IKlantbeoordeling } from 'app/shared/model/klantbeoordeling.model';
import { IHeffing } from 'app/shared/model/heffing.model';
import { IProject } from 'app/shared/model/project.model';
import { IZaaktype } from 'app/shared/model/zaaktype.model';
import { IDocument } from 'app/shared/model/document.model';
import { IMedewerker } from 'app/shared/model/medewerker.model';
import { IGrondslag } from 'app/shared/model/grondslag.model';
import { IBedrijfsproces } from 'app/shared/model/bedrijfsproces.model';
import { IBetrokkene } from 'app/shared/model/betrokkene.model';

export interface IZaak {
  id?: number;
  empty?: string | null;
  archiefnominatie?: string | null;
  datumeinde?: string | null;
  datumeindegepland?: string | null;
  datumeindeuiterlijkeafdoening?: string | null;
  datumlaatstebetaling?: string | null;
  datumpublicatie?: string | null;
  datumregistratie?: string | null;
  datumstart?: string | null;
  datumvernietigingdossier?: string | null;
  document?: string | null;
  duurverlenging?: string | null;
  indicatiebetaling?: string | null;
  indicatiedeelzaken?: string | null;
  indicatieopschorting?: string | null;
  leges?: string | null;
  omschrijving?: string | null;
  omschrijvingresultaat?: string | null;
  redenopschorting?: string | null;
  redenverlenging?: string | null;
  toelichting?: string | null;
  toelichtingresultaat?: string | null;
  vertrouwelijkheid?: string | null;
  zaakidentificatie?: string | null;
  zaakniveau?: string | null;
  heeftproductProducttype?: IProducttype;
  heeftKlantbeoordeling?: IKlantbeoordeling;
  heeftHeffing?: IHeffing;
  betreftProject?: IProject | null;
  isvanZaaktype?: IZaaktype | null;
  kentDocuments?: IDocument[] | null;
  afhandelendmedewerkerMedewerkers?: IMedewerker[] | null;
  heeftGrondslags?: IGrondslag[];
  uitgevoerdbinnenBedrijfsproces?: IBedrijfsproces[];
  oefentuitBetrokkenes?: IBetrokkene[];
}

export const defaultValue: Readonly<IZaak> = {};
