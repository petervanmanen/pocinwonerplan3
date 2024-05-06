import dayjs from 'dayjs';
import { IVastgoedobject } from 'app/shared/model/vastgoedobject.model';
import { IPand } from 'app/shared/model/pand.model';

export interface IVerblijfsobject {
  id?: number;
  aantalkamers?: string | null;
  datumbegingeldigheid?: dayjs.Dayjs | null;
  datumeinde?: dayjs.Dayjs | null;
  datumeindegeldigheid?: dayjs.Dayjs | null;
  datumingang?: dayjs.Dayjs | null;
  documentdatum?: dayjs.Dayjs | null;
  documentnr?: string | null;
  gebruiksdoel?: string | null;
  geconstateerd?: boolean | null;
  geometrie?: string | null;
  hoogstebouwlaagverblijfsobject?: string | null;
  identificatie?: string | null;
  inonderzoek?: boolean | null;
  laagstebouwlaagverblijfsobject?: string | null;
  ontsluitingverdieping?: string | null;
  soortwoonobject?: string | null;
  status?: string | null;
  toegangbouwlaagverblijfsobject?: string | null;
  versie?: string | null;
  heeftVastgoedobject?: IVastgoedobject;
  maaktdeeluitvanPands?: IPand[] | null;
}

export const defaultValue: Readonly<IVerblijfsobject> = {
  geconstateerd: false,
  inonderzoek: false,
};
