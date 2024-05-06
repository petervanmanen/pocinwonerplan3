import { ILocatie } from 'app/shared/model/locatie.model';
import { IProject } from 'app/shared/model/project.model';

export interface IPut {
  id?: number;
  key?: string | null;
  projectcd?: string | null;
  putnummer?: string | null;
  heeftlocatieLocaties?: ILocatie[] | null;
  heeftProject?: IProject | null;
}

export const defaultValue: Readonly<IPut> = {};
