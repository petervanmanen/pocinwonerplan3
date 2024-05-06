import { IFormuliersoort } from 'app/shared/model/formuliersoort.model';

export interface IFormuliersoortveld {
  id?: number;
  helptekst?: string | null;
  isverplicht?: boolean | null;
  label?: string | null;
  maxlengte?: string | null;
  veldnaam?: string | null;
  veldtype?: string | null;
  heeftveldenFormuliersoort?: IFormuliersoort | null;
}

export const defaultValue: Readonly<IFormuliersoortveld> = {
  isverplicht: false,
};
