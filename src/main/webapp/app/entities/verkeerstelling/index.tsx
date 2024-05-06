import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verkeerstelling from './verkeerstelling';
import VerkeerstellingDetail from './verkeerstelling-detail';
import VerkeerstellingUpdate from './verkeerstelling-update';
import VerkeerstellingDeleteDialog from './verkeerstelling-delete-dialog';

const VerkeerstellingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verkeerstelling />} />
    <Route path="new" element={<VerkeerstellingUpdate />} />
    <Route path=":id">
      <Route index element={<VerkeerstellingDetail />} />
      <Route path="edit" element={<VerkeerstellingUpdate />} />
      <Route path="delete" element={<VerkeerstellingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerkeerstellingRoutes;
