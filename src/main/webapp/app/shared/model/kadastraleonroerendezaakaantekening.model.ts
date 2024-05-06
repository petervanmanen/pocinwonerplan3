import dayjs from 'dayjs';

export interface IKadastraleonroerendezaakaantekening {
  id?: number;
  aardaantekeningkadastraalobject?: string | null;
  beschrijvingaantekeningkadastraalobject?: string | null;
  datumbeginaantekeningkadastraalobject?: dayjs.Dayjs | null;
  datumeindeaantekeningkadastraalobject?: dayjs.Dayjs | null;
  kadasteridentificatieaantekening?: string | null;
}

export const defaultValue: Readonly<IKadastraleonroerendezaakaantekening> = {};
