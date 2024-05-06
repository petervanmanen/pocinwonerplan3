import dayjs from 'dayjs';

export interface IVorderingregel {
  id?: number;
  aangemaaktdoor?: string | null;
  aanmaakdatum?: dayjs.Dayjs | null;
  bedragexclbtw?: string | null;
  bedraginclbtw?: string | null;
  btwcategorie?: string | null;
  gemuteerddoor?: string | null;
  mutatiedatum?: dayjs.Dayjs | null;
  omschrijving?: string | null;
  periodiek?: string | null;
  type?: string | null;
}

export const defaultValue: Readonly<IVorderingregel> = {};
