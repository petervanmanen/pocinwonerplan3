import dayjs from 'dayjs';
import { IRechtspersoon } from 'app/shared/model/rechtspersoon.model';

export interface ITenaamstelling {
  id?: number;
  aandeelinrecht?: string | null;
  burgerlijkestaattentijdevanverkrijging?: string | null;
  datumbegingeldigheid?: dayjs.Dayjs | null;
  datumeindegeldigheid?: dayjs.Dayjs | null;
  exploitantcode?: string | null;
  identificatietenaamstelling?: string | null;
  verklaringinzakederdenbescherming?: string | null;
  verkregennamenssamenwerkingsverband?: string | null;
  heeftRechtspersoon?: IRechtspersoon | null;
}

export const defaultValue: Readonly<ITenaamstelling> = {};
