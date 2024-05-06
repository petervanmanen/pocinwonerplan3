import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Norm from './norm';
import NormDetail from './norm-detail';
import NormUpdate from './norm-update';
import NormDeleteDialog from './norm-delete-dialog';

const NormRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Norm />} />
    <Route path="new" element={<NormUpdate />} />
    <Route path=":id">
      <Route index element={<NormDetail />} />
      <Route path="edit" element={<NormUpdate />} />
      <Route path="delete" element={<NormDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NormRoutes;
