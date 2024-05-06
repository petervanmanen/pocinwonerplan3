import dayjs from 'dayjs';
import { IZaak } from 'app/shared/model/zaak.model';
import { ISector } from 'app/shared/model/sector.model';
import { IMedewerker } from 'app/shared/model/medewerker.model';
import { IRechtspersoon } from 'app/shared/model/rechtspersoon.model';
import { IKostenplaats } from 'app/shared/model/kostenplaats.model';
import { IDocument } from 'app/shared/model/document.model';

export interface ISubsidie {
  id?: number;
  accountantscontrole?: string | null;
  cofinanciering?: number | null;
  datumbehandeltermijn?: dayjs.Dayjs | null;
  datumbewaartermijn?: dayjs.Dayjs | null;
  datumeinde?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  datumsubsidievaststelling?: dayjs.Dayjs | null;
  datumverzendingeindeafrekening?: dayjs.Dayjs | null;
  deadlineindiening?: dayjs.Dayjs | null;
  doelstelling?: string | null;
  gerealiseerdeprojectkosten?: dayjs.Dayjs | null;
  hoogtesubsidie?: number | null;
  niveau?: string | null;
  onderwerp?: string | null;
  ontvangenbedrag?: number | null;
  opmerkingen?: string | null;
  opmerkingenvoorschotten?: string | null;
  prestatiesubsidie?: string | null;
  socialreturnbedrag?: number | null;
  socialreturnnagekomen?: string | null;
  socialreturnverplichting?: string | null;
  status?: string | null;
  subsidiebedrag?: number | null;
  subsidiesoort?: string | null;
  subsidievaststellingbedrag?: number | null;
  uitgaandesubsidie?: string | null;
  verantwoordenop?: dayjs.Dayjs | null;
  heeftZaak?: IZaak | null;
  valtbinnenSector?: ISector | null;
  behandelaarMedewerker?: IMedewerker | null;
  verstrekkerRechtspersoon?: IRechtspersoon | null;
  heeftKostenplaats?: IKostenplaats | null;
  heeftDocument?: IDocument | null;
  aanvragerRechtspersoon?: IRechtspersoon | null;
  aanvragerMedewerker?: IMedewerker | null;
}

export const defaultValue: Readonly<ISubsidie> = {};
