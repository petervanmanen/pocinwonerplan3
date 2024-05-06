import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Knm from './knm';
import KnmDetail from './knm-detail';
import KnmUpdate from './knm-update';
import KnmDeleteDialog from './knm-delete-dialog';

const KnmRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Knm />} />
    <Route path="new" element={<KnmUpdate />} />
    <Route path=":id">
      <Route index element={<KnmDetail />} />
      <Route path="edit" element={<KnmUpdate />} />
      <Route path="delete" element={<KnmDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KnmRoutes;
