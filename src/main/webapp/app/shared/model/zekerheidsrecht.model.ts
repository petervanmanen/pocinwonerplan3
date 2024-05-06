import dayjs from 'dayjs';
import { ITenaamstelling } from 'app/shared/model/tenaamstelling.model';

export interface IZekerheidsrecht {
  id?: number;
  aandeelinbetrokkenrecht?: string | null;
  datumeinderecht?: dayjs.Dayjs | null;
  datumingangrecht?: dayjs.Dayjs | null;
  identificatiezekerheidsrecht?: string | null;
  omschrijvingbetrokkenrecht?: string | null;
  typezekerheidsrecht?: string | null;
  bezwaartTenaamstelling?: ITenaamstelling | null;
}

export const defaultValue: Readonly<IZekerheidsrecht> = {};
