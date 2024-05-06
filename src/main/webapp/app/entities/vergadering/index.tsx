import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vergadering from './vergadering';
import VergaderingDetail from './vergadering-detail';
import VergaderingUpdate from './vergadering-update';
import VergaderingDeleteDialog from './vergadering-delete-dialog';

const VergaderingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vergadering />} />
    <Route path="new" element={<VergaderingUpdate />} />
    <Route path=":id">
      <Route index element={<VergaderingDetail />} />
      <Route path="edit" element={<VergaderingUpdate />} />
      <Route path="delete" element={<VergaderingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VergaderingRoutes;
