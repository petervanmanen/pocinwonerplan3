import { ITaakveld } from 'app/shared/model/taakveld.model';
import { IAgendapunt } from 'app/shared/model/agendapunt.model';
import { IProgramma } from 'app/shared/model/programma.model';
import { IVergadering } from 'app/shared/model/vergadering.model';
import { ICategorie } from 'app/shared/model/categorie.model';
import { IDossier } from 'app/shared/model/dossier.model';
import { IIndiener } from 'app/shared/model/indiener.model';

export interface IRaadsstuk {
  id?: number;
  besloten?: string | null;
  datumexpiratie?: string | null;
  datumpublicatie?: string | null;
  datumregistratie?: string | null;
  typeraadsstuk?: string | null;
  heeftTaakveld?: ITaakveld | null;
  behandeltAgendapunts?: IAgendapunt[] | null;
  hoortbijProgrammas?: IProgramma[] | null;
  wordtbehandeldinVergaderings?: IVergadering[] | null;
  heeftCategorie?: ICategorie | null;
  hoortbijDossiers?: IDossier[] | null;
  heeftIndieners?: IIndiener[];
}

export const defaultValue: Readonly<IRaadsstuk> = {};
