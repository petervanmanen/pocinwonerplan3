import dayjs from 'dayjs';
import { IWoonplaats } from 'app/shared/model/woonplaats.model';
import { IBuurt } from 'app/shared/model/buurt.model';
import { IGebied } from 'app/shared/model/gebied.model';

export interface INummeraanduiding {
  id?: number;
  datumbegingeldigheid?: dayjs.Dayjs | null;
  datumeinde?: dayjs.Dayjs | null;
  datumeindegeldigheid?: dayjs.Dayjs | null;
  datumingang?: dayjs.Dayjs | null;
  geconstateerd?: boolean | null;
  geometrie?: string | null;
  huisletter?: string | null;
  huisnummer?: string | null;
  huisnummertoevoeging?: string | null;
  identificatie?: string | null;
  inonderzoek?: boolean | null;
  postcode?: string | null;
  status?: string | null;
  typeadresseerbaarobject?: string | null;
  versie?: string | null;
  ligtinWoonplaats?: IWoonplaats | null;
  ligtinBuurt?: IBuurt | null;
  ligtinGebieds?: IGebied[] | null;
}

export const defaultValue: Readonly<INummeraanduiding> = {
  geconstateerd: false,
  inonderzoek: false,
};
