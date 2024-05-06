import { ISensor } from 'app/shared/model/sensor.model';

export interface IVerkeerstelling {
  id?: number;
  aantal?: string | null;
  tijdtot?: string | null;
  tijdvanaf?: string | null;
  gegenereerddoorSensor?: ISensor | null;
}

export const defaultValue: Readonly<IVerkeerstelling> = {};
