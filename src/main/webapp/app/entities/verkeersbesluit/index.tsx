import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verkeersbesluit from './verkeersbesluit';
import VerkeersbesluitDetail from './verkeersbesluit-detail';
import VerkeersbesluitUpdate from './verkeersbesluit-update';
import VerkeersbesluitDeleteDialog from './verkeersbesluit-delete-dialog';

const VerkeersbesluitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verkeersbesluit />} />
    <Route path="new" element={<VerkeersbesluitUpdate />} />
    <Route path=":id">
      <Route index element={<VerkeersbesluitDetail />} />
      <Route path="edit" element={<VerkeersbesluitUpdate />} />
      <Route path="delete" element={<VerkeersbesluitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerkeersbesluitRoutes;
