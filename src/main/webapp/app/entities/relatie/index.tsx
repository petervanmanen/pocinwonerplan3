import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Relatie from './relatie';
import RelatieDetail from './relatie-detail';
import RelatieUpdate from './relatie-update';
import RelatieDeleteDialog from './relatie-delete-dialog';

const RelatieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Relatie />} />
    <Route path="new" element={<RelatieUpdate />} />
    <Route path=":id">
      <Route index element={<RelatieDetail />} />
      <Route path="edit" element={<RelatieUpdate />} />
      <Route path="delete" element={<RelatieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RelatieRoutes;
