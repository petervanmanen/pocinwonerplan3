import dayjs from 'dayjs';

export interface INationaliteit {
  id?: number;
  buitenlandsenationaliteit?: boolean | null;
  datumeindegeldigheid?: dayjs.Dayjs | null;
  datuminganggeldigheid?: dayjs.Dayjs | null;
  datumopnamen?: string | null;
  datumverliesnationaliteit?: dayjs.Dayjs | null;
  nationaliteit?: string | null;
  nationaliteitcode?: string | null;
  redenverkrijgingnederlandsenationaliteit?: string | null;
  redenverliesnederlandsenationaliteit?: string | null;
}

export const defaultValue: Readonly<INationaliteit> = {
  buitenlandsenationaliteit: false,
};
