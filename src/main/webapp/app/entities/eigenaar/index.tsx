import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Eigenaar from './eigenaar';
import EigenaarDetail from './eigenaar-detail';
import EigenaarUpdate from './eigenaar-update';
import EigenaarDeleteDialog from './eigenaar-delete-dialog';

const EigenaarRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Eigenaar />} />
    <Route path="new" element={<EigenaarUpdate />} />
    <Route path=":id">
      <Route index element={<EigenaarDetail />} />
      <Route path="edit" element={<EigenaarUpdate />} />
      <Route path="delete" element={<EigenaarDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EigenaarRoutes;
