import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Bredeintake from './bredeintake';
import BredeintakeDetail from './bredeintake-detail';
import BredeintakeUpdate from './bredeintake-update';
import BredeintakeDeleteDialog from './bredeintake-delete-dialog';

const BredeintakeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Bredeintake />} />
    <Route path="new" element={<BredeintakeUpdate />} />
    <Route path=":id">
      <Route index element={<BredeintakeDetail />} />
      <Route path="edit" element={<BredeintakeUpdate />} />
      <Route path="delete" element={<BredeintakeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BredeintakeRoutes;
