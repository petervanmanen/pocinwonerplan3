import dayjs from 'dayjs';

export interface ILigplaats {
  id?: number;
  datumbegingeldigheid?: dayjs.Dayjs | null;
  datumeinde?: dayjs.Dayjs | null;
  datumeindegeldigheid?: dayjs.Dayjs | null;
  datumingang?: dayjs.Dayjs | null;
  documentdatum?: dayjs.Dayjs | null;
  documentnummer?: string | null;
  geconstateerd?: string | null;
  geometrie?: string | null;
  identificatie?: string | null;
  inonderzoek?: boolean | null;
  status?: string | null;
  versie?: string | null;
}

export const defaultValue: Readonly<ILigplaats> = {
  inonderzoek: false,
};
