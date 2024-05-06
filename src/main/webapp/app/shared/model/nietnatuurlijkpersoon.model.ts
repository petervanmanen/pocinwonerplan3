import dayjs from 'dayjs';

export interface INietnatuurlijkpersoon {
  id?: number;
  datumaanvang?: dayjs.Dayjs | null;
  datumeinde?: dayjs.Dayjs | null;
  datumuitschrijving?: dayjs.Dayjs | null;
  datumvoortzetting?: dayjs.Dayjs | null;
  faxnummer?: string | null;
  ingeschreven?: boolean | null;
  inoprichting?: boolean | null;
  kvknummer?: string | null;
  nnpid?: string | null;
  rechtsvorm?: string | null;
  rsinnummer?: string | null;
  statutairenaam?: string | null;
  statutairezetel?: string | null;
  websiteurl?: string | null;
}

export const defaultValue: Readonly<INietnatuurlijkpersoon> = {
  ingeschreven: false,
  inoprichting: false,
};
