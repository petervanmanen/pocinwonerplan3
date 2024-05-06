import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Leiding from './leiding';
import LeidingDetail from './leiding-detail';
import LeidingUpdate from './leiding-update';
import LeidingDeleteDialog from './leiding-delete-dialog';

const LeidingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Leiding />} />
    <Route path="new" element={<LeidingUpdate />} />
    <Route path=":id">
      <Route index element={<LeidingDetail />} />
      <Route path="edit" element={<LeidingUpdate />} />
      <Route path="delete" element={<LeidingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeidingRoutes;
