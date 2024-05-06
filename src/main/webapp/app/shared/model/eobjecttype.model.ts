import dayjs from 'dayjs';

export interface IEobjecttype {
  datumopname?: dayjs.Dayjs | null;
  definitie?: string | null;
  eaguid?: string | null;
  herkomst?: string | null;
  herkomstdefinitie?: string | null;
  id?: string;
  indicatieabstract?: boolean | null;
  kwaliteit?: string | null;
  naam?: string | null;
  populatie?: string | null;
  stereotype?: string | null;
  toelichting?: string | null;
  uniekeaanduiding?: string | null;
}

export const defaultValue: Readonly<IEobjecttype> = {
  indicatieabstract: false,
};
