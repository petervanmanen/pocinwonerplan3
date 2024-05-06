import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verzuimsoort from './verzuimsoort';
import VerzuimsoortDetail from './verzuimsoort-detail';
import VerzuimsoortUpdate from './verzuimsoort-update';
import VerzuimsoortDeleteDialog from './verzuimsoort-delete-dialog';

const VerzuimsoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verzuimsoort />} />
    <Route path="new" element={<VerzuimsoortUpdate />} />
    <Route path=":id">
      <Route index element={<VerzuimsoortDetail />} />
      <Route path="edit" element={<VerzuimsoortUpdate />} />
      <Route path="delete" element={<VerzuimsoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerzuimsoortRoutes;
