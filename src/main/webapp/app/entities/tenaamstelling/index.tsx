import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Tenaamstelling from './tenaamstelling';
import TenaamstellingDetail from './tenaamstelling-detail';
import TenaamstellingUpdate from './tenaamstelling-update';
import TenaamstellingDeleteDialog from './tenaamstelling-delete-dialog';

const TenaamstellingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Tenaamstelling />} />
    <Route path="new" element={<TenaamstellingUpdate />} />
    <Route path=":id">
      <Route index element={<TenaamstellingDetail />} />
      <Route path="edit" element={<TenaamstellingUpdate />} />
      <Route path="delete" element={<TenaamstellingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TenaamstellingRoutes;
