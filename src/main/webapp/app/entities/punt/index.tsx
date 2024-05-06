import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Punt from './punt';
import PuntDetail from './punt-detail';
import PuntUpdate from './punt-update';
import PuntDeleteDialog from './punt-delete-dialog';

const PuntRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Punt />} />
    <Route path="new" element={<PuntUpdate />} />
    <Route path=":id">
      <Route index element={<PuntDetail />} />
      <Route path="edit" element={<PuntUpdate />} />
      <Route path="delete" element={<PuntDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PuntRoutes;
