import dayjs from 'dayjs';
import { IBetrokkene } from 'app/shared/model/betrokkene.model';

export interface IKlantbeoordeling {
  id?: number;
  beoordeling?: string | null;
  categorie?: string | null;
  contactopnemen?: boolean | null;
  ddbeoordeling?: dayjs.Dayjs | null;
  kanaal?: string | null;
  onderwerp?: string | null;
  subcategorie?: string | null;
  doetBetrokkene?: IBetrokkene | null;
}

export const defaultValue: Readonly<IKlantbeoordeling> = {
  contactopnemen: false,
};
