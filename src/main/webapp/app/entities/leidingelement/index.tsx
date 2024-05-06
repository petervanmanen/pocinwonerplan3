import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Leidingelement from './leidingelement';
import LeidingelementDetail from './leidingelement-detail';
import LeidingelementUpdate from './leidingelement-update';
import LeidingelementDeleteDialog from './leidingelement-delete-dialog';

const LeidingelementRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Leidingelement />} />
    <Route path="new" element={<LeidingelementUpdate />} />
    <Route path=":id">
      <Route index element={<LeidingelementDetail />} />
      <Route path="edit" element={<LeidingelementUpdate />} />
      <Route path="delete" element={<LeidingelementDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeidingelementRoutes;
