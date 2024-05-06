import dayjs from 'dayjs';

export interface ISpeeltoestel {
  id?: number;
  catalogusprijs?: string | null;
  certificaat?: boolean | null;
  certificaatnummer?: string | null;
  certificeringsinstantie?: string | null;
  controlefrequentie?: string | null;
  datumcertificaat?: dayjs.Dayjs | null;
  gemakkelijktoegankelijk?: boolean | null;
  inspectievolgorde?: string | null;
  installatiekosten?: string | null;
  speelterrein?: string | null;
  speeltoesteltoestelonderdeel?: string | null;
  technischelevensduur?: string | null;
  toestelcode?: string | null;
  toestelgroep?: string | null;
  toestelnaam?: string | null;
  type?: string | null;
  typenummer?: string | null;
  typeplus?: string | null;
  typeplus2?: string | null;
  valruimtehoogte?: string | null;
  valruimteomvang?: string | null;
  vrijevalhoogte?: string | null;
}

export const defaultValue: Readonly<ISpeeltoestel> = {
  certificaat: false,
  gemakkelijktoegankelijk: false,
};
