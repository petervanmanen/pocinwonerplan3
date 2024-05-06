import dayjs from 'dayjs';

export interface IVordering {
  id?: number;
  aangemaaktdoor?: string | null;
  bedragbtw?: string | null;
  datumaanmaak?: dayjs.Dayjs | null;
  datummutatie?: dayjs.Dayjs | null;
  geaccordeerd?: string | null;
  geaccordeerddoor?: string | null;
  geaccordeerdop?: dayjs.Dayjs | null;
  geexporteerd?: string | null;
  gemuteerddoor?: string | null;
  omschrijving?: string | null;
  totaalbedrag?: string | null;
  totaalbedraginclusief?: string | null;
  vorderingnummer?: string | null;
}

export const defaultValue: Readonly<IVordering> = {};
