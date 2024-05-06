import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Leverancier from './leverancier';
import LeverancierDetail from './leverancier-detail';
import LeverancierUpdate from './leverancier-update';
import LeverancierDeleteDialog from './leverancier-delete-dialog';

const LeverancierRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Leverancier />} />
    <Route path="new" element={<LeverancierUpdate />} />
    <Route path=":id">
      <Route index element={<LeverancierDetail />} />
      <Route path="edit" element={<LeverancierUpdate />} />
      <Route path="delete" element={<LeverancierDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeverancierRoutes;
