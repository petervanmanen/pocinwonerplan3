export interface IDocumenttype {
  id?: number;
  datumbegingeldigheiddocumenttype?: string | null;
  datumeindegeldigheiddocumenttype?: string | null;
  documentcategorie?: string | null;
  documenttypeomschrijving?: string | null;
  documenttypeomschrijvinggeneriek?: string | null;
  documenttypetrefwoord?: string | null;
}

export const defaultValue: Readonly<IDocumenttype> = {};
