import { IProject } from 'app/shared/model/project.model';

export interface IBoring {
  id?: number;
  heeftProject?: IProject | null;
}

export const defaultValue: Readonly<IBoring> = {};
