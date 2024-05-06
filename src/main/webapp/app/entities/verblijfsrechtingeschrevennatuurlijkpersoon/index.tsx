import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verblijfsrechtingeschrevennatuurlijkpersoon from './verblijfsrechtingeschrevennatuurlijkpersoon';
import VerblijfsrechtingeschrevennatuurlijkpersoonDetail from './verblijfsrechtingeschrevennatuurlijkpersoon-detail';
import VerblijfsrechtingeschrevennatuurlijkpersoonUpdate from './verblijfsrechtingeschrevennatuurlijkpersoon-update';
import VerblijfsrechtingeschrevennatuurlijkpersoonDeleteDialog from './verblijfsrechtingeschrevennatuurlijkpersoon-delete-dialog';

const VerblijfsrechtingeschrevennatuurlijkpersoonRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verblijfsrechtingeschrevennatuurlijkpersoon />} />
    <Route path="new" element={<VerblijfsrechtingeschrevennatuurlijkpersoonUpdate />} />
    <Route path=":id">
      <Route index element={<VerblijfsrechtingeschrevennatuurlijkpersoonDetail />} />
      <Route path="edit" element={<VerblijfsrechtingeschrevennatuurlijkpersoonUpdate />} />
      <Route path="delete" element={<VerblijfsrechtingeschrevennatuurlijkpersoonDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerblijfsrechtingeschrevennatuurlijkpersoonRoutes;
