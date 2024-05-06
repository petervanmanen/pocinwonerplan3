import dayjs from 'dayjs';

export interface IFormulierverlenginginhuur {
  id?: number;
  datumeindenieuw?: dayjs.Dayjs | null;
  indicatieredeninhuurgewijzigd?: string | null;
  indicatieverhogeninkooporder?: string | null;
  toelichting?: string | null;
}

export const defaultValue: Readonly<IFormulierverlenginginhuur> = {};
