import dayjs from 'dayjs';

export interface IToepasbareregel {
  id?: number;
  datumbegingeldigheid?: dayjs.Dayjs | null;
  datumeindegeldigheid?: dayjs.Dayjs | null;
  domein?: string | null;
  naam?: string | null;
  omschrijving?: string | null;
  soortaansluitpunt?: string | null;
  toestemming?: string | null;
}

export const defaultValue: Readonly<IToepasbareregel> = {};
