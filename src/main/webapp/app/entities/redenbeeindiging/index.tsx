import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Redenbeeindiging from './redenbeeindiging';
import RedenbeeindigingDetail from './redenbeeindiging-detail';
import RedenbeeindigingUpdate from './redenbeeindiging-update';
import RedenbeeindigingDeleteDialog from './redenbeeindiging-delete-dialog';

const RedenbeeindigingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Redenbeeindiging />} />
    <Route path="new" element={<RedenbeeindigingUpdate />} />
    <Route path=":id">
      <Route index element={<RedenbeeindigingDetail />} />
      <Route path="edit" element={<RedenbeeindigingUpdate />} />
      <Route path="delete" element={<RedenbeeindigingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RedenbeeindigingRoutes;
