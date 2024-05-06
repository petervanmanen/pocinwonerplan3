import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Voorziening from './voorziening';
import VoorzieningDetail from './voorziening-detail';
import VoorzieningUpdate from './voorziening-update';
import VoorzieningDeleteDialog from './voorziening-delete-dialog';

const VoorzieningRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Voorziening />} />
    <Route path="new" element={<VoorzieningUpdate />} />
    <Route path=":id">
      <Route index element={<VoorzieningDetail />} />
      <Route path="edit" element={<VoorzieningUpdate />} />
      <Route path="delete" element={<VoorzieningDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VoorzieningRoutes;
