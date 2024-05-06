import { IProject } from 'app/shared/model/project.model';

export interface IArcheologiebesluit {
  id?: number;
  heeftProject?: IProject | null;
}

export const defaultValue: Readonly<IArcheologiebesluit> = {};
