import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vorderingregel from './vorderingregel';
import VorderingregelDetail from './vorderingregel-detail';
import VorderingregelUpdate from './vorderingregel-update';
import VorderingregelDeleteDialog from './vorderingregel-delete-dialog';

const VorderingregelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vorderingregel />} />
    <Route path="new" element={<VorderingregelUpdate />} />
    <Route path=":id">
      <Route index element={<VorderingregelDetail />} />
      <Route path="edit" element={<VorderingregelUpdate />} />
      <Route path="delete" element={<VorderingregelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VorderingregelRoutes;
