export interface IAcademischetitel {
  id?: number;
  codeacademischetitel?: string | null;
  datumbegingeldigheidtitel?: string | null;
  datumeindegeldigheidtitel?: string | null;
  omschrijvingacademischetitel?: string | null;
  positieacademischetiteltovnaam?: string | null;
}

export const defaultValue: Readonly<IAcademischetitel> = {};
