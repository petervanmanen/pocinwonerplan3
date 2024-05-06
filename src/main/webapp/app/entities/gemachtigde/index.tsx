import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Gemachtigde from './gemachtigde';
import GemachtigdeDetail from './gemachtigde-detail';
import GemachtigdeUpdate from './gemachtigde-update';
import GemachtigdeDeleteDialog from './gemachtigde-delete-dialog';

const GemachtigdeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Gemachtigde />} />
    <Route path="new" element={<GemachtigdeUpdate />} />
    <Route path=":id">
      <Route index element={<GemachtigdeDetail />} />
      <Route path="edit" element={<GemachtigdeUpdate />} />
      <Route path="delete" element={<GemachtigdeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GemachtigdeRoutes;
