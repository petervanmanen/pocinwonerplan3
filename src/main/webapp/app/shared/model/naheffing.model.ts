import dayjs from 'dayjs';

export interface INaheffing {
  id?: number;
  bedrag?: number | null;
  bezwaarafgehandeld?: dayjs.Dayjs | null;
  bezwaaringetrokken?: dayjs.Dayjs | null;
  bezwaartoegewezen?: dayjs.Dayjs | null;
  bonnummer?: string | null;
  datumbetaling?: dayjs.Dayjs | null;
  datumbezwaar?: dayjs.Dayjs | null;
  datumgeseponeerd?: dayjs.Dayjs | null;
  datumindiening?: dayjs.Dayjs | null;
  dienstcd?: string | null;
  fiscaal?: boolean | null;
  organisatie?: string | null;
  overtreding?: string | null;
  parkeertarief?: number | null;
  redenseponeren?: string | null;
  vorderingnummer?: string | null;
}

export const defaultValue: Readonly<INaheffing> = {
  fiscaal: false,
};
