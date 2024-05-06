import { IDoos } from 'app/shared/model/doos.model';
import { IArtefactsoort } from 'app/shared/model/artefactsoort.model';
import { IVondst } from 'app/shared/model/vondst.model';

export interface IArtefact {
  id?: number;
  artefectnummer?: string | null;
  beschrijving?: string | null;
  conserveren?: boolean | null;
  datering?: string | null;
  dateringcomplex?: string | null;
  determinatieniveau?: string | null;
  dianummer?: string | null;
  doosnummer?: string | null;
  exposabel?: boolean | null;
  fotonummer?: string | null;
  functie?: string | null;
  herkomst?: string | null;
  key?: string | null;
  keydoos?: string | null;
  keymagazijnplaatsing?: string | null;
  keyput?: string | null;
  keyvondst?: string | null;
  literatuur?: string | null;
  maten?: string | null;
  naam?: string | null;
  opmerkingen?: string | null;
  origine?: string | null;
  projectcd?: string | null;
  putnummer?: string | null;
  restauratiewenselijk?: boolean | null;
  tekeningnummer?: string | null;
  type?: string | null;
  vondstnummer?: string | null;
  zitinDoos?: IDoos | null;
  isvansoortArtefactsoort?: IArtefactsoort | null;
  bevatVondst?: IVondst | null;
}

export const defaultValue: Readonly<IArtefact> = {
  conserveren: false,
  exposabel: false,
  restauratiewenselijk: false,
};
