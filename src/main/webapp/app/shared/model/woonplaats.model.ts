import dayjs from 'dayjs';

export interface IWoonplaats {
  id?: number;
  datumbegingeldigheid?: dayjs.Dayjs | null;
  datumeinde?: dayjs.Dayjs | null;
  datumeindegeldigheid?: dayjs.Dayjs | null;
  datumingang?: dayjs.Dayjs | null;
  geconstateerd?: boolean | null;
  geometrie?: string | null;
  identificatie?: string | null;
  inonderzoek?: boolean | null;
  status?: string | null;
  versie?: string | null;
  woonplaatsnaam?: string | null;
  woonplaatsnaamnen?: string | null;
}

export const defaultValue: Readonly<IWoonplaats> = {
  geconstateerd: false,
  inonderzoek: false,
};
