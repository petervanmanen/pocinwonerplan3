import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Storing from './storing';
import StoringDetail from './storing-detail';
import StoringUpdate from './storing-update';
import StoringDeleteDialog from './storing-delete-dialog';

const StoringRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Storing />} />
    <Route path="new" element={<StoringUpdate />} />
    <Route path=":id">
      <Route index element={<StoringDetail />} />
      <Route path="edit" element={<StoringUpdate />} />
      <Route path="delete" element={<StoringDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StoringRoutes;
