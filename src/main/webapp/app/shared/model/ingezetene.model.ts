import { IVerblijfstitel } from 'app/shared/model/verblijfstitel.model';

export interface IIngezetene {
  id?: number;
  aanduidingeuropeeskiesrecht?: boolean | null;
  aanduidinguitgeslotenkiesrecht?: boolean | null;
  datumverkrijgingverblijfstitel?: string | null;
  datumverliesverblijfstitel?: string | null;
  indicatieblokkering?: string | null;
  indicatiecurateleregister?: string | null;
  indicatiegezagminderjarige?: string | null;
  heeftVerblijfstitel?: IVerblijfstitel | null;
}

export const defaultValue: Readonly<IIngezetene> = {
  aanduidingeuropeeskiesrecht: false,
  aanduidinguitgeslotenkiesrecht: false,
};
