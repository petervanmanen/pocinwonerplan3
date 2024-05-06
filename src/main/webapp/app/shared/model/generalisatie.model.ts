import dayjs from 'dayjs';

export interface IGeneralisatie {
  datumopname?: dayjs.Dayjs | null;
  definitie?: string | null;
  eaguid?: string | null;
  herkomst?: string | null;
  herkomstdefinitie?: string | null;
  id?: string;
  indicatiematerielehistorie?: boolean | null;
  naam?: string | null;
  toelichting?: string | null;
}

export const defaultValue: Readonly<IGeneralisatie> = {
  indicatiematerielehistorie: false,
};
