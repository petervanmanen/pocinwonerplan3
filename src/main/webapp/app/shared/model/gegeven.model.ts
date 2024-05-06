import { IClassificatie } from 'app/shared/model/classificatie.model';
import { IApplicatie } from 'app/shared/model/applicatie.model';

export interface IGegeven {
  alias?: string | null;
  eaguid?: string | null;
  id?: string;
  naam?: string | null;
  stereotype?: string | null;
  toelichting?: string | null;
  geclassificeerdalsClassificaties?: IClassificatie[] | null;
  bevatApplicatie?: IApplicatie | null;
}

export const defaultValue: Readonly<IGegeven> = {};
