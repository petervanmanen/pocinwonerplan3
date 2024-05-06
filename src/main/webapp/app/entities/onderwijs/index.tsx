import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Onderwijs from './onderwijs';
import OnderwijsDetail from './onderwijs-detail';
import OnderwijsUpdate from './onderwijs-update';
import OnderwijsDeleteDialog from './onderwijs-delete-dialog';

const OnderwijsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Onderwijs />} />
    <Route path="new" element={<OnderwijsUpdate />} />
    <Route path=":id">
      <Route index element={<OnderwijsDetail />} />
      <Route path="edit" element={<OnderwijsUpdate />} />
      <Route path="delete" element={<OnderwijsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OnderwijsRoutes;
