import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Prijs from './prijs';
import PrijsDetail from './prijs-detail';
import PrijsUpdate from './prijs-update';
import PrijsDeleteDialog from './prijs-delete-dialog';

const PrijsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Prijs />} />
    <Route path="new" element={<PrijsUpdate />} />
    <Route path=":id">
      <Route index element={<PrijsDetail />} />
      <Route path="edit" element={<PrijsUpdate />} />
      <Route path="delete" element={<PrijsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PrijsRoutes;
