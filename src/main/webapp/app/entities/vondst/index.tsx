import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vondst from './vondst';
import VondstDetail from './vondst-detail';
import VondstUpdate from './vondst-update';
import VondstDeleteDialog from './vondst-delete-dialog';

const VondstRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vondst />} />
    <Route path="new" element={<VondstUpdate />} />
    <Route path=":id">
      <Route index element={<VondstDetail />} />
      <Route path="edit" element={<VondstUpdate />} />
      <Route path="delete" element={<VondstDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VondstRoutes;
