import { IThema } from 'app/shared/model/thema.model';
import { IIdealisatie } from 'app/shared/model/idealisatie.model';
import { ILocatie } from 'app/shared/model/locatie.model';
import { IOmgevingsdocument } from 'app/shared/model/omgevingsdocument.model';

export interface IRegeltekst {
  id?: number;
  identificatie?: string | null;
  omschrijving?: string | null;
  tekst?: string | null;
  heeftthemaThemas?: IThema[] | null;
  heeftidealisatieIdealisaties?: IIdealisatie[] | null;
  werkingsgebiedLocaties?: ILocatie[] | null;
  bevatOmgevingsdocument?: IOmgevingsdocument;
  werkingsgebiedRegeltekst2?: IRegeltekst | null;
  isgerelateerdRegeltekst2?: IRegeltekst | null;
}

export const defaultValue: Readonly<IRegeltekst> = {};
