import dayjs from 'dayjs';

export interface IAomaanvraagwmojeugd {
  id?: number;
  clientreactie?: string | null;
  datumbeschikking?: dayjs.Dayjs | null;
  datumeersteafspraak?: dayjs.Dayjs | null;
  datumeinde?: dayjs.Dayjs | null;
  datumplanvastgesteld?: dayjs.Dayjs | null;
  datumstartaanvraag?: dayjs.Dayjs | null;
  deskundigheid?: string | null;
  doorloopmethodiek?: string | null;
  maximaledoorlooptijd?: string | null;
  redenafsluiting?: string | null;
}

export const defaultValue: Readonly<IAomaanvraagwmojeugd> = {};
