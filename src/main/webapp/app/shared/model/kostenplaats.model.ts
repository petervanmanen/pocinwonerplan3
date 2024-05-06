import { IInkooporder } from 'app/shared/model/inkooporder.model';
import { ITaakveld } from 'app/shared/model/taakveld.model';
import { IHoofdrekening } from 'app/shared/model/hoofdrekening.model';
import { IProject } from 'app/shared/model/project.model';

export interface IKostenplaats {
  id?: number;
  btwcode?: string | null;
  btwomschrijving?: string | null;
  kostenplaatssoortcode?: string | null;
  kostenplaatssoortomschrijving?: string | null;
  kostenplaatstypecode?: string | null;
  kostenplaatstypeomschrijving?: string | null;
  naam?: string | null;
  omschrijving?: string | null;
  heeftInkooporders?: IInkooporder[] | null;
  heeftTaakvelds?: ITaakveld[];
  heeftHoofdrekenings?: IHoofdrekening[] | null;
  heeftProjects?: IProject[] | null;
}

export const defaultValue: Readonly<IKostenplaats> = {};
