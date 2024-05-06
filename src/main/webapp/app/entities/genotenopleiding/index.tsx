import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Genotenopleiding from './genotenopleiding';
import GenotenopleidingDetail from './genotenopleiding-detail';
import GenotenopleidingUpdate from './genotenopleiding-update';
import GenotenopleidingDeleteDialog from './genotenopleiding-delete-dialog';

const GenotenopleidingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Genotenopleiding />} />
    <Route path="new" element={<GenotenopleidingUpdate />} />
    <Route path=":id">
      <Route index element={<GenotenopleidingDetail />} />
      <Route path="edit" element={<GenotenopleidingUpdate />} />
      <Route path="delete" element={<GenotenopleidingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GenotenopleidingRoutes;
