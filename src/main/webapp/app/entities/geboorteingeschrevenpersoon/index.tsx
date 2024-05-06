import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Geboorteingeschrevenpersoon from './geboorteingeschrevenpersoon';
import GeboorteingeschrevenpersoonDetail from './geboorteingeschrevenpersoon-detail';
import GeboorteingeschrevenpersoonUpdate from './geboorteingeschrevenpersoon-update';
import GeboorteingeschrevenpersoonDeleteDialog from './geboorteingeschrevenpersoon-delete-dialog';

const GeboorteingeschrevenpersoonRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Geboorteingeschrevenpersoon />} />
    <Route path="new" element={<GeboorteingeschrevenpersoonUpdate />} />
    <Route path=":id">
      <Route index element={<GeboorteingeschrevenpersoonDetail />} />
      <Route path="edit" element={<GeboorteingeschrevenpersoonUpdate />} />
      <Route path="delete" element={<GeboorteingeschrevenpersoonDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GeboorteingeschrevenpersoonRoutes;
