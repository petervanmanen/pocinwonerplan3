import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Nationaliteitingeschrevennatuurlijkpersoon from './nationaliteitingeschrevennatuurlijkpersoon';
import NationaliteitingeschrevennatuurlijkpersoonDetail from './nationaliteitingeschrevennatuurlijkpersoon-detail';
import NationaliteitingeschrevennatuurlijkpersoonUpdate from './nationaliteitingeschrevennatuurlijkpersoon-update';
import NationaliteitingeschrevennatuurlijkpersoonDeleteDialog from './nationaliteitingeschrevennatuurlijkpersoon-delete-dialog';

const NationaliteitingeschrevennatuurlijkpersoonRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Nationaliteitingeschrevennatuurlijkpersoon />} />
    <Route path="new" element={<NationaliteitingeschrevennatuurlijkpersoonUpdate />} />
    <Route path=":id">
      <Route index element={<NationaliteitingeschrevennatuurlijkpersoonDetail />} />
      <Route path="edit" element={<NationaliteitingeschrevennatuurlijkpersoonUpdate />} />
      <Route path="delete" element={<NationaliteitingeschrevennatuurlijkpersoonDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NationaliteitingeschrevennatuurlijkpersoonRoutes;
