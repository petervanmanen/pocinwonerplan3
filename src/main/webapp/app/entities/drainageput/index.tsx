import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Drainageput from './drainageput';
import DrainageputDetail from './drainageput-detail';
import DrainageputUpdate from './drainageput-update';
import DrainageputDeleteDialog from './drainageput-delete-dialog';

const DrainageputRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Drainageput />} />
    <Route path="new" element={<DrainageputUpdate />} />
    <Route path=":id">
      <Route index element={<DrainageputDetail />} />
      <Route path="edit" element={<DrainageputUpdate />} />
      <Route path="delete" element={<DrainageputDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DrainageputRoutes;
