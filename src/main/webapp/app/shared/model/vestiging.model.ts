import dayjs from 'dayjs';
import { IWerkgelegenheid } from 'app/shared/model/werkgelegenheid.model';
import { INummeraanduiding } from 'app/shared/model/nummeraanduiding.model';

export interface IVestiging {
  id?: number;
  commercielevestiging?: string | null;
  datumaanvang?: dayjs.Dayjs | null;
  datumeinde?: dayjs.Dayjs | null;
  datumvoortzetting?: dayjs.Dayjs | null;
  fulltimewerkzamemannen?: string | null;
  fulltimewerkzamevrouwen?: string | null;
  handelsnaam?: string | null;
  parttimewerkzamemannen?: string | null;
  parttimewerkzamevrouwen?: string | null;
  toevoegingadres?: string | null;
  totaalwerkzamepersonen?: string | null;
  verkortenaam?: string | null;
  vestigingsnummer?: string | null;
  heeftWerkgelegenheid?: IWerkgelegenheid;
  heeftalslocatieadresNummeraanduiding?: INummeraanduiding | null;
}

export const defaultValue: Readonly<IVestiging> = {};
