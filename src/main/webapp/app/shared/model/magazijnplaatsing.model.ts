import dayjs from 'dayjs';

export interface IMagazijnplaatsing {
  id?: number;
  beschrijving?: string | null;
  datumgeplaatst?: dayjs.Dayjs | null;
  herkomst?: string | null;
  key?: string | null;
  keydoos?: string | null;
  keymagazijnlocatie?: string | null;
  projectcd?: string | null;
  uitgeleend?: boolean | null;
}

export const defaultValue: Readonly<IMagazijnplaatsing> = {
  uitgeleend: false,
};
