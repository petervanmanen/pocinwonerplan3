import { ILocatie } from 'app/shared/model/locatie.model';
import { IProject } from 'app/shared/model/project.model';
import { IVerzoek } from 'app/shared/model/verzoek.model';

export interface IProjectlocatie {
  id?: number;
  adres?: string | null;
  kadastraalperceel?: string | null;
  kadastralegemeente?: string | null;
  kadastralesectie?: string | null;
  betreftLocatie?: ILocatie | null;
  heeftProject?: IProject | null;
  betreftVerzoeks?: IVerzoek[] | null;
}

export const defaultValue: Readonly<IProjectlocatie> = {};
