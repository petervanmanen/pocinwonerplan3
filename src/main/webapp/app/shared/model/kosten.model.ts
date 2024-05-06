import dayjs from 'dayjs';

export interface IKosten {
  id?: number;
  aangemaaktdoor?: string | null;
  aantal?: string | null;
  bedrag?: string | null;
  bedragtotaal?: string | null;
  datumaanmaak?: dayjs.Dayjs | null;
  datummutatie?: dayjs.Dayjs | null;
  eenheid?: string | null;
  geaccordeerd?: string | null;
  gefactureerdop?: dayjs.Dayjs | null;
  gemuteerddoor?: string | null;
  naam?: string | null;
  omschrijving?: string | null;
  opbasisvangrondslag?: string | null;
  tarief?: string | null;
  type?: string | null;
  vastgesteldbedrag?: string | null;
}

export const defaultValue: Readonly<IKosten> = {};
