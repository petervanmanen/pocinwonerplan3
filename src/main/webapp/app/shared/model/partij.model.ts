import dayjs from 'dayjs';

export interface IPartij {
  id?: number;
  code?: string | null;
  datumaanvanggeldigheidpartij?: dayjs.Dayjs | null;
  datumeindegeldigheidpartij?: dayjs.Dayjs | null;
  naam?: string | null;
  soort?: string | null;
  verstrekkingsbeperkingmogelijk?: string | null;
}

export const defaultValue: Readonly<IPartij> = {};
