import dayjs from 'dayjs';

export interface IJuridischeregel {
  id?: number;
  datumbekend?: dayjs.Dayjs | null;
  datumeindegeldigheid?: dayjs.Dayjs | null;
  datuminwerking?: dayjs.Dayjs | null;
  datumstart?: dayjs.Dayjs | null;
  omschrijving?: string | null;
  regeltekst?: string | null;
  thema?: string | null;
}

export const defaultValue: Readonly<IJuridischeregel> = {};
