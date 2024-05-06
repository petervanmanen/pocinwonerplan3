import { IProject } from 'app/shared/model/project.model';
import { IVerzoek } from 'app/shared/model/verzoek.model';

export interface IProjectactiviteit {
  id?: number;
  heeftProject?: IProject | null;
  betreftVerzoeks?: IVerzoek[] | null;
}

export const defaultValue: Readonly<IProjectactiviteit> = {};
