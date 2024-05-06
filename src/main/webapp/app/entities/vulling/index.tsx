import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vulling from './vulling';
import VullingDetail from './vulling-detail';
import VullingUpdate from './vulling-update';
import VullingDeleteDialog from './vulling-delete-dialog';

const VullingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vulling />} />
    <Route path="new" element={<VullingUpdate />} />
    <Route path=":id">
      <Route index element={<VullingDetail />} />
      <Route path="edit" element={<VullingUpdate />} />
      <Route path="delete" element={<VullingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VullingRoutes;
