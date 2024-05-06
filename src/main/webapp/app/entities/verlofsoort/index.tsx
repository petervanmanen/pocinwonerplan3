import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verlofsoort from './verlofsoort';
import VerlofsoortDetail from './verlofsoort-detail';
import VerlofsoortUpdate from './verlofsoort-update';
import VerlofsoortDeleteDialog from './verlofsoort-delete-dialog';

const VerlofsoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verlofsoort />} />
    <Route path="new" element={<VerlofsoortUpdate />} />
    <Route path=":id">
      <Route index element={<VerlofsoortDetail />} />
      <Route path="edit" element={<VerlofsoortUpdate />} />
      <Route path="delete" element={<VerlofsoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerlofsoortRoutes;
