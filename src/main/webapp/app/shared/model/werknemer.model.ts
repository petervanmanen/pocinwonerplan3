import dayjs from 'dayjs';
import { IGeweldsincident } from 'app/shared/model/geweldsincident.model';
import { IRol } from 'app/shared/model/rol.model';
import { ISollicitatiegesprek } from 'app/shared/model/sollicitatiegesprek.model';

export interface IWerknemer {
  id?: number;
  geboortedatum?: dayjs.Dayjs | null;
  naam?: string | null;
  voornaam?: string | null;
  woonplaats?: string | null;
  heeftondergaanGeweldsincident?: IGeweldsincident | null;
  heeftRols?: IRol[];
  doetsollicitatiegesprekSollicitatiegespreks?: ISollicitatiegesprek[];
}

export const defaultValue: Readonly<IWerknemer> = {};
