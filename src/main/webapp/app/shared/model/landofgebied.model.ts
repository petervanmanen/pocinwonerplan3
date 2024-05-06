import dayjs from 'dayjs';

export interface ILandofgebied {
  id?: number;
  datumeindeland?: dayjs.Dayjs | null;
  datumingangland?: dayjs.Dayjs | null;
  landcode?: string | null;
  landcodeiso?: string | null;
  landnaam?: string | null;
}

export const defaultValue: Readonly<ILandofgebied> = {};
