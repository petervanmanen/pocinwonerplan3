import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Mutatie from './mutatie';
import MutatieDetail from './mutatie-detail';
import MutatieUpdate from './mutatie-update';
import MutatieDeleteDialog from './mutatie-delete-dialog';

const MutatieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Mutatie />} />
    <Route path="new" element={<MutatieUpdate />} />
    <Route path=":id">
      <Route index element={<MutatieDetail />} />
      <Route path="edit" element={<MutatieUpdate />} />
      <Route path="delete" element={<MutatieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MutatieRoutes;
