import dayjs from 'dayjs';
import { IBeschikking } from 'app/shared/model/beschikking.model';
import { IClient } from 'app/shared/model/client.model';
import { IToewijzing } from 'app/shared/model/toewijzing.model';
import { IVoorziening } from 'app/shared/model/voorziening.model';
import { ILeverancier } from 'app/shared/model/leverancier.model';

export interface ILevering {
  id?: number;
  code?: string | null;
  datumstart?: dayjs.Dayjs | null;
  datumstop?: dayjs.Dayjs | null;
  eenheid?: string | null;
  frequentie?: string | null;
  omvang?: string | null;
  stopreden?: string | null;
  geleverdeprestatieBeschikking?: IBeschikking | null;
  prestatievoorClient?: IClient | null;
  geleverdezorgToewijzing?: IToewijzing | null;
  voorzieningVoorziening?: IVoorziening | null;
  leverdeprestatieLeverancier?: ILeverancier | null;
}

export const defaultValue: Readonly<ILevering> = {};
