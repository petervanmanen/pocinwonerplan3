import dayjs from 'dayjs';
import { IDatatype } from 'app/shared/model/datatype.model';

export interface IAttribuutsoort {
  authentiek?: boolean | null;
  datumopname?: dayjs.Dayjs | null;
  definitie?: string | null;
  domein?: string | null;
  eaguid?: string | null;
  herkomst?: string | null;
  herkomstdefinitie?: string | null;
  id?: string;
  identificerend?: boolean | null;
  indicatieafleidbaar?: boolean | null;
  indicatiematerielehistorie?: boolean | null;
  kardinaliteit?: string | null;
  lengte?: string | null;
  mogelijkgeenwaarde?: boolean | null;
  naam?: string | null;
  patroon?: string | null;
  precisie?: string | null;
  stereotype?: string | null;
  toelichting?: string | null;
  heeftDatatype?: IDatatype | null;
}

export const defaultValue: Readonly<IAttribuutsoort> = {
  authentiek: false,
  identificerend: false,
  indicatieafleidbaar: false,
  indicatiematerielehistorie: false,
  mogelijkgeenwaarde: false,
};
