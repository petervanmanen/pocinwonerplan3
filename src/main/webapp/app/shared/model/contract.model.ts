import dayjs from 'dayjs';
import { ILeverancier } from 'app/shared/model/leverancier.model';

export interface IContract {
  id?: number;
  autorisatiegroep?: string | null;
  beschrijving?: string | null;
  categorie?: string | null;
  classificatie?: string | null;
  contractrevisie?: string | null;
  datumcreatie?: dayjs.Dayjs | null;
  datumeinde?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  groep?: string | null;
  interncontractid?: string | null;
  interncontractrevisie?: string | null;
  opmerkingen?: string | null;
  status?: string | null;
  type?: string | null;
  voorwaarde?: string | null;
  zoekwoorden?: string | null;
  bovenliggendContract?: IContract | null;
  heeftLeverancier?: ILeverancier | null;
  contractantLeverancier?: ILeverancier | null;
}

export const defaultValue: Readonly<IContract> = {};
