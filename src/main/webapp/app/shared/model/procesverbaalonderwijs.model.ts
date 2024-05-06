import dayjs from 'dayjs';

export interface IProcesverbaalonderwijs {
  id?: number;
  datumafgehandeld?: dayjs.Dayjs | null;
  datumeindeproeftijd?: dayjs.Dayjs | null;
  datumingelicht?: dayjs.Dayjs | null;
  datumuitspraak?: dayjs.Dayjs | null;
  datumzitting?: dayjs.Dayjs | null;
  geldboete?: number | null;
  geldboetevoorwaardelijk?: string | null;
  opmerkingen?: string | null;
  proeftijd?: string | null;
  reden?: string | null;
  sanctiesoort?: string | null;
  uitspraak?: string | null;
  verzuimsoort?: string | null;
}

export const defaultValue: Readonly<IProcesverbaalonderwijs> = {};
