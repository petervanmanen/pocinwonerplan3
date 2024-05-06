import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Lijn from './lijn';
import LijnDetail from './lijn-detail';
import LijnUpdate from './lijn-update';
import LijnDeleteDialog from './lijn-delete-dialog';

const LijnRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Lijn />} />
    <Route path="new" element={<LijnUpdate />} />
    <Route path=":id">
      <Route index element={<LijnDetail />} />
      <Route path="edit" element={<LijnUpdate />} />
      <Route path="delete" element={<LijnDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LijnRoutes;
