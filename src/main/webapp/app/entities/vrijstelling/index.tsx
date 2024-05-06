import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vrijstelling from './vrijstelling';
import VrijstellingDetail from './vrijstelling-detail';
import VrijstellingUpdate from './vrijstelling-update';
import VrijstellingDeleteDialog from './vrijstelling-delete-dialog';

const VrijstellingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vrijstelling />} />
    <Route path="new" element={<VrijstellingUpdate />} />
    <Route path=":id">
      <Route index element={<VrijstellingDetail />} />
      <Route path="edit" element={<VrijstellingUpdate />} />
      <Route path="delete" element={<VrijstellingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VrijstellingRoutes;
