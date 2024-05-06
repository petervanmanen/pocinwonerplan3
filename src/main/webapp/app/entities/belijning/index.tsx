import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Belijning from './belijning';
import BelijningDetail from './belijning-detail';
import BelijningUpdate from './belijning-update';
import BelijningDeleteDialog from './belijning-delete-dialog';

const BelijningRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Belijning />} />
    <Route path="new" element={<BelijningUpdate />} />
    <Route path=":id">
      <Route index element={<BelijningDetail />} />
      <Route path="edit" element={<BelijningUpdate />} />
      <Route path="delete" element={<BelijningDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BelijningRoutes;
