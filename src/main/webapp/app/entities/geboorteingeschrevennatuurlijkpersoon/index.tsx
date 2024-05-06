import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Geboorteingeschrevennatuurlijkpersoon from './geboorteingeschrevennatuurlijkpersoon';
import GeboorteingeschrevennatuurlijkpersoonDetail from './geboorteingeschrevennatuurlijkpersoon-detail';
import GeboorteingeschrevennatuurlijkpersoonUpdate from './geboorteingeschrevennatuurlijkpersoon-update';
import GeboorteingeschrevennatuurlijkpersoonDeleteDialog from './geboorteingeschrevennatuurlijkpersoon-delete-dialog';

const GeboorteingeschrevennatuurlijkpersoonRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Geboorteingeschrevennatuurlijkpersoon />} />
    <Route path="new" element={<GeboorteingeschrevennatuurlijkpersoonUpdate />} />
    <Route path=":id">
      <Route index element={<GeboorteingeschrevennatuurlijkpersoonDetail />} />
      <Route path="edit" element={<GeboorteingeschrevennatuurlijkpersoonUpdate />} />
      <Route path="delete" element={<GeboorteingeschrevennatuurlijkpersoonDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GeboorteingeschrevennatuurlijkpersoonRoutes;
