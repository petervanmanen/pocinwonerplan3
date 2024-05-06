import dayjs from 'dayjs';

export interface IDisciplinairemaatregel {
  id?: number;
  datumgeconstateerd?: dayjs.Dayjs | null;
  datumopgelegd?: dayjs.Dayjs | null;
  omschrijving?: string | null;
  reden?: string | null;
}

export const defaultValue: Readonly<IDisciplinairemaatregel> = {};
