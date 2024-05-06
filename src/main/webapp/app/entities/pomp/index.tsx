import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Pomp from './pomp';
import PompDetail from './pomp-detail';
import PompUpdate from './pomp-update';
import PompDeleteDialog from './pomp-delete-dialog';

const PompRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Pomp />} />
    <Route path="new" element={<PompUpdate />} />
    <Route path=":id">
      <Route index element={<PompDetail />} />
      <Route path="edit" element={<PompUpdate />} />
      <Route path="delete" element={<PompDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PompRoutes;
