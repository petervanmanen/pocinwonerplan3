import dayjs from 'dayjs';
import { IDocument } from 'app/shared/model/document.model';

export interface IVerkeersbesluit {
  id?: number;
  datumbesluit?: dayjs.Dayjs | null;
  datumeinde?: string | null;
  datumstart?: string | null;
  huisnummer?: string | null;
  postcode?: string | null;
  referentienummer?: string | null;
  straat?: string | null;
  titel?: string | null;
  isvastgelegdinDocument?: IDocument;
}

export const defaultValue: Readonly<IVerkeersbesluit> = {};
