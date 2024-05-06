import dayjs from 'dayjs';
import { IVastgoedobject } from 'app/shared/model/vastgoedobject.model';
import { IBuurt } from 'app/shared/model/buurt.model';
import { IVerblijfsobject } from 'app/shared/model/verblijfsobject.model';

export interface IPand {
  id?: number;
  brutoinhoudpand?: string | null;
  datumbegingeldigheid?: dayjs.Dayjs | null;
  datumeinde?: dayjs.Dayjs | null;
  datumeindegeldigheid?: dayjs.Dayjs | null;
  datumingang?: dayjs.Dayjs | null;
  geconstateerd?: boolean | null;
  geometriebovenaanzicht?: string | null;
  geometriemaaiveld?: string | null;
  geometriepunt?: string | null;
  hoogstebouwlaagpand?: string | null;
  identificatie?: string | null;
  inonderzoek?: boolean | null;
  laagstebouwlaagpand?: string | null;
  oorspronkelijkbouwjaar?: string | null;
  oppervlakte?: string | null;
  relatievehoogteliggingpand?: string | null;
  status?: string | null;
  statusvoortgangbouw?: string | null;
  versie?: string | null;
  heeftVastgoedobject?: IVastgoedobject;
  zonderverblijfsobjectligtinBuurt?: IBuurt | null;
  maaktdeeluitvanVerblijfsobjects?: IVerblijfsobject[];
}

export const defaultValue: Readonly<IPand> = {
  geconstateerd: false,
  inonderzoek: false,
};
