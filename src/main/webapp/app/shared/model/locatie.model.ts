import { IPut } from 'app/shared/model/put.model';
import { IProject } from 'app/shared/model/project.model';
import { IVerzoek } from 'app/shared/model/verzoek.model';
import { IRegeltekst } from 'app/shared/model/regeltekst.model';
import { IActiviteit } from 'app/shared/model/activiteit.model';
import { IGebiedsaanwijzing } from 'app/shared/model/gebiedsaanwijzing.model';
import { INormwaarde } from 'app/shared/model/normwaarde.model';

export interface ILocatie {
  id?: number;
  adres?: string | null;
  heeftlocatiePuts?: IPut[];
  wordtbegrensddoorProjects?: IProject[];
  betreftVerzoeks?: IVerzoek[];
  werkingsgebiedRegelteksts?: IRegeltekst[] | null;
  isverbondenmetActiviteits?: IActiviteit[];
  verwijstnaarGebiedsaanwijzings?: IGebiedsaanwijzing[];
  geldtvoorNormwaardes?: INormwaarde[];
}

export const defaultValue: Readonly<ILocatie> = {};
