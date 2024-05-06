import { IProducttype } from 'app/shared/model/producttype.model';
import { IProduct } from 'app/shared/model/product.model';
import { IBedrijfsprocestype } from 'app/shared/model/bedrijfsprocestype.model';
import { IMedewerker } from 'app/shared/model/medewerker.model';
import { IFormuliersoort } from 'app/shared/model/formuliersoort.model';

export interface IZaaktype {
  id?: number;
  archiefcode?: string | null;
  datumbegingeldigheidzaaktype?: string | null;
  datumeindegeldigheidzaaktype?: string | null;
  doorlooptijdbehandeling?: string | null;
  indicatiepublicatie?: string | null;
  publicatietekst?: string | null;
  servicenormbehandeling?: string | null;
  trefwoord?: string | null;
  vertrouwelijkaanduiding?: string | null;
  zaakcategorie?: string | null;
  zaaktypeomschrijving?: string | null;
  zaaktypeomschrijvinggeneriek?: string | null;
  heeftProducttype?: IProducttype;
  betreftProduct?: IProduct | null;
  heeftBedrijfsprocestype?: IBedrijfsprocestype;
  isverantwoordelijkevoorMedewerker?: IMedewerker | null;
  isaanleidingvoorFormuliersoorts?: IFormuliersoort[] | null;
}

export const defaultValue: Readonly<IZaaktype> = {};
