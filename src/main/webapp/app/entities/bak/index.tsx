import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Bak from './bak';
import BakDetail from './bak-detail';
import BakUpdate from './bak-update';
import BakDeleteDialog from './bak-delete-dialog';

const BakRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Bak />} />
    <Route path="new" element={<BakUpdate />} />
    <Route path=":id">
      <Route index element={<BakDetail />} />
      <Route path="edit" element={<BakUpdate />} />
      <Route path="delete" element={<BakDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BakRoutes;
