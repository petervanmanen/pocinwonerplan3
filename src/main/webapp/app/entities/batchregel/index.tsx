import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Batchregel from './batchregel';
import BatchregelDetail from './batchregel-detail';
import BatchregelUpdate from './batchregel-update';
import BatchregelDeleteDialog from './batchregel-delete-dialog';

const BatchregelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Batchregel />} />
    <Route path="new" element={<BatchregelUpdate />} />
    <Route path=":id">
      <Route index element={<BatchregelDetail />} />
      <Route path="edit" element={<BatchregelUpdate />} />
      <Route path="delete" element={<BatchregelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BatchregelRoutes;
