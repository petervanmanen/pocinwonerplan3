import { IProject } from 'app/shared/model/project.model';
import { IDepot } from 'app/shared/model/depot.model';
import { IKast } from 'app/shared/model/kast.model';
import { IPlank } from 'app/shared/model/plank.model';
import { IStelling } from 'app/shared/model/stelling.model';

export interface IVindplaats {
  id?: number;
  aard?: string | null;
  begindatering?: string | null;
  beschrijving?: string | null;
  bibliografie?: string | null;
  datering?: string | null;
  depot?: string | null;
  documentatie?: string | null;
  einddatering?: string | null;
  gemeente?: string | null;
  locatie?: string | null;
  mobilia?: string | null;
  onderzoek?: string | null;
  projectcode?: string | null;
  vindplaats?: string | null;
  hoortbijProject?: IProject | null;
  istevindeninDepot?: IDepot | null;
  istevindeninKast?: IKast | null;
  istevindeninPlank?: IPlank | null;
  istevindeninStelling?: IStelling | null;
}

export const defaultValue: Readonly<IVindplaats> = {};
