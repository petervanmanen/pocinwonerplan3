import dayjs from 'dayjs';

export interface IGemeente {
  id?: number;
  datumbegingeldigheid?: dayjs.Dayjs | null;
  datumeinde?: dayjs.Dayjs | null;
  datumeindegeldigheid?: dayjs.Dayjs | null;
  datumingang?: dayjs.Dayjs | null;
  geconstateerd?: boolean | null;
  gemeentecode?: string | null;
  gemeentenaam?: string | null;
  gemeentenaamnen?: string | null;
  geometrie?: string | null;
  identificatie?: string | null;
  inonderzoek?: boolean | null;
  versie?: string | null;
}

export const defaultValue: Readonly<IGemeente> = {
  geconstateerd: false,
  inonderzoek: false,
};
