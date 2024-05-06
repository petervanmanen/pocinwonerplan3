import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Voorzieningsoort from './voorzieningsoort';
import VoorzieningsoortDetail from './voorzieningsoort-detail';
import VoorzieningsoortUpdate from './voorzieningsoort-update';
import VoorzieningsoortDeleteDialog from './voorzieningsoort-delete-dialog';

const VoorzieningsoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Voorzieningsoort />} />
    <Route path="new" element={<VoorzieningsoortUpdate />} />
    <Route path=":id">
      <Route index element={<VoorzieningsoortDetail />} />
      <Route path="edit" element={<VoorzieningsoortUpdate />} />
      <Route path="delete" element={<VoorzieningsoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VoorzieningsoortRoutes;
