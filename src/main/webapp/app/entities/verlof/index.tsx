import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verlof from './verlof';
import VerlofDetail from './verlof-detail';
import VerlofUpdate from './verlof-update';
import VerlofDeleteDialog from './verlof-delete-dialog';

const VerlofRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verlof />} />
    <Route path="new" element={<VerlofUpdate />} />
    <Route path=":id">
      <Route index element={<VerlofDetail />} />
      <Route path="edit" element={<VerlofUpdate />} />
      <Route path="delete" element={<VerlofDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerlofRoutes;
