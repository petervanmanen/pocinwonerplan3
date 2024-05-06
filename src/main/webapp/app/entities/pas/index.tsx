import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Pas from './pas';
import PasDetail from './pas-detail';
import PasUpdate from './pas-update';
import PasDeleteDialog from './pas-delete-dialog';

const PasRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Pas />} />
    <Route path="new" element={<PasUpdate />} />
    <Route path=":id">
      <Route index element={<PasDetail />} />
      <Route path="edit" element={<PasUpdate />} />
      <Route path="delete" element={<PasDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PasRoutes;
