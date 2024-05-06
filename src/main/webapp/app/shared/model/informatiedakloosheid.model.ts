import dayjs from 'dayjs';

export interface IInformatiedakloosheid {
  id?: number;
  datumeinde?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  gemeenteoorsprong?: string | null;
  toestemminggemeentelijkbriefadres?: boolean | null;
  toestemmingnachtopvang?: boolean | null;
}

export const defaultValue: Readonly<IInformatiedakloosheid> = {
  toestemminggemeentelijkbriefadres: false,
  toestemmingnachtopvang: false,
};
