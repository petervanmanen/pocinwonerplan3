import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verblijfadresingeschrevennatuurlijkpersoon from './verblijfadresingeschrevennatuurlijkpersoon';
import VerblijfadresingeschrevennatuurlijkpersoonDetail from './verblijfadresingeschrevennatuurlijkpersoon-detail';
import VerblijfadresingeschrevennatuurlijkpersoonUpdate from './verblijfadresingeschrevennatuurlijkpersoon-update';
import VerblijfadresingeschrevennatuurlijkpersoonDeleteDialog from './verblijfadresingeschrevennatuurlijkpersoon-delete-dialog';

const VerblijfadresingeschrevennatuurlijkpersoonRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verblijfadresingeschrevennatuurlijkpersoon />} />
    <Route path="new" element={<VerblijfadresingeschrevennatuurlijkpersoonUpdate />} />
    <Route path=":id">
      <Route index element={<VerblijfadresingeschrevennatuurlijkpersoonDetail />} />
      <Route path="edit" element={<VerblijfadresingeschrevennatuurlijkpersoonUpdate />} />
      <Route path="delete" element={<VerblijfadresingeschrevennatuurlijkpersoonDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerblijfadresingeschrevennatuurlijkpersoonRoutes;
