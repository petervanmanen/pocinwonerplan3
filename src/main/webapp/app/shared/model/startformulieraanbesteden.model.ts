export interface IStartformulieraanbesteden {
  id?: number;
  beoogdelooptijd?: string | null;
  beoogdetotaleopdrachtwaarde?: number | null;
  indicatieaanvullendeopdrachtleverancier?: boolean | null;
  indicatiebeoogdeaanbestedingonderhands?: string | null;
  indicatiebeoogdeprockomtovereen?: boolean | null;
  indicatieeenmaligelos?: string | null;
  indicatiemeerjarigeraamovereenkomst?: boolean | null;
  indicatiemeerjarigrepeterend?: string | null;
  indicatoroverkoepelendproject?: string | null;
  omschrijving?: string | null;
  opdrachtcategorie?: string | null;
  opdrachtsoort?: string | null;
  toelichtingaanvullendeopdracht?: string | null;
  toelichtingeenmaligofrepeterend?: string | null;
}

export const defaultValue: Readonly<IStartformulieraanbesteden> = {
  indicatieaanvullendeopdrachtleverancier: false,
  indicatiebeoogdeprockomtovereen: false,
  indicatiemeerjarigeraamovereenkomst: false,
};
