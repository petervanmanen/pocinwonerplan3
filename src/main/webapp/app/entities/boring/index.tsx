import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Boring from './boring';
import BoringDetail from './boring-detail';
import BoringUpdate from './boring-update';
import BoringDeleteDialog from './boring-delete-dialog';

const BoringRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Boring />} />
    <Route path="new" element={<BoringUpdate />} />
    <Route path=":id">
      <Route index element={<BoringDetail />} />
      <Route path="edit" element={<BoringUpdate />} />
      <Route path="delete" element={<BoringDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BoringRoutes;
