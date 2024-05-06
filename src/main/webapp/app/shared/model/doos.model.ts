import { IMagazijnlocatie } from 'app/shared/model/magazijnlocatie.model';

export interface IDoos {
  id?: number;
  doosnummer?: string | null;
  herkomst?: string | null;
  inhoud?: string | null;
  key?: string | null;
  keymagazijnlocatie?: string | null;
  projectcd?: string | null;
  staatopMagazijnlocatie?: IMagazijnlocatie | null;
}

export const defaultValue: Readonly<IDoos> = {};
