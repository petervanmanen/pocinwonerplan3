import { IContainer } from 'app/shared/model/container.model';

export interface IVulgraadmeting {
  id?: number;
  tijdstip?: string | null;
  vulgraad?: string | null;
  vullinggewicht?: string | null;
  heeftContainer?: IContainer | null;
}

export const defaultValue: Readonly<IVulgraadmeting> = {};
