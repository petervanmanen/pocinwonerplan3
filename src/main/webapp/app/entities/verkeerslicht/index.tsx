import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verkeerslicht from './verkeerslicht';
import VerkeerslichtDetail from './verkeerslicht-detail';
import VerkeerslichtUpdate from './verkeerslicht-update';
import VerkeerslichtDeleteDialog from './verkeerslicht-delete-dialog';

const VerkeerslichtRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verkeerslicht />} />
    <Route path="new" element={<VerkeerslichtUpdate />} />
    <Route path=":id">
      <Route index element={<VerkeerslichtDetail />} />
      <Route path="edit" element={<VerkeerslichtUpdate />} />
      <Route path="delete" element={<VerkeerslichtDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerkeerslichtRoutes;
