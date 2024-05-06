import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Put from './put';
import PutDetail from './put-detail';
import PutUpdate from './put-update';
import PutDeleteDialog from './put-delete-dialog';

const PutRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Put />} />
    <Route path="new" element={<PutUpdate />} />
    <Route path=":id">
      <Route index element={<PutDetail />} />
      <Route path="edit" element={<PutUpdate />} />
      <Route path="delete" element={<PutDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PutRoutes;
