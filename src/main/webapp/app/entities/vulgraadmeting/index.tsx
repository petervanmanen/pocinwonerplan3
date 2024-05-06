import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vulgraadmeting from './vulgraadmeting';
import VulgraadmetingDetail from './vulgraadmeting-detail';
import VulgraadmetingUpdate from './vulgraadmeting-update';
import VulgraadmetingDeleteDialog from './vulgraadmeting-delete-dialog';

const VulgraadmetingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vulgraadmeting />} />
    <Route path="new" element={<VulgraadmetingUpdate />} />
    <Route path=":id">
      <Route index element={<VulgraadmetingDetail />} />
      <Route path="edit" element={<VulgraadmetingUpdate />} />
      <Route path="delete" element={<VulgraadmetingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VulgraadmetingRoutes;
