import dayjs from 'dayjs';

export interface IVerzoekomtoewijzing {
  id?: number;
  beschikkingsnummer?: string | null;
  commentaar?: string | null;
  datumeindetoewijzing?: dayjs.Dayjs | null;
  datumingangbeschikking?: dayjs.Dayjs | null;
  datumingangtoewijzing?: dayjs.Dayjs | null;
  datumontvangst?: dayjs.Dayjs | null;
  eenheid?: string | null;
  frequentie?: string | null;
  raamcontract?: boolean | null;
  referentieaanbieder?: string | null;
  soortverwijzer?: string | null;
  verwijzer?: string | null;
  volume?: string | null;
}

export const defaultValue: Readonly<IVerzoekomtoewijzing> = {
  raamcontract: false,
};
