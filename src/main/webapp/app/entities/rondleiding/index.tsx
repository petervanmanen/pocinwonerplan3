import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Rondleiding from './rondleiding';
import RondleidingDetail from './rondleiding-detail';
import RondleidingUpdate from './rondleiding-update';
import RondleidingDeleteDialog from './rondleiding-delete-dialog';

const RondleidingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Rondleiding />} />
    <Route path="new" element={<RondleidingUpdate />} />
    <Route path=":id">
      <Route index element={<RondleidingDetail />} />
      <Route path="edit" element={<RondleidingUpdate />} />
      <Route path="delete" element={<RondleidingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RondleidingRoutes;
