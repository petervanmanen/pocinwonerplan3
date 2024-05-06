import dayjs from 'dayjs';

export interface IInrichtingselement {
  id?: number;
  datumbegingeldigheidinrichtingselement?: dayjs.Dayjs | null;
  datumeindegeldigheidinrichtingselement?: dayjs.Dayjs | null;
  geometrieinrichtingselement?: string | null;
  identificatieinrichtingselement?: string | null;
  lod0geometrieinrichtingselement?: string | null;
  plustypeinrichtingselement?: string | null;
  relatievehoogteligginginrichtingselement?: string | null;
  statusinrichtingselement?: string | null;
  typeinrichtingselement?: string | null;
}

export const defaultValue: Readonly<IInrichtingselement> = {};
