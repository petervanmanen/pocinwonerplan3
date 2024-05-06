import dayjs from 'dayjs';
import { IZaak } from 'app/shared/model/zaak.model';
import { IGunning } from 'app/shared/model/gunning.model';
import { IMedewerker } from 'app/shared/model/medewerker.model';

export interface IAanbesteding {
  id?: number;
  datumpublicatie?: string | null;
  datumstart?: dayjs.Dayjs | null;
  digitaal?: string | null;
  naam?: string | null;
  procedure?: string | null;
  referentienummer?: string | null;
  scoremaximaal?: string | null;
  status?: string | null;
  tendernedkenmerk?: string | null;
  type?: string | null;
  volgendesluiting?: string | null;
  betreftZaak?: IZaak | null;
  mondtuitGunning?: IGunning | null;
  procesleiderMedewerker?: IMedewerker | null;
}

export const defaultValue: Readonly<IAanbesteding> = {};
