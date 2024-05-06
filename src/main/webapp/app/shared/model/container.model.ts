import { IFractie } from 'app/shared/model/fractie.model';
import { IContainertype } from 'app/shared/model/containertype.model';
import { ILocatie } from 'app/shared/model/locatie.model';

export interface IContainer {
  id?: number;
  containercode?: string | null;
  sensorid?: string | null;
  geschiktvoorFractie?: IFractie | null;
  soortContainertype?: IContainertype | null;
  heeftLocatie?: ILocatie | null;
}

export const defaultValue: Readonly<IContainer> = {};
