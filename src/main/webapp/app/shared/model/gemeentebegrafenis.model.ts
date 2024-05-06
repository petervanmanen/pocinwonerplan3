import dayjs from 'dayjs';

export interface IGemeentebegrafenis {
  id?: number;
  achtergrondmelding?: string | null;
  begrafeniskosten?: number | null;
  datumafgedaan?: dayjs.Dayjs | null;
  datumbegrafenis?: dayjs.Dayjs | null;
  datumgemeld?: dayjs.Dayjs | null;
  datumruiminggraf?: dayjs.Dayjs | null;
  doodsoorzaak?: string | null;
  gemeentelijkekosten?: number | null;
  inkoopordernummer?: string | null;
  melder?: string | null;
  urengemeente?: string | null;
  verhaaldbedrag?: number | null;
}

export const defaultValue: Readonly<IGemeentebegrafenis> = {};
