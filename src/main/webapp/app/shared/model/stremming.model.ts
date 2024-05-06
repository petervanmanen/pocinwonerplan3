import { IWegdeel } from 'app/shared/model/wegdeel.model';
import { IMedewerker } from 'app/shared/model/medewerker.model';

export interface IStremming {
  id?: number;
  aantalgehinderden?: string | null;
  datumaanmelding?: string | null;
  datumeinde?: string | null;
  datumstart?: string | null;
  datumwijziging?: string | null;
  delentoegestaan?: boolean | null;
  geschiktvoorpublicatie?: boolean | null;
  hinderklasse?: string | null;
  locatie?: string | null;
  naam?: string | null;
  status?: string | null;
  betreftWegdeels?: IWegdeel[] | null;
  ingevoerddoorMedewerker?: IMedewerker | null;
  gewijzigddoorMedewerker?: IMedewerker | null;
}

export const defaultValue: Readonly<IStremming> = {
  delentoegestaan: false,
  geschiktvoorpublicatie: false,
};
