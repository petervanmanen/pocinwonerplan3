import { IAanbesteding } from 'app/shared/model/aanbesteding.model';
import { ILeverancier } from 'app/shared/model/leverancier.model';

export interface IOfferte {
  id?: number;
  betreftAanbesteding?: IAanbesteding | null;
  ingedienddoorLeverancier?: ILeverancier | null;
}

export const defaultValue: Readonly<IOfferte> = {};
